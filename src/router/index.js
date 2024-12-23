import{ createRouter, createWebHistory } from 'vue-router';
import QuizRouter from '@/router/QuizRouter.js';
import WikiRouter from "@/router/WikiRouter.js";
import UserRouter from '@/router/UserRouter';
import MainRouter from '@/router/MainRouter';
import TaskEvalRouter from "@/router/TaskEvalRouter.js";
import TaskRouter from '@/router/TaskRouter.js';
import FinalEvalRouter from "@/router/FinalEvalRouter.js";
import MentoringRouter from "@/router/MentoringRouter.js";
import PeerReviewRouter from '@/router/PeerReviewRouter.js';
import ChatbotRouter from '@/router/ChatbotRouter.js';
import TaskIndRouter from "@/router/TaskIndRouter.js";
import OnboardingRouter from "@/router/OnboardingRouter.js";
import {useUserStore} from "@/stores/UserStore.js";
import {useSSEStore} from "@/stores/sse.js";

const routes = [
    ...QuizRouter,
    ...WikiRouter,
    ...TaskEvalRouter,
    ...UserRouter,
    ...MainRouter,
    ...TaskRouter,
    ...FinalEvalRouter,
    ...MentoringRouter,
    ...PeerReviewRouter,
    ...ChatbotRouter,
    ...TaskIndRouter,
    ...OnboardingRouter,
]

const router = createRouter({
    history : createWebHistory(),
    routes,

    scrollBehavior (to, from, savedPosition) {
        
        if(savedPosition){
            return savedPosition;
        }else{
            return { top: 0 };
        }
    }
});

router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore();
    const sseStore = useSSEStore();

    // SSE 연결 상태 확인 및 처리
    if (userStore.accessToken && sseStore.connectionStatus === 'disconnected') {
        console.log('SSE 연결 시도 중...');
        await sseStore.connectSSE();
    }

    // 인증된 사용자가 '/' 경로로 접근하는 경우
    if (to.path === '/' && userStore.accessToken) {
        // 대시보드나 다른 메인 페이지로 리다이렉트
        next({ path: '/main' }); // 또는 적절한 라우트 이름
        return;
    }

    next();
});

export default router;