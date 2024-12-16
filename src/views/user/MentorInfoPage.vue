<template>
    <div class="mentor-container">
        <header class="mentor-header">
            <h1>멘토 조회</h1>
        </header>

        <div class="mentor-card">
            <div class="mentor-card-header">
                <div class="mentor-image-container">
                    <img class="mentor-image"
                        src="https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/68a6a4c5-2e9f-4618-ac13-dfa0860b1052_%EB%A3%A8%ED%94%BC.jpg"
                        alt="멘토 이미지" />
                </div>

                <div class="mentor-header-details">
                    <h2 class="mentor-name">{{ mentor.name }}</h2>
                    <p class="mentor-phone">{{ mentor.phone }}</p>
                    <a :href="`mailto:${mentor.email}`" class="mentor-email">{{ mentor.email }}</a>
                </div>
            </div>

            <div class="mentor-info">
                <div class="info-row">
                    <span class="info-label">소속</span>
                    <span class="info-value">{{ mentor.department }}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">사번</span>
                    <span class="info-value">{{ mentor.employeeId }}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">직급</span>
                    <span class="info-value">{{ mentor.position }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import "@/styles/user/MentorInfoPage.css";
import { ref, onMounted } from "vue";

const mentor = ref({
    name: "구대윤",
    phone: "02-1234-1023",
    email: "soulsoul@hihello.com",
    department: "IT사업부",
    employeeId: "202401111",
    position: "대리",
});

const fetchMentorInfo = async () => {
    try {
        // API 로직
    } catch (error) {
        console.error("데이터 불러오기 실패:", error);
    }
};

onMounted(() => {
    fetchMentorInfo();
});
</script>
