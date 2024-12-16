<script setup>
defineProps({
  text: String  // 전달받을 글씨 데이터
})
</script>

<template>
<div class="box">
  <div class="box__title">
    {{ text }}
  </div>
</div>
</template>

<style scoped>
.box {
  background-color: #7031FC;
  width: 89px;
  height: 25px;
  border-radius: 10px;
  display: flex;
  justify-content: center;  /* 글씨를 중앙에 정렬 */
  align-items: center;  /* 글씨를 중앙에 정렬 */
  color: white;  /* 글씨 색상 */
}
.box__title{
  padding: 3px 0 0 0;
}
</style>