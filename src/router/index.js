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
import TaskIndRouter from "@/router/TaskIndRouter.js";
import OnboardingRouter from "@/router/OnboardingRouter.js";

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
    ...TaskIndRouter,
    ...OnboardingRouter
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

export default router;