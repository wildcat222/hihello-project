<template>
  <aside class="aside-menu" @click.stop>
    <!-- 로고 클릭 시 /main으로 이동 -->
    <div class="logo">
      <router-link to="/main" class="logo-style">HiHello</router-link>
    </div>

    <!-- 메뉴 -->
    <ul class="aside-menu-list">
      <!-- 이름 조회 -->
      <div>
        <li class="aside-login-name" >
          <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" class="bi bi-person"
               viewBox="0 0 16 16">
            <path
                d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6m2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0m4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4m-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10s-3.516.68-4.168 1.332c-.678.678-.83 1.418-.832 1.664z"/>
          </svg>
          <span class="login-name" @click.stop="openProfileModal">{{ employeeName }} 님</span>
          <i class="notify fa-solid fa-bell" @click.stop="openAlarmModal"/>
        </li>
      </div>


      <!-- 로그아웃 메뉴 -->
      <li class="aside-logout-button" @click="logout">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-box-arrow-right"
             viewBox="0 0 16 16">
          <path fill-rule="evenodd"
                d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0z"/>
          <path fill-rule="evenodd"
                d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708z"/>
        </svg>
        로그아웃
      </li>

      <!-- 메뉴 리스트 -->
      <li v-for="menu in filteredMenus" :key="menu.name" class="menu-item">
        <div :class="{ active: activeMenu }" @click="toggleMenu(menu.name)">
          <router-link v-if="menu.url" :to="menu.url">{{ menu.name }}</router-link>
          <span v-else>{{ menu.name }}</span>
        </div>

        <!-- 서브 메뉴 -->
        <ul v-if="menu.subMenus && activeMenu === menu.name" class="sub-menu">
          <li v-for="subMenu in menu.subMenus" :key="subMenu.name" class="sub-menu-item">
            <router-link :to="subMenu.url">{{ subMenu.name }}</router-link>
          </li>
        </ul>
      </li>
    </ul>
  </aside>
  <AlarmModal class="alarm-modal" v-if="shouldShowAlarms" @click.stop/>
  <EmployeeProfile class="profile-modal" v-if="shouldShowProfile" @click.stop/>
</template>

<script setup>
import {computed, onMounted, onUnmounted, ref} from "vue";
import {useUserStore} from "@/stores/UserStore.js";
import router from "@/router/index.js";
import {fetchName} from "@/services/UserApi.js";
import {springAPI} from "@/services/axios.js";
import AlarmModal from "@/components/AlarmModal.vue";
import EmployeeProfile from "@/components/EmployeeProfile.vue";

const userStore = useUserStore();

// 상태 관리
const shouldShowAlarms = ref(false);
const shouldShowProfile = ref(false);
const activeMenu = ref(null);

const openProfileModal = () => {
  shouldShowProfile.value = !shouldShowProfile.value; // 프로필 모달 열기
  activeMenu.value = null;
  shouldShowAlarms.value = false;
};

const openAlarmModal = () => {
  shouldShowAlarms.value = !shouldShowAlarms.value;
  activeMenu.value = null;
  shouldShowProfile.value = false;
};

// 메뉴 토글 로직
const toggleMenu = (menuName) => {
  shouldShowProfile.value = false; // 메뉴 클릭 시 프로필 모달 닫기
  shouldShowAlarms.value = false;
  activeMenu.value = activeMenu.value === menuName ? null : menuName;
};

// 모달 및 메뉴 초기화 함수
const resetModalsAndMenu = () => {
  shouldShowAlarms.value = false;
  shouldShowProfile.value = false;
  activeMenu.value = null;
};

const handleOutsideClick = (event) => {
  // aside-menu, alarm-modal, profile-modal 클래스를 가진 요소들을 찾습니다
  const asideMenu = document.querySelector('.aside-menu');
  const alarmModal = document.querySelector('.alarm-modal');
  const profileModal = document.querySelector('.profile-modal');

  // 클릭된 요소가 aside나 모달 내부가 아닌 경우에만 초기화
  if (!asideMenu?.contains(event.target) &&
      !alarmModal?.contains(event.target) &&
      !profileModal?.contains(event.target)) {
    resetModalsAndMenu();
  }
};

// 라우터 변경 감지
router.beforeEach((to, from, next) => {
  resetModalsAndMenu();
  next();
});

function logout() {
  userStore.logout();
}

// 로그인 한 사원 이름 조회
const employeeSeq = ref(null);
const employeeName = ref("");
const loadName = async (employeeSeq) => {
  if (!employeeSeq) return;

  try {
    const name = await fetchName(employeeSeq);
    if (name) employeeName.value = name;
  } catch (error) {
    console.error('Error loading name:', error);
    employeeName.value = "";
  }
};

