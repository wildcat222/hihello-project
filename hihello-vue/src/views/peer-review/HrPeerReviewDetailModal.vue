<template>
    <!-- 배경 오버레이 -->
    <div class="modal-overlay" @click.self="closeModal">
        <!-- white-box 컴포넌트가 모달 역할 -->
        <white-box class="modal-content">
            <!-- 닫기 버튼 (오른쪽 위) -->
            <button class="modal-close-btn" @click="closeModal">×</button>

            <div v-if="loading">
                <p>로딩 중...</p>
            </div>

            <div v-else>
                <h2 class="modal-title">과제 명 :</h2>
                <div class="score-list">
                    <div 
                        v-for="(item, index) in reviewDetails" 
                        :key="index" 
                        class="score-item"
                    >
                        <span>{{ item.peerReviewListContent }}</span>
                        <span>{{ item.peerReviewScore }} 점</span>
                    </div>
                    <div class="score-total">
                        <strong>총점</strong>
                        <strong>{{ totalScore }} 점</strong>
                    </div>
                </div>
            </div>
        </white-box>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import WhiteBox from '@/components/WhiteBoxComponent.vue';
import { fetchPeerReviewResultDetail } from '@/services/PeerReviewApi';

const props = defineProps({
    taskSeq: Number,
    taskGroupSeq: Number,
    employeeSeq: Number,
});

const emit = defineEmits(['close']);

const loading = ref(true);
const reviewDetails = ref([]);
const totalScore = ref(0);

const closeModal = () => {
    emit('close');
};

const fetchData = async () => {
    try {
        const response = await fetchPeerReviewResultDetail(props.taskSeq, props.taskGroupSeq, props.employeeSeq);
        if (response.success) {
            reviewDetails.value = response.data.peerReviewDetails; 
            totalScore.value = response.data.peerReviewScoreSum;
        }
    } catch (error) {
        console.error('동료 평가 결과 상세 조회에 실패했습니다:', error);
    } finally {
        loading.value = false;
    }
};
onMounted(() => {
    fetchData();
});
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 999;
}

.modal-content {
    width: 25vw;
    padding: 50px;
    border-radius: 8px;
    position: relative;
}

.modal-close-btn {
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 20px;
    color: var(--black);
    cursor: pointer;
}

.modal-close-btn:hover {
    color: var(--red);
}

.modal-title {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 50px;
}

.score-list {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.score-item {
    display: flex;
    justify-content: space-between;
    font-size: 16px;
}

.score-total {
    display: flex;
    justify-content: space-between;
    font-weight: bold;
    margin-top: 15px;
    padding-top: 10px;
    font-size: 17px;
    border-top: 2px solid var(--purple);
}
</style>
