from fastapi import FastAPI
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