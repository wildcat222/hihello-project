import TaskAddPage from "@/views/task/TaskAddPage.vue";
import TaskListPage from "@/views/task/TaskListPage.vue";
import MentorTaskDetailReadPage from "@/views/task/MentorTaskDetailReadPage.vue";
import GroupingPage from "@/views/task/GroupingPage.vue";
import TaskModifyPage from "@/views/task/TaskModifyPage.vue";
import MenteeTaskDetailReadPage from "@/views/task/MenteeTaskDetailReadPage.vue";

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
        component: TaskModifyPage,
        props: true,  // URL 파라미터를 컴포넌트에 props로 전달
    },
    {
        path: '/task-submit/:taskSubmitSeq',
        component: MentorTaskDetailReadPage
    },
    {
        path: '/task/:taskSeq',
        component: MenteeTaskDetailReadPage
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