import {springAPI} from "@/services/axios.js";

// 멘토링 계획서 전체 리스트 조회
export const fetchMentoringPlanningList = async() => {
    try{
        const response = await springAPI.get('/mentor/planning');
        return response.data;
    } catch (error){
        console.log("멘토링 계획서 리스트를 불러오지 못했습니다.",error);
    }
}

// 멘토링 계획서 검색
export const searchMentoringPlans = (category, word) => {
    return springAPI.get(`/mentor/planning/search`, {
        params: { category, word }
    });
};

// 멘토링 계획서 등록
export const submitMentoringPlanService = (formData) => {
    return springAPI.post("/mentor/planning", formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    }).then((response) => response.data);
};

// 멘토링 계획서 상세 조회
export const fetchMentoringPlanDetail = async (planningSeq) => {
    try {
        const response = await springAPI.get(`/mentor/planning/${planningSeq}`);
        return response.data; // 성공 시 데이터 반환
    } catch (error) {
        console.error("멘토링 계획서 상세 조회 실패:", error);
        throw error;
    }
};

// 멘토링 계획서 상태 처리 (팀장)
export const updateMentoringPlanStatus = (planningSeq, planningStatus) => {
    return springAPI.put(`/mentor/planning/${planningSeq}`, { planningStatus });
};

// 멘토링 그룹 생성
export const createMentoringGroup = (mentoringGroupDTO) => {
    return springAPI.post(`/hr/matching`, mentoringGroupDTO);
};

// 부서 조회
export const fetchDepartmentList = () => {
    return springAPI.get("/hr/department");
};

// 부서별 멘토 조회
export const fetchMentorsByDepartment = (departmentSeq) => {
    return springAPI.get(`/hr/mentor/${departmentSeq}`)
}

// 부서별 멘티 조회
export const fetchMenteeByDepartment = (departmentSeq) => {
    return springAPI.get(`/hr/mentee/${departmentSeq}`)
}