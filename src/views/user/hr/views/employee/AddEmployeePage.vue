<script setup>

import WhiteBoxListComponent from "@/components/WhiteBoxListComponent.vue";
import SearchBarComponent from "@/components/SearchBarComponent.vue";
</script>

<template>
<!--  <div class="add-employee-container">-->
<!--    <div class="page-title">사원 등록</div>-->
<!--    <div class="search-bar-container">-->
<!--      <div class="search-box">-->
<!--        <SearchBarComponent @search="">-->
<!--          <select v-model="" class="box">-->
<!--            <option value="name">이름</option>-->
<!--            <option value="num">사번</option>-->
<!--          </select>-->
<!--          <div class="yellow-box" v-if="">-->

<!--          </div>-->
<!--        </SearchBarComponent>-->
<!--      </div>-->
<!--    </div>-->

<!--    <WhiteBoxListComponent>-->
<!--      <div class="input-form">-->
<!--        <form class="input-employee-info" @submit.prevent="">-->
<!--          <div class="input-group">-->
<!--            <label for="employee-name">이름</label>-->
<!--            <input type="text" class="employee-info" id="employee-name">-->
<!--          </div>-->
<!--          <div class="input-group">-->
<!--            <label for="employee-num">사번</label>-->
<!--            <input type="text" class="employee-info" id="employee-num">-->
<!--          </div>-->
<!--          <div class="input-group">-->
<!--            <label for="employee-department">부서</label>-->
<!--            <input type="text" class="employee-info" id="employee-department">-->
<!--          </div>-->
<!--          <div class="input-group">-->
<!--            <label for="employee-position">직급</label>-->
<!--            <input type="text" class="employee-info" id="employee-position">-->
<!--          </div>-->
<!--          <div class="input-group">-->
<!--            <label for="employee-phone">연락처</label>-->
<!--            <input type="text" class="employee-info" id="employee-phone">-->
<!--          </div>-->
<!--          <div class="input-group">-->
<!--            <label for="employee-email">이메일</label>-->
<!--            <input type="text" class="employee-info" id="employee-email">-->
<!--          </div>-->
<!--          <div class="input-group">-->
<!--            <label for="employee-role">역할</label>-->
<!--            <input type="text" class="employee-info" id="employee-role">-->
<!--          </div>-->
<!--          <div class="add-button">-->
<!--            <button type="submit">등록</button>-->
<!--          </div>-->
<!--        </form>-->
<!--      </div>-->
<!--    </WhiteBoxListComponent>-->
<!--  </div>-->
</template>

<style scoped>
.add-employee-container {
  width: 70%;
}

.page-title {
  font-size: 35px;
  font-weight: bold;
  text-align: center;
  margin: 6.562rem 0 2.88rem 0;
}

.input-form {
  display: flex;
  justify-content: center;
}

.input-employee-info {
  margin-top: 30px;
  width: 60%;
}

.input-group {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 30px;
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  white-space: nowrap;
}

input {
  width: 60%;
  height: 8px;
  padding: 10px;
  border: 1px solid var(--light-gray);
  border-radius: 5px;
  align-self: end;
}

.add-button {
  display: flex;
  justify-content: center;
}

button {
  width: 200px;
  background-color: var(--purple);
  color: var(--white);
  margin-top: 20px;
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  cursor: pointer;
}
</style>