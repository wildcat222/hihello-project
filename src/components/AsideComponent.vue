<template>
  <aside class="aside-menu">
    <!-- 로고 -->
    <div class="logo">
      HiHello
    </div>

    <!-- 메뉴 -->
    <ul>
      <!-- 김인턴 -->
      <li class="menu-item menu-title">김인턴</li>

      <li v-for="menu in filteredMenus" :key="menu.name" class="menu-item">
        <div :class="{ active: activeMenu === menu.name }" @mouseenter="showSubMenu(menu.name)">
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
import {computed, ref} from 'vue';
import {useUserStore} from "@/stores/UserStore.js";

const userStore = useUserStore();
const activeMenu = ref(null);


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
      {name: '과제 평가 조회', url: '/onboarding/result/task'},
      {name: '동료 평가 조회', url: '/onboarding/peer-review'},
    ]
  },
  {name: '최종 평가', url: '/final-eval', role: 'HR'},
  {name: '위키 관리', url: '/wiki/managing', role: 'HR'},
  {name: '챗봇 커스텀', url: '/chatbot/custom', role: 'HR'},

  // 멘토, 팀장 ASIDE
  {
    name: '멘토링', role: 'MENTOR', position: '1',
    subMenus: [
      {name: '멘토링 계획서', url: '/mentoring/planning'},
      {name: '멘토링 보고서', url: '/mentoring/report'}
    ]
  },
  {
    name: '온보딩 데이터 입력', role: 'MENTOR', position: '1',
    subMenus: [
      {name: '과제 등록', url: '/onboarding/task'},
      {name: '평가 관리', url: '/onboarding/eval'}
    ]
  },
  {
    name: '온보딩 결과 조회', role: 'MENTOR', position: '1',
    subMenus: [
      {name: '퀴즈 결과 조회', url: '/onboarding/result/quiz'},
      {name: '과제 평가 조회', url: '/onboarding/result/task'},
      {name: '멘토링 보고서 조회', url: '/onboarding/report'},
      {name: '동료 평가 조회', url: '/onboarding/peer-review'},
    ]
  },
  {name: '최종 평가', url: '/final-eval', role: 'MENTOR', position: '1'},
  {name: '위키 관리', url: '/wiki/managing', role: 'MENTOR', position: '1'}
]);

const filteredMenus = computed(() => {
  const role = userStore.getEmployeeInfo().employeeRole;
  const positionSeq = userStore.getEmployeeInfo().positionSeq;
  return menus.value.filter(menu => menu.role === role || menu.position === positionSeq);
})

const showSubMenu = (menuName) => {
  activeMenu.value = menuName;
}
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