import { fastAPI } from "@/services/axios.js";

// 담당자 챗봇 카테고리 전체 조회
export const fetchChatbotCategory = async () => {
  try {
    const response = await fastAPI.get(`/hr/chatbot/category`);
    return response.data;
  } catch (error) {
    console.error(
      "챗봇 카테고리 조회 실패: ",
      error.response?.data || error.message
    );
    throw error;
  }
};

// 담당자 챗봇 카테고리 추가
export const addChatbotCategory = async (data) => {
    try {
        const response = await fastAPI.post(`/hr/chatbot/category`, data);
        return response.data;
    } catch (error) {
        console.error("챗봇 카테고리 추가 실패:", error.response?.data || error.message);
        throw error;
    }
};

export const deleteChatbotCategory = async (categorySeq) => {
    try {
        const response = await fastAPI.delete(`/hr/chatbot/category/${categorySeq}`);
        return response.data;
    } catch (error) {
        console.error("챗봇 카테고리 삭제 실패:", error.response?.data || error.message);
        throw error;
    }
};
