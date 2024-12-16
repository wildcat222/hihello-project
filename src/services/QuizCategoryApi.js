import { springAPI } from "@/services/axios";

// 퀴즈 카테고리 조회
export const fetchQuizCategory = async () => {
    try {
        const response = await springAPI.get(`/hr/quizCategory`)
        return response.data;
    } catch (error) {
        console.error("퀴즈 카테고리 조회 실패", error.response?.data || error.message);
        throw error;
    }
};

// 퀴즈 카테고리 추가
export const postQuizCategory = async () => {
    try{
        const response = await springAPI.post(`/hr/quizCategory`, {
            quiz_category_name: quizCategoryName
        });
        return response.data;
    } catch (error){
        console.error("퀴즈 카테고리 추가를 실패했습니다.", error.response?.data || error.message);
        throw error;
    }
};