import { springAPI } from "@/services/axios";

// 멘토 정보 조회
export const fetchMentorInfo = async() =>{
    try{
        const response = await springAPI.get(`/mentee/mentor/info`)
        return response.data;
    }catch(error){
        console.error("멘토 정보 조회 실패", error.response?.data || error.message);
        throw error;
    }
};

// 멘티 정보 조회
export const fetchMenteeInfo = async() =>{
    try{
        const response = await springAPI.get(`/mentor/mentee/info`)
        return response.data;
    }catch(error){
        console.log("멘티 정보 조회 실패", error.response?.data || error.message)
        throw error;
    }
};