import {springAPI} from "@/services/axios.js";

export const getChatMessages = async (roomId) => {
    try{
        const response = await springAPI.get(`/chat/${roomId}/messages`);
        // console.log(response.data.data);
        return response.data;
    } catch (error){
        console.log("대화 내용 조회 실패",error);
        throw error;
    }
};