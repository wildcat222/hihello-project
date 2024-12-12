<template>
    <div class="login-page">
        <WhiteBoxComponent>
            <h2 class="login-title">로그인</h2>
            <form class="login-form" @submit.prevent="onLogin">
                <div class="form-group">
                    <label for="username">사번</label>
                    <input type="text" id="username" v-model="username" placeholder="사번을 입력하세요" />
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" v-model="password" placeholder="비밀번호를 입력하세요" />
                </div>
                <button type="submit" class="login-button">로그인</button>
            </form>
        </WhiteBoxComponent>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import WhiteBoxComponent from '@/components/WhiteBoxComponent.vue';
import '@/styles/user/LoginPage.css'

const username = ref('');
const password = ref('');

const onLogin = () => {
    console.log('사번:', username.value, '비밀번호:', password.value);
};
</script>