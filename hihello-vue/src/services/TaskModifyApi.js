import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { springAPI } from "@/services/axios.js";
import axios from 'axios'; // 추가 필요
import router from "@/router/index.js";

export function useTask() {
    // 변수 초기화
    const taskTitle = ref('');
    const taskContent = ref('');
    const departmentSeq = ref(1);
    const round = ref('1주차');
    const taskType = ref('PERSONAL');
    const fileName = ref('');
    const fileUrl = ref('');
    const tableData = ref([]);
    const taskSubmitSeq = ref('');
    const isLoading = ref(false);
    const template_type = ref('NORMAL');
    const selectedDepartmentSeq = ref(1);
    const templateSeq = ref(1);
    const departments = ref([]);
    const taskRounds = ref([]);

    // URL에서 taskSeq 가져오기
    const route = useRoute();
    const taskSeq = ref(route.query.taskSeq || route.params.taskSeq);

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
        if(index > 0){
            tableData.value.splice(index, 1);
        }
    }

    const fetchData = async () => {
        if (isLoading.value) return; // 중복 호출 방지
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
                    newContent: '', // 추가 속성 초기화
                }));
            } else {
                console.error('응답 데이터가 없습니다:', response);
                tableData.value = []; // 안전한 초기화
            }
        } catch (error) {
            console.error('API 요청 실패:', error);
            alert('데이터를 불러오는데 실패했습니다. 다시 시도해주세요.');
            tableData.value = []; // 안전한 초기화
        } finally {
            isLoading.value = false;
        }
    };

    const goToGroupingPage = () => {
        const queryParams = { template_seq: '3' }; // 기본값 설정
        if (template_type.value === 'JOB') {
            queryParams.template_type = 'JOB';
            queryParams.department_seq = selectedDepartmentSeq.value;
            queryParams.template_seq = '2'; // 나중에 동적으로 받아오기
        } else if (template_type.value === 'NORMAL') {
            queryParams.template_type = 'NORMAL';
        }

        router.push({ path: '/grouping', query: queryParams });
    };

    const back = () => {
        window.history.back();
    }

    const fetchTaskData = async () => {
        if (!taskSeq.value) {
            console.error('taskSeq가 존재하지 않습니다.');
            return;
        }

        try {
            const response = await springAPI.get(`/task/${taskSeq.value}`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
                },
            });



            if (!response.data || !response.data.data) {
                console.error('올바르지 않은 API 응답 구조:', response);
                return;
            }

            const taskData = response.data.data[0]; // 데이터 구조 확인 후 수정 필요

            // 데이터 매핑
            taskTitle.value = taskData.taskTitle || '';
            taskContent.value = taskData.taskContent || '';
            departmentSeq.value = taskData.departmentSeq || null;
            templateSeq.value = taskData.templateSeq || null;
            round.value = taskData.templateTaskRound || null;
            taskType.value = taskData.taskType || 'PERSONAL';
            fileName.value = taskData.fileName || '';
            fileUrl.value = taskData.fileUrl || '';

            tableData.value = taskData.evalList.map(item => ({
                evalIndSeq: item.evalIndSeq,
                evalIndContent: item.evalIndContent,
                evalListContent: item.evalListContent || '',
                evalListScore: item.evalListScore || 0,
            }));
        } catch (error) {
            console.error('과제 데이터 조회 실패:', error.response?.data || error);
        }
    };

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            fileName.value = file.name.length > 15
                ? file.name.substring(0, 10) + '...'
                : file.name;
        }
    };

    const updateTask = async () => {
        if (!taskSeq.value) {
            console.error('taskSeq가 존재하지 않습니다.');
            return;
        }
        const formData = new FormData();
        const updateTaskDTO = {
            departmentSeq: departmentSeq.value,
            templateSeq: templateSeq.value,
            taskType: taskType.value,
            taskTitle: taskTitle.value,
            taskContent: taskContent.value,
            evalIndicators: tableData.value.map(item => ({
                evalListContent: item.evalListContent,
                evalListScore: item.evalListScore,
                evalIndSeq: item.evalIndSeq,
            })),
        };
        // 파일 선택 여부에 따른 처리
        const fileInput = document.getElementById('fileInput');
        const file = fileInput.files[0];
        // 파일이 있을 경우
        if (file) {
            updateTaskDTO.fileName = file.fileName; // 파일 이름을 updateTaskDTO에 추가
            formData.append('fileUrl', file);   // 파일을 formData에 추가
        } else {
            // 파일이 없으면 fileName을 포함하지 않음
            delete updateTaskDTO.fileName; // fileName 필드를 제거
        }
        // updateTaskDTO를 formData에 추가
        formData.append('updateTaskDTO', JSON.stringify(updateTaskDTO));
        try {
            const response = await springAPI.put(`/task/${taskSeq.value}`, formData, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                    'Content-Type': 'multipart/form-data',
                },
            });
            alert('과제 수정이 성공하였습니다.');
            window.location.href = '/task/list';
        } catch (error) {
            alert('과제 수정에 실패했습니다.');
            console.error('API Error:', error.response?.data || error);
        }
    };

    // API 호출 함수
    const fetchDepartments = async () => {
        try {
            const response = await springAPI.get('/hr/department', {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                    'Content-Type': 'multipart/form-data',
                },
            });
            if (response.data.success) {
                departments.value = response.data.data; // 데이터 저장
            } else {
                console.error('부서 정보를 가져오는 데 실패했습니다:', response.data.message);
            }
        } catch (error) {
            console.error('API 요청 중 오류가 발생했습니다:', error);
        }
    };

    const fetchTaskRounds = async () => {
        try {
            const response = await springAPI.get('/hr/onboarding/template', {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                },
            });

            if (response.data.success) {
                if (response.data.data.length === 0) {
                    console.warn('데이터가 없습니다.');
                } else {
                    // 데이터 저장
                    taskRounds.value = response.data.data;
                }
            } else {
                console.error('차수 정보를 가져오는 데 실패했습니다:', response.data.message);
            }
        } catch (error) {
            console.error('API 요청 중 오류가 발생했습니다:', error);
        }
    };

    // 컴포넌트가 마운트될 때 fetchTaskData 호출
    onMounted(() => {
        fetchTaskData();
        fetchDepartments();
    });

    return {
        taskSeq,
        taskType,
        fileName,
        tableData,
        templateSeq,
        taskSubmitSeq,
        isLoading,
        taskTitle,
        taskContent,
        departmentSeq,
        round,
        departments,
        taskRounds,
        back,
        fetchTaskRounds,
        fetchDepartments,
        addRow,
        deleteRow,
        handleFileChange,
        goToGroupingPage,
        fetchData,
        fetchTaskData,
        updateTask,
    };
}