const menus = ref([
  // 멘티 ASIDE
  {name: "인턴 위키", url: "/wiki", role: "MENTEE"},
  {name: "멘토 소개", url: "/mentor/intro", role: "MENTEE"},
  // { name: "멘토 채팅", url: "/mentor-chat", role: "MENTEE" },
  {name: "보고서 조회", url: "/mentoring/report", role: "MENTEE"},

  // 담당자 ASIDE
  {name: "사원 관리", url: "/employee-management", role: "HR"},
  {
    name: "멘토링",
    role: "HR",
    subMenus: [
      {name: "멘토링 매칭", url: "/mentoring/matching"},
      {name: "멘토링 계획서", url: "/mentoring/planning"},
      {name: "멘토링 보고서", url: "/mentoring/report"},
    ],
  },
  {name: "온보딩 설계", url: "/onboarding/design", role: "HR"},
  {
    name: "온보딩 데이터 입력",
    role: "HR",
    subMenus: [
      {name: "퀴즈 관리", url: "/hr/quiz"},
      {name: "과제 등록", url: "/task/add"},
      {name: "동료 평가 지표 관리", url: "/hr/peer/review/list"},
      {name: "공통 평가 지표 관리", url: "/taskInd/manage"},
    ],
  },
  {
    name: "온보딩 결과 조회",
    role: "HR",
    subMenus: [
      {name: "퀴즈 결과 조회", url: "/hr/quiz/result"},
      {name: "과제 평가 조회", url: "/task-eval"},
      {name: "동료 평가 조회", url: "/hr/peer/review"},
    ],
  },
  {name: "최종 평가", url: "/final-eval", role: "HR"},
  {name: "위키 관리", url: "/wiki", role: "HR"},
  {name: "챗봇 커스텀", url: "/hr/chatbot", role: "HR"},

  // 멘토, 팀장 ASIDE
  {
    name: "멘토링",
    role: "MENTOR",
    position: "팀장",
    subMenus: [
      {name: "멘티 소개", url: "/mentee/intro"},
      {name: "멘토링 계획서", url: "/mentoring/planning"},
      {name: "멘토링 보고서", url: "/mentoring/report"},
    ],
  },
  {
    name: "온보딩 과제 관리",
    role: "MENTOR",
    position: "팀장",
    url: "/task/add",
  },
  {
    name: "온보딩 결과 조회",
    role: "MENTOR",
    position: "팀장",
    subMenus: [
      {name: "과제 평가 조회", url: "/task-eval"},
      {name: "멘토링 보고서 조회", url: "/onboarding/report"},
    ],
  },
  {name: "최종 평가", url: "/final-eval", role: "MENTOR", position: "팀장"},
  {name: "위키 조회", url: "/wiki", role: "MENTOR", position: "팀장"},
]);

const employeeInfo = computed(() => userStore.getEmployeeInfo());
const filteredMenus = computed(() => {
  const role = employeeInfo.value.employeeRole[0];
  const positionName = employeeInfo.value.employeePositionName;
  return menus.value.filter(
      (menu) => menu.role === role || menu.position === positionName
  );
});



// 마운트 시 초기화
onMounted(async () => {
  document.addEventListener('click', handleOutsideClick);

  await userStore.$state.initialized;

  springAPI.defaults.headers.common['Authorization'] = `Bearer ${userStore.accessToken}`;
  employeeSeq.value = userStore.getEmployeeInfo().employeeSeq;

  // 이름 로딩 전에 employeeSeq 유효성 확인
  if (employeeSeq.value) {
    await loadName(employeeSeq.value);
  } else {
    console.error('employeeSeq is not available');
  }
});

// 컴포넌트 언마운트 시 이벤트 리스너 제거
onUnmounted(() => {
  document.removeEventListener('click', handleOutsideClick);
});

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;700&display=swap');

.aside-login-name {
  position: relative;
  font-weight: 600;
  font-size: 20px;
  display: flex;
  gap: 10px;
  margin-left: -3px;
}

.aside-logout-button {
  cursor: pointer;
  margin-top: 10px;
  margin-bottom: 30px;
  color: var(--gray);
  display: flex;
  gap: 10px;
}

.aside-menu-list {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.aside-menu {
  width: 200px;
  height: 100vh;
  flex-direction: column;
  align-items: center;
  background-color: var(--white);
  padding: 20px;
  box-sizing: border-box;
  text-align: center;
}

.logo {
  margin-top: 50px;
  font-size: 35px;
  font-weight: 700;
  font-family: 'Inter', sans-serif;
  margin-bottom: 60px;
}

.logo-style {
  text-decoration: none;
  color: var(--black);
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
  width: 100%;
}

.menu-item {
  margin-bottom: 20px;
  font-size: 17px;
  font-weight: 600;
  text-align: center;
  white-space: nowrap;
  position: relative;
  cursor: pointer;
}

.menu-item a {
  text-decoration: none;
  color: var(--black);
  font-family: 'Inter', sans-serif;
}

.menu-item a:hover,
.menu-item > div.active {
  color: var(--purple);
}

.sub-menu {
  font-size: 19px;
  font-weight: normal;
  position: absolute;
  top: 0;
  left: 180px;
  width: 180px;
  background-color: var(--white);
  border: 1px solid var(--purple);
  border-radius: 15px;
  padding: 1rem 0;
}

.login-name {
  cursor: pointer;
}

.notify {
  display: flex;
  margin-top: 3px;
  cursor: pointer;
}

.alarm-modal {
  position: absolute;
  z-index: 1000;
  top: 180px;
  left: 220px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 30px;
}

.profile-modal {
  position: absolute;
  z-index: 1000;
  top: 180px;
  left: 220px;
}
</style>
