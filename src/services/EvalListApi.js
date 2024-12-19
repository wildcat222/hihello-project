// 과제 평가 항목 조회
import {springAPI} from "@/services/axios.js";

export const fetchEvalLists = async(taskSeq) => {
    try {
        return await springAPI.get(`/task/${taskSeq}/eval-list`);
    } catch(error) {
        console.error("과제 평가 항목을 조회하던 도중 오류가 발생했습니다.", error);
    }
}