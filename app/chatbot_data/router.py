from fastapi import APIRouter, Depends, HTTPException, UploadFile, File
from sqlalchemy.orm import Session
from db_connection.rds_connection import get_db
from chatbot_data.model import Chatbot
from chatbot_category.model import ChatbotCategory
from chatbot_data.schema import ChatbotAddRequest
from chatbot_data.schema import ChatbotUpdateRequest
from chatbot_data.schema import PineconeAddRequest
from sentence_transformers import SentenceTransformer
from langchain_teddynote.korean import stopwords
from langchain_community.document_loaders import PyMuPDFLoader
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_teddynote.community.pinecone import (
    create_index,
    create_sparse_encoder,
    fit_sparse_encoder,
    upsert_documents,
    preprocess_documents
)
from langchain_teddynote.korean import stopwords
from langchain.schema import Document
from pydantic import BaseModel
from pinecone import Pinecone, ServerlessSpec,PodSpec
from dotenv import load_dotenv
import fitz
import glob
import uuid
import numpy as np
import os
import pinecone

load_dotenv()
PINECONE_API_KEY = os.environ["PINECONE_API_KEY"]
PINECONE_ENV = os.environ["PINECONE_ENV"]
UPSTAGE_API_KEY= os.environ["UPSTAGE_API_KEY"]
host = os.getenv("PINECONE_HOST")

# Pinecone 객체 생성
pinecone_instance = Pinecone(
    api_key=PINECONE_API_KEY,
    environment=PINECONE_ENV
)

embedding = UpstageEmbeddings(
    model="solar-embedding-1-large-passage",
    upstage_api_key=os.getenv("UPSTAGE_API_KEY")
)

# 인덱스 이름 설정
INDEX_NAME = "hihello-db-index"

if INDEX_NAME not in pinecone_instance.list_indexes().names():
    pinecone_instance.create_index(
        name=INDEX_NAME,
        dimension=4096,
        metric=dotproduct,
        spec=ServerlessSpec(
            cloud=aws,
            region=us-east-1
        )
    )
else:
    print(f"인덱스 '{INDEX_NAME}'가 이미 존재합니다.")

router = APIRouter(
    prefix="/api/v1/hr/chatbot",
    tags=["Chatbot"]
)

text_splitter = RecursiveCharacterTextSplitter(chunk_size=500, chunk_overlap=100)

split_docs = []

# 텍스트 -> list[document]
files = sorted(glob.glob("data/*.pdf"))

for file in files:
    loader = PyMuPDFLoader(file)
    split_docs.extend(loader.load_and_split(text_splitter))

contents, metadatas = preprocess_documents(
    split_docs=split_docs,
    metadata_keys=["source", "page", "author"],
    min_length=5,
    use_basename=True,
)

@router.post("/pinecone/upsert")
async def add_pinecone_data(uploaded_file: UploadFile = File(...)):
    try:
        # 파일 읽기
        file_content = await uploaded_file.read()

        # 파일 처리
        split_docs = []
        if uploaded_file.type == "application/pdf":
            # PDF 파일을 메모리에서 처리
            doc = fitz.open(stream=file_content, filetype="pdf")

            split_docs = []
            for page in doc:
                text = page.get_text()
                split_docs.append(text)
        else:
            # 텍스트 파일 처리
            split_docs = file_content.decode("utf-8").splitlines()

        # 텍스트 분할
        text_splitter = RecursiveCharacterTextSplitter(
            chunk_size=500,
            chunk_overlap=50,
            separators=["\n\n", "\n", "(?<=\. )", " ", ""],
            length_function=len,
        )

        split_docs = [Document(page_content=text) for text in split_docs]

        for doc in split_docs:
            if not doc.metadata:
                doc.metadata = {}
            # 'source' 키가 None인 경우 기본값을 추가
            doc.metadata["source"] = doc.metadata.get("source", "unknown")

        # 파일의 이름을 메타데이터로 설정
        filename = uploaded_file.name
        for doc in split_docs:
            doc.metadata = {"source": filename}  # 메타데이터를 각 Document 객체에 추가

        # 문서 전처리
        contents, metadatas = preprocess_documents(
            split_docs=split_docs,
            metadata_keys=["source"],
            min_length=5,
            use_basename=True,  # 이 값이 True일 경우 basename 사용
        )

        pc = Pinecone(api_key=PINECONE_API_KEY, embedding=embedding)
        # Pinecone Index 객체로 연결
        index = pinecone.Index(index_name=INDEX_NAME, api_key=PINECONE_API_KEY, host=host)
        print(f"Index '{INDEX_NAME}' is ready to use.")

        # Pinecone 인덱스 목록 확인하기
        if INDEX_NAME not in pc.list_indexes():
            print("인덱스 생성 중...")
            pc_index = create_index(
                api_key=os.environ["PINECONE_API_KEY"],
                index_name=INDEX_NAME,
                dimension=4096,  # 임베딩 차원
                metric="dotproduct",
            )
        else:
            pc_index = index(INDEX_NAME)  # 기존 인덱스 사용

        # Sparse encoder 생성 및 학습
        sparse_encoder = create_sparse_encoder(stopwords(), mode="kiwi")
        saved_path = fit_sparse_encoder(
            sparse_encoder=sparse_encoder, contents=contents, save_path="./sparse_encoder.pkl"
        )
        print("임베딩을 시작")
        # Pinecone에 벡터 업로드
        upsert_documents(
            index=pc_index,  # Pinecone 인덱스
            namespace="hihello-namespace-01",  # Pinecone namespace
            contents=contents,  # 전처리된 문서 내용
            metadatas=metadatas,  # 문서 메타데이터
            sparse_encoder=sparse_encoder,  # Sparse encoder
            embedder=embedding,  # 임베딩 객체
            batch_size=32,
        )

        print("인덱스 상태 확인")
        # Pinecone 인덱스 상태 확인
        pc_index.describe_index_stats()

        return {"message": "데이터가 성공적으로 Pinecone에 업로드되었습니다."}

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"서버 에러: {str(e)}")

