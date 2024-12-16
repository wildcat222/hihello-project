import axios from 'axios';

// 멘토링 계획서 전체 리스트 조회
export const fetchMentoringPlanningList = async() => {
    try{
        const response = await axios.get('http://localhost:8253/api/v1/mentor/planning');
        return response.data;
    } catch (error){
        console.log("멘토링 계획서 리스트를 불러오지 못했습니다.",error);
    }
}

// 멘토링 계획서 검색
export const searchMentoringPlans = (category, word) => {
    return axios.get(`http://localhost:8253/api/v1/mentor/planning/search`, {
        params: { category, word }
    });
};
