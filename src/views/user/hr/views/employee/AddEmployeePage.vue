<template>
  <div class="registration-container">
    <h2 class="title">사원 등록</h2>

    <WhiteBoxComponent class="form-container">
      <div class="form-box">
        <form @submit.prevent="handleSubmit">
          <!-- Basic Information -->
          <div class="form-group">
            <div class="info">이름</div>
            <div class="form-input-container">
              <input
                  v-model="formData.employeeName"
                  type="text"
                  required
                  class="form-input"
              />
            </div>
          </div>

          <div class="form-group">
            <div class="info">사번</div>
            <div class="form-input-container">
              <input
                  v-model="formData.employeeNum"
                  type="text"
                  required
                  class="form-input"
              />
            </div>
          </div>

          <!-- Department Dropdown -->
          <div class="form-group">
            <div class="info">부서</div>
            <div class="form-input-container">
              <div class="dropdown-container">
                <input
                    v-model="formData.departmentName"
                    type="text"
                    readonly
                    @click="toggleDropdown('department')"
                    class="form-input dropdown-input"
                />
                <div v-if="dropdowns.department" class="dropdown-menu">
                  <div
                      v-for="dept in departmentName"
                      :key="dept"
                      @click="selectOption('department', dept)"
                      class="dropdown-item"
                  >
                    {{ dept }}
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Position Dropdown -->
          <div class="form-group">
            <div class="info">직급</div>
            <div class="form-input-container">
              <div class="dropdown-container">
                <input
                    v-model="formData.positionName"
                    type="text"
                    readonly
                    @click="toggleDropdown('position')"
                    class="form-input dropdown-input"
                />
                <div v-if="dropdowns.position" class="dropdown-menu">
                  <div
                      v-for="pos in positionName"
                      :key="pos"
                      @click="selectOption('position', pos)"
                      class="dropdown-item"
                  >
                    {{ pos }}
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Contact Information -->
          <div class="form-group">
            <div class="info">연락처</div>
            <div class="form-input-container">
              <div class="phone-input">
                <input
                    v-model="phone1"
                    type="text"
                    maxlength="3"
                    class="form-input phone-part"
                    @input="updatePhone"
                />
                <span>-</span>
                <input
                    v-model="phone2"
                    type="text"
                    maxlength="4"
                    class="form-input phone-part"
                    @input="updatePhone"
                />
                <span>-</span>
                <input
                    v-model="phone3"
                    type="text"
                    maxlength="4"
                    class="form-input phone-part"
                    @input="updatePhone"
                />
              </div>
            </div>
          </div>

          <!-- Email -->
          <div class="form-group">
            <div class="info">이메일</div>
            <div class="form-input-container">
              <input
                  v-model="formData.employeeEmail"
                  type="text"
                  class="form-input"
              />
            </div>
          </div>

          <!-- Role Selection -->
          <div class="form-group">
            <div class="info">역할</div>
            <div class="form-input-container">
              <div class="dropdown-container">
                <input
                    v-model="formData.employeeRole"
                    type="text"
                    readonly
                    @click="toggleDropdown('role')"
                    class="form-input dropdown-input"
                />
                <div v-if="dropdowns.role" class="dropdown-menu">
                  <div
                      v-for="role in employeeRole"
                      :key="role"
                      @click="selectOption('role', role)"
                      class="dropdown-item"
                  >
                    {{ role }}
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Buttons -->
          <div class="button-group">
            <button type="submit" class="btn btn-primary">등록</button>
            <button type="button" @click="goBack" class="btn btn-secondary">돌아가기</button>
          </div>
        </form>
      </div>
    </WhiteBoxComponent>
  </div>
</template>

<script setup>
import {reactive, ref} from 'vue'
import WhiteBoxComponent from "@/components/WhiteBoxComponent.vue"
import router from "@/router/index.js";
import {createEmployee} from "@/services/UserApi.js";

const route = router;

