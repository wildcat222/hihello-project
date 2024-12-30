<script setup>
import {ref} from 'vue';

const props = defineProps({
  employee: {
    type: Object,
    required: true,
  },
});

const roles = [
  {label: '멘티', value: 'MENTEE'},
  {label: '멘토', value: 'MENTOR'},
  {label: '담당자', value: 'HR'},
  {label: '사원', value: 'STAFF'}
];

// 기본 선택된 역할
const selectedRole = ref(props.employee.employeeRole);

const emit = defineEmits(['close-modal', 'update-employee']);

const closeModal = () => {
  emit('close-modal');
};

const updateEmployeeInfo = () => {
  emit('update-employee', selectedRole.value);
}


</script>

<template>
  <div class="confirmation-container">
    <h2 class="confirmation-title">해당 사원의 정보를 수정하시겠습니까?</h2>

    <div class="info-group">
      <div class="info-row">
        <span class="info">사번</span>
        <span class="info-value">{{ employee.employeeNum }}</span>
      </div>
      <div class="info-row">
        <span class="info">부서</span>
        <span class="info-value">{{ employee.departmentName }}</span>
      </div>
      <div class="info-row">
        <span class="info">직급</span>
        <span class="info-value">{{ employee.positionName }}</span>
      </div>
      <div class="info-row">
        <span class="info">이름</span>
        <span class="info-value">{{ employee.employeeName }}</span>
      </div>
      <div class="info-row">
        <label class="info">역할</label>
        <select v-model="selectedRole" class="select-role">
          <option v-for="role in roles" :value="role.value" :key="role.value">
            {{ role.label }}
          </option>
        </select>
      </div>
    </div>

    <div class="button-group">
      <button @click="updateEmployeeInfo" class="update-button">수정</button>
      <button @click="closeModal" class="cancel-button">취소</button>
    </div>
  </div>
</template>

<style scoped>
.confirmation-container {
  padding: 20px;
  border-radius: 10px;
  background-color: var(--white);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  justify-items: center;
}

.confirmation-title {
  font-size: 20px;
  font-weight: bold;
  padding-bottom: 10px;
  margin-bottom: 20px;
}

.info-group {
  width: 60%;
  margin-bottom: 20px;
}

.info-row {
  display: flex;
  justify-content: left;
  margin-bottom: 10px;
}

.info {
  width: 40px;
  font-weight: bold;
}

.info-value {
  margin-left: 50px;
}

.input-position {
  width: 30%;
  height: 1vw;
  margin-left: 50px;
}

.button-group {
  display: flex;
  justify-content: center;
  width: 100%;
  align-self: center;
}

.update-button {
  width: 40%;
  background-color: var(--purple);
  color: var(--white);
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  cursor: pointer;
  margin-right: 5px;
}

.cancel-button {
  width: 40%;
  background-color: var(--light-gray);
  color: var(--black);
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  cursor: pointer;
  margin-left: 5px;
}
</style>