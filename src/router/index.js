import{ createRouter, createWebHistory } from 'vue-router';
import QuizRouter from '@/router/QuizRouter.js';
import WikiRouter from "@/router/WikiRouter.js";
import UserRouter from '@/router/UserRouter';
import MainRouter from '@/router/MainRouter';
import TaskEvalRouter from "@/router/TaskEvalRouter.js";

const routes = [
    ...QuizRouter,
    ...WikiRouter,
    ...TaskEvalRouter,
    ...UserRouter,
    ...MainRouter
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