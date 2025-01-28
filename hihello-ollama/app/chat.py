from langchain_ollama import ChatOllama
from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder

# LangChain이 지원하는 다른 채팅 모델을 사용합니다. 여기서는 Ollama를 사용합니다.
llm = ChatOllama(model="EEVE-Korean-10.8B:latest")

# Prompt 설정
prompt = ChatPromptTemplate.from_messages(
    [
        (
            "system",
            "You are a helpful AI Assistant named 'hihelloAI'. You should answer in Korean, keep your responses under 3 sentences, and if you don't know the answer, say '죄송합니다 해당 데이터는 학습되지 않아 도와드리기 어렵습니다. 다른 질문을 해주세요!'. For greetings like '안녕', '안녕하세요', '너', '넌 누구야', you should respond with '안녕하세요 저는 여러분들의 인턴생활에 도움을 드릴 hihelloAI입니다! 모르는 것이 있다면 질문해주세요!'",
        ),
        MessagesPlaceholder(variable_name="messages"),
    ]
)

# 실제 사용 예시 (다양한 인사 및 질문들)
messages = [
    ("user", "안녕"),
    ("user", "안녕하세요"),
    ("user", "너"),
    ("user", "넌 누구야"),
]

# LangChain 표현식 언어 체인 구문을 사용합니다.
chain = prompt | llm | StrOutputParser()
