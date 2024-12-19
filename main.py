from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from chatbot_category.router import router as category_router
from chatbot_data.router import router as data_router
from exception import (
    http_exception_handler,
    validation_exception_handler,
    custom_validation_exception_handler,
    conflict_exception_handler
)
from fastapi.exceptions import RequestValidationError
from fastapi.exceptions import HTTPException
import uvicorn

app = FastAPI()

# CORS 설정 추가
origins = [
    "http://localhost:5173"
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,  # 허용할 Origin
    allow_credentials=True,  # 쿠키 허용 여부
    allow_methods=["*"],  # 허용할 HTTP 메서드 (예: GET, POST 등)
    allow_headers=["*"],  # 허용할 HTTP 헤더
)

# 글로벌 예외 핸들러 등록
app.add_exception_handler(HTTPException, http_exception_handler)
app.add_exception_handler(RequestValidationError, validation_exception_handler)
app.add_exception_handler(HTTPException, custom_validation_exception_handler)
app.add_exception_handler(HTTPException, conflict_exception_handler)

# 라우터 등록
app.include_router(category_router)
app.include_router(data_router)

if __name__ == "__main__":
    uvicorn.run(
        "app.main:app",
        host="localhost",
        port=8255,
        reload=True
    )
