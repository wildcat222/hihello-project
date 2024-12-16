<script setup>
import { ref } from "vue";
import { submitMentoringPlanService } from "@/services/MentoringApi.js";
import { useUserStore } from "@/stores/UserStore.js";

// 기안자 ID는 로그인한 사용자 정보에서 추출
const employeeSeq = useUserStore().getEmployeeInfo().employeeSeq;

// 폼 데이터 초기 상태
const mentoringPlanForm = ref({
  employeeSeq: employeeSeq,
  planningName: "",
  planningContent: "",
  file: null,
  fileName: "",
});

// 파일 선택 시 파일과 파일 이름 추출
const handleFileChange = (event) => {
  const file = event.target.files[0];
  mentoringPlanForm.value.file = file;
  mentoringPlanForm.value.fileName = file.name;  // 자동으로 파일 이름 추출
};

// 성공/오류 메시지 관리
const successMessage = ref("");
const errorMessage = ref("");

// 폼 제출 로직
const submitMentoringPlan = async () => {
  try {
    successMessage.value = "";
    errorMessage.value = "";

    const formData = new FormData();
    formData.append("createProductReqDTO", JSON.stringify({
      employeeSeq: mentoringPlanForm.value.employeeSeq,
      planningName: mentoringPlanForm.value.planningName,
      planningContent: mentoringPlanForm.value.planningContent,
      fileName: mentoringPlanForm.value.fileName,
    }));
    formData.append("productImgUrl", mentoringPlanForm.value.file);

    // 디버그 출력: FormData의 내용을 확인
    for (let pair of formData.entries()) {
      console.log(pair[0] + ": " + pair[1]);
    }

    // API 호출
    const response = await submitMentoringPlanService(formData);
    successMessage.value = response.message || "등록에 성공했습니다.";

    // 폼 초기화
    mentoringPlanForm.value.planningName = "";
    mentoringPlanForm.value.planningContent = "";
    mentoringPlanForm.value.file = null;
    mentoringPlanForm.value.fileName = "";

  } catch (error) {
    errorMessage.value = error.response?.data?.message || "등록 중 오류가 발생했습니다. 다시 시도해주세요.";
  }
};
</script>

<template>
  <div class="registration-container">
    <h1>멘토링 계획서 등록</h1>
    <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

    <form @submit.prevent="submitMentoringPlan">
      <div class="form-group">
        <label for="employeeSeq">기안자</label>
        <input
            id="employeeSeq"
            v-model="mentoringPlanForm.employeeSeq"
            type="number"
            readonly
            placeholder="기안자 ID를 입력하세요"
        />
      </div>
      <div class="form-group">
        <label for="planningName">계획서 제목</label>
        <input
            id="planningName"
            v-model="mentoringPlanForm.planningName"
            type="text"
            placeholder="계획서 제목을 입력하세요"
        />
      </div>
      <div class="form-group">
        <label for="planningContent">계획서 내용</label>
        <textarea
            id="planningContent"
            v-model="mentoringPlanForm.planningContent"
            placeholder="계획서 내용을 입력하세요"
        ></textarea>
      </div>
      <div class="form-group">
        <label for="file">파일 업로드</label>
        <input
            id="file"
            type="file"
            accept=".jpg, .png, .pdf"
            @change="handleFileChange"
        />
      </div>
      <button type="submit" class="submit-button">등록</button>
    </form>
  </div>
</template>
