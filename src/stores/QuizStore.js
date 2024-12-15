// stores/quizStore.js
import { defineStore } from 'pinia';

export const useQuizStore = defineStore('quizStore', {
  state: () => ({
    quizCategorySeq: 1, // quizCategorySeq 값 관리 기본값 1로 설정 추후 고쳐야 함
  }),
  actions: {
    setQuizCategorySeq(seq) {
      this.quizCategorySeq = seq;
    },
  },
});
