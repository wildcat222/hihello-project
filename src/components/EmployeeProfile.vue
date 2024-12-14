<script setup>

</script>

<template>
  <div class="profile">
    <div class="employee_image"></div>
    <h3 class="employee_name">이름</h3>
    <span>(사번)</span>
    <div class="info">부서: </div>
    <div class="info">직급: </div>
    <div class="info">전화 번호: </div>
    <div class="info">이메일: </div>
    <button class="button">비밀번호 수정</button>
  </div>
</template>

<style scoped>
.profile {
  background-color: var(--ivory);
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 200px;
  height: 250px;
  text-align: left;
}
.employee_image {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: var(--gray);
  margin-bottom: 10px;
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
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  cursor: pointer;
  margin-top: 25px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}
</style>