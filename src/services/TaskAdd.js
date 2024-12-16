import { ref } from 'vue';
import axios from 'axios';

export function useTask() {
    const taskType = ref('PERSONAL');
    const fileName = ref('');
    const tableData = ref([]);
    const taskSubmitSeq = ref(1);
    const isLoading = ref(false);
    const taskTitle = ref('');
    const taskContent = ref('');
    const departmentSeq = ref(1);
    const round = ref('1주차');

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        console.log(file);

        if (file) {
            const fileType = file.type;
            if (fileType !== 'image/jpeg' && fileType !== 'image/png') {
                alert('JPG 또는 PNG 파일만 업로드 가능합니다.');
                fileName.value = '';
                event.target.value = '';
                return;
            }

            fileName.value = file.name.length > 15 ? file.name.substring(0, 10) + '...' : file.name;
        }
    };

    const fetchData = async () => {
        if (isLoading.value) return;
        isLoading.value = true;

        try {
            const response = await axios.get('http://localhost:8253/api/v1/hr/eval/ind', {
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
        const evalIndicators = tableData.value.map(item => ({
            evalListContent: item.evalListContent,
            evalListScore: item.evalListScore,
            evalIndSeq: item.evalIndSeq,
        }));

        const formData = new FormData();

        const taskCreateDTO = {
            departmentSeq: departmentSeq.value,
            templateSeq: taskSubmitSeq.value,
            taskType: taskType.value,
            taskTitle: taskTitle.value,
            taskContent: taskContent.value,
            evalIndicators: evalIndicators,
        };

        formData.append('taskCreateDTO', JSON.stringify(taskCreateDTO));

        const fileInput = document.getElementById('fileInput');
        if (fileInput.files[0]) {
            formData.append('fileUrl', fileInput.files[0]);
            formData.append('fileName', fileName.value);
            console.log(fileInput.files[0]);
        } else {
            console.log('파일이 선택되지 않았습니다.');
            formData.delete('fileUrl');
            formData.delete('fileName');
        }

        try {
            const response = await axios.post('http://localhost:8253/api/v1/task', formData, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                    'Content-Type': 'multipart/form-data',
                },
            });

            console.log('Task successfully registered:', response.data);
            alert('과제가 등록되었습니다.');
        } catch (error) {
            console.error('API 요청 실패:', error);
            alert('과제 등록에 실패했습니다.');
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
        handleFileChange,
        fetchData,
        addRow,
        submitTask,
    };
}