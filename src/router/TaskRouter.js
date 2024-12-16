import TaskAddPage from "@/views/task/TaskAddPage.vue";
import TaskListPage from "@/views/task/TaskListPage.vue";

export default[
    {
        path: '/TaskList',
        component: TaskListPage,
    },
    {
        path: '/TaskAdd',
        component: TaskAddPage,
    }
]