<script setup>
import { defineProps } from "vue";
import { useRouter } from "vue-router";

// defineProps는 컴포넌트의 props를 정의하는 방법
const props = defineProps({
  buttonText: {
    type: String,
  },
  targetPage: {
    type: String,
    required: true,
  },
});

const router = useRouter();

const handleClick = () => {
  router.push(props.targetPage); // 전달받은 targetPage로 이동
};
</script>

<template>
  <div class="add-button" @click="handleClick">
    <img src="../assets/plus-icon.png" class="plus-icon" alt="플러스 아이콘">
    <div class="button-text">
      {{ buttonText }}
    </div>
  </div>
</template>

<style scoped>
.plus-icon {
  width: 25px;
  height: 24px;
  margin-right: 4px; /* 아이콘과 텍스트 사이 간격 */
  display: inline-block; /* 아이콘의 정렬을 텍스트와 맞추기 */
}

.button-text {
  margin: 3px 0 0 0;
}

.add-button {
  background-color: #FFC107;
  width: 125px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 15px;
  font-weight: bold;
  border-radius: 5px;
  cursor: pointer;
}
</style>
