<script setup>
import WhiteBoxListComponent from "@/components/WhiteBoxListComponent.vue";
import ListComponent from "@/components/ListComponent.vue";
import SearchBarComponent from "@/components/SearchBarComponent.vue";
import router from "@/router/index.js";
import {onMounted, reactive, ref} from "vue";
import {deleteEmployee, fetchEmployeeList, searchEmployees, updateEmployee} from "@/services/UserApi.js";
import DeleteEmployeeModal from "@/views/user/hr/modal/employee/DeleteEmployeeModal.vue";
import UpdateEmployeeModal from "@/views/user/hr/modal/employee/UpdateEmployeeModal.vue";

const route = router;
const employeeList = reactive([]);

const searchCategory = ref('name');

// 사원 리스트 조회
const loadEmployeeList = async () => {
  try {
    const response = await fetchEmployeeList();
    response.data.forEach(employee => {
      employeeList.push({
        employeeSeq: employee.employeeSeq,
        employeeNum: employee.employeeNum,
        employeeName: employee.employeeName,
        departmentName: employee.departmentName,
        positionName: employee.positionName,
        employeePhone: employee.employeePhone,
        employeeEmail: employee.employeeEmail,
        employeeRole: employee.employeeRole
      });
    });
  } catch (error) {
    alert('사원 목록을 조회하는 도중 오류가 발생했습니다.');
  }
}

// 사원 검색
const searchEmployeeList = async (query) => {
  if (!query.trim()) {  // 검색어가 비어있다면
    await loadEmployeeList();
    return;
  }
  try {
    console.log('검색 호출 시작 : ', searchCategory.value, query);
    const encodedQuery = encodeURIComponent(query);
    const response = await searchEmployees(searchCategory.value, encodedQuery);
    employeeList.splice(0);  // 기존 리스트 초기화
    response.data.data.forEach(employee => {
      employeeList.push({
        employeeSeq: employee.employeeSeq,
        employeeNum: employee.employeeNum,
        employeeName: employee.employeeName,
        departmentName: employee.departmentName,
        positionName: employee.positionName,
        employeePhone: employee.employeePhone,
        employeeEmail: employee.employeeEmail,
        employeeRole: employee.employeeRole
      });
    });
  } catch (error) {
    alert("해당하는 데이터가 없습니다.");
  }
};

const roleMap = {
  MENTEE: '멘티',
  MENTOR: '멘토',
  HR: '담당자',
  STAFF: '사원'
}

const goToRegisterPage = () => {
  route.push(`/employee-management/create`);
};

const shouldShowDelModal = ref(false);
const employee = ref(null);
const activeDelModal = (item) => {
  if (shouldShowDelModal.value) {
    shouldShowDelModal.value = false;
    return;
  }
  shouldShowDelModal.value = true;
  employee.value = {
    employeeSeq: item.employeeSeq,
    employeeName: item.employeeName,
    employeeNum: item.employeeNum,
    departmentName: item.departmentName,
    positionName: item.positionName
  }
  console.log(employee);
}

const shouldShowUpdateModal = ref(false);
const activeUpdateModal = (item) => {
  if (shouldShowUpdateModal.value) {
    shouldShowUpdateModal.vale = false;
    return;
  }
  shouldShowUpdateModal.value = true;
  employee.value = {
    employeeSeq: item.employeeSeq,
    employeeName: item.employeeName,
    employeeNum: item.employeeNum,
    departmentName: item.departmentName,
    positionName: item.positionName,
    employeeRole: item.employeeRole
  }
}

// 수정, 삭제 모달 숨기는 용도
const visibleDelModal = () => {
  if (shouldShowDelModal.value) {
    shouldShowDelModal.value = false;
  } else if (shouldShowUpdateModal.value) {
    shouldShowUpdateModal.value = false;
  }
}

// 수정 모달 수정 버튼 클릭 시
const modifyEmployee = async (employeeRole) => {
  try {
    await updateEmployee(employee.value.employeeSeq, employeeRole);
    shouldShowUpdateModal.value = false;
    alert('사원 정보를 수정하였습니다.');
  } catch (error) {
    console.error(error);
    alert('사원 정보를 수정하는데 실패하였습니다.');
  }
  // location.reload();
}

