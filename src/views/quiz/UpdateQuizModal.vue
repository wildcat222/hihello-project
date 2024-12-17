<template>
    <div class="modal-overlay" @click.self="$emit('close')">
        <div class="modal-content">
            <h2 class="modal-title">퀴즈 수정</h2>

            <!-- 문제 입력 -->
            <div class="form-group">
                <label for="question">문제 내용</label>
                <textarea id="question" v-model="quizQuestion" placeholder="문제를 입력하세요"></textarea>
            </div>

            <!-- 정답 선택 -->
            <div class="form-group">
                <label>정답</label>
                <div class="answer-options">
                    <label>
                        <input type="radio" :value="true" v-model="quizAnswer" />
                        <span class="radio-custom"></span> O
                    </label>
                    <label>
                        <input type="radio" :value="false" v-model="quizAnswer" />
                        <span class="radio-custom"></span> X
                    </label>
                </div>
            </div>

            <!-- 설명 입력 -->
            <div class="form-group">
                <label for="explanation">설명</label>
                <textarea id="explanation" v-model="quizExplanation" placeholder="설명을 입력하세요"></textarea>
            </div>

            <!-- 버튼 -->
            <div class="button-container">
                <button class="cancel-button" @click="$emit('close')">취소</button>
                <button class="update-button" :disabled="!isFormValid" @click="updateQuiz">수정</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import { updateHrQuiz } from "@/services/QuizApi";

// Props 정의 (반드시 먼저 선언)
const props = defineProps({
    quizCategorySeq: { type: Number, required: true },
    quizData: { type: Object, required: true },
});

const emit = defineEmits(["close", "quiz-updated"]);

const quizSeq = ref(props.quizData.quizSeq);
const quizQuestion = ref(props.quizData.quizQuestion || "");
const quizAnswer = ref(props.quizData.quizAnswer);
const quizExplanation = ref(props.quizData.quizExplanation || "");

watch(
    () => props.quizData,
    (newData) => {
        quizSeq.value = newData.quizSeq;
        quizQuestion.value = newData.quizQuestion || "";
        quizAnswer.value = newData.quizAnswer;
        quizExplanation.value = newData.quizExplanation || "";
    },
    { immediate: true }
);

const isFormValid = computed(() => {
    return quizQuestion.value.trim() !== "" && quizAnswer.value !== null;
});

// 수정 API 호출
const updateQuiz = async () => {
    try {
        const requestBody = {
            quizQuestion: quizQuestion.value,
            quizAnswer: quizAnswer.value,
            quizExplanation: quizExplanation.value,
        };

        const response = await updateHrQuiz(
            props.quizCategorySeq,
            quizSeq.value,
            requestBody
        );

        emit("quiz-updated", { ...requestBody, quizSeq: quizSeq.value });
        emit("close");
    } catch (error) {
        console.error("퀴즈 수정 중 오류 발생:", error);
        alert("퀴즈 수정에 실패했습니다. 다시 시도해주세요.");
    }
};
</script>

<style scoped>
/* 모달 스타일 */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.6);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    animation: fadeIn 0.5s ease-out;
}

.modal-content {
    background: #ffffff;
    padding: 30px;
    border-radius: 12px;
    width: 35vw;
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
    animation: slideUp 0.5s ease-out;
}

.modal-title {
    margin-bottom: 20px;
    font-size: 1.8rem;
    color: #333;
    text-align: center;
}

.form-group {
    display: flex;
    align-items: center;
    gap: 15px;
    margin-bottom: 20px;
}

label {
    flex: 0 0 100px;
    font-size: 1rem;
    font-weight: 600;
    color: #555;
}

textarea,
.answer-options {
    flex: 1;
    width: 100%;
    padding: 10px 12px;
    border: 1px solid #ccc;
    border-radius: 6px;
    outline: none;
    font-size: 0.95rem;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

textarea:focus {
    border-color: #7a5af5;
    box-shadow: 0 0 5px rgba(122, 90, 245, 0.4);
}


.answer-options {
    display: flex;
    gap: 20px;
    
    align-items: center;
}

input[type="radio"] {
    display: none;
}

.radio-custom {
    display: inline-block;
    width: 18px;
    height: 18px;
    border: 2px solid var(--purple);
    border-radius: 50%;
    background-color: var(--white);
    position: relative;
    cursor: pointer;
    transition: all 0.3s ease;
}

input[type="radio"]:checked+.radio-custom {
    background-color: var(--purple);
}

input[type="radio"]:checked+.radio-custom::after {
    content: "";
    position: absolute;
    top: 50%;
    left: 50%;
    width: 8px;
    height: 8px;
    background-color: var(--white);
    border-radius: 50%;
    transform: translate(-50%, -50%);
}

/* 버튼 영역 */
.button-container {
    display: flex;
    justify-content: space-between;
    gap: 10px;
    margin-top: 30px;
}

.update-button,
.cancel-button {
    flex: 1;
    padding: 10px;
    font-size: 1rem;
    font-weight: bold;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.update-button {
    background-color: var(--purple);
    color: #fff;
}

.update-button:disabled {
    background-color: #ccc;
    color: #888;
}

.cancel-button {
    background-color: #f5f5f5;
    color: #555;
}

.cancel-button:hover {
    background-color: #e0e0e0;
}

@keyframes fadeIn {
    from {
        opacity: 0;
    }

    to {
        opacity: 1;
    }
}

@keyframes slideUp {
    from {
        transform: translateY(50px);
    }

    to {
        transform: translateY(0);
    }
}
</style>