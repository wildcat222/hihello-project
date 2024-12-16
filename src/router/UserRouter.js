import LoginPage from "@/views/user/LoginPage.vue";
import { useUserStore } from '@/stores/UserStore';
import MentorInfoPage from "@/views/user/MentorInfoPage.vue";
import MenteeInfoPage from "@/views/user/MenteeInfoPage.vue";

export default[
    {
        path: '/',
        component: LoginPage,
        meta: { hideAside : true },
        beforeEnter: () => {
            const userStore = useUserStore();
            userStore.logout(); // 로그인 페이지 접근 시 로그아웃
        },
    },
    {
        path: '/mentor/intro',
        component: MentorInfoPage,
    },
    {
        path: '/mentee/intro',
        component: MenteeInfoPage,
    }
]