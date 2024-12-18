import { springAPI } from "@/services/axios";

// 담당자 동료 평가 지표 조회
export const fetchPeerReviewList = async () => {
    try {
        const response = await springAPI.get(`/hr/peer/review/list`);
        return response.data;
    } catch (error) {
        console.error("동료 평가 지표 조회에 실패했습니다:", error);
        throw error;
    }
};

// 담당자 동료 평가 지표 추가
export const postPeerReviewList = async (requestData) => {
    try {
        const response = await springAPI.post(`/hr/peer/review/list`, requestData); // 객체로 감싸지 않음
        return response.data;
    } catch (error) {
        console.error("동료 평가 지표 추가에 실패했습니다:", error);
        throw error;
    }
};

// 담당자 동료 평가 지표 삭제
export const deletePeerReviewList = async (peerReviewListSeq) => {
    try {
        const response = await springAPI.delete(`/hr/peer/review/list/${peerReviewListSeq}`);
        return response.data;
    } catch (error) {
        console.error("동료 평가 지표 추가에 실패했습니다:", error);
        throw error;
    }
};