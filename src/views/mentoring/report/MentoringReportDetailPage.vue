<script setup>
import {onMounted, ref} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import {getReportDetail, updateFeedback, updateReport} from "@/services/MentoringApi.js";
import {useUserStore} from "@/stores/UserStore.js";

const router = useRouter();
const route = useRoute();

// 상태 관리
const employeeInfo = useUserStore().getEmployeeInfo();
const employeeRole = employeeInfo.employeeRole[0];
const employeeSeq = employeeInfo.employeeSeq;
const reportSeq = ref(Number(route.params.reportSeq));
const reportWeek = ref(0);
const reportContent = ref('');
const editedReportContent = ref('');
const reportFeeling = ref('');
const editedReportFeeling = ref('');
const reportFeedbackContent = ref('');
const editedMentorFeedback = ref('');
const menteeName = ref('');
const mentorName = ref('');

// 상세 조회 api
const loadReportDetail = async () => {
  try {
    const response = await getReportDetail(reportSeq.value);
    console.log(response);
    menteeName.value = response.data.data.menteeName;
    mentorName.value = response.data.data.mentorName;
    reportWeek.value = response.data.data.reportWeek;
    reportContent.value = response.data.data.reportContent;
    reportFeeling.value = response.data.data.reportFeeling;
    reportFeedbackContent.value = response.data.data.reportFeedbackContent;
  } catch (error) {
    console.error('상세 조회 중 오류가 발생하였습니다.'.error);
    // alert('상세 조회 중 오류가 발생하였습니다.');
  }

}

// 멘토 피드백 수정 모드 상태
const isMentorFeedbackEditing = ref(false);
const isReportEditing = ref(false);

// 라우팅 핸들러
// const navigateToReportEdit = () => {
//   router.push('/report/edit');
// }

// 멘티 보고서 수정 핸들러
const handleReportEdit = async () => {
  if (isReportEditing.value) {
    try {
      const editData = {
        reportContent: editedReportContent.value,
        reportFeeling: editedReportFeeling.value
      }
      await updateReport(employeeSeq, reportSeq.value, editData);
      reportContent.value = editedReportContent.value;
      reportFeeling.value = editedReportFeeling.value;
      console.log('보고서가 저장되었습니다.')
    } catch (error) {
      console.error('보고서 저장 중 오류가 발생했습니다:', error)
      return
    }
  } else {
    editedReportContent.value = reportContent.value;
    editedReportFeeling.value = reportFeeling.value;
  }
  isReportEditing.value = !isReportEditing.value;
}

// 멘토 피드백 수정 핸들러
const handleMentorFeedbackEdit = async () => {
  if (isMentorFeedbackEditing.value) {
    try {
      await saveMentorFeedback(editedMentorFeedback.value);
      reportFeedbackContent.value = editedMentorFeedback.value;
      console.log('멘토 피드백이 저장되었습니다.')
    } catch (error) {
      console.error('멘토 피드백 저장 중 오류가 발생했습니다:', error)
      return
    }
  } else {
    editedMentorFeedback.value = reportFeedbackContent.value
  }
  isMentorFeedbackEditing.value = !isMentorFeedbackEditing.value
}

// API 호출 함수
const saveMentorFeedback = async (editedMentorFeedback) => {
  try {
    await updateFeedback(employeeSeq, reportSeq.value, editedMentorFeedback);
  } catch (error) {
    console.error('saveFeedback 메서드 피드백 수정 중 오류가 발생하였습니다.'. error);
    // alert('피드백 수정중 오류가 발생하였습니다.');
    throw error;
  }
}

onMounted(() => {
  loadReportDetail();
})
</script>

<template>
  <div class="report-container">
    <div>{{ reportWeek }}주차</div>
    <h1>보고서</h1>
    <div>멘토 : {{ mentorName }}</div>
    <div>멘티 : {{ menteeName }}</div>
    <section class="report-section">
      <div class="mentee-section">
        <div class="content-section">
          <h2>활동 내용</h2>
          <div class="content-box" v-if="!isReportEditing">
            <p>{{ reportContent }}</p>
          </div>
          <div class="content-box" v-else>
        <textarea
            v-model="editedReportContent"
            class="feedback-textarea"
            rows="4"
        ></textarea>
          </div>

          <h2>배움점 및 소감</h2>
          <div class="content-box" v-if="!isReportEditing">
            <p>{{ reportFeeling }}</p>
          </div>
          <div class="content-box" v-else>
        <textarea
            v-model="editedReportFeeling"
            class="feedback-textarea"
            rows="4"
        ></textarea>
          </div>
        </div>

        <div class="button-container" v-if="employeeRole === 'MENTEE'">
          <button class="edit-button" @click="handleReportEdit">
            {{ isReportEditing ? '저장하기' : '수정하기' }}
          </button>
        </div>
      </div>
    </section>
    <hr>
    <section class="report-section">
      <h2>멘토 피드백</h2>
      <div class="content-box" v-if="!isMentorFeedbackEditing">
        <p>{{ reportFeedbackContent }}</p>
      </div>
      <div class="content-box" v-else>
        <textarea
            v-model="editedMentorFeedback"
            class="feedback-textarea"
            rows="4"
        ></textarea>
      </div>
      <div class="button-container" v-if="employeeRole === 'MENTOR'">
        <button class="edit-button" @click="handleMentorFeedbackEdit">
          {{ isMentorFeedbackEditing ? '저장하기' : '수정하기' }}
        </button>
      </div>
    </section>
  </div>
</template>

<style scoped>
.report-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.report-section {
  margin-bottom: 30px;
}

.content-section {
  background-color: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 10px;
}

.content-box {
  background-color: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 10px;
}

.button-container {
  display: flex;
  justify-content: flex-end;
}

.edit-button {
  background-color: #7950f2;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
  font-size: 14px;
}

.edit-button:hover {
  background-color: #6741d9;
}

.feedback-textarea {
  width: 100%;
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 10px;
  font-size: 14px;
  resize: vertical;
}

h2 {
  font-size: 18px;
  margin-bottom: 15px;
}
</style>