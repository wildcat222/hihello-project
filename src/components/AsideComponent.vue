<template>
  <aside class="aside-menu">
    <!-- 로고 클릭 시 /main으로 이동 -->
    <div class="logo">
      <router-link to="/main">HiHello</router-link>
    </div>

    <!-- 메뉴 -->
    <ul>
      <!-- 이름 조회 -->
      <li class="menu-item" @click.stop="openProfileModal">{{ employeeName }}</li>

      <!-- 로그아웃 메뉴 -->
      <li class="menu-item menu-title" @click="logout">로그아웃</li>

      <!-- 메뉴 리스트 -->
      <li v-for="menu in filteredMenus" :key="menu.name" class="menu-item">
        <div :class="{ active: props.activeMenu === menu.name }" @click="toggleMenu(menu.name)">
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
</template>

<script setup>
import { computed, onMounted, ref } from "vue";
import { useUserStore } from "@/stores/UserStore.js";
import router from "@/router/index.js";
import { fetchName } from "@/services/UserApi.js";

const userStore = useUserStore();
const shouldShowProfile = ref(false);

const openProfileModal = () => {
  shouldShowProfile.value = true; // 프로필 모달 열기
  emit("profile-modal");
};

function logout() {
  userStore.logout();
}

// 상태 관리
const employeeSeq = ref(null);
const employeeName = ref("");
const loadName = async (employeeSeq) => {
  try {
    employeeName.value = await fetchName(employeeSeq);
  } catch (error) {
    employeeName.value = "";
  }
};

// Props & Events
const props = defineProps({
  activeMenu: String,
});
const emit = defineEmits(["profile-modal", "update-active-menu"]);

const menus = ref([
  // 멘티 ASIDE
  { name: "인턴 위키", url: "/wiki", role: "MENTEE" },
  { name: "멘토 소개", url: "/mentor/intro", role: "MENTEE" },
  // { name: "멘토 채팅", url: "/mentor-chat", role: "MENTEE" },
  { name: "보고서 조회", url: "/mentoring/report", role: "MENTEE" },

  // 담당자 ASIDE
  { name: "사원 관리", url: "/employee-management", role: "HR" },
  {
    name: "멘토링",
    role: "HR",
    subMenus: [
      { name: "멘토링 매칭", url: "/mentoring/matching" },
      { name: "멘토링 계획서", url: "/mentoring/planning" },
      { name: "멘토링 보고서", url: "/mentoring/report" },
    ],
  },
  { name: "온보딩 설계", url: "/onboarding/design", role: "HR" },
  {
    name: "온보딩 데이터 입력",
    role: "HR",
    subMenus: [
      { name: "퀴즈 관리", url: "/hr/quiz" },
      { name: "과제 등록", url: "/task/add" },
      { name: "동료 평가 지표 관리", url: "/hr/peer/review/list" },
      { name: "공통 평가 지표 관리", url: "/taskInd/manage" },
    ],
  },
  {
    name: "온보딩 결과 조회",
    role: "HR",
    subMenus: [
      { name: "퀴즈 결과 조회", url: "/hr/quiz/result" },
      { name: "과제 평가 조회", url: "/task-eval" },
      { name: "동료 평가 조회", url: "/hr/peer/review" },
    ],
  },
  { name: "최종 평가", url: "/final-eval", role: "HR" },
  { name: "위키 관리", url: "/wiki", role: "HR" },
  { name: "챗봇 커스텀", url: "/hr/chatbot", role: "HR" },

  // 멘토, 팀장 ASIDE
  {
    name: "멘토링",
    role: "MENTOR",
    position: "팀장",
    subMenus: [
      { name: "멘토링 계획서", url: "/mentoring/planning" },
      { name: "멘토링 보고서", url: "/mentoring/report" },
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
      { name: "과제 평가 조회", url: "/task-eval" },
      { name: "멘토링 보고서 조회", url: "/onboarding/report" },
    ],
  },
  { name: "최종 평가", url: "/final-eval", role: "MENTOR", position: "팀장" },
  { name: "위키 조회", url: "/wiki", role: "MENTOR", position: "팀장" },
]);

const employeeInfo = computed(() => userStore.getEmployeeInfo());
const filteredMenus = computed(() => {
  const role = employeeInfo.value.employeeRole[0];
  const positionName = employeeInfo.value.employeePositionName;
  return menus.value.filter(
    (menu) => menu.role === role || menu.position === positionName
  );
});

// 메뉴 토글 로직
const toggleMenu = (menuName) => {
  shouldShowProfile.value = false; // 메뉴 클릭 시 프로필 모달 닫기
  const newActiveMenu = props.activeMenu === menuName ? null : menuName;
  emit("update-active-menu", newActiveMenu);
};

// 마운트 시 초기화
onMounted(() => {
  router.afterEach(() => emit("update-active-menu", null));
  employeeSeq.value = userStore.getEmployeeInfo().employeeSeq;
  loadName(employeeSeq.value);
});
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;700&display=swap');

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
  font-size: 35px;
  font-weight: 700;
  font-family: 'Inter', sans-serif;
  margin-bottom: 60px;
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
  width: 100%;
}

.menu-item {
  margin-bottom: 30px;
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  white-space: nowrap;
  position: relative;
  cursor: pointer;
}

.menu-title {
  margin-bottom: 60px;
}

.menu-item a {
  text-decoration: none;
  color: var(--black);
  font-family: 'Inter', sans-serif;
}

.menu-item a:hover,
.menu-item>div.active {
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
</style>
