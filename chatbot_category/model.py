from sqlalchemy import Column, BigInteger, String
from db_connection.rds_connection import Base

class ChatbotCategory(Base):
    __tablename__ = "chatbot_category"

    chatbot_category_seq = Column(BigInteger, primary_key=True, autoincrement=True, index=True)  # PRIMARY KEY
    chatbot_category_content = Column(String(20), nullable=False, unique=True)  # VARCHAR(20), NOT NULL, UNIQUE