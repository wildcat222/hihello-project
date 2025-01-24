import FinalEvalListReadPage from "@/views/final-eval/FinalEvalListReadPage.vue";
import FinalEvalDetailReadPage from "@/views/final-eval/FinalEvalDetailReadPage.vue";

export default [
    {
        path: '/final-eval',
        component: FinalEvalListReadPage
    },
    {
        path: `/final-eval/:employeeSeq`,
        component: FinalEvalDetailReadPage,
        props: true
    }
]