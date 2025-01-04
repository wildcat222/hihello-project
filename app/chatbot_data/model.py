from sqlalchemy import Column, BigInteger, String, ForeignKey
from sqlalchemy.orm import relationship
from app.db_connection.rds_connection import Base

class Chatbot(Base):
    __tablename__ = "chatbot"

    chatbot_seq = Column(BigInteger, primary_key=True, autoincrement=True, index=True)
    chatbot_category_seq = Column(BigInteger, ForeignKey("chatbot_category.chatbot_category_seq", ondelete="CASCADE", onupdate="CASCADE"), nullable=False)
    chatbot_data = Column(String(200), nullable=False)

    # Optional: Define a relationship back to the category
    category = relationship("ChatbotCategory", back_populates="chatbots")