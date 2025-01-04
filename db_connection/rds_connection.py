import os
from dotenv import load_dotenv
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

# .env 파일 로드 (개발 환경에서만)
if os.getenv("ENV", "development") == "development":
    load_dotenv()

# 환경변수에서 DATABASE_URL 가져오기
DATABASE_URL = os.getenv("DATABASE_URL")
if not DATABASE_URL:
    raise ValueError("DATABASE_URL 환경변수가 설정되지 않았습니다.")

# 데이터베이스 엔진 생성 (커넥션 풀 옵션 포함)
engine = create_engine(
    DATABASE_URL,
    pool_size=5,         # 기본 풀 크기
    max_overflow=10,     # 추가적으로 생성할 수 있는 연결 수
    pool_timeout=30,     # 풀에서 연결을 얻기 위한 대기 시간 (초)
    pool_recycle=1800    # 커넥션 재활용 주기 (초)
)

# 세션 및 베이스 클래스 설정
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()

# 데이터베이스 세션 생성
def get_db():
    db = SessionLocal()
    try:
        yield db
    except Exception as e:
        db.rollback()
        raise e
    finally:
        db.close()