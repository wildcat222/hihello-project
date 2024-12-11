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

            <!-- URL 이동용 메뉴 -->
            <li v-for="menu in menus" :key="menu.name" class="menu-item">
                <router-link :to="menu.url">{{ menu.name }}</router-link>
            </li>
        </ul>
    </aside>
</template>

<script setup>
import { ref } from 'vue';

// 메뉴 데이터
const menus = ref([
    { name: '인턴 위키', url: '/intern-crisis' },
    { name: '멘토 소개', url: '/mentor-intro' },
    { name: '멘토 채팅', url: '/mentor-chat' },
    { name: '보고서 작성', url: '/report-write' },
]);
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;700&display=swap');

.aside-menu {
    width: 200px;
    height: 100vh;
    display: flex;
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

.menu-item a:hover {
    color: var(--blue);
    /* 원하는 hover 색상 */
    text-decoration: underline;
}
</style>