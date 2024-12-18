<template>
    <div class="peer-review__container">
        <!-- 헤더 -->
        <h1>동료 평가 조회</h1>

        <!-- 리스트 헤더와 드롭다운 -->
        <div class="peer-review__header-wrapper">
            <div></div> <!-- 빈 공간 (왼쪽 정렬을 위해) -->
            <div class="peer-review__dropdown-wrapper">
                <!-- 과제 제목 선택 -->
                <select
                    class="peer-review__dropdown"
                    v-model="selectedTask"
                    @change="fetchGroupList"
                >
                    <option value="" disabled>과제를 선택하세요</option>
                    <option v-for="task in taskList" :key="task.taskSeq" :value="task.taskSeq">
                        {{ task.taskContent }}
                    </option>
                </select>

                <!-- 그룹 리스트 -->
                <select
                    class="peer-review__dropdown peer-review__group-dropdown"
                    v-model="selectedGroup"
                    @change="fetchReviewResults"
                >
                    <option value="" disabled>그룹을 선택하세요</option>
                    <option v-for="group in groupList" :key="group.taskGroupSeq" :value="group.taskGroupSeq">
                        {{ group.taskGroupNum }}
                    </option>
                </select>
            </div>
        </div>

        <!-- 리스트 컴포넌트 -->
        <white-box>
            <div class="peer-review__list-wrapper">
                <div class="peer-review__list-header">
                    <div class="peer-review__list-column">피평가자</div>
                    <div class="peer-review__list-column">사번</div>
                    <div class="peer-review__list-column">팀</div>
                    <div class="peer-review__list-column">평가자</div>
                    <div class="peer-review__list-column">배점</div>
                    <div class="peer-review__list-column">점수</div>
                </div>

                <div v-if="reviewResults.length > 0">
                    <list-component :items="reviewResults">
                        <template #item="{ item }">
                            <div class="peer-review__result-item">
                                <div class="peer-review__list-column">{{ item.revieweeName }}</div>
                                <div class="peer-review__list-column">{{ item.employeeNum }}</div>
                                <div class="peer-review__list-column">{{ item.departmentName }}</div>
                                <div class="peer-review__list-column">{{ item.reviewerName || 'N/A' }}</div>
                                <div class="peer-review__list-column">{{ item.peerReviewScoreListSum }}</div>
                                <div class="peer-review__list-column">{{ item.peerReviewScoreSum }}</div>
                            </div>
                        </template>
                    </list-component>
                </div>
                <div v-else>
                    <p class="no-data">동료 평가 결과가 없습니다.</p>
                </div>
            </div>
        </white-box>
    </div>
</template>

<script setup>
import "@/styles/peer-review/PeerReview.css";
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import ListComponent from "@/components/ListComponent.vue";
import { fetchGroupTaskTitle, fetchPeerReview, fetchPeerReviewResult } from "@/services/PeerReviewApi";
import { ref, onMounted } from "vue";

// 데이터 선언
const taskList = ref([]);
const groupList = ref([]);
const selectedTask = ref("");
const selectedGroup = ref("");
const reviewResults = ref([]);

const fetchTaskTitle = async () => {
    try {
        const response = await fetchGroupTaskTitle();
        taskList.value = response.data;

        if (taskList.value.length > 0) {
            selectedTask.value = taskList.value[0].taskSeq;
            await fetchGroupList();
        }
    } catch (error) {
        console.error("그룹 과제 제목 리스트를 가져오는 중 오류가 발생했습니다:", error);
    }
};

const fetchGroupList = async () => {
    if (!selectedTask.value) return;
    try {
        const response = await fetchPeerReview(selectedTask.value);
        groupList.value = response.data;

        if (groupList.value.length > 0) {
            selectedGroup.value = groupList.value[0].taskGroupSeq;
            await fetchReviewResults();
        }
    } catch (error) {
        console.error("그룹 리스트를 가져오는 중 오류가 발생했습니다:", error);
    }
};

const fetchReviewResults = async () => {
    if (!selectedTask.value || !selectedGroup.value) return;
    try {
        const response = await fetchPeerReviewResult(selectedTask.value, selectedGroup.value);
        reviewResults.value = response.data;
    } catch (error) {
        console.error("동료 평가 결과를 가져오는 중 오류가 발생했습니다:", error);
    }
};

onMounted(() => {
    fetchTaskTitle();
});
</script>

<style scoped>
.white-box {
    margin-top: 0;
    padding-top: 1vw;
}

.no-data {
    color: var(--gray);
    margin-top: 20px;
}
</style>
