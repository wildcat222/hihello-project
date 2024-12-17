import { defineStore } from 'pinia';

export const useQuizStore = defineStore('quizStore', {
  state: () => ({
    // 일반 퀴즈 상태
    userQuizCategorySeq: 1,
    userQuizItems: [],

    // HR 퀴즈 상태
    hrQuizCategorySeq: null,
    hrQuizItems: [],
  }),
  actions: {
    // 일반 퀴즈 메서드
    setUserQuizCategorySeq(seq) {
      this.userQuizCategorySeq = seq;
    },
    setUserQuizItems(items) {
      this.userQuizItems = items;
    },
    clearUserQuizItems() {
      this.userQuizItems = [];
    },
  },
  persist: {
    storage: sessionStorage,
  },
});