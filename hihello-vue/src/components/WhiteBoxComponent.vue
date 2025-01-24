<template>
    <div class="white-box">
      <slot></slot>
    </div>
</template>
  
<style scoped>
.white-box {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 2vw;
  margin: 2vw auto;
  width: 90%;
  text-align: center;
  box-sizing: border-box;
  transition: all 0.3s ease;
}
</style>
  