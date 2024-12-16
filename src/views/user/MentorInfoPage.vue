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

<style scoped>
.mentor-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
}

.mentor-header h1 {
    font-size: 1.8rem;
    font-weight: bold;
    margin-bottom: 2rem;
    color: var(--black);
}

.mentor-card {
    display: flex;
    flex-direction: column;
    background-color: var(--white);
    border-radius: 12px;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
    padding: 2rem;
    width: 30vw;
    margin-bottom: 30px;
}

.mentor-card-header {
    display: flex;
    align-items: center;
    gap: 2rem;
    margin-bottom: 1.5rem;
    flex-wrap: wrap;
}

.mentor-image-container {
    width: 130px;
    height: 130px;
    border-radius: 50%;
    margin-left: 30px;
    overflow: hidden;
    flex-shrink: 0;
}

.mentor-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.mentor-header-details {
    display: flex;
    flex-direction: column;
    justify-content: center;
    gap: 0.5rem;
}

.mentor-name {
    font-size: 1.4rem;
    font-weight: bold;
    margin: 0;
    color: #222;
}

.mentor-phone,
.mentor-email {
    color: var(--black);
    font-size: 0.9rem;
    margin: 0;
}

.mentor-email {
    text-decoration: none;
    color: var(--purple);
}

.mentor-info {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    font-size: 0.9rem;
    margin-left: 70px;
    margin-top: 30px;
    margin-bottom: 30px;
}

.info-row {
    display: flex;
    align-items: center;
    gap: 4rem;
}

.info-label {
    font-weight: bold;
    color: var(--black);
    width: 60px;
    text-align: left;
}

.info-value {
    color: var(--black);
    flex: 1;
    text-align: left;
    font-weight: bold;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
    .mentor-card {
        width: 90%;
        padding: 1.5rem;
    }

    .mentor-card-header {
        flex-direction: column;
        align-items: center;
        text-align: center;
    }

    .mentor-header-details {
        align-items: center;
    }

    .mentor-info {
        margin-left: 0;
    }

    .info-row {
        flex-direction: column;
        gap: 0.3rem;
        text-align: center;
    }
}

@media (max-width: 480px) {
    .mentor-header h1 {
        font-size: 1.5rem;
    }

    .mentor-image-container {
        width: 100px;
        height: 100px;
        margin-left: 0;
    }

    .mentor-card {
        padding: 1rem;
    }

    .mentor-info {
        margin-top: 20px;
        margin-bottom: 20px;
    }
}
</style>
