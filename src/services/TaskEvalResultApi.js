import {springAPI} from "@/services/axios.js";

// 과제 평가 결과 리스트 조회
export const fetchTaskEvalResultList = async() => {
    try {
        return await springAPI.get(`/task-eval`);
    } catch(error) {
        console.error("과제 평가 결과 리스트를 조회하던 도중 오류가 발생했습니다.", error);
    }
}

// 과제 평가 결과 상세 조회
export const fetchTaskEvalDetailResult = async(taskSubmitSeq) => {
    try {
        return await springAPI.get(`/task-submit/${taskSubmitSeq}/task-eval`);
    } catch(error) {
        console.error("과제 평가 결과 상세 내용을 조회하던 도중 오류가 발생했습니다.", error);
    }
}