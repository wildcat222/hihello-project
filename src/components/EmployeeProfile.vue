<script setup>

import {onMounted, ref} from "vue";
import {fetchEmployeeInfo} from "@/services/UserApi.js";
import {useUserStore} from "@/stores/UserStore.js";
import router from "@/router/index.js";


const employeeInfo = ref(null);
const employeeFileUrl = ref(null);
const route = router;

// 클릭 이벤트로 라우팅 처리
const navigateToUpdatePassword = () => {
  const employeeSeq = useUserStore().getEmployeeInfo().employeeSeq;
  route.push(`/employee/${employeeSeq}/password`);
};

onMounted(async () => {
  employeeInfo.value = await fetchEmployeeInfo();
  employeeFileUrl.value = employeeInfo.value.data.fileUrl;
});
</script>

<template>

  <div class="profile" v-if="employeeInfo?.data">
    <img class="employee_image mb-20" v-if="!employeeFileUrl" src="../../public/profileImg.png"/>
    <img class="employee_image mb-20" v-if="employeeFileUrl" :src="{ employeeFileUrl }"/>
    <div class="mb-10">
      <h3 class="employee_name">{{ employeeInfo.data.employeeName }}</h3>
      <span class="employee_num">({{ employeeInfo.data.employeeNum }})</span>
    </div>
    <div class="info mb-10">부서 {{ employeeInfo.data.departmentName }}</div>
    <div class="info mb-10">직급 {{ employeeInfo.data.positionName }}</div>
    <div class="info mb-10">전화번호 {{ employeeInfo.data.employeePhone }}</div>
    <div class="info mb-10">이메일 {{ employeeInfo.data.employeeEmail }}</div>
    <button class="button" @click="navigateToUpdatePassword">비밀번호 수정</button>
  </div>

</template>

<style scoped>
.profile {
  background-color: var(--white);
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 30px;
  width: 210px;
  height: 270px;
  text-align: left;
}

.employee_image {
  display: block;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: var(--gray);
}

.employee_name {
  margin: 10px 0;
  display: inline;
}

.info {
  margin: 8px 0;
  font-size: 14px;
}

.button {
  background-color: var(--purple);
  color: var(--white);
  text-decoration: none;
  text-align: center;
  width: 60%;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 25px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.mb-10 {
  margin-bottom: 10px;
}

.mb-20 {
  margin-bottom: 20px;
}
</style>