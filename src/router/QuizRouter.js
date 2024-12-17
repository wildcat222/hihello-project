import HrQuizPage from "@/views/quiz/HrQuizPage.vue";
import QuizPage from "@/views/quiz/QuizPage.vue";
import QuizResultPage from "@/views/quiz/QuizResultPage.vue";

export default [
    {
        path: '/quiz',
        component: QuizPage,
    },
    {
        path: '/quiz/result',
        component: QuizResultPage,
    },
    {
        path: '/hr/quiz',
        component: HrQuizPage,
    }
]