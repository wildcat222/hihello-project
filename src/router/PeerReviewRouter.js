import HrPeerReviewListPage from "@/views/peer-review/HrPeerReviewListPage.vue";
import HrPeerReviewPage from "@/views/peer-review/HrPeerReviewPage.vue";
import GroupEval from "@/views/peer-review/GroupEval.vue";


export default[
    {
        path: '/hr/peer/review/list',
        component: HrPeerReviewListPage,
    },
    {
        path: '/hr/peer/review',
        component: HrPeerReviewPage,
    },
    {
        path: '/onboarding/groupEval',
        component: GroupEval,
    },
]