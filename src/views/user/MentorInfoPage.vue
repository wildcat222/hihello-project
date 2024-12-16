<template>
    <div class="mentor-container">
        <header class="mentor-header">
            <h1>멘토 조회</h1>
        </header>

        <div class="mentor-card" v-if="mentor">
            <div class="mentor-card-header">
                <div class="mentor-image-container">
                    <img class="mentor-image"
                        :src="mentor.fileUrl || 'https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/a24c3335-c7cf-4c87-934b-daf74b3ba9d6_%EA%B8%B0%EB%B3%B8%20%ED%94%84%EB%A1%9C%ED%95%84%20%EC%9D%B4%EB%AF%B8%EC%A7%80.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241216T054358Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAQXPZDBYQREV7D6US%2F20241216%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=41579ddf56fe556d187ac8fbfabdcdf6fb768c23c09427702d2e791597c76656'"
                        alt="멘토 이미지" />
                </div>

                <div class="mentor-header-details">
                    <h2 class="mentor-name">{{ mentor.employeeName }}</h2>
                    <p class="mentor-phone">{{ mentor.employeePhone }}</p>
                    <a :href="`mailto:${mentor.employeeEmail}`" class="mentor-email">{{ mentor.employeeEmail }}</a>
                </div>
            </div>

            <div class="mentor-info">
                <div class="info-row">
                    <span class="info-label">소속</span>
                    <span class="info-value">{{ mentor.departmentName }}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">역할</span>
                    <span class="info-value">{{ roleLabel }}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">직급</span>
                    <span class="info-value">{{ mentor.positionName }}</span>
                </div>
            </div>
        </div>

        <div v-else>
            <p>멘토 정보를 불러오는 중...</p>
        </div>
    </div>
</template>

<script setup>
import "@/styles/user/MentorInfoPage.css";
import { ref, computed, onMounted } from "vue";
import { fetchMentorInfo } from "@/services/UserApi.js";

const mentor = ref(null);

// 역할 표시 로직
const roleLabel = computed(() => {
    if (!mentor.value) return "N/A";
    return mentor.value.employeeRole === "MENTOR" ? "멘토" : mentor.value.employeeRole || "N/A";
});

const loadMentorInfo = async () => {
    try {
        const response = await fetchMentorInfo();
        if (response.success) {
            mentor.value = response.data;
        } else {
            console.error("멘토 정보 불러오기 실패:", response.message);
        }
    } catch (error) {
        console.error("API 호출 중 오류:", error);
    }
};

onMounted(() => {
    loadMentorInfo();
});
</script>
