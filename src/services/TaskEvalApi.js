// 과제 평가 등록
import {springAPI} from "@/services/axios.js";

export const createTaskEval = async(taskSubmitSeq, taskEvalsData) => {
    try {
        return await springAPI.post(`/task-submit/${taskSubmitSeq}/task-eval`, taskEvalsData);
    } catch(error) {
        console.error("과제 평가를 등록하던 중 오류가 발생했습니다.", error);
        throw error;
    }
}