// 삭제 모달 삭제 버튼 클릭 시
const deleteEmployeeBySeq = async () => {
  try {
    await deleteEmployee(employee.value.employeeSeq);
    alert('사원을 삭제하였습니다.');
  } catch (error) {
    alert('사원 삭제에 실패하였습니다.')
  }
  location.reload();
}

onMounted(async () => {
  await loadEmployeeList();
});

</script>

<template>
  <div class="employee-list-container">
    <div class="page-title">사원 관리</div>
    <div class="search-bar-container">
      <div class="search-box">
        <SearchBarComponent @search="searchEmployeeList"/>
        <select v-model="searchCategory" class="box">
          <option value="name">이름</option>
          <option value="num">사번</option>
        </select>
        <div class="yellow-box" @click="goToRegisterPage">
          <img
              src="https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/8d64cbf7-77f8-4670-8ddf-40e43d7bc481_plus.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241216T063855Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAQXPZDBYQREV7D6US%2F20241216%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=7b785c3af1fdf24cef7127814d2d0327e29824719c832902b14f72af28fb0af6"/>
          <div>사원 등록</div>
        </div>
      </div>
    </div>

    <WhiteBoxListComponent>
      <ListComponent :items="employeeList">
        <!-- 헤더 슬롯 -->
        <template #header>
          <div class="employee-list-row-container">
            <div>이름/사번</div>
            <div>부서</div>
            <div>직급</div>
            <div>연락처</div>
            <div>이메일</div>
            <div>역할</div>
          </div>
        </template>

        <!-- 아이템 슬롯 -->
        <template #item="{ item, index }">
          <div class="employee-list-row">
            <div>{{ item.employeeName }}/{{ item.employeeNum }}</div>
            <div class="left-title">{{ item.departmentName }}</div>
            <div class="left-title">{{ item.positionName }}</div>
            <div class="left-title">{{ item.employeePhone }}</div>
            <div class="left-title">{{ item.employeeEmail }}</div>
            <div class="left-title">{{ roleMap[item.employeeRole] }}</div>

            <div class="button-group">
              <div class="update-btn" @click="activeUpdateModal(item)">수정</div>
              <div class="delete-btn" @click="activeDelModal(item)">삭제</div>
            </div>
          </div>
        </template>
      </ListComponent>
    </WhiteBoxListComponent>
  </div>

  <UpdateEmployeeModal v-if="shouldShowUpdateModal" class="modal"
                       :employee="employee"
                       @close-modal="visibleDelModal"
                       @update-employee="modifyEmployee"/>

  <DeleteEmployeeModal v-if="shouldShowDelModal" class="modal"
                       :employee="employee"
                       @close-modal="visibleDelModal"
                       @delete-employee="deleteEmployeeBySeq"/>
</template>

<style scoped>
.employee-list-container {
  width: 70%;
  margin: 0 auto;
  position: relative;
}

.page-title {
  font-size: 35px;
  font-weight: bold;
  text-align: center;
  margin: 6.562rem 0 2.88rem 0;
}

.employee-list-row-container {
  position: relative;
  width: 100%;
  text-align: center;
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 2fr 2fr 1fr 2fr;
}

.employee-list-row {
  margin: 0 20px 14px 20px;
  font-size: 13px;
  justify-content: space-between;
  align-items: center;
  width: 96%;
  text-align: center;
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 2fr 2fr 1fr 2fr;
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

.left-title {
  justify-content: center;
}

.search-bar-container {
  width: 90%;
  margin: 29px auto;
  align-items: center;
}

.search-box {
  display: flex;
  gap: 20px;
}

.box {
  border: none;
  background-color: var(--white);
  box-shadow: 2px 2px 4px 0 var(--gray);
  border-radius: 15px;
  width: 100px;
  height: 50px;
}

.button-group {
  display: flex;
}

.button-group > div {
  width: 70%;
}

.update-btn {
  width: 40%;
  background-color: var(--purple);
  color: var(--white);
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  cursor: pointer;
  margin-right: 5px;
}

.delete-btn {
  width: 40%;
  background-color: var(--gray);
  color: var(--white);
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  cursor: pointer;
  margin-right: 5px;
}

.modal {
  position: fixed;
  margin-top: 3vw;
}
</style>