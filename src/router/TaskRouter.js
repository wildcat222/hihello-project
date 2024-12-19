import TaskAddPage from "@/views/task/TaskAddPage.vue";
import TaskListPage from "@/views/task/TaskListPage.vue";
import GroupingPage from "@/views/task/GroupingPage.vue";

export default[
    {
        path: '/TaskList',
        component: TaskListPage,
    },
    {
        path: '/TaskAdd',
        component: TaskAddPage,
    },
    {
        path: '/grouping',
        component: GroupingPage,
        props: (route) => ({
            templateType: route.query.template_type,
            departmentSeq: route.query.department_seq,
            templateSeq: route.query.template_seq, // 추가
        }),
    }
]