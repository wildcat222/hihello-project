import { ref } from 'vue';
import { springAPI } from "@/services/axios.js";

// 템플릿 리스트
export const templateList = ref([]); // 저장된 템플릿 리스트

// 가장 큰 templateProcedure 값을 관리할 ref
export const maxTemplateProcedure = ref(0); // 가장 큰 templateProcedure 값 초기화

// 템플릿 리스트에서 가장 큰 templateProcedure 값을 갱신
export const setMaxTemplateProcedure = () => {
    if (templateList.value.length === 0) return; // 템플릿이 없으면 처리 안함

    maxTemplateProcedure.value = templateList.value.reduce((max, current) => {
        return current.templateProcedure > max ? current.templateProcedure : max;
    }, 0);  // 기본값 0 설정
};

// 템플릿 리스트 불러오기 함수
export const loadTemplates = async () => {
    try {
        const response = await springAPI.get('/hr/onboarding'); // 템플릿 리스트 가져오는 API
        templateList.value = response.data.data; // 템플릿 리스트 갱신

        // 템플릿 리스트가 갱신된 후, 가장 큰 templateProcedure 값을 갱신
        setMaxTemplateProcedure();
        console.log("가장 큰 templateProcedure 값:", maxTemplateProcedure.value);

    } catch (error) {
        console.error('템플릿 목록 로드 중 오류:', error);
    }
};

// 템플릿 데이터를 리셋하는 함수
export const resetTemplateData = () => {
    templateForm.value = {
        templateType: templateForm.value.templateType, // 현재 선택된 templateType만 남겨둠
        templateTrainingType: '',
        templateTitle: '',
        templateSub: '',
        templateDetail: '',
        checklistContent: [],
        templateQuizQty: 0,
        templateTaskRound: '',
        templateProcedure: maxTemplateProcedure.value + 1,  // 가장 큰 값 + 1
        templateEndAt: '',
    };
};

// 템플릿 순서를 업데이트하는 함수
export const updateTemplateProcedure = async (updatedTemplates) => {
    try {
        // updateDTO 포맷에 맞게, 순서가 변경된 템플릿들을 모두 포함해서 한 번에 서버에 전송
        const updateDTO = updatedTemplates.map((template, index) => ({
            templateSeq: template.templateSeq, // 템플릿의 실제 식별자인 templateSeq
            templateProcedure: index + 1, // 순서를 1부터 시작하여 할당
        }));

        // 서버로 모든 템플릿의 순서를 업데이트 요청
        await springAPI.put(`/hr/onboarding/updateTemplateProcedure`, { templates: updateDTO });

        alert("템플릿 순서가 업데이트되었습니다.");
        loadTemplates();  // 템플릿 리스트 새로 고침
    } catch (error) {
        console.error("템플릿 순서 업데이트 실패:", error);
        alert("템플릿 순서 업데이트에 실패했습니다.");
    }
};

// 템플릿 삭제 함수
export const deleteTemplate = async (templateSeq, templateProcedure) => {
    try {
        // 1. 서버에서 해당 템플릿 삭제
        const response = await springAPI.delete(`/hr/onboarding/${templateSeq}`);

        // 2. 로컬에서 해당 템플릿 항목 제거
        templateList.value = templateList.value.filter((template) => template.templateSeq !== templateSeq);

        // 3. templateProcedure가 삭제된 항목보다 큰 값들을 찾아 -1씩 처리
        const updatedTemplates = templateList.value.map((template) => {
            if (template.templateProcedure > templateProcedure) {
                template.templateProcedure -= 1;
            }
            return template;
        });

        // 4. 모든 템플릿의 순서 업데이트를 한 번에 서버로 전송
        await updateTemplateProcedure(updatedTemplates);  // 템플릿 순서를 업데이트하는 함수 호출

        // 5. 로컬 데이터 업데이트 완료
        templateList.value = updatedTemplates;

        alert("온보딩 스토리보드가 삭제되었습니다.");
    } catch (error) {
        alert("삭제에 실패했습니다.");
        console.error("삭제 실패:", error);
    }
};

// 템플릿 데이터를 전송하는 함수
export const submitTemplate = async () => {
    const formData = new FormData();
    console.log(templateList.value)
    // JSON 데이터를 문자열로 변환하여 추가
    formData.append("createDTO", JSON.stringify({
        quizCategorySeq: templateForm.value.quizCategorySeq,
        templateType: templateForm.value.templateType,
        templateCheckRequiredStatus: templateForm.value.templateCheckRequiredStatus,
        templateTrainingType: templateForm.value.templateTrainingType,
        templateTitle: templateForm.value.templateTitle,
        templateSub: templateForm.value.templateSub,
        templateDetail: templateForm.value.templateDetail,
        templateUrlName: templateForm.value.templateUrlName,
        templateQuizQty: templateForm.value.templateQuizQty,
        templateTaskRound: templateForm.value.templateTaskRound,
        templateProcedure: templateForm.value.templateProcedure,
        templateEndAt: formatDate(templateForm.value.templateEndAt),
        checklistContent: templateForm.value.checklistContent
    }));

    console.log(JSON.stringify(templateForm.value.checklistContent))
    // 파일이 있는 경우에만 FormData에 파일 추가
    if (templateForm.value.file) {
        formData.append("productImgUrl", templateForm.value.file);
    }

    try {
        const response = await springAPI.post('/hr/onboarding', formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        });

        alert(response.data.message); // 성공 메시지
        resetTemplateData();
        loadTemplates(); // 템플릿 리스트 새로고침
    } catch (error) {
        console.error('템플릿 저장 중 오류:', error);
        alert('템플릿 저장에 실패했습니다.');
    }
};

// 화면 로드시 템플릿 리스트 불러오기
loadTemplates();

// 파일 이름 처리
export const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
        templateForm.value.file = file;
        templateForm.value.templateUrlName = file.name;
    }
};

// 템플릿 폼 데이터를 위한 ref
export const templateForm = ref({
    quizCategorySeq: null,
    templateType: 'NORMAL',
    templateCheckRequiredStatus: false, // 초기값 false
    templateTrainingType: '',
    templateTitle: '',
    templateSub: '',
    templateDetail: '',
    templateUrlName: '',
    templateQuizQty: '',
    templateTaskRound: '',
    templateProcedure: 0, // 초기값 설정 (default로 0)
    templateEndAt: '',
    checklistContent: [],
});

// 새로운 체크리스트 항목을 위한 ref
export const newChecklistItem = ref(""); // 새로운 체크리스트 항목 입력 값

// 체크리스트 항목 추가 함수
export const addChecklistItem = () => {
    if (newChecklistItem.value.trim() !== "") {
        // checklistContent 배열에 객체로 항목 추가
        templateForm.value.checklistContent.push({
            checklistContent: newChecklistItem.value.trim(), // 항목 이름 설정
        });

        console.log(templateForm.value.checklistContent); // 확인용 콘솔 출력

        newChecklistItem.value = ""; // 항목 입력 칸 초기화
    } else {
        alert("항목 이름을 입력하세요.");
    }
};

// 체크리스트 항목 삭제 함수
export const removeChecklistItem = (index) => {
    templateForm.value.checklistContent.splice(index, 1); // 해당 항목 삭제
};

// 날짜 형식을 'yyyy-MM-dd'T'HH:mm:ss'로 변환
export const formatDate = (date) => {
    const d = new Date(date);
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    const hours = String(d.getHours()).padStart(2, '0');
    const minutes = String(d.getMinutes()).padStart(2, '0');
    const seconds = String(d.getSeconds()).padStart(2, '0');  // 초까지 포함
    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
};

// 드래그 시작 이벤트 핸들러
export const dragStart = (event, index) => {
    event.dataTransfer.setData('draggedItem', index); // 드래그한 항목의 인덱스를 저장
};

// 드래그 오버 이벤트 핸들러
export const dragOver = (event) => {
    event.preventDefault(); // 이 이벤트가 drop 이벤트로 처리되도록 허용
};

// 드래그 앤 드롭 종료 이벤트 핸들러
export const drop = (event, index) => {
    const draggedIndex = event.dataTransfer.getData('draggedItem'); // 드래그한 항목의 인덱스를 가져옴

    // 템플릿 리스트에서 순서를 변경
    const draggedItem = templateList.value[draggedIndex];
    templateList.value.splice(draggedIndex, 1); // 원래 위치에서 제거
    templateList.value.splice(index, 0, draggedItem); // 새로운 위치에 추가

    // 순서 업데이트 후 서버에 전송
    updateTemplateProcedure(templateList.value); // 순서 갱신 후 서버 전송

    event.preventDefault(); // 기본 동작 방지
};
