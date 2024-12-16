// stores/quizStore.js
import { defineStore } from 'pinia';

export const useQuizStore = defineStore('quizStore', {
  state: () => ({
    quizCategorySeq: 1,
    quizItems: [],
  }),
  actions: {
    setQuizCategorySeq(seq) {
      this.quizCategorySeq = seq;
    },
    setQuizItems(items) {
      this.quizItems = items;
    },
    clearQuizItems() {
      this.quizItems = [];
    },
  },
  persist: {
    storage: sessionStorage, 
  },
});
