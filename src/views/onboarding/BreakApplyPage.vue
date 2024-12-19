<template>
    <div>
        <h1 class="break-title">휴가 신청</h1>
        <white-box>
            <div class="break-info">
                <form class="break-form" @submit.prevent="handleSubmit">
                    <div class="break-form-group-row">
                        <label>잔여 휴가 :</label>
                        <span class="break-days">14일</span>
                    </div>
                    <div class="break-form-group-row">
                        <label for="target">대상자 :</label>
                        <span id="target">{{ employeeInfo ? employeeInfo.employeeName : '' }}</span>
                    </div>
                    <div class="break-form-group-row">
                        <label for="reason">휴가사유 :</label>
                        <select id="reason" class="break-input" v-model="reason">
                            <option value="">선택</option>
                            <option>연차휴가</option>
                            <option>출장휴가</option>
                            <option>경조휴가</option>
                            <option>건강검진</option>
                        </select>
                    </div>
                    <div class="break-form-group-row">
                        <label for="type">휴가형태 :</label>
                        <select id="type" class="break-input" v-model="leaveType">
                            <option value="">선택</option>
                            <option>연차</option>
                            <option>반차</option>
                            <option>반반차</option>
                        </select>
                    </div>
                    <div class="break-form-group-row">
                        <label for="start-date">기간 :</label>
                        <div class="break-date-range">
                            <!-- 오늘 날짜 이전 선택 불가 -->
                            <input id="start-date" type="date" class="break-input" v-model="startDate" :min="today">
                            <input type="time" class="break-input" v-model="startTime">
                            <span class="break-separator">부터</span>
                            <input type="date" class="break-input" v-model="endDate" :min="today">
                            <input type="time" class="break-input" v-model="endTime">
                            <span class="break-separator">까지</span>
                        </div>
                    </div>
                    <div class="break-form-group-row">
                        <label for="reason-text">사유 :</label>
                        <textarea id="reason-text" class="break-textarea" rows="4" v-model="reasonText"></textarea>
                    </div>
                    <div class="break-button-container">
                        <button type="submit" class="break-submit-button">기안하기</button>
                    </div>
                </form>
            </div>
        </white-box>
    </div>
</template>

<script setup>
import "@/styles/onboarding/BreakApply.css";
import { ref, onMounted } from 'vue';
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import { fetchEmployeeInfo } from "@/services/UserApi.js";
import { useRouter } from 'vue-router';

const router = useRouter();

const employeeInfo = ref(null);

// 휴가 신청에 필요한 필드들
const reason = ref('');
const leaveType = ref('');
const startDate = ref('');
const startTime = ref('');
const endDate = ref('');
const endTime = ref('');
const reasonText = ref('');

// 오늘 날짜 (YYYY-MM-DD 형태로 변환)
const today = new Date().toISOString().split('T')[0];

onMounted(async () => {
  try {
    const response = await fetchEmployeeInfo();
    employeeInfo.value = response.data;
  } catch (error) {
    console.error("내 정보 조회 실패", error);
  }
});

function handleSubmit() {
  if (!employeeInfo.value?.employeeName || !reason.value || !leaveType.value || !startDate.value || !startTime.value || !endDate.value || !endTime.value || !reasonText.value) {
    alert('모든 필드를 입력해주세요.');
    return;
  }
  
  // 모든 필드 입력 완료 시 알림
  alert('축하합니다! 미션을 수행하셨습니다!');
  
  // /main 페이지로 이동
  router.push('/main');
}
</script>

<style scoped>
.white-box {
    width: 70vw;
}
</style>
