<template>
    <div class="mentor-container">
        <header class="mentor-header">
            <div class="mentee_profile_page">멘토 조회</div>
        </header>

        <div class="mentor-card" v-if="mentor">
            <div class="mentor-card-header">
                <div class="mentor-image-container">
                    <img class="mentor-image"
                        :src="mentor.fileUrl || 'https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/982d01fa-4aac-4cba-b477-993841d53842_%EB%A3%A8%ED%94%BC.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241216T071922Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAQXPZDBYQREV7D6US%2F20241216%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=eed2f8afca007ee9a5e3d5f04f92029d984940566bc8716e1c108a3d0a13526b'"
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
