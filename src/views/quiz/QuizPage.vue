<template>
  <div class="quiz-container">
    <h1>퀴즈</h1>
    <white-box v-if="currentQuiz">
      <div class="white-box-content">
        <div class="quiz-number">
          <h2>Q{{ currentQuizIndex + 1 }}</h2>
        </div>
        <div class="quiz-question">
          <p>{{ currentQuiz.quiz_question }}</p>
        </div>
        <div class="quiz-options">
          <div
            class="quiz-option o-option"
            @click="handleAnswer(true)"
          >
            <span class="symbol">O</span>
            <span class="text">그렇다</span>
          </div>
          <div class="divider"></div>
          <div
            class="quiz-option x-option"
            @click="handleAnswer(false)"
          >
            <span class="symbol">X</span>
            <span class="text">아니다</span>
          </div>
        </div>
      </div>
    </white-box>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/UserStore";
import { useQuizStore } from "@/stores/QuizStore";
import { fetchQuizzes, submitQuizAnswer } from "@/services/QuizApi";
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import "@/styles/quiz/Quiz.css";

const router = useRouter();
const userStore = useUserStore();
const quizStore = useQuizStore();

const quizzes = ref([]);
const currentQuizIndex = ref(0);

const currentQuiz = computed(() => quizzes.value[currentQuizIndex.value] || null);

const loadQuizzes = async () => {
  const employeeInfo = userStore.getEmployeeInfo();

  if (!employeeInfo || !quizStore.quizCategorySeq) {
    console.error("quizCategorySeq or employeeInfo is missing");
    return;
  }

  try {
    quizzes.value = await fetchQuizzes(quizStore.quizCategorySeq);
  } catch (error) {
    console.error("Failed to load quizzes:", error);
  }
};

const handleAnswer = async (userAnswer) => {
  const employeeInfo = userStore.getEmployeeInfo();

  if (!currentQuiz.value || !employeeInfo) {
    console.error("Current quiz or employee info is missing");
    return;
  }

  const isCorrect = currentQuiz.value.quiz_answer === (userAnswer ? 1 : 0);

  try {
    await submitQuizAnswer(
      quizStore.quizCategorySeq,
      employeeInfo.employeeSeq, // Use the employeeSeq from the store
      currentQuiz.value.quiz_seq,
      isCorrect
    );

    if (currentQuizIndex.value < quizzes.value.length - 1) {
      currentQuizIndex.value++;
    } else {
      router.push(`/quiz/${quizStore.quizCategorySeq}/result`);
    }
  } catch (error) {
    console.error("Error submitting quiz answer:", error.response?.data || error.message);
    alert("퀴즈 제출에 실패했습니다. 다시 시도해주세요.");
  }
};

onMounted(() => {
  // Ensure employee info is initialized before loading quizzes
  const employeeInfo = userStore.getEmployeeInfo();
  if (!employeeInfo) {
    console.error("Employee info is missing");
  }
  loadQuizzes();
});
</script>
