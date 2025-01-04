from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.db_connection.rds_connection import get_db
from app.chatbot_data.model import Chatbot
from app.chatbot_category.model import ChatbotCategory
from app.chatbot_data.schema import ChatbotAddRequest
from app.chatbot_data.schema import ChatbotUpdateRequest

router = APIRouter(
    prefix="/api/v1/hr/chatbot",
    tags=["Chatbot"]
)

@router.post("/category/{categorySeq}/data")
def add_chatbot_data(
        categorySeq: int,
        request_body: ChatbotAddRequest,
        db: Session = Depends(get_db)
):

    category = db.query(ChatbotCategory).filter_by(chatbot_category_seq=categorySeq).first()
    if not category:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot category with ID {categorySeq} not found."
            }
        )

    new_data = Chatbot(
        chatbot_category_seq=categorySeq,
        chatbot_data=request_body.chatbotData
    )
    db.add(new_data)
    db.commit()

    return {"message": "Chatbot data added successfully."}

@router.get("/category/{categorySeq}/data")
def get_chatbot_data_by_category(categorySeq: int, db: Session = Depends(get_db)):
    """
    API to fetch all chatbot data for a specific category.
    """
    category = db.query(ChatbotCategory).filter_by(chatbot_category_seq=categorySeq).first()
    if not category:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot category with ID {categorySeq} not found."
            }
        )

    chatbot_data = db.query(Chatbot).filter_by(chatbot_category_seq=categorySeq).all()
    if not chatbot_data:
        return {"success": True, "data": []}

    return {
        "success": True,
        "data": [
            {
                "id": data.chatbot_seq,
                "content": data.chatbot_data  # 키 이름 변경
            }
            for data in chatbot_data
        ]
    }

# 챗봇 데이터 수정
@router.put("/category/{categorySeq}/data/{chatbotSeq}")
def update_chatbot_data(
        categorySeq: int,
        chatbotSeq: int,
        request_body: ChatbotUpdateRequest,
        db: Session = Depends(get_db)
):
    chatbot_data_content = request_body.chatbotData

    # Validate category existence
    category = db.query(ChatbotCategory).filter_by(chatbot_category_seq=categorySeq).first()
    if not category:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot category with ID {categorySeq} not found."
            }
        )

    # Validate chatbot data existence
    chatbot_data = db.query(Chatbot).filter_by(chatbot_seq=chatbotSeq, chatbot_category_seq=categorySeq).first()
    if not chatbot_data:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot data with ID {chatbotSeq} not found in category {categorySeq}."
            }
        )

    # Update chatbot data
    chatbot_data.chatbot_data = chatbot_data_content  # Update the content
    db.commit()  # Commit the changes to the database

    # Return success message
    return {"message": "Chatbot data updated successfully."}


# 챗봇 데이터 삭제
@router.delete("/category/{categorySeq}/data/{chatbotSeq}")
def delete_chatbot_data(categorySeq: int, chatbotSeq: int, db: Session = Depends(get_db)):

    category = db.query(ChatbotCategory).filter_by(chatbot_category_seq=categorySeq).first()
    if not category:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot category with ID {categorySeq} not found."
            }
        )

    # Validate chatbot data existence
    chatbot_data = db.query(Chatbot).filter_by(chatbot_seq=chatbotSeq, chatbot_category_seq=categorySeq).first()
    if not chatbot_data:
        raise HTTPException(
            status_code=404,
            detail={
                "error": "NotFoundError",
                "message": f"Chatbot data with ID {chatbotSeq} not found in category {categorySeq}."
            }
        )

    # Delete chatbot data
    db.delete(chatbot_data)
    db.commit()

    return {"message": "Chatbot data deleted successfully."}