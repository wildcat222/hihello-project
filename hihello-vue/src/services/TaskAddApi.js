import {ref, onMounted, toRaw} from 'vue';
import { springAPI } from "@/services/axios.js";
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from "@/stores/UserStore.js";

export function useTask() {
    const taskType = ref('PERSONAL');
    const fileName = ref('');
    const tableData = ref([]);
    const taskSubmitSeq = ref(1);
    const isLoading = ref(false);
    const taskTitle = ref('');
    const taskContent = ref('');
    const departmentSeq = ref('');
    const round = ref('1주차');
    const router = useRouter();
    const route = useRoute();
    const groupsData = ref([]);
    const departments = ref([]);
    const taskRounds = ref([]);
    const templateSeq = ref('');
    const userStore = useUserStore();
    const employeeInfo = userStore.getEmployeeInfo();
    const employeeRole = employeeInfo.employeeRole;

    // 로컬 스토리지에 상태 저장
    const saveStateToLocalStorage = () => {
        const state = {
            taskType: taskType.value,
            fileName: fileName.value,
            tableData: tableData.value,
            taskTitle: taskTitle.value,
            taskContent: taskContent.value,
            departmentSeq: departmentSeq.value,
            templateSeq: templateSeq.value,
        };
        localStorage.setItem('taskState', JSON.stringify(state));
    };

    // 로컬 스토리지에서 상태 복원
    const loadStateFromLocalStorage = () => {
        const savedState = localStorage.getItem('taskState');
        if (savedState) {
            const state = JSON.parse(savedState);
            taskType.value = state.taskType || 'PERSONAL';
            fileName.value = state.fileName || '';
            tableData.value = state.tableData || [];
            taskTitle.value = state.taskTitle || '';
            taskContent.value = state.taskContent || '';
            departmentSeq.value = state.departmentSeq || '';
            templateSeq.value = state.templateSeq || '';
        }
    };

    onMounted(() => {
        if (route.query.groupsData) {
            try {
                groupsData.value = JSON.parse(route.query.groupsData);
                // console.log('받아온 그룹 데이터:', groupsData.value);
            } catch (error) {
                console.error('Invalid groupsData format:', error);
            }
        }
        loadStateFromLocalStorage();
    });

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            fileName.value = file.name.length > 15
                ? file.name.substring(0, 10) + '...'
                : file.name;
            // console.log('파일 이름:', fileName.value);
        }
    };

    const fetchData = async () => {
        if (isLoading.value) return;
        isLoading.value = true;

        try {
            const response = await springAPI.get('/hr/eval/ind', {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
                },
            });

            if (response.data?.data) {
                tableData.value = response.data.data.map(item => ({
                    ...item,
                    newContent: '',
                }));
            }
        } catch (error) {
            console.error('API 요청 실패:', error);
        } finally {
            isLoading.value = false;
        }
    };

    const addRow = (index) => {
        const content = tableData.value[index].evalListContent;
        if (!content) {
            return alert('내용을 입력해주세요.');
        }

        tableData.value[index].addedContent = content;

        tableData.value.splice(index + 1, 0, {
            evalIndSeq: tableData.value[index].evalIndSeq,
            evalIndContent: tableData.value[index].evalIndContent,
            newContent: '',
            evalListContent: '',
            evalListScore: '',
        });

        tableData.value[index + 1].newContent = '';
    };

    const deleteRow = (index) => {
        if (index > 0) {
            tableData.value.splice(index, 1);
        }
    };

    const back = () => {
        loadStateFromLocalStorage(); // 뒤로 갈 때 상태 복원
        window.history.back();
    };

    const submitTask = async () => {
        const formData = new FormData();

        const taskCreateDTO = {
            departmentSeq: departmentSeq.value,
            templateSeq: templateSeq.value,
            taskType: taskType.value,
            taskTitle: taskTitle.value,
            taskContent: taskContent.value,
            evalIndicators: tableData.value.map(item => ({
                evalListContent: item.evalListContent,
                evalListScore: item.evalListScore,
                evalIndSeq: item.evalIndSeq,
            }))
        };

        // taskType이 "GROUP"인 경우에만 group 데이터를 추가
        if (taskType.value === 'GROUP') {
            const rawGroupsData = toRaw(groupsData.value);

            if (!rawGroupsData || !rawGroupsData.tasks || rawGroupsData.tasks.length === 0) {
                alert('그룹 데이터가 없습니다. 그룹을 추가한 후 다시 시도하세요.');
                return;
            }

            // TaskRequestWrapper 형태로 객체를 전달해야 하므로 배열이 아닌 객체로 수정
            taskCreateDTO.tasks = {
                tasks: rawGroupsData.tasks.map(group => ({
                    members: group.members // 그룹 멤버들만 포함
                }))
            };
        }

        // taskType이 "GROUP"이 아닌 경우 tasks 필드를 아예 생략
        else {
            delete taskCreateDTO.tasks;
        }

        const fileInput = document.getElementById('fileInput');
        const file = fileInput?.files[0];

        if (file) {
            taskCreateDTO.fileName = file.name;
            formData.append('fileUrl', file);
        }

        formData.append('taskCreateDTO', JSON.stringify(taskCreateDTO));

        try {
            const response = await springAPI.post('/task', formData, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                    'Content-Type': 'multipart/form-data',
                },
            });

            alert('과제가 등록되었습니다.');
            localStorage.removeItem('taskState');
            window.location.href = '/task/list';
        } catch (error) {
            alert('과제 등록에 실패했습니다.');
            console.error(error);
        }
    };

    const goToGroupingPage = () => {
        saveStateToLocalStorage(); // 상태 저장
        const userRole = employeeRole.toString();
        const template_type = userRole === 'MENTOR' ? 'JOB' : userRole === 'HR' ? 'NORMAL' : null;

        if (!template_type) {
            console.error('Invalid user role. Cannot determine template_type.');
            return;
        }

        router.push({
            path: '/grouping',
            query: {
                template_type,
                department_seq: departmentSeq.value,
                template_seq: templateSeq.value,
            },
        });
    };

    const fetchDepartments = async () => {
        try {
            const response = await springAPI.get('/hr/task/department', {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                },
            });
            if (response.data.success) {
                departments.value = response.data.data;
            } else {
                console.error('부서 정보를 가져오는 데 실패했습니다:', response.data.message);
            }
        } catch (error) {
            console.error('API 요청 중 오류가 발생했습니다:', error);
        }
    };

    const fetchTaskRounds = async () => {
        try {
            const response = await springAPI.get('hr/onboarding/template', {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                },
            });

            if (response.data.success) {
                taskRounds.value = response.data.data;
            } else {
                console.error('차수 정보를 가져오는 데 실패했습니다:', response.data.message);
            }
        } catch (error) {
            console.error('API 요청 중 오류가 발생했습니다:', error);
        }
    };

    return {
        taskType,
        fileName,
        tableData,
        taskSubmitSeq,
        isLoading,
        taskTitle,
        taskContent,
        departmentSeq,
        round,
        departments,
        taskRounds,
        templateSeq,
        back,
        fetchTaskRounds,
        fetchDepartments,
        handleFileChange,
        fetchData,
        deleteRow,
        addRow,
        submitTask,
        goToGroupingPage,
    };
}
