import axios from "axios";

// 최종 평가 결과 리스트 조회
export const fetchFinalEvalResultList = async() => {
    try {
        return await axios.get(`http://localhost:8253/api/v1/final-eval`);
    } catch(error) {
        console.error("최종 평가 결과 리스트를 조회하던 도중 오류가 발생했습니다.", error);
    }
}

// 최종 평가 결과 리스트 검색
export const searchFinalEvalResultList = async(keyword) => {
    try {
        return await axios.get(`http://localhost:8253/api/v1/final-eval/search`, {
            params: {keyword}
        });
    } catch(error) {
        console.error("최종 평가 결과 리스트를 검색하던 도중 오류가 발생했습니다.", error);
    }
}