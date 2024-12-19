import axios from 'axios';
import { ref } from 'vue';
import { springAPI } from "@/services/axios.js";

// 동료 정보 상태와 리뷰 항목 상태 관리
export const useGroupEval = (taskGroupSeq) => {
    const groupInfo = ref([]);  // 동료 정보
    const reviewItems = ref([]); // 리뷰 항목
    const selectedRevieweeSeq = ref(null);  // 선택된 revieweeSeq 저장할 변수
    const inputText = ref({});  // 입력 필드에 대한 객체로 수정

    // 동료 정보 불러오기
    const fetchGroupInfo = async () => {
        if (!taskGroupSeq) {
            console.error('taskGroupSeq가 정의되지 않았습니다.');
            return;
        }

        try {
            const response = await springAPI.get(
                `mentee/task/group/${taskGroupSeq}`,
                {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
                    },
                }
            );

            if (response.data.success) {
                groupInfo.value = response.data.data; // 배열 형태로 저장
            } else {
                console.error('데이터 조회 실패:', response.data.message);
            }
        } catch (error) {
            console.error('API 요청 실패:', error);
        }
    };

    // 리뷰 데이터 불러오기
    const fetchReviewData = async () => {
        try {
            const response = await springAPI.get(
                'hr/peer/review/list',
                {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
                    },
                }
            );

            if (response.data.success) {
                reviewItems.value = response.data.data.map(item => ({
                    peerReviewListSeq: item.peerReviewListSeq,
                    peerReviewListContent: item.peerReviewListContent,
                    peerReviewListScore: item.peerReviewListScore,
                }));

                reviewItems.value.forEach(item => {
                    inputText.value[item.peerReviewListSeq] = '';  // 각 항목에 대해 초기값 설정
                });
            } else {
                console.error('데이터 조회 실패:', response.data.message);
            }
        } catch (error) {
            console.error('API 요청 실패:', error);
        }
    };

    return {
        groupInfo,
        reviewItems,
        selectedRevieweeSeq,
        inputText,
        fetchGroupInfo,
        fetchReviewData,
    };
};
