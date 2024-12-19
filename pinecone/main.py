import os
import streamlit as st
from langchain.embeddings import CacheBackedEmbeddings
from langchain.storage import LocalFileStore
from langchain_community.embeddings.huggingface import HuggingFaceEmbeddings
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import StrOutputParser
from langchain_core.runnables import RunnablePassthrough
from langchain_core.messages import ChatMessage
from langchain_teddynote.korean import stopwords
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_community.document_loaders.unstructured import UnstructuredFileLoader
from langchain_community.document_loaders import PyMuPDFLoader
from langchain_community.vectorstores.faiss import FAISS
from langchain_teddynote.community.pinecone import create_index
from langserve import RemoteRunnable
from dotenv import load_dotenv

import glob
from langchain_teddynote.community.pinecone import(
    create_sparse_encoder,
    fit_sparse_encoder,
    preprocess_documents
)
from langchain_teddynote import logging

# ⭐️ Embedding 설정
USE_BGE_EMBEDDING = True

# ⭐️ LangServe 모델 설정(EndPoint)
LANGSERVE_ENDPOINT = "http://localhost:8000/chat/c/N4XyA"

# 필수 디렉토리 생성 @Mineru
if not os.path.exists(".cache"):
    os.mkdir(".cache")
if not os.path.exists(".cache/embeddings"):
    os.mkdir(".cache/embeddings")
if not os.path.exists(".cache/files"):
    os.mkdir(".cache/files")

# 프롬프트를 자유롭게 수정해 보세요!
RAG_PROMPT_TEMPLATE = """당신은 질문에 친절하게 답변하는 hihelloAI 입니다. 검색된 다음 문맥을 사용하여 질문에 답하세요. 답을 모른다면 모른다고 답변하세요.
Question: {question} 
Context: {context} 
Answer:"""

st.set_page_config(page_title="OLLAMA Local 모델 테스트", page_icon="💬")
st.title("OLLAMA Local 모델 테스트")

# API 키 로드
load_dotenv()
# 불용어 처리
stopwords()[:20]

# 텍스트 분할
text_splitter = RecursiveCharacterTextSplitter(chunk_size=300, chunk_overlap=50)

split_docs = []

# 텍스트 파일 Load -> List[Document] 형태로 변환
files = sorted(glob.glob("./data/*.pdf"))
print("files:", files)

for file in files:
    loader = PyMuPDFLoader(file)
    split_docs.extend(loader.load_and_split(text_splitter))

# 문서 개수 확인
print(f"문서 개수 확인 {len(split_docs)}")
print(f"PDF 파일 경로: {files}")
if not files:
    print("No PDF files found in the data folder!")
# 첫번째 data 제목 로깅
print(f"페이지 로깅 {split_docs[0].page_content}")

# 첫번째 data 메타데이터 로깅
print(f"메타데이터 로깅 {split_docs[0].metadata}")

contents, metadatas = preprocess_documents(
    split_docs=split_docs,
    metadata_keys=["source", "page", "author"],
    min_length=5,
    use_basename=True,
)

# VectorStore 에 저장할 문서 확인
print(f"VectorStore 에 저장할 문서 확인 {contents[:5]}")

# VectorStore 에 저장할 metadata 확인
print(f"VectorStore 에 저장할 metadata 확인 {metadatas.keys()}")

# metadata 에서 source 를 확인
print(metadatas["source"][:5])

# 문서 개수 확인, 소스 개수 확인, 페이지 개수 확인
len(contents), len(metadatas["source"]), len(metadatas["page"])
print(len(contents), len(metadatas["source"]), len(metadatas["page"]))

# 파인콘 인덱스 생성
pc_index = create_index(
    api_key=os.environ["PINECONE_API_KEY"],
    index_name="hihello-db-index",
    dimension=4096, # 임베딩은 UpstageEmbeddings 써서 4096 차원으로 했음
    metric="dotproduct", #하이브리드 서치 알고리즘 쓰게 될 경우를 대비하여 Dotproduct로 지정
)

sparse_encoder = create_sparse_encoder(stopwords(), mode="kiwi")

saved_path = fit_sparse_encoder(
    sparse_encoder=sparse_encoder, contents=contents, save_path="./sparse_encoder.pkl"
)


from langchain_upstage import UpstageEmbeddings

upstage_embeddings = UpstageEmbeddings(model="solar-embedding-1-large-passage", upstage_api_key=os.getenv("UPSTAGE_API_KEY"))

