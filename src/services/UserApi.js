import { springAPI } from "@/services/axios";

// 사원 이름 조회
export const fetchName = async (employeeSeq) => {
    try {
        const response = await springAPI.get(`/${employeeSeq}/name`);
        return response.data.data;
    } catch (error) {
        console.error("멘토 이름 조회 실패", error.response?.data || error.message);
        throw error;
    }
};

// 내 정보 조회
export const fetchEmployeeInfo = async () => {
    try {
        const response = await springAPI.get(`/employee/info`);
        return response.data;
    } catch (error) {
        console.error("내 정보 조회 실패", error.response?.data || error.message);
        throw error;
    }
}

// 비밀번호 변경
export const updatePassword = async (employeeSeq, inputValue) => {
    try {
        const response = await springAPI.put(`/employee/${employeeSeq}/password`, inputValue);
        return response.data;
    } catch (error) {
        console.error('비밀번호 변경에 실패했습니다.', error);
        throw error;
    }
}

// 멘토 정보 조회
export const fetchMentorInfo = async() => {
    try{
        const response = await springAPI.get(`/mentee/mentor/info`)
        return response.data;
    }catch(error){
        console.error("멘토 정보 조회 실패", error.response?.data || error.message);
        throw error;
    }
};

// 멘티 정보 조회
export const fetchMenteeInfo = async() => {
    try{
        const response = await springAPI.get(`/mentor/mentee/info`)
        return response.data;
    }catch(error){
        console.log("멘티 정보 조회 실패", error.response?.data || error.message)
        throw error;
    }
};

// 사원 리스트 조회
export const fetchEmployeeList = async () => {
    try {
        const response = await springAPI.get(`/hr/user`);
        return response.data;
    } catch (error) {
        console.log("사원 목록을 불러오지 못했습니다.", error);
    }
}

// 사원 검색
export const searchEmployees = (searchType, keyword) => {
    return springAPI.get(`/hr/user/search`, {
        params: {searchType, keyword}
    });
}