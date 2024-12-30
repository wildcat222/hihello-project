<script setup>
import {onMounted, ref} from 'vue'
import {CreateReport, getMentoringWeek} from "@/services/MentoringApi.js";
import {useUserStore} from "@/stores/UserStore.js";
import WhiteBoxComponent from "@/components/WhiteBoxComponent.vue";

const reportContent = ref('');
const reportFeeling = ref('');
const reportWeek = ref(0);

// 주차 정보 가져오기
const fetchWeekNumber = async () => {
  try {
    const response = await getMentoringWeek();
    reportWeek.value = response.data.data;
  } catch (error) {
    console.error('주차 조회에 실패하였습니다. :', error)
  }
}

// 보고서 제출
const submitReport = async () => {
  try {
    const employeeSeq = useUserStore().getEmployeeInfo().employeeSeq;
    const reportData = {
      reportWeek: reportWeek.value,
      reportContent: reportContent.value,
      reportFeeling: reportFeeling.value
    }

    await CreateReport(employeeSeq, reportData);
    alert('보고서가 성공적으로 제출되었습니다.')

    // 폼 초기화
    reportContent.value = ''
    reportFeeling.value = ''
  } catch (error) {
    console.error('Error submitting report:', error)
    alert('보고서 제출 중 오류가 발생했습니다.')
  }
}

onMounted(() => {
  fetchWeekNumber()
})
</script>

<template>
  <div class="report-create-container">

    <div class="report-title-section">
      <div class="report-week">{{ reportWeek }}주차</div>
      <h1 class="text-2xl font-bold mb-6 report-title">보고서</h1>
    </div>

    <WhiteBoxComponent class="input-section">
      <div class="form-section mb-8">
        <h2 class="text-lg font-semibold mb-3">활동 내용</h2>
        <textarea
            v-model="reportContent"
            class="w-full h-40 p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500"
            placeholder="주요 실시한 업무, 교육, 연구, 세미나 등의 사내활동 작성"
        ></textarea>
      </div>

      <div class="form-section mb-8">
        <h2 class="text-lg font-semibold mb-3">배운점 및 소감</h2>
        <textarea
            v-model="reportFeeling"
            class="w-full h-40 p-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500"
            placeholder="주요 활동을 통해 새롭게 습득한 지식, 생각, 느낀점, 향후 활용 사항"
        ></textarea>
      </div>
    </WhiteBoxComponent>

    <div class="button-group">
      <button
          @click="submitReport"
          class="create-btn">
        제출하기
      </button>
    </div>
  </div>
</template>

<style scoped>
.report-create-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.report-title-section {
  display: flex;
  justify-items: center;
  align-items: baseline;
  width: 55vw;
}

.report-week {
  display: flex;
  background-color: var(--white);
  border-radius: 8px;
  height: 2vw;
  width: 5vw;
  align-items: center;
  justify-content: center;
  text-align: left;
  box-shadow: 2px 2px 4px 0 var(--gray);
}

.report-title {
  text-align: center;
  flex-grow: 1;
}

.input-section {
  box-shadow: 2px 2px 4px 0 var(--gray);
  width: 59vw;
}

.form-section h2 {
  justify-self: left;
}

.form-section textarea {
  border-color: var(--light-gray);
  resize: none;
  font-size: 0.95rem;
  width: 55vw;
  height: 20vw;
}

.button-group {
  width: 59vw;
  display: flex;
  justify-content: center;
}

.create-btn {
  background-color: var(--purple);
  color: var(--white);
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
  font-size: 18px;
}
</style>