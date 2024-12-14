<template>
  <div class="quiz-container">
    <h1>퀴즈</h1>
    <white-box v-if="currentQuiz">
      <div class="white-box-content">
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
      </div>
    </white-box>
  </div>
</template>

<script>
import { fetchQuizzes, submitQuizAnswer } from "@/services/QuizApi";
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import "@/styles/quiz/Quiz.css"; // 스타일 파일을 import

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
