<script setup>
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import AsideComponent from '@/components/AsideComponent.vue';
import { useUserStore } from '@/stores/UserStore';
import { springAPI } from '@/services/axios.js';

const route = useRoute();
const userStore = useUserStore();

const shouldShowAside = computed(() => !route.meta.hideAside);

onMounted(() => {
    if (userStore.accessToken) {
        springAPI.defaults.headers.common['Authorization'] = `Bearer ${userStore.accessToken}`;
        userStore.isAuthenticated = true;
    }

    // Axios 인터셉터 초기화
    userStore.initializeInterceptors();
});
</script>

<template>
  <div id="app">
    <!-- AsideComponent 렌더링 여부 결정 -->
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
