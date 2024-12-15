<script setup>

import WhiteBoxListComponent from "@/components/WhiteBoxListComponent.vue";
import ListComponent from "@/components/ListComponent.vue";
</script>

<template>
  <div class="employee-list-container">
    <div class="page-title">사원 관리</div>
    <WhiteBoxListComponent>
      <ListComponent>
        <template #header>
          <div class="employee-list-row-container">
            <div>이름/사번</div>
            <div>직급</div>
            <div>부서</div>
            <div>연락처</div>
            <div>이메일</div>
            <div>역할</div>
          </div>
        </template>
        <template #item="{ item }">
          <div class="employee-list-row-container item">
          </div>
        </template>
      </ListComponent>
    </WhiteBoxListComponent>
  </div>
</template>

<style scoped>
.employee-list-container {
  width: 70%;
}

.page-title {
  font-size: 35px;
  font-weight: bold;
  text-align: center;
  margin: 6.562rem 0 2.88rem 0;
}

.employee-list-row-container {
  width: 100%;
  text-align: center;
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 2fr 2fr 1fr 2fr;

}
</style>