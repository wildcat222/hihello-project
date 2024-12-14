<template>
  <div class="quiz-result-container">
    <white-box>
      <h1 class="quiz-result-title">퀴즈 결과</h1>
      <div class="quiz-result-list">
        <div
          v-for="(result, index) in quizResults"
          :key="index"
          class="quiz-result-item"
        >
          <!-- 문제 번호 -->
          <div class="quiz-result-question">
            Q{{ index + 1 }}.
          </div>
          <!-- 정답 여부 (✔️ 또는 ❌) -->
          <div
            class="quiz-result-status"
            :class="{ correct: result.isCorrect, incorrect: !result.isCorrect }"
          >
            {{ result.isCorrect ? "✔️" : "❌" }}
          </div>
          <!-- 정답 해설 -->
          <div class="quiz-result-answer">
            정답 : {{ result.correctAnswer }}
          </div>
        </div>
      </div>
      <button class="quiz-result-finish-btn" @click="handleFinish">
        완료
      </button>
    </white-box>
  </div>
</template>

<script>
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import "@/styles/quiz/QuizResult.css"

export default {
  components: {
    WhiteBox,
  },
  data() {
    return {
      quizResults: [
        { isCorrect: false, correctAnswer: "한화 시스템은 대기업이다." },
        { isCorrect: true, correctAnswer: "부장은 팀장보다 높다." },
        { isCorrect: false, correctAnswer: "한화 시스템은 대기업이다." },
        { isCorrect: true, correctAnswer: "부장은 팀장보다 높다." },

      ],
    };
  },
  methods: {
    handleFinish() {
      this.$router.push("/dashboard");
    },
  },
};
</script>

<style scoped>
/* 컴포넌트에 따로 적용한 css는 scoped 속성을 이용해야 함 */
.white-box {
  width: fit-content; 
  padding: 2vw; 
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); 
  border-radius: 12px; 
  background-color: var(--white);
  overflow: hidden; 
}
</style>
