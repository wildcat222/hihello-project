import { lambdaAPI } from "@/services/axios";

// 전체 퀴즈 조회
export const fetchQuizzes = async (quizCategorySeq) => {
    try {
      const response = await lambdaAPI.get(`/quiz/${quizCategorySeq}`);
      return response.data;
    } catch (error) {
      console.error("Failed to fetch quizzes:", error);
      throw error;
    }
  };

// 퀴즈 결과 조회
export const fetchQuizResults = async (quizCategorySeq, employeeSeq) => {
    try {
      const response = await lambdaAPI.get(`/quiz/${quizCategorySeq}/result/${employeeSeq}`);
      return response.data;
    } catch (error) {
      console.error("Failed to fetch quiz results:", error);
      throw error;
    }
};


// 퀴즈 답안 제출
export const submitQuizAnswer = async (quizCategorySeq, employeeSeq, quizSeq, correctStatus) => {
  try {
      const response = await lambdaAPI.post(`/quiz/${quizCategorySeq}/result/${employeeSeq}`, {
          quiz_seq: quizSeq,
          correct_status: correctStatus, // 올바른 필드명 사용
      });
      return response.data;
  } catch (error) {
      console.error("Failed to submit quiz answer:", error.response?.data || error.message);
      throw error;
  }
};
  