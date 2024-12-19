import LoginPage from "@/views/user/LoginPage.vue";
import MentorInfoPage from "@/views/user/MentorInfoPage.vue";
import MenteeInfoPage from "@/views/user/MenteeInfoPage.vue";
import UpdatePassword from "@/views/user/UpdatePassword.vue";
import EmployeeListPage from "@/views/user/hr/views/employee/EmployeeListPage.vue";
import AddEmployeePage from "@/views/user/hr/views/employee/AddEmployeePage.vue";

export default[
    {
        path: '/',
        component: LoginPage,
        meta: { hideAside : true },
        beforeEnter: () => {
            // const userStore = useUserStore();
            // userStore.logout(); // 로그인 페이지 접근 시 로그아웃
            // 로그인 상태일 때 접근 못하게
        },
    },
    {
        path: '/mentor/intro',
        component: MentorInfoPage,
    },
    {
        path: '/mentee/intro',
        component: MenteeInfoPage,
    },
    {
        path: '/employee/:employeeSeq/password',
        component: UpdatePassword,
    },
    {
        path: '/employee-management',
        component: EmployeeListPage
    },
    {
        path: '/employee-management/create',
        component: AddEmployeePage
    }
]