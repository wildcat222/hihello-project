<script setup>
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import AsideComponent from '@/components/AsideComponent.vue';

const route = useRoute();

// 반응형으로 `shouldShowAside`를 계산
const shouldShowAside = computed(() => !route.meta.hideAside);
</script>

<template>
  <div id="app">
    <!-- 조건부 렌더링 -->
    <AsideComponent v-if="shouldShowAside" />
    <div class="router-container">
      <router-view />
    </div>
  </div>
</template>

<style scoped>
#app {
  display: flex;
}

.router-container {
  flex-grow: 1;
  background-color: var(--ivory);
}
</style>
