<script setup>
import {computed, onMounted, onUnmounted, ref} from 'vue';
import {useRoute} from 'vue-router';
import AsideComponent from '@/components/AsideComponent.vue';
import {useUserStore} from '@/stores/UserStore';
import {springAPI} from '@/services/axios.js';

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
    <AsideComponent class="aside" v-if="shouldShowAside"/>
    <div class="router-container">
      <router-view />
    </div>
  </div>
</template>

<style scoped>
#app {
  display: flex;
  height: 100%;
  background-color: var(--ivory);
}

.router-container {
  font-family: "Noto Sans KR", sans-serif;
  flex-grow: 1;
  height: 100vh;
  overflow: hidden;
  flex-direction: column;
  align-items: center;
}

.aside {
  height: 100%;
  position: relative;
}
</style>
