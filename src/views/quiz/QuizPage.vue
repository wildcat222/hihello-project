<template>
  <div class="quiz-container">
    <h1>퀴즈</h1>
    <white-box v-if="currentQuiz">
      <div class="quiz-number">
        <h2>Q{{ currentQuizIndex + 1 }}</h2> <!-- 퀴즈 번호 변경 -->
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
    </white-box>
  </div>
</template>

<script>
import { fetchQuizzes, submitQuizAnswer } from "@/services/QuizApi";
import WhiteBox from "@/components/WhiteBoxComponent.vue";

export default {
  components: {
    WhiteBox,
  },
  data() {
    return {
      quizzes: [],
      currentQuizIndex: 0,
      quizCategorySeq: 1, // 예제 카테고리 Seq
      employeeSeq: 1, // 예제 직원 ID
    };
  },
  computed: {
    currentQuiz() {
      return this.quizzes[this.currentQuizIndex] || null;
    },
  },
  async mounted() {
    try {
      this.quizzes = await fetchQuizzes(this.quizCategorySeq);
    } catch (error) {
      console.error("Failed to load quizzes:", error);
    }
  },
  methods: {
    async handleAnswer(userAnswer) {
      if (!this.currentQuiz) return;

      // 정답 판별
      const isCorrect = this.currentQuiz.quiz_answer === (userAnswer ? 1 : 0);

      try {
          console.log("Submitting quiz answer:", {
              quiz_seq: this.currentQuiz.quiz_seq,
              correct_status: isCorrect,
          });

          await submitQuizAnswer(
              this.quizCategorySeq,
              this.employeeSeq,
              this.currentQuiz.quiz_seq,
              isCorrect
          );

          // 다음 퀴즈로 이동
          if (this.currentQuizIndex < this.quizzes.length - 1) {
              this.currentQuizIndex++;
          } else {
              this.$router.push("/quiz/result");
          }
      } catch (error) {
          console.error("Error submitting quiz answer:", error.response?.data || error.message);
          alert("퀴즈 제출에 실패했습니다. 다시 시도해주세요.");
      }
    },
  },
};
</script>

<style scoped>
.quiz-container {
  text-align: center;
  padding: 20px;
}

.quiz-number h2 {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.quiz-question p {
  font-size: 1.2rem;
  margin-bottom: 20px;
}

.quiz-options {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
  margin-bottom: 30px;
}

.quiz-option {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 120px;
  height: 120px;
  border: 2px solid transparent;
  border-radius: 50%;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.3s;
}

.o-option {
  background-color: #007bff;
  color: white;
}

.x-option {
  background-color: #ff4d4d;
  color: white;
}

.symbol {
  font-size: 2rem;
  font-weight: bold;
}

.text {
  margin-top: 10px;
  font-size: 1rem;
}

.divider {
  width: 2px;
  height: 100px;
  background-color: #ddd;
}
</style>
