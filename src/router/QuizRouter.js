import HrQuizPage from "@/views/quiz/HrQuizPage.vue";
import HrQuizResultPage from "@/views/quiz/HrQuizResultPage.vue";
import QuizPage from "@/views/quiz/QuizPage.vue";
import QuizResultPage from "@/views/quiz/QuizResultPage.vue";

export default [
    {
        path: '/quiz/:quizCategorySeq',
        component: QuizPage,
    },
    {
        path: '/quiz/result',
        component: QuizResultPage,
    },
    {
        path: '/hr/quiz',
        component: HrQuizPage,
    },
    {
        path: '/hr/quiz/result',
        component: HrQuizResultPage,
    }
]