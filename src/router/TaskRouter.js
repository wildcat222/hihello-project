import TaskAddPage from "@/views/task/TaskAddPage.vue";
import TaskListPage from "@/views/task/TaskListPage.vue";
import MentorTaskDetailReadPage from "@/views/task/MentorTaskDetailReadPage.vue";
import GroupingPage from "@/views/task/GroupingPage.vue";
import TaskModifyPage from "@/views/task/TaskModifyPage.vue";

export default[
    {
        path: '/task/list',
        component: TaskListPage,
    },
    {
        path: '/task/add',
        name: 'TaskAddPage',
        component: TaskAddPage,
    },
    {
        path: '/task/modify/:taskSeq',
        component: TaskModifyPage
    },
    {
        path: '/task-submit/:taskSubmitSeq',
        component: MentorTaskDetailReadPage
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