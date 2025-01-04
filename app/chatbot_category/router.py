from fastapi import APIRouter, HTTPException, Depends
from sqlalchemy.orm import Session
from app.db_connection.rds_connection import get_db
from app.chatbot_category.schema import ChatbotCategoryRequest
from .model import ChatbotCategory

router = APIRouter(
    prefix="/api/v1/hr/chatbot",
    tags=["Chatbot"]
)

# 챗봇 카테고리 추가
@router.post("/category")
def create_chatbot_category(
        request_body: ChatbotCategoryRequest,
        db: Session = Depends(get_db)
):
    chatbot_category_name = request_body.ChatbotCategoryContent
    if not chatbot_category_name:
        raise HTTPException(
            status_code=400,
            detail={
                "error": "ValidationError",
                "message": "chatbotCategory is required."
            }
        )

    # Check for duplicate categories
    existing_category = db.query(ChatbotCategory).filter_by(chatbot_category_content=chatbot_category_name).first()
    if existing_category:
        raise HTTPException(
            status_code=409,
            detail={
                "error": "ConflictError",
                "message": "Chatbot category already exists."
            }
        )

    # Create new category
    new_category = ChatbotCategory(chatbot_category_content=chatbot_category_name)
    db.add(new_category)
    db.commit()
    db.refresh(new_category)

    return {"success": True, "data": new_category}

# 챗봇 카테고리 전체 조회
@router.get("/category")
def get_chatbot_categories(db: Session = Depends(get_db)):
    try:
        categories = db.query(ChatbotCategory).all()
        if not categories:
            raise HTTPException(status_code=404, detail="No chatbot categories found.")

        return [
            {
                "chatbotCategorySeq": category.chatbot_category_seq,
                "chatbotCategoryContent": category.chatbot_category_content
            }
            for category in categories
        ]
    except Exception as e:
        raise HTTPException(
            status_code=500,
            detail={
                "error": "InternalServerError",
                "message": "Failed to fetch chatbot categories.",
                "debug_info": str(e)
            }
        )

# 챗봇 카테고리 삭제
@router.delete("/category/{categorySeq}")
def delete_chatbot_category(categorySeq: int, db: Session = Depends(get_db)):
    try:
        # Find the category by ID
        category = db.query(ChatbotCategory).filter_by(chatbot_category_seq=categorySeq).first()
        if not category:
            raise HTTPException(
                status_code=404,
                detail={
                    "error": "NotFoundError",
                    "message": "Chatbot category not found."
                }
            )

        # Delete the category
        db.delete(category)
        db.commit()

        return {"success": True, "message": "Chatbot category deleted successfully."}

    except Exception as e:
        raise HTTPException(
            status_code=500,
            detail={
                "error": "InternalServerError",
                "message": "Failed to delete chatbot category.",
                "debug_info": str(e)
            }
        )