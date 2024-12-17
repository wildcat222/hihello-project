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
  // 파일이 선택되지 않은 경우 경고 메시지 출력
  if (!mentoringPlanForm.value.file) {
    alert("파일을 첨부해주세요.");
    return; // 파일이 없으면 더 이상 진행되지 않도록
  }

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
    const errorMessage = error.response?.data?.message || "등록 중 오류가 발생했습니다. 다시 시도해주세요.";
    alert(errorMessage);
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
          <div class="file_name">파일</div>
          <label for="file" class="file_button">
            <img
                src="https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/62dd909f-f788-4694-bd9b-6e01e7806fa2_attach_file.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241217T021443Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAQXPZDBYQREV7D6US%2F20241217%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=521920dac44120fdaec406215d16562aeaa600321a67418e622977c8e0652355"
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

<style scoped>
.form-group {
  display: flex;
  flex-direction: row;
  margin: 5px 0px;
  justify-content: center;
  gap: 50px;
}
.file_name{
  margin-top: 10px;
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
  padding-top: 10px;
}
h1{
  text-align: center;
  margin: 150px 0px 49px 0px;
  font-size: 35px;
  font-weight: 700;
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
.form-group label{
  margin-top: 5px;
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
