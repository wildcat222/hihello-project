// 과제 제출 내용 조회 (과제 정보와 과제의 제출 내용)
import {springAPI} from "@/services/axios.js";

export const fetchSubmittedTask = async(taskSubmitSeq) => {
    try {
        const response = await springAPI.get(`/task-submit/${taskSubmitSeq}`);
        return response.data;
    } catch(error) {
        console.error("과제 제출 내용을 가져오는 중 오류가 발생했습니다.", error);
    }
}

export const back = () => {
    window.history.back();
}