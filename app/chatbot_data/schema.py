from pydantic import BaseModel, Field

class ChatbotAddRequest(BaseModel):
    chatbotData: str = Field(..., max_length=200)  # 데이터 길이를 최대 200자로 제한

class ChatbotUpdateRequest(BaseModel):
    chatbotData: str = Field(..., max_length=200)