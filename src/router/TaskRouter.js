import TaskAddPage from "@/views/task/TaskAddPage.vue";
import TaskListPage from "@/views/task/TaskListPage.vue";
import TaskEvalResultDetailReadModal from "@/views/task-eval/TaskEvalResultDetailReadModal.vue";
import MentorTaskDetailReadPage from "@/views/task/MentorTaskDetailReadPage.vue";

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
        path: '/task-submit/:taskSubmitSeq',
        component: MentorTaskDetailReadPage
    }
]