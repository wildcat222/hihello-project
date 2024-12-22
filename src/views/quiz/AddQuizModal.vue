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
          <label class="quiz-add-answer-line">
            <input type="radio" value="true" v-model="quizAnswer" />
            <span class="radio-custom"></span> O
          </label>
          <label class="quiz-add-answer-line">
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
        quizAnswer: quizAnswer.value === "true" || quizAnswer.value === true, // Boolean 값으로 처리
        quizExplanation: quizExplanation.value,
    };

    try {
        const response = await postHrQuiz(quizCategorySeq, newQuiz);
        if (response && response.success) {
            emit("quiz-added", {
                ...newQuiz,
                quizSeq: response.data.quizSeq || Date.now(), // 추가된 데이터에 quizSeq 할당
            });
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
.quiz-add-answer-line{
  display: flex;
  align-items: center;
}
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
    border: 2px solid #7a5af5;
    border-radius: 50%;
    background-color: #fff;
    position: relative;
    cursor: pointer;
    transition: all 0.3s ease;
    margin-right: 10px;
}

input[type="radio"]:checked + .radio-custom {
    background-color: #7a5af5;
}

input[type="radio"]:checked + .radio-custom::after {
    content: "";
    position: absolute;
    top: 50%;
    left: 50%;
    width: 8px;
    height: 8px;
    background-color: #fff;
    border-radius: 50%;
    transform: translate(-50%, -50%);
}

.button-container {
    display: flex;
    justify-content: space-between;
    gap: 10px;
    margin-top: 30px;
}

.save-button,
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

.save-button {
    background-color: var(--purple);
    color: var(--white);
}

.save-button:disabled {
    background-color: var(--light-gray);
    color: var(--gray);
}

.cancel-button {
    background-color: var(--light-gray);
    color: var(--black);
}

.cancel-button:hover {
    background-color: var(--gray);
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

