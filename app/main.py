import os
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.chatbot_category.router import router as category_router
from app.chatbot_data.router import router as data_router
from app.exception import (
    http_exception_handler,
    validation_exception_handler,
    custom_validation_exception_handler,
    conflict_exception_handler
)
from fastapi.exceptions import RequestValidationError
from fastapi.exceptions import HTTPException
import uvicorn

# 애플리케이션 생성
app = FastAPI()

# 환경 구분
ENV = os.getenv("ENV", "development")  # 기본값은 'development'

# CORS 설정
if ENV == "development":
    origins = [
        "http://localhost:5173"  # 개발 환경에서의 허용 Origin
    ]
else:
    origins = [
        "https://fastapi.hi-hello.site"  # 배포 환경에서의 허용 Origin
    ]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,  # 허용할 Origin
    allow_credentials=True,  # 쿠키 허용 여부
    allow_methods=["*"],  # 허용할 HTTP 메서드
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

# 개발과 배포에 따른 서버 설정
if __name__ == "__main__":
    if ENV == "development":
        uvicorn.run(
            "app.main:app",
            host="localhost",  # 개발 환경에서는 localhost 사용
            port=8255,
            reload=True  # 코드 변경 시 자동으로 서버 재시작
        )
    else:
        uvicorn.run(
            "app.main:app",
            host="0.0.0.0",  # 배포 환경에서는 0.0.0.0 사용
            port=8000,
            reload=False  # 배포 환경에서는 reload 비활성화
        )