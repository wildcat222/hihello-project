import LoginPage from "@/views/user/LoginPage.vue";
import { useUserStore } from '@/stores/UserStore';

export default[
    {
        path: '/',
        component: LoginPage,
        meta: { hideAside : true },
        beforeEnter: () => {
            const userStore = useUserStore();
            userStore.logout(); // 로그인 페이지 접근 시 로그아웃
        },
    }
]