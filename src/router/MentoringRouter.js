import MentoringPlanningListPage from "@/views/mentoring/MentoringPlanningListPage.vue";
import MentoringPlanningCreatePage from "@/views/mentoring/MentoringPlanningCreatePage.vue";
import MentoringPlanningDetailPage from "@/views/mentoring/MentoringPlanningDetailPage.vue";
import MentoringMatchingCreatePage from "@/views/mentoring/MentoringMatchingCreatePage.vue";

export default [
    {
        path: '/mentoring/planning',
        component: MentoringPlanningListPage
    },
    {
        path: '/mentoring/planning/create',
        component: MentoringPlanningCreatePage
    },
    {
        path: '/mentoring/planning/:planningSeq',
        component:MentoringPlanningDetailPage,
        props: true
    },
    {
        path: '/mentoring/matching',
        component: MentoringMatchingCreatePage
    }
]