import MentoringPlanningListPage from "@/views/mentoring/MentoringPlanningListPage.vue";
import MentoringPlanningCreate from "@/views/mentoring/MentoringPlanningCreate.vue";
export default [
    {
        path: '/mentoring/planning',
        component: MentoringPlanningListPage
    },
    {
        path: '/mentoring/planning/create',
        component: MentoringPlanningCreate
    }
]