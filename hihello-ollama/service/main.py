import os
from langchain.schema import Document
import streamlit as st
import fitz
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
LANGSERVE_ENDPOINT = "http://localhost:8001/chat/c/N4XyA/"

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

RAG_PROMPT_TEMPLATE = """

You are hihelloAI, a helpful assistant. Respond concisely and accurately to the user's question in 2 lines or less.

- If the user's input explicitly matches greetings like "안녕", "안녕하세요", "ㅎㅇ", "하이", "헬로우", "hi", or similar, respond with:
  "안녕하세요! 무엇을 도와드릴까요? 😊."

- For questions about introducing yourself like "너에 대해 설명해줘", "너에 대해 말해봐", "넌 누구야", "너에 대해 말해", "너에 대해 말해줘", "너에 대해 설명해", "너에 대해 설명해봐", or similar, respond with:
  "저는 hihelloAI입니다. Ollama, Llama3, Pinecone 으로 구현된 자체 모델을 바탕으로 RAG 시스템을 이용하여 커스터마이징 된 답변을 출력할 수 있습니다. 저의 지식을 바탕으로 여러분의 인턴 생활을 최대한 도와드릴게요!"

- If the user's question is unrelated to the retrieved context or if the answer is not found in the context, respond with:
  "죄송합니다. 해당 내용은 학습되지 않았습니다. 다른 질문을 해주세요!"

- If you don't know the answer, respond with:
  "죄송합니다. 해당 내용은 학습되지 않았습니다. 다른 질문을 해주세요!"

Do not include any additional context, references, or links in your answer unless explicitly asked by the user. 

Question: {question}
Context: {context}
Answer:
"""

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
def embed_file(uploaded_file):
    # 파일 내용을 메모리에서 바로 처리
    file_content = uploaded_file.read()

    # 파일 타입에 따라 분기 처리 (예: PDF, TXT 등)
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

    # 텍스트 분할기 설정
    text_splitter = RecursiveCharacterTextSplitter(
        chunk_size=500,
        chunk_overlap=50,
        separators=["\n\n", "\n", "(?<=\. )", " ", ""],
        length_function=len,
    )

    # 텍스트 분할 (Document 객체로 변환)
    split_docs = [Document(page_content=text) for text in split_docs]
    split_docs = text_splitter.split_documents(split_docs)

    # 파일의 이름을 메타데이터로 설정
    filename = uploaded_file.name
    for doc in split_docs:
        doc.metadata = {"source": filename}  # 메타데이터를 각 Document 객체에 추가

    # 전처리 및 메타데이터 추가
    contents, metadatas = preprocess_documents(
        split_docs=split_docs,
        metadata_keys=["source"],
        min_length=5,
        use_basename=True,  # 이 값이 True일 경우 basename 사용
    )

    # Pinecone 클라이언트 초기화
    pc = Pinecone(api_key=PINECONE_API_KEY, embedding=embedding)
    index = pinecone.Index(index_name = INDEX_NAME, api_key = PINECONE_API_KEY, host=host)
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

    # Sparse encoder 생성
    sparse_encoder = create_sparse_encoder(stopwords(), mode="kiwi")

    # Sparse encoder 훈련 및 저장
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


# # Streamlit UI를 위한 파일 업로드 처리
# uploaded_file = st.file_uploader("파일 업로드", type=["txt", "pdf", "docx", "csv"])
#
# if uploaded_file is not None:
#     retriever = embed_file(uploaded_file)
#     st.write("파일 임베딩 완료되었습니다.")

print_history()

if user_input := st.chat_input():
    add_history("user", user_input)
    st.chat_message("user").write(user_input)

    with st.chat_message("assistant"):
        ollama = RemoteRunnable(LANGSERVE_ENDPOINT)
        with st.spinner("답변을 생각하는 중입니다...."):

            chat_container = st.empty()

            user_vector = embedding.embed_query(user_input)

            # 검색용 리트리버(PineconeKiwiHybridRertriever 초기화)
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
                top_k=5,  # Top-K 문서 반환 개수
                alpha=0.75,  # alpha=0.75로 설정한 경우, (0.75: Dense Embedding, 0.25: Sparse Embedding)
            )

            # 파인콘 검색기 생성
            pinecone_retriever = PineconeKiwiHybridRetriever(**pinecone_params)

            # 실행 결과
            print(user_input)
            search_results = pinecone_retriever.invoke(user_input, search_kwargs={"alpha": 1, "k": 3})

            if search_results is None or all(result.metadata is None for result in search_results):
                # DB에서 결과가 없으면 기본 응답 처리
                if user_input.lower() in ["안녕", "넌 누구야", "어떻게 지내", "hi"]:
                    response = "안녕하세요! 여러분의 인턴생활에 도움을 드릴 HiHello AI입니다. 무엇을 도와드릴까요? 😊"
                    chat_container.markdown(response)
                    add_history("ai", response)
                else:
                    # DB 결과가 없으면 일반적인 대화형 답변으로 처리
                    response = "DB에서 관련된 정보를 찾을 수 없어요. 그렇지만 저는 항상 대화할 준비가 되어 있어요!"
                    chat_container.markdown(response)
                    add_history("ai", response)
            else:
                # DB에서 관련 결과가 있을 경우
                for result in search_results:
                    print(result.page_content)
                    print(result.metadata)
                    print("\n====================\n")

                # 파인콘 검색 결과를 바탕으로한 챗봇 출력
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

