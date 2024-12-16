import { ref, computed } from 'vue';
import axios from 'axios';
import { useUserStore } from "@/stores/UserStore.js";

export function useTask() {
    const searchQuery = ref('');
    const taskItems = ref([]);
    const selectedTaskSeq = ref(null);
    const deleteMessage = ref('');
    const isDeleteModalVisible = ref(false);

    const token = localStorage.getItem('accessToken');
    const userStore = useUserStore();
    const employeeInfo = userStore.getEmployeeInfo();
    const employeeRole = employeeInfo.employeeRole;

    const formattedTitle = computed(() => {
        return '<span class="underline">삭제</span> 하고자 하는 과제가 맞나요? ';
    });

    // API 호출
    const fetchTasks = async () => {
        try {
            const url = employeeRole === 'MENTOR'
                ? 'http://localhost:8253/api/v1/mentor/task'
                : 'http://localhost:8253/api/v1/hr/task';

            const response = await axios.get(url, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.data.success) {
                taskItems.value = response.data.data.map(task => ({
                    title: task.taskTitle,
                    departmentName: task.departmentName,
                    templateTaskRound: task.templateTaskRound,
                    seq: task.taskSeq
                }));
            }
        } catch (error) {
            console.error('API 요청 실패:', error);
        }
    };

    const handleSearch = async (query) => {
        try {
            const response = await axios.get('http://localhost:8253/api/v1/task', {
                params: { taskContent: query },
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                },
            });
            if (response.data.success) {
                taskItems.value = response.data.data.map(task => ({
                    title: task.taskTitle,
                    departmentName: task.departmentName,
                    templateTaskRound: task.templateTaskRound,
                    seq: task.taskSeq,
                }));
            }
        } catch (error) {
            console.error('API 요청 실패:', error);
        }
    };

    const formatDeleteMessage = (message) => {
        return message.replace(/\n/g, '<br>');
    };

    const openDeleteModal = (taskSeq, departmentName, templateTaskRound, taskTitle) => {
        selectedTaskSeq.value = taskSeq;
        deleteMessage.value = `과제 번호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${taskSeq}\n과제 부서&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${departmentName}\n과제 유형&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${templateTaskRound}\n과제 제목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${taskTitle}`;
        isDeleteModalVisible.value = true;
    };

    const deleteTask = async () => {
        try {
            const response = await axios.delete(`http://localhost:8253/api/v1/task/${selectedTaskSeq.value}`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('accessToken')}`
                }
            });

            if (response.data.success) {
                alert("삭제되었습니다.");
                taskItems.value = taskItems.value.filter(task => task.taskSeq !== selectedTaskSeq.value);
                await fetchTasks();
            }
        } catch (error) {
            console.error("삭제 실패:", error);
        } finally {
            isDeleteModalVisible.value = false;
        }
    };

    return {
        searchQuery,
        taskItems,
        selectedTaskSeq,
        deleteMessage,
        isDeleteModalVisible,
        formattedTitle,
        fetchTasks,
        handleSearch,
        formatDeleteMessage,
        openDeleteModal,
        deleteTask,
        employeeRole,
    };
}
