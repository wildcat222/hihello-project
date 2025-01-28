from sqlalchemy import Column, BigInteger, String
from app.db_connection.rds_connection import Base
from sqlalchemy.orm import relationship

class ChatbotCategory(Base):
    __tablename__ = "chatbot_category"

    chatbot_category_seq = Column(BigInteger, primary_key=True, autoincrement=True, index=True)
    chatbot_category_content = Column(String(255), nullable=False, unique=True)

    # Relationship with Chatbot
    chatbots = relationship("Chatbot", back_populates="category", cascade="all, delete-orphan")