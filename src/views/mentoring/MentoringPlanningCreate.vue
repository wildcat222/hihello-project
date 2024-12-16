<script setup>
import { ref } from "vue";
import { submitMentoringPlanService } from "@/services/MentoringApi.js";
import { useUserStore } from "@/stores/UserStore.js";
import WhiteBoxListComponent from "@/components/WhiteBoxListComponent.vue";
import router from "@/router/index.js";

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
  if (file) {
    mentoringPlanForm.value.file = file;
    mentoringPlanForm.value.fileName = file.name;  // 자동으로 파일 이름 추출
  }
};


// 폼 제출 로직
const submitMentoringPlan = async () => {
  try {

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
    alert("계획서 등록이 되었습니다.");

    // 폼 초기화
    mentoringPlanForm.value.planningName = "";
    mentoringPlanForm.value.planningContent = "";
    mentoringPlanForm.value.file = null;
    mentoringPlanForm.value.fileName = "";

    router.push("/mentoring/planning");
  } catch (error) {
    errorMessage.value = error.response?.data?.message || "등록 중 오류가 발생했습니다. 다시 시도해주세요.";
  }
};
</script>

<template>
  <div class="planning-container">
    <h1>멘토링 계획서 등록</h1>

    <WhiteBoxListComponent>
      <form @submit.prevent="submitMentoringPlan">
        <div class="form-group">
          <label for="planningName">제목</label>
          <input
              id="planningName"
              v-model="mentoringPlanForm.planningName"
              type="text"
              placeholder="계획서 제목을 입력하세요"
          />
        </div>
        <div class="form-group">
          <label for="planningContent">내용</label>
          <textarea
              id="planningContent"
              v-model="mentoringPlanForm.planningContent"
              placeholder="계획서 내용을 입력하세요"
          ></textarea>
        </div>
        <div class="form-group">
          <div>파일</div>
          <label for="file" class="file_button">
            <img
                src="https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/d941c931-e33a-4055-8309-87cb762d17fd_attach_file.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241216T074513Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAQXPZDBYQREV7D6US%2F20241216%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=1254a50ab2c8cc440d0e67d8855b0b38237958f7a1a448d0c399e763226b6226"
                id="file-button"
            />
            <p v-if="mentoringPlanForm.fileName" id="file-name">{{ mentoringPlanForm.fileName }}</p>
          </label>
          <input
              id="file"
              type="file"
              accept=".jpg, .png, .pdf"
              @change="handleFileChange"
              style="display: none;"
          />
        </div>
        <br><br>
        <button type="submit" class="submit-button">등록</button>
      </form>
    </WhiteBoxListComponent>
  </div>
</template>

<style>
.form-group {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 5px 0px;
  justify-content: center;
  gap: 50px;
}

.planning-container {
  width: 550px;
}
#planningName,
#planningContent {
  all: unset;
  background-color: var(--white);
  width: 70%;
  border-radius: 10px;
  border: 2px solid var(--border-color);
  padding-left: 15px;
}
#planningName{
  height: 33px;
}
#planningContent{
  height: 150px;
}
h1{
  text-align: center;
  margin: 150px 0px 49px 0px;
}
.submit-button {
  width: 297px;
  height: 50px;
  background-color: var(--black);
  font-weight: 700;
  font-size: 20px;
  color: var(--white);
  border-radius: 10px;
}

form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.file_button {
  border-radius: 10px;
  width: 70%;
  background-color: var(--ivory);
  padding-left: 15px;
  display: flex;
  align-items: center;
  height: 33px;
}

#file-name {
  font-size: 14px;
  color: #333;
  margin-top: 10px;
}
textarea{
  height: 150px;
}
</style>
