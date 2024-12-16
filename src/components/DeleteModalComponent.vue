<!-- ModalComponent.vue -->
<template>
  <div v-if="visible" class="modal-overlay">
    <div class="modal">
      <h3 v-html="title"></h3>  <!-- v-html로 title을 HTML로 처리 -->
      <p class="modal-message" v-html="message"></p>
      <div class="modal-actions">
        <button @click="onConfirm" class="modal-confirm-button">{{ confirmText }}</button>
        <button @click="closeModal" class="modal-cancel-button">{{ cancelText }}</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';

// Props for dynamic content
const props = defineProps({
  visible: Boolean,
  title: String,
  message: String,
  confirmText: {
    type: String,
    default: "확인",
  },
  cancelText: {
    type: String,
    default: "취소",
  },
});

// Emit events
const emit = defineEmits(["close", "confirm"]);

const closeModal = () => {
  emit("close"); // Emit close event
};

const onConfirm = () => {
  emit("confirm"); // Emit confirm event
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal {
  background: #F4F2EE;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 400px;
  text-align: center;
}
.modal-title {
  font-size: 20px;
  font-weight: bold;
}
.modal-message {
  margin: 10px 0 20px 135px;
  text-align: left; /* 왼쪽 정렬 */
  white-space: pre-line; /* 줄 바꿈을 반영 */
}
.modal-actions button {
  margin: 0 10px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.modal-confirm-button {
  background: #7031fc;
  color: white;
}
.modal-cancel-button {
  background: black;
  color:white;
}

</style>
