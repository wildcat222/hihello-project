import HrPeerReviewListPage from "@/views/peer-review/HrPeerReviewListPage.vue";
import HrPeerReviewPage from "@/views/peer-review/HrPeerReviewPage.vue";

export default[
    {
        path: '/hr/peer/review/list',
        component: HrPeerReviewListPage,
    },
    {
        path: '/hr/peer/review',
        component: HrPeerReviewPage,
    }
]