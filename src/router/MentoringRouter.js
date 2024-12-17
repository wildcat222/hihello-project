import MentoringPlanningListPage from "@/views/mentoring/MentoringPlanningListPage.vue";
import MentoringPlanningCreatePage from "@/views/mentoring/MentoringPlanningCreatePage.vue";
export default [
    {
        path: '/mentoring/planning',
        component: MentoringPlanningListPage
    },
    {
        path: '/mentoring/planning/create',
        component: MentoringPlanningCreatePage
    }
]