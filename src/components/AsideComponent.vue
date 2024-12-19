<template>
  <aside class="aside-menu">
    <!-- 로고 -->
    <div class="logo">
      HiHello
    </div>

    <!-- 메뉴 -->
    <ul>
      <!-- 김인턴 -->
      <li class="menu-item menu-title" @click="$emit('profile-modal')">{{ employeeName }}</li>
      <button @click="logout">로그아웃 버튼(임시)</button>

      <li v-for="menu in filteredMenus" :key="menu.name" class="menu-item">
        <div :class="{ active: props.activeMenu === menu.name }" @click="toggleMenu(menu.name)">
          <router-link :to="menu.url">{{ menu.name }}</router-link>
        </div>

        <!--서브 메뉴-->
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
import {computed, onMounted, onUnmounted, ref} from 'vue';
import {useUserStore} from "@/stores/UserStore.js";
import router from "@/router/index.js";
import {fetchName} from "@/services/UserApi.js";

const userStore = useUserStore();

function logout() {
  userStore.logout();
}

const employeeSeq = ref(null);
const employeeName = ref('');
const loadName = async (employeeSeq) => {
    try {
        employeeName.value = await fetchName(employeeSeq);
    } catch (error) {
        employeeName.value = '';
    }
}

// Props & Events
const props = defineProps({
  activeMenu: String,
});
const emit = defineEmits(['profile-modal', 'update-active-menu']);

const menus = ref([
  // 멘티 ASIDE
  {name: '인턴 위키', url: '/wiki', role: 'MENTEE'},
  {name: '멘토 소개', url: '/mentor/intro', role: 'MENTEE'},
  {name: '멘토 채팅', url: '/mentor-chat', role: 'MENTEE'},
  {name: '보고서 작성', url: '/report-write', role: 'MENTEE'},

  // 담당자 ASIDE
  {name: '사원 관리', url: '/employee-management', role: 'HR'},
  {
    name: '멘토링', role: 'HR',
    subMenus: [
      {name: '멘토링 매칭', url: '/mentoring/matching'},
      {name: '멘토링 계획서', url: '/mentoring/planning'},
      {name: '멘토링 보고서', url: '/mentoring/report'}
    ]
  },
  {name: '온보딩 설계', url: '/onboarding/design', role: 'HR'},
  {
    name: '온보딩 데이터 입력', role: 'HR',
    subMenus: [
      {name: '퀴즈 관리', url: '/onboarding/quiz'},
      {name: '과제 등록', url: '/onboarding/task'},
      {name: '평가 관리', url: '/onboarding/eval'},
      {name: '평가 지표 관리', url: '/onboarding/eval-ind'},
    ]
  },
  {
    name: '온보딩 결과 조회', role: 'HR',
    subMenus: [
      {name: '퀴즈 결과 조회', url: '/onboarding/result/quiz'},
      {name: '과제 평가 조회', url: '/task-eval'},
      {name: '동료 평가 조회', url: '/onboarding/peer-review'},
    ]
  },
  {name: '최종 평가', url: '/final-eval', role: 'HR'},
  {name: '위키 관리', url: '/wiki/managing', role: 'HR'},
  {name: '챗봇 커스텀', url: '/chatbot/custom', role: 'HR'},

  // 멘토, 팀장 ASIDE
  {
    name: '멘토링', role: 'MENTOR', position: '팀장',
    subMenus: [
      {name: '멘토링 계획서', url: '/mentoring/planning'},
      {name: '멘토링 보고서', url: '/mentoring/report'}
    ]
  },
  {
    name: '온보딩 데이터 입력', role: 'MENTOR', position: '팀장',
    subMenus: [
      {name: '과제 등록', url: '/onboarding/task'},
      {name: '평가 관리', url: '/onboarding/eval'}
    ]
  },
  {
    name: '온보딩 결과 조회', role: 'MENTOR', position: '팀장',
    subMenus: [
      {name: '퀴즈 결과 조회', url: '/onboarding/result/quiz'},
      {name: '과제 평가 조회', url: '/task-eval'},
      {name: '멘토링 보고서 조회', url: '/onboarding/report'},
      {name: '동료 평가 조회', url: '/onboarding/peer-review'},
    ]
  },
  {name: '최종 평가', url: '/final-eval', role: 'MENTOR', position: '팀장'},
  {name: '위키 관리', url: '/wiki', role: 'MENTOR', position: '팀장'}
]);

const filteredMenus = computed(() => {
  const role = userStore.getEmployeeInfo().employeeRole[0];
  const positionName = userStore.getEmployeeInfo().employeePositionName;
  return menus.value.filter(menu => menu.role === role || menu.position === positionName);
})

// 토글 로직
const toggleMenu = (menuName) => {
  const newActiveMenu = props.activeMenu === menuName ? null : menuName;
  emit('update-active-menu', newActiveMenu);

  // SubMenu 활성화 시 Profile 비활성화
  if (newActiveMenu) {
    emit('profile-modal'); // Profile 강제 비활성화
  }
};

// 마운트 시 이벤트 추가, 언마운트 시 이벤트 제거
onMounted(() => {
  router.afterEach(() => emit('update-active-menu', null));
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
  /* 수평 가운데 정렬 */
  background-color: var(--white);
  padding: 20px;
  box-sizing: border-box;
  text-align: center;
}

.logo {
  font-size: 35px;
  font-weight: 700;
  font-family: 'Inter', sans-serif;
  /* Inter 폰트 적용 */
  margin-bottom: 100px;
  /* 로고와 김인턴 간 간격 */
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
  width: 100%;
  /* 메뉴의 가로 너비를 채우기 위해 */
}

.menu-item {
  margin-bottom: 30px;
  /* 메뉴 항목 간 간격 */
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  /* 텍스트 수평 가운데 정렬 */
  display: flex;
  white-space: nowrap;
  position: relative;
}

.menu-title {
  margin-bottom: 100px;
  /* 김인턴과 나머지 메뉴 사이의 간격 */
  font-size: 18px;
  cursor: pointer;
}

.menu-item a {
  text-decoration: none;
  color: var(--black);
  font-family: 'Inter', sans-serif;
  /* Inter 폰트 적용 */
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
</style>