<template>
    <div class="mentee-container">
        <header class="mentee-header">
            <h1>멘티 조회</h1>
        </header>

        <div class="mentee-card">
            <div class="mentee-card-header">
                <div class="mentee-image-container">
                    <img class="mentee-image"
                        src="https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/68a6a4c5-2e9f-4618-ac13-dfa0860b1052_%EB%A3%A8%ED%94%BC.jpg"
                        alt="멘티 이미지" />
                </div>

                <div class="mentee-header-details">
                    <h2 class="mentee-name">{{ mentee.name }}</h2>
                    <p class="mentee-phone">{{ mentee.phone }}</p>
                    <a :href="`mailto:${mentee.email}`" class="mentee-email">{{ mentee.email }}</a>
                </div>
            </div>

            <div class="mentee-info">
                <div class="info-row">
                    <span class="info-label">소속</span>
                    <span class="info-value">{{ mentee.department }}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">사번</span>
                    <span class="info-value">{{ mentee.employeeId }}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">직급</span>
                    <span class="info-value">{{ mentee.position }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import "@/styles/user/MenteeInfoPage.css"
import { ref, onMounted } from "vue";

const mentee = ref({
    name: "구대윤",
    phone: "02-1234-1023",
    email: "soulsoul@hihello.com",
    department: "IT사업부",
    employeeId: "202401111",
    position: "대리",
});

const fetchMenteeInfo = async () => {
    try {
        // API 로직
    } catch (error) {
        console.error("데이터 불러오기 실패:", error);
    }
};

onMounted(() => {
    fetchMenteeInfo();
});
</script>
