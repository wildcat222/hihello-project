<template>
  <div class="quiz-result-container">
    <white-box>
      <h1 class="quiz-result-title">퀴즈 결과</h1>
      <div class="quiz-result-list">
        <div
          v-for="(result, index) in quizResults"
          :key="result.quiz_number"
          class="quiz-result-item"
        >
          <!-- 문제 번호 -->
          <div class="quiz-result-question">
            Q{{ result.quiz_number }}.
          </div>
          <!-- 정답 여부 (✔️ 또는 ❌) -->
          <div
            class="quiz-result-status"
            :class="{ correct: result.quiz_correct_status === 1, incorrect: result.quiz_correct_status !== 1 }"
          >
            {{ result.quiz_correct_status === 1 ? "✔️" : "❌" }}
          </div>
          <!-- 정답 해설 -->
          <div class="quiz-result-answer">
            정답 : {{ result.quiz_explanation }}
          </div>
        </div>
      </div>
      <button class="quiz-result-finish-btn" @click="handleFinish">
        완료
      </button>
    </white-box>
  </div>
</template>

<script setup>
import "@/styles/quiz/QuizResult.css";
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useQuizStore } from "@/stores/QuizStore";
import { useUserStore } from "@/stores/UserStore";
import { fetchQuizResults } from "@/services/QuizApi";

const route = useRoute();

const userStore = useUserStore();
const quizStore = useQuizStore();

const quizResults = ref([]);
const quizCategorySeq = quizStore.quizCategorySeq;


const fetchResults = async () => {
  const employeeInfo = userStore.getEmployeeInfo();

  try {
    if (!quizCategorySeq) {
      console.error("Quiz Category Seq is missing.");
      return;
    }
    quizResults.value = await fetchQuizResults(quizCategorySeq, employeeInfo.employeeSeq);
  } catch (error) {
    console.error("Failed to fetch quiz results:", error);
  }
};

onMounted(() => {
  fetchResults();
});

</script>

<style scoped>
.white-box {
  width: fit-content;
  padding: 2vw;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  background-color: var(--white);
  overflow: hidden;
}
</style>