from langchain_teddynote.community.pinecone import upsert_documents

upsert_documents(
    index=pc_index,  # Pinecone 인덱스
    namespace="hihello-namespace-01",  # Pinecone namespace
    contents=contents,  # 이전에 전처리한 문서 내용
    metadatas=metadatas,  # 이전에 전처리한 문서 메타데이터
    sparse_encoder=sparse_encoder,  # Sparse encoder
    embedder=upstage_embeddings,
    batch_size=32,
)

pc_index.describe_index_stats()

if "messages" not in st.session_state:
    st.session_state["messages"] = [
        ChatMessage(role="assistant", content="무엇을 도와드릴까요?")
    ]


def print_history():
    for msg in st.session_state.messages:
        st.chat_message(msg.role).write(msg.content)


def add_history(role, content):
    st.session_state.messages.append(ChatMessage(role=role, content=content))


def format_docs(docs):
    # 검색한 문서 결과를 하나의 문단으로 합쳐줍니다.
    return "\n\n".join(doc.page_content for doc in docs)

# 임베딩
@st.cache_resource(show_spinner="Embedding file...")
def embed_file(file):
    file_content = file.read()
    file_path = f"./.cache/files/{file.name}"
    with open(file_path, "wb") as f:
        f.write(file_content)

    cache_dir = LocalFileStore(f"./.cache/embeddings/{file.name}")

    text_splitter = RecursiveCharacterTextSplitter(
        chunk_size=500,
        chunk_overlap=50,
        separators=["\n\n", "\n", "(?<=\. )", " ", ""],
        length_function=len,
    )
    loader = UnstructuredFileLoader(file_path)
    docs = loader.load_and_split(text_splitter=text_splitter)

    if USE_BGE_EMBEDDING:
        # BGE Embedding: @Mineru
        model_name = "BAAI/bge-m3"
        # GPU Device 설정:
        # - NVidia GPU: "cuda"
        # - Mac M1, M2, M3: "mps"
        # - CPU: "cpu"
        model_kwargs = {
            # "device": "cuda"
            "device": "mps"
            # "device": "cpu"
        }
        encode_kwargs = {"normalize_embeddings": True}
        embeddings = HuggingFaceEmbeddings(
            model_name=model_name,
            model_kwargs=model_kwargs,
            encode_kwargs=encode_kwargs,
        )

    cached_embeddings = CacheBackedEmbeddings.from_bytes_store(embeddings, cache_dir)
    vectorstore = FAISS.from_documents(docs, embedding=cached_embeddings)
    retriever = vectorstore.as_retriever()
    return retriever


def format_docs(docs):
    # 검색한 문서 결과를 하나의 문단으로 합쳐줍니다.
    return "\n\n".join(doc.page_content for doc in docs)


with st.sidebar:
    file = st.file_uploader(
        "파일 업로드",
        type=["pdf", "txt", "docx"],
    )

if file:
    retriever = embed_file(file)

print_history()


if user_input := st.chat_input():
    add_history("user", user_input)
    st.chat_message("user").write(user_input)
    with st.chat_message("assistant"):
        #주소 설정
        ollama = RemoteRunnable(LANGSERVE_ENDPOINT)

        chat_container = st.empty()
        if file is not None:
            prompt = ChatPromptTemplate.from_template(RAG_PROMPT_TEMPLATE)

            # 체인을 생성합니다.
            rag_chain = (
                {
                    "context": retriever | format_docs,
                    "question": RunnablePassthrough(),
                }
                | prompt
                | ollama
                | StrOutputParser()
            )
            # 문서에 대한 질의를 입력하고, 답변을 출력합니다.
            answer = rag_chain.stream(user_input)  # 문서에 대한 질의
            chunks = []
            for chunk in answer:
                chunks.append(chunk)
                chat_container.markdown("".join(chunks))
            add_history("ai", "".join(chunks))
        else:
            prompt = ChatPromptTemplate.from_template(
                "다음의 질문에 간결하게 답변해 주세요:\n{input}"
            )

            # 체인을 생성합니다.
            chain = prompt | ollama | StrOutputParser()

            answer = chain.stream(user_input)  # 문서에 대한 질의
            chunks = []
            for chunk in answer:
                chunks.append(chunk)
                chat_container.markdown("".join(chunks))
            add_history("ai", "".join(chunks))