from langchain_ollama import ChatOllama
from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder


# LangChain이 지원하는 ollama 채팅 모델을 사용
llm = ChatOllama(model="Llama-3-Open-Ko-8B-Q4_0:latest")


# Prompt 설정
prompt = ChatPromptTemplate.from_messages(
    [
        (
            "system",
            "You are a helpful AI Assistant. Your name is 'hihelloAI'. You must answer in Korean.",
        ),
        MessagesPlaceholder(variable_name="messages"),
    ]
)

# LangChain 표현식 언어 체인 구문을 사용
chain = prompt | llm | StrOutputParser()
