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
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bibi-plus-circle" viewBox="0 0 16 16">
      <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
      <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
    </svg>
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
  background-color: var(--yellow);
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
.add-button:hover{
  background: var(--purple)
}

.bibi-plus-circle{
  margin-top: 5px;
  margin-right: 7px;
}
</style>
