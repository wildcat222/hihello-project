<script setup>
import {onMounted, ref} from 'vue'
import {CreateReport, getMentoringWeek} from "@/services/MentoringApi.js";
import {useUserStore} from "@/stores/UserStore.js";

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
  <div class="report-container">
    <div>{{ reportWeek }}주차</div>
    <h1 class="text-2xl font-bold mb-6">보고서</h1>

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

    <div class="flex justify-center">
      <button
          @click="submitReport"
          class="px-6 py-2 bg-purple-600 text-white rounded-md hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2"
      >
        제출하기
      </button>
    </div>
  </div>
</template>

<style scoped>
.report-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.form-section textarea {
  resize: none;
  font-size: 0.95rem;
}
</style>