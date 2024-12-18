<script setup>
import {computed, onMounted, onUnmounted, ref} from 'vue';
import {useRoute} from 'vue-router';
import AsideComponent from '@/components/AsideComponent.vue';
import {useUserStore} from '@/stores/UserStore';
import {springAPI} from '@/services/axios.js';
import EmployeeProfile from "@/components/EmployeeProfile.vue";

const route = useRoute();
const userStore = useUserStore();

const shouldShowAside = computed(() => !route.meta.hideAside);

const shouldShowProfile = ref(false);
const activeMenu = ref(null);

// Aside에서 이벤트 발생 시 프로필 활성화/비활성화
const toggleProfile = () => {
  shouldShowProfile.value = !shouldShowProfile.value;

  // Profile 활성화 시 SubMenu 비활성화
  if (shouldShowProfile.value) {
    activeMenu.value = null;
  }
};

// 외부 클릭 시 Profile/SubMenu 비활성화
const hideComponentsOnOutsideClick = (event) => {
  const profileElement = document.querySelector('.profile');
  const asideMenuElement = document.querySelector('.aside');

  if (!event.target.closest('.profile') && !event.target.closest('.menu-item')) {
    shouldShowProfile.value = false;
    activeMenu.value = null;
  }
};

onMounted(() => {
  if (userStore.accessToken) {
      springAPI.defaults.headers.common['Authorization'] = `Bearer ${userStore.accessToken}`;
      userStore.isAuthenticated = true;
  }

  // Axios 인터셉터 초기화
  userStore.initializeInterceptors();

  // 외부 클릭 이벤트 리스너 등록
  document.addEventListener('mousedown', hideComponentsOnOutsideClick);
});

onUnmounted(() => {
  // 컴포넌트가 언마운트 될 때 이벤트 리스너 제거
  document.removeEventListener('mousedown', hideComponentsOnOutsideClick);
});
</script>

<template>
  <div id="app">
    <!-- AsideComponent 렌더링 여부 결정 -->
    <AsideComponent class="aside" v-if="shouldShowAside"
                    @profile-modal="toggleProfile"
                    :active-menu="activeMenu"
                    @update-active-menu="activeMenu = $event"/>
    <EmployeeProfile class="profile" v-if="shouldShowProfile"/>
    <div class="router-container">
      <router-view />
    </div>
  </div>
</template>

<style scoped>
#app {
  display: flex;
  background-color: var(--ivory);
}

.router-container {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.aside {
  position: relative;
}

.profile {
  position: absolute;
  margin-top: 150px;
  margin-left: 210px;

}
</style>