// Phone number parts
const phone1 = ref('')
const phone2 = ref('')
const phone3 = ref('')

// Form data
const formData = reactive({
  employeeName: '',
  employeeNum: '',
  departmentName: '',
  positionName: '',
  employeePhone: '',
  employeeEmail: '',
  employeeRole: ''
})

// Dropdown
const dropdowns = reactive({
  department: false,
  position: false,
  role: false
})

// 역할 매핑 객체 추가
const roleMapping = {
  '멘티': 'MENTEE',
  '멘토': 'MENTOR',
  '담당자': 'HR',
  '사원': 'STAFF'
}

// Dropdown 옵션
const departmentName = ['교육팀', '영업팀', '총무팀', 'SW 개발팀', '인사팀']
const positionName = ['인턴', '부장', '대리', '팀장']
const employeeRole = ['멘티', '멘토', '담당자', '사원']

// 휴대폰 번호 통합
const updatePhone = () => {
  formData.employeePhone = `${phone1.value}-${phone2.value}-${phone3.value}`
}

// Toggle dropdown visibility
const toggleDropdown = (type) => {
  Object.keys(dropdowns).forEach(key => {
    if (key !== type) dropdowns[key] = false
  })
  dropdowns[type] = !dropdowns[type]
}

// Select dropdown option
const selectOption = (type, value) => {
  switch (type) {
    case 'department':
      formData.departmentName = value
      break
    case 'position':
      formData.positionName = value
      break
    case 'role':
      formData.employeeRole = value
      break
  }
  dropdowns[type] = false
}

// Form submission
const handleSubmit = async () => {
  try {
    const submissionData = {
      ...formData,
      employeeRole: roleMapping[formData.employeeRole] // 역할 값을 DB 형식으로 변환
    }

    await createEmployee(submissionData);
    alert('사원이 등록되었습니다.');
    await route.push(`/employee-management`);
  } catch (error) {
    console.error('사원 등록을 실패하였습니다.', error);
    alert('등록 중 오류가 발생하였습니다.');
    location.reload();
  }
}

const goBack = () => {
  route.back()
}
</script>

<style scoped>
.title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1.5rem;
}

.form-container {
  width: min(70vw, 1200px);  /* 최대 1200px까지만 커지도록 제한 */
  margin: 0 auto;
}

.form-box {
  width: min(35vw, 800px);  /* 컨테이너의 80%를 차지하되 최대 800px로 제한 */
  margin: 0 auto;
  padding: calc(1rem + 1vw);  /* 기본 여백 + 화면 크기에 따른 추가 여백 */
}

.form-group {
  margin-bottom: calc(1rem + 0.5vw);
  display: flex;
  align-items: center;
  gap: calc(1rem + 0.5vw);
}

.info {
  min-width: calc(60px + 1vw);  /* 기본 크기 + 화면 크기에 따른 추가 너비 */
  font-size: calc(0.9rem + 0.2vw);  /* 기본 폰트 크기 + 화면 크기에 따른 증가 */
}

.form-input-container {
  flex: 1;
}

.form-input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.dropdown-container {
  position: relative;
  width: 100%;
}

.dropdown-input {
  cursor: pointer;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  width: 100%;
  background: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.dropdown-item {
  padding: 0.5rem;
  cursor: pointer;
}

.dropdown-item:hover {
  background: #f5f5f5;
}

.phone-input {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.phone-part {
  width: 80px;
}

.button-group {
  display: flex;
  justify-content: center;  /* 버튼 그룹만 가운데 정렬 */
  gap: 1rem;
  margin-top: 3rem;
}

.btn {
  min-width: 120px;
  padding: 0.7rem 1.5rem;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s ease;  /* transition 속성 수정 */
}

.btn-primary {
  background-color: var(--purple);
  color: white;
}

.btn-primary:hover {
  background-color: var(--light-purple);
}

.btn-secondary {
  background-color: var(--light-gray);
  color: white;
}

.btn-secondary:hover {
  background-color: #757575;
}
</style>