import {springAPI} from "@/services/axios.js";

export const getChatMessages = (roomId) => {
    return springAPI.get(`/chat/${roomId}/messages`)
        .then(response => response.data) // 응답 데이터 반환
        .catch(error => console.error("Error loading chat messages", error));
};