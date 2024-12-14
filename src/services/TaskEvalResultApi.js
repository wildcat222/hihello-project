import axios from 'axios';

// 과제 평가 결과 리스트 조회
export const fetchTaskEvalResultList = async() => {
    try {
        return await axios.get(`http://localhost:8253/api/v1/task-eval`);
    } catch(error) {
        console.error("과제 평가 결과 리스트를 조회하던 도중 오류가 발생했습니다.", error);
    }
}