<!-- MentoringReportView.vue -->
<script setup>
import {ref, onMounted} from 'vue';
import SearchBarComponent from '@/components/SearchBarComponent.vue';
import ListComponent from '@/components/ListComponent.vue';
import router from "@/router/index.js";
import {
  fetchReportListByHR, fetchReportListByLeader,
  fetchReportListByMentee,
  fetchReportListByMentor,
  searchReports, searchReportsByLeader
} from "@/services/MentoringApi.js";
import {useUserStore} from "@/stores/UserStore.js";

// 상태 관리
const reports = ref([]);
const loading = ref(false);
const searchCategory = ref('name');

const employeeInfo = useUserStore().getEmployeeInfo();
const employeeRole = employeeInfo.employeeRole;
const employeePositionName = employeeInfo.employeePositionName;


console.log(employeeRole[0]);
console.log(employeePositionName);

// 검색 처리 함수
const handleSearch = async (keyword) => {
  loading.value = true;

  if (!keyword.trim()) {  // 검색어가 비어있다면
    const response = ref(null);
    if (employeeRole[0] === 'MENTEE') {
      response.value = await fetchReportListByMentee();
    } else if (employeeRole[0] === 'MENTOR') {
      response.value = await fetchReportListByMentor();
    } else if (employeeRole[0] === 'HR') {
      response.value = await fetchReportListByHR();
    } else if (employeePositionName === '팀장') {
      response.value = await fetchReportListByLeader();
    } else {
      alert('접근 권한이 없습니다.');
      router.back();
    }
    reports.value = response.value.data.data;
    loading.value = false;
    return;
  }

  try {
    const response = ref(null);
    // 담당자
    if (employeeRole[0] === 'HR') {
      response.value = await searchReports(searchCategory,keyword);
    } else if (employeePositionName === '팀장') {
      response.value = await searchReportsByLeader(searchCategory,keyword);
    }
    reports.value = response.value.data.data;
  } catch (error) {
    console.error('검색 중 오류 발생:', error);
  } finally {
    loading.value = false;
  }
};

// 라우팅 처리 함수(보고서 작성)
const goToRegisterPage = () => {
  router.push('/report/edit');
};

// 라우팅 처리 함수(상세 조회)
const goToDetail = (reportSeq) => {
  router.push(`/mentoring/report/${reportSeq}`);
}

// 초기 데이터 로드
onMounted(async () => {
  await handleSearch('');
});
</script>

<template>
  <div class="report-container">
    <div class="title-section">
      <h1 class="title">멘토링 보고서</h1>
    </div>

    <div class="search-section" v-if="employeeRole[0] !== 'MENTEE' && employeeRole[0] !== 'MENTOR'">
      <SearchBarComponent @search="handleSearch"/>
      <select v-model="searchCategory" class="box">
        <option value="name">이름</option>
        <option value="num">사번</option>
      </select>
    </div>
    <div class="yellow-box" @click="goToRegisterPage" v-if="employeeRole[0] === 'MENTEE'">
      <img
          src="https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/8d64cbf7-77f8-4670-8ddf-40e43d7bc481_plus.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241216T063855Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAQXPZDBYQREV7D6US%2F20241216%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=7b785c3af1fdf24cef7127814d2d0327e29824719c832902b14f72af28fb0af6"/>
      <div>보고서 등록</div>
    </div>

    <ListComponent :items="reports">
      <template #header>
        <div class="header-item">순서</div>
        <div class="header-item">주차</div>
        <div class="header-item" v-if="employeeRole[0] !== 'MENTEE' && employeeRole[0] !== 'MENTOR'">작성자</div>
        <div class="header-item">제출날짜</div>
      </template>

      <template #item="{ item, index }">
        <div @click="goToDetail(item.reportSeq)" class="list-row">
          <div class="list-cell">{{ index + 1 }}</div>
          <div class="list-cell">{{ item.reportWeek }}</div>
          <div class="list-cell" v-if="employeeRole[0] !== 'MENTEE' && employeeRole[0] !== 'MENTOR'">{{ item.menteeName }}</div>
          <div class="list-cell">{{ item.regDate }}</div>
        </div>
      </template>
    </ListComponent>
  </div>
</template>

<style scoped>
.report-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.title {
  text-align: center;
  margin-bottom: 30px;
}

.search-section {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
}

.box {
  border: none;
  background-color: var(--white);
  box-shadow: 2px 2px 4px 0 var(--gray);
  border-radius: 15px;
  width: 100px;
  height: 50px;
}

.yellow-box {
  box-shadow: 2px 2px 4px 0 var(--gray);
  background-color: var(--yellow);
  border-radius: 15px;
  height: 50px;
  display: flex;
  width: 200px;
  align-items: center;
  justify-content: space-evenly;
  font-size: 15px;
  font-weight: 600;
  color: var(--white);
  cursor: pointer;
}

.list-row {
  display: flex;
  padding: 10px 20px;
  cursor: pointer;
}

.list-cell {
  flex: 1;
  text-align: center;
}

.header-item {
  flex: 1;
  text-align: center;
}
</style>