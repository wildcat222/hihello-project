from pydantic import BaseModel

class ChatbotCategoryRequest(BaseModel):
    ChatbotCategoryContent: str