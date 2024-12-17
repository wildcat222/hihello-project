<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { fetchMentoringPlanDetail } from '@/services/MentoringApi.js';
import WhiteBoxListComponent from "@/components/WhiteBoxListComponent.vue";

const route = useRoute();
const planningSeq = ref(null); // planningSeq를 안전하게 보관
const planDetail = ref({}); // API 데이터를 저장 (빈 객체로 초기화)

// 라우트가 유효할 때 planningSeq 추출
onMounted(() => {
  if (route.params && route.params.planningSeq) {
    planningSeq.value = route.params.planningSeq;
    getPlanDetail();
  }
});

// 상태별 클래스와 텍스트 반환
const getStatusClass = (status) => {
  switch (status) {
    case 'APPROVE':
      return { class: 'approved', text: '승인' };
    case 'PENDING':
      return { class: 'pending', text: '대기' };
    case 'REJECT':
      return { class: 'rejected', text: '반려' };
    default:
      return { class: '', text: status };
  }
};

// API 호출 함수
const getPlanDetail = async () => {
  try {
    const response = await fetchMentoringPlanDetail(planningSeq.value);
    if (response && response.data) {
      planDetail.value = response.data; // 서버에서 반환된 DTO 데이터
    } else {
      // 데이터가 없을 경우 처리 로직
      planDetail.value = {};  // 빈 객체로 설정
    }
  } catch (error) {
    // 에러 발생 시 planDetail을 빈 객체로 설정
    console.error("멘토링 계획서 상세 조회 오류:", error);
    planDetail.value = {};  // 에러 시에도 빈 객체로 설정
  }
};
</script>

<template>
  <div class="content-box">
    <!-- 상세 데이터 화면 -->
    <h1>멘토링 계획서</h1>
    <WhiteBoxListComponent>
      <div class="detail-container">
        <div class="small">
          <div class="date">{{ planDetail.regDate }}</div>
          <div :class="getStatusClass(planDetail.planningStatus).class" class="statue">{{ getStatusClass(planDetail.planningStatus).text }}</div>
        </div>
        <div class="inline">
          <div class="head">기안자</div>
          <div class="content-line">{{ planDetail.employeeName }}</div>
        </div>
        <div class="inline">
          <div class="head">제목</div>
          <div class="content-line">{{ planDetail.planningName }}</div>
        </div>
        <div class="inline">
          <div class="head">계획서 내용</div>
          <div class="content-line">{{ planDetail.planningContent }}</div>
        </div>
        <div class="inline">
          <div class="head">첨부 파일</div>
          <a :href="planDetail.fileUrl" target="_blank" class="content-line">{{ planDetail.fileName }}</a>
        </div>
      </div>
    </WhiteBoxListComponent>
  </div>
</template>

<style scoped>
.inline{
  display: flex;
  margin: 15px 0px;
}
.head{
  font-size: 15px;
  font-weight: 700;
  width: 110px;
}
.statue{
  color: var(--red);
}
h1{
  font-size: 35px;
  font-weight: 700;
  text-align: center;
  margin: 150px 0px 49px 0px;
}
.content-box {
  width: 550px;
}
.content-line{
  width: 330px;
}
.detail-container{
  display: flex;
  flex-direction: column;

}
.small{
  display: flex;
  font-size: 12px;
  gap: 15px;
  margin-bottom: 10px;
}
</style>
