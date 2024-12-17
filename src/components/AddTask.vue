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
    <img
        src="https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/4bbe8df4-8f32-47a8-bf29-d04f1198cd42_plus-icon.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241217T064323Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAQXPZDBYQREV7D6US%2F20241217%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=1ae7a4d9e0574c673608ca68d39c389dc84724811d83bbbc9bfb4bac197dbce5"
         class="plus-icon"
         alt="플러스 아이콘">
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