@router.post("/category/{categorySeq}/data")
def add_chatbot_data(
        categorySeq: int,
        request_body: ChatbotAddRequest,
        db: Session = Depends(get_db)
):

    category = db.query(ChatbotCategory).filter_by(chatbot_category_seq=categorySeq).first()
    if not category:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot category with ID {categorySeq} not found."
            }
        )

    new_data = Chatbot(
        chatbot_category_seq=categorySeq,
        chatbot_data=request_body.chatbotData
    )
    db.add(new_data)
    db.commit()

    return {"message": "Chatbot data added successfully."}

@router.get("/category/{categorySeq}/data")
def get_chatbot_data_by_category(categorySeq: int, db: Session = Depends(get_db)):
    """
    API to fetch all chatbot data for a specific category.
    """
    category = db.query(ChatbotCategory).filter_by(chatbot_category_seq=categorySeq).first()
    if not category:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot category with ID {categorySeq} not found."
            }
        )

    chatbot_data = db.query(Chatbot).filter_by(chatbot_category_seq=categorySeq).all()
    if not chatbot_data:
        return {"success": True, "data": []}

    return {
        "success": True,
        "data": [
            {
                "id": data.chatbot_seq,
                "content": data.chatbot_data  # 키 이름 변경
            }
            for data in chatbot_data
        ]
    }

# 챗봇 데이터 수정
@router.put("/category/{categorySeq}/data/{chatbotSeq}")
def update_chatbot_data(
        categorySeq: int,
        chatbotSeq: int,
        request_body: ChatbotUpdateRequest,
        db: Session = Depends(get_db)
):
    chatbot_data_content = request_body.chatbotData

    # Validate category existence
    category = db.query(ChatbotCategory).filter_by(chatbot_category_seq=categorySeq).first()
    if not category:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot category with ID {categorySeq} not found."
            }
        )

    # Validate chatbot data existence
    chatbot_data = db.query(Chatbot).filter_by(chatbot_seq=chatbotSeq, chatbot_category_seq=categorySeq).first()
    if not chatbot_data:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot data with ID {chatbotSeq} not found in category {categorySeq}."
            }
        )

    # Update chatbot data
    chatbot_data.chatbot_data = chatbot_data_content  # Update the content
    db.commit()  # Commit the changes to the database

    # Return success message
    return {"message": "Chatbot data updated successfully."}


# 챗봇 데이터 삭제
@router.delete("/category/{categorySeq}/data/{chatbotSeq}")
def delete_chatbot_data(categorySeq: int, chatbotSeq: int, db: Session = Depends(get_db)):

    category = db.query(ChatbotCategory).filter_by(chatbot_category_seq=categorySeq).first()
    if not category:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot category with ID {categorySeq} not found."
            }
        )

    # Validate chatbot data existence
    chatbot_data = db.query(Chatbot).filter_by(chatbot_seq=chatbotSeq, chatbot_category_seq=categorySeq).first()
    if not chatbot_data:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot data with ID {chatbotSeq} not found in category {categorySeq}."
            }
        )

    # Delete chatbot data
    db.delete(chatbot_data)
    db.commit()

    return {"message": "Chatbot data deleted successfully."}