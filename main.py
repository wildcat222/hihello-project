from fastapi import FastAPI
import uvicorn

app = FastAPI()

# 테스트용 API 추후 삭제 요망
@app.get("/")
def read_root():
    return {"message": "Hello from localhost"}


if __name__ == "__main__":
    uvicorn.run(
        "main:app",
        host="localhost",  # 호스트를 localhost로 설정
        port=8255,
        reload=True
    )
