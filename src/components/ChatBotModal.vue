<template>
  <div v-if="isVisible" class="chatbot-modal">
    <div class="chatbot-modal-content">
      <button class="close-button" @click="$emit('update:isVisible', false)">×</button>
      <iframe
          class="chatbot-iframe"
          :src="iframeSrc"
          frameborder="0"
          allowfullscreen
      ></iframe>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

defineProps({
  isVisible: Boolean,
  iframeSrc: {
    type: String,
    default: '' // 기본값으로 빈 URL 설정
  }
});
</script>

<style scoped>
.chatbot-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.chatbot-modal-content {
  position: relative;
  width: 80%;
  max-width: 600px;
  height: 80%;
  background: white;
  border-radius: 10px;
  overflow: hidden;
}

.close-button {
  position: absolute;
  top: 0;
  right: 4px;
  background: none;
  border: none;
  font-size: 30px;
  cursor: pointer;
  color: var(--black)
}

.chatbot-iframe {
  width: 100%;
  height: 100%;
  border: none;
}
</style>
