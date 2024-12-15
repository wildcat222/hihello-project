<script setup>

</script>

<template>
  <div class="container">

    <h3>비밀번호 수정</h3>
    <form class="input-form" @submit.prevent="">
      <div class="input-group">
        <label for="currentPassword">현재 비밀번호</label>
        <input type="password" id="currentPassword"/>
      </div>
      <div class="input-group">
        <label for="newPassword">변경 비밀번호</label>
        <input type="password" id="newPassword"/>
      </div>
      <div class="input-group">
        <label for="confirmPassword">변경 비밀번호 확인</label>
        <input type="password" id="confirmPassword"/>
      </div>
      <div class="button-group">
        <button type="submit" class="submit-button">수정</button>
        <button type="button" class="cancel-button">취소</button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.container {
  width: 400px;
  margin: 50px auto;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: var(--white);
}

h3 {
  text-align: center;
  margin-bottom: 20px;
}

.input-form {
  width: 70%;
  justify-self: center;
}

.input-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input {
  width: 95%;
  height: 8px;
  padding: 10px;
  border: 1px solid var(--light-gray);
  border-radius: 5px;
}

.button-group {
  display: flex;
  justify-content: center;
}

.submit-button {
  background-color: var(--purple);
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  cursor: pointer;
  margin-right: 5px;
}

.cancel-button {
  background-color: var(--light-gray);
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  cursor: pointer;
  margin-left: 5px;
}
</style>