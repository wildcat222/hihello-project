import os
import streamlit as st
import pinecone
from pinecone import Pinecone
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import StrOutputParser
from langchain_core.runnables import RunnablePassthrough
from langchain_teddynote.korean import stopwords
from langchain_core.messages import ChatMessage
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_teddynote.community.pinecone import(
    create_sparse_encoder,
    preprocess_documents,
    create_index,
    init_pinecone_index,
    fit_sparse_encoder
)
from langchain_teddynote.community.pinecone import PineconeKiwiHybridRetriever
from langchain_teddynote.community.pinecone import upsert_documents
from langchain_community.document_loaders.unstructured import UnstructuredFileLoader
from langchain.vectorstores import Pinecone as LangchainPinecone
from langserve import RemoteRunnable
from langchain_upstage import UpstageEmbeddings
from dotenv import load_dotenv

# API 키 로드
load_dotenv()

# Pinecone 설정
PINECONE_API_KEY = os.environ["PINECONE_API_KEY"]
PINECONE_ENV = os.environ["PINECONE_ENV"]

# ⭐️ LangServe 모델 설정(EndPoint)
LANGSERVE_ENDPOINT = "http://localhost:8000/chat/c/N4XyA"

UPSTAGE_API_KEY= os.environ["UPSTAGE_API_KEY"]

# Pinecone 클라이언트 초기화
embedding = UpstageEmbeddings(
    model="solar-embedding-1-large-passage",
    upstage_api_key=os.getenv("UPSTAGE_API_KEY")
)

host = os.getenv("PINECONE_HOST")

pc = Pinecone(api_key=PINECONE_API_KEY, embedding=embedding)

# 인덱스 이름 설정
INDEX_NAME = "hihello-db-index"

# Pinecone Index 객체로 연결
index = pinecone.Index(index_name = INDEX_NAME, api_key = PINECONE_API_KEY, host=host)
print(f"Index '{INDEX_NAME}' is ready to use.")

# 프롬프트를 자유롭게 수정해 보세요!
RAG_PROMPT_TEMPLATE = """당신은 질문에 친절하게 답변하는 hihelloAI 입니다. 검색된 다음 문맥을 사용하여 질문에 답하세요. 답을 모른다면 모른다고 답변하세요.
Question: {question} 
Context: {context} 
Answer:"""

st.set_page_config(page_title="HiHello ChatBot", page_icon="💬")
st.title("HiHello ChatBot")

if "messages" not in st.session_state:
    st.session_state["messages"] = [
        ChatMessage(role="assistant", content="반가워요 HiHello AI 입니다! 무엇을 도와드릴까요?")
    ]

def print_history():
    for msg in st.session_state.messages:
        st.chat_message(msg.role).write(msg.content)


def add_history(role, content):
    st.session_state.messages.append(ChatMessage(role=role, content=content))


def format_docs(docs):
    return "\n\n".join(doc.page_content for doc in docs)

@st.cache_resource(show_spinner="Embedding file...")
def embed_file(file):
    file_content = file.read()
    file_path = f"./.cache/files/{file.name}"
    with open(file_path, "wb") as f:
        f.write(file_content)
    split_docs = []
    text_splitter = RecursiveCharacterTextSplitter(
        chunk_size=500,
        chunk_overlap=50,
        separators=["\n\n", "\n", "(?<=\. )", " ", ""],
        length_function=len,
    )
    loader = UnstructuredFileLoader(file_path)
    split_docs.extend(loader.load_and_split(text_splitter=text_splitter))

    contents, metadatas = preprocess_documents(
        split_docs=split_docs,
        metadata_keys=["source"],
        min_length=5,
        use_basename=True,
    )

    # 파인콘 인덱스 생성
    if INDEX_NAME not in pc.list_indexes().names():
        print("인덱스 2차")
        pc_index = create_index(
            api_key=os.environ["PINECONE_API_KEY"],
            index_name="hihello-db-index",
            dimension=4096,  # 임베딩은 UpstageEmbeddings 써서 4096 차원으로 했음
            metric="dotproduct",  # 하이브리드 서치 알고리즘 쓰게 될 경우를 대비하여 Dotproduct로 지정
        )

    sparse_encoder = create_sparse_encoder(stopwords(), mode="kiwi")

    saved_path = fit_sparse_encoder(
        sparse_encoder=sparse_encoder, contents=contents, save_path="./sparse_encoder.pkl"
    )

    upsert_documents(
        index=index,  # Pinecone 인덱스
        namespace="hihello-namespace-01",  # Pinecone namespace
        contents=contents,  # 이전에 전처리한 문서 내용
        metadatas=metadatas,  # 이전에 전처리한 문서 메타데이터
        sparse_encoder=sparse_encoder,  # Sparse encoder
        embedder=embedding,
        batch_size=32,
    )

    index.describe_index_stats()

    # LangchainPinecone으로 문서를 Pinecone에 업로드
    # LangchainPinecone.from_documents(docs, embedding=embedding, index_name=INDEX_NAME)

    retriever = LangchainPinecone(index=index, text_key= "content", embedding=embedding).as_retriever()
    return retriever


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
        ollama = RemoteRunnable(LANGSERVE_ENDPOINT)
        with st.spinner("답변을 생각하는 중입니다...."):

            chat_container = st.empty()

            user_vector = embedding.embed_query(user_input)

            # 검색용 인덱스 초기화
            pinecone_params = init_pinecone_index(
                index_name="hihello-db-index",  # Pinecone 인덱스 이름
                namespace="hihello-namespace-01",  # Pinecone Namespace
                api_key=os.environ["PINECONE_API_KEY"],  # Pinecone API Key
                sparse_encoder_path="./sparse_encoder.pkl",  # Sparse Encoder 저장경로(save_path)
                stopwords=stopwords(),  # 불용어 사전
                tokenizer="kiwi",
                embeddings=UpstageEmbeddings(
                    model="solar-embedding-1-large-query"
                ),  # Dense Embedder
                top_k=1,  # Top-K 문서 반환 개수
                alpha=0.5,  # alpha=0.75로 설정한 경우, (0.75: Dense Embedding, 0.25: Sparse Embedding)
            )

            # 파인콘 검색기 생성
            pinecone_retriever = PineconeKiwiHybridRetriever(**pinecone_params)

            # 실행 결과
            print(user_input)
            search_results = pinecone_retriever.invoke(user_input, search_kwargs={"alpha": 1, "k" : 1})
            for result in search_results:
                print(result.page_content)
                print(result.metadata)
                print("\n====================\n")

            # 파인콘 검색 결과를 바탕으로한 챗봇 출력
            if search_results is not None:
                prompt = ChatPromptTemplate.from_template(RAG_PROMPT_TEMPLATE)
                context = format_docs(search_results)
                # formatted_docs를 문자열에서 Runnable로 변환
                rag_chain = (
                        {
                            "context": RunnablePassthrough() | (lambda _: context),
                            "question": RunnablePassthrough(),
                        }
                        | prompt
                        | ollama
                        | StrOutputParser()
                )
                answer = rag_chain.stream(user_input)
                chunks = []
                for chunk in answer:
                    chunks.append(chunk)
                    chat_container.markdown("".join(chunks))
                add_history("ai", "".join(chunks))
            else:
                print("else")
                # 검색 결과가 없을 경우 대답
                prompt = ChatPromptTemplate.from_template(
                    "다음의 질문에 간결하게 답변해 주세요:\n{input}"
                )
                chain = prompt | ollama | StrOutputParser()
                answer = chain.stream(user_input)
                print(3)
                chunks = []
                print(41)
                for chunk in answer:
                    print(5)
                    chunks.append(chunk)
                    print(6)
                    chat_container.markdown("".join(chunks))
                print(7)
                add_history("ai", "".join(chunks))
                print("finished")