<template>
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-content">
      <h2 class="modal-title">퀴즈 등록</h2>

      <!-- 문제 입력 -->
      <div class="form-group">
        <label for="question">문제 내용</label>
        <textarea
          id="question"
          v-model="quizQuestion"
          placeholder="문제를 입력하세요"
        ></textarea>
      </div>

      <!-- 정답 선택 -->
      <div class="form-group">
        <label>정답</label>
        <div class="answer-options">
          <label>
            <input type="radio" value="true" v-model="quizAnswer" />
            <span class="radio-custom"></span> O
          </label>
          <label>
            <input type="radio" value="false" v-model="quizAnswer" />
            <span class="radio-custom"></span> X
          </label>
        </div>
      </div>

      <!-- 설명 입력 -->
      <div class="form-group">
        <label for="explanation">설명</label>
        <textarea
          id="explanation"
          v-model="quizExplanation"
          placeholder="설명을 입력하세요"
        ></textarea>
      </div>

      <!-- 버튼 -->
      <div class="button-container">
        <button class="cancel-button" @click="$emit('close')">취소</button>
        <button
          class="save-button"
          :disabled="!isFormValid"
          @click="registerQuiz"
        >
          등록
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import { postHrQuiz } from "@/services/QuizApi";

// Props로 quizCategorySeq 받기
const { quizCategorySeq } = defineProps({
    quizCategorySeq: {
        type: Number,
        required: true,
    },
});

const quizQuestion = ref("");
const quizAnswer = ref(null);
const quizExplanation = ref("");

const emit = defineEmits(["close", "quiz-added"]);

const resetForm = () => {
    quizQuestion.value = "";
    quizAnswer.value = null;
    quizExplanation.value = "";
};

const isFormValid = computed(() => {
  return quizQuestion.value.trim() !== "" && quizAnswer.value !== null;
});

const registerQuiz = async () => {
    if (!quizQuestion.value || quizAnswer.value === null) {
        alert("모든 필드를 입력해주세요.");
        return;
    }

    const newQuiz = {
        quizQuestion: quizQuestion.value,
        quizAnswer: quizAnswer.value === "true" || quizAnswer.value === true ? 1 : 0,
        quizExplanation: quizExplanation.value,
    };

    try {
        const response = await postHrQuiz(quizCategorySeq, newQuiz);
        if (response && response.success) {
            emit("quiz-added", response.data);
            resetForm();
            emit("close");
        } else {
            throw new Error(response.message || "등록에 실패했습니다.");
        }
    } catch (error) {
        console.error("퀴즈 등록 실패:", error.response?.data || error.message);
        alert("퀴즈 등록 중 오류가 발생했습니다. 다시 시도해주세요.");
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
  margin-bottom: 15px;
}

label {
  font-size: 1rem;
  font-weight: 500;
  color: #555;
}

textarea {
  width: 95%;
  padding: 8px 12px;
  margin-top: 5px;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
  transition: border 0.3s ease;
  font-size: 0.9rem;
}

textarea:focus {
  border-color: #7a5af5;
  box-shadow: 0 0 5px rgba(122, 90, 245, 0.3);
}

.answer-options {
  display: flex;
  gap: 15px;
  margin-top: 8px;
}

input[type="radio"] {
  display: none;
}

.radio-custom {
  display: inline-block;
  width: 14px;
  height: 14px;
  border: 2px solid #7a5af5; /* 더 선명한 색상 */
  background-color: #fff; /* 흰색 배경 */
  border-radius: 50%; /* 완전한 원형 */
  position: relative;
  transition: all 0.3s ease; /* 전체적인 부드러운 전환 효과 */
  cursor: pointer;
}

input[type="radio"]:checked + .radio-custom {
  border-color: #7a5af5; /* 선택된 상태에서 더 강조된 색상 */
  background-color: #7a5af5; /* 선택 시 배경 색 변경 */
}

input[type="radio"]:checked + .radio-custom::after {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  width: 7px;
  height: 7px;
  background-color: #fff; /* 선택된 내부 원의 색상 */
  border-radius: 50%; /* 내부 원도 동그랗게 */
  transform: translate(-50%, -50%); /* 원의 중심 정렬 */
}

.button-container {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.save-button,
.cancel-button {
  padding: 8px 16px;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.save-button {
  background-color: #7a5af5;
  color: white;
}

.save-button:disabled {
  background-color: #aaa;
  cursor: not-allowed;
}

.cancel-button {
  background-color: #ddd;
  color: #555;
}

.cancel-button:hover {
  background-color: #ccc;
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
