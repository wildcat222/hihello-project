import { ref } from 'vue';
import {springAPI} from "@/services/axios.js";
import { useRouter } from 'vue-router'; // useRouter 가져오기
import axios from 'axios'; // 추가 필요
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
    const departments = ref([]);
    const taskRounds = ref([]);


    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            fileName.value = file.name.length > 15
                ? file.name.substring(0, 10) + '...'
                : file.name;

            console.log('파일 이름:', fileName.value); // 디버깅용 로그
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

    const submitTask = async () => {
        const formData = new FormData();

        // 기본 DTO 생성
        const taskCreateDTO = {
            departmentSeq: departmentSeq.value,
            templateSeq: taskSubmitSeq.value,
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
            taskCreateDTO.fileName = file.name; // 파일 이름을 taskCreateDTO에 추가
            formData.append('fileUrl', file);   // 파일을 formData에 추가
        } else {
            // 파일이 없으면 fileName을 포함하지 않음
            delete taskCreateDTO.fileName; // fileName 필드를 제거
        }

        // taskCreateDTO를 formData에 추가
        formData.append('taskCreateDTO', JSON.stringify(taskCreateDTO));

        try {
            const response = await springAPI.post('/task', formData, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                    'Content-Type': 'multipart/form-data',
                },
            });

            alert('과제가 등록되었습니다.');
        } catch (error) {
            alert('과제 등록에 실패했습니다.');
            console.error(error);
        }
    };

    const template_type = ref('JOB'); // 예시 값: 'JOB' 또는 'NORMAL'
    const selectedDepartmentSeq = ref(1); // 예시 값: department_seq (JOB에서 사용)

    // 그룹 매칭 버튼 클릭 시 페이지 이동
    const goToGroupingPage = () => {
        if (template_type.value === 'JOB') {
            // JOB일 때 department_seq와 templateSeq 함께 라우팅
            router.push({
                path: '/grouping',
                query: {
                    template_type: 'JOB',
                    department_seq: selectedDepartmentSeq.value,
                    template_seq: '2', // 나중에 값 받아오기
                },
            });
        } else if (template_type.value === 'NORMAL') {
            // NORMAL일 때는 template_type과 templateSeq만 넘김
            router.push({
                path: '/grouping',
                query: {
                    template_type: 'NORMAL',
                    template_seq: '3', // 나중에 값 받아오기
                },
            });
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
            const response = await springAPI.get('/onboarding/template', {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                },
            });
            console.log('API 응답:', response.data); // 디버깅용 로그

            if (response.data.success) {
                if (response.data.data.length === 0) {
                    console.warn('데이터가 없습니다.');
                } else {
                    // 데이터 저장
                    taskRounds.value = response.data.data;
                    console.log('taskRounds 값:', taskRounds.value); // 데이터가 잘 저장되었는지 확인
                }
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
        fetchTaskRounds,
        fetchDepartments,
        handleFileChange,
        fetchData,
        addRow,
        submitTask,
        goToGroupingPage
    };
}