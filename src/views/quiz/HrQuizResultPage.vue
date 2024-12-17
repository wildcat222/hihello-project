<template>
    <div class="hr-quiz-result-container">
        <!-- 헤더 -->
        <h1>퀴즈 결과 조회</h1>

        <!-- 검색 바 -->
        <div class="search-container">
            <search-bar></search-bar>
        </div>

        <!-- 퀴즈 카테고리 -->
        <div class="quiz-category-container">
            <quiz-category :show-delete="false"></quiz-category>
        </div>

        <!-- 리스트 컴포넌트 -->
        <white-box>
            <div class="list-wrapper">
                <div class="list-header">
                    <div class="column header-number">사번</div>
                    <div class="column header-name">이름</div>
                    <div class="column header-department">부서</div>
                    <div class="column header-score">정답개수</div>
                </div>

                <div v-if="quizResults.length > 0">
                    <list-component :items="quizResults">
                        <template #item="{ item }">
                            <div class="list-item">
                                <div class="column item-number">{{ item.employeeId }}</div>
                                <div class="column item-name">{{ item.name }}</div>
                                <div class="column item-department">{{ item.department }}</div>
                                <div class="column item-score">{{ item.correctAnswers }}</div>
                            </div>
                        </template>
                    </list-component>
                </div>
                <div v-else>
                    <p class="no-data">해당 카테고리에 퀴즈 결과가 없습니다.</p>
                </div>
            </div>
        </white-box>
    </div>
</template>

<script setup>
import "@/styles/quiz/HrQuizResult.css"
import { ref } from 'vue';
import SearchBar from "@/components/SearchBarComponent.vue";
import QuizCategory from "@/components/QuizCategoryComponent.vue";
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import ListComponent from "@/components/ListComponent.vue";

const quizResults = ref([
    { employeeId: '21102003', name: '김사원', department: '인사팀', correctAnswers: '7/10' },
    { employeeId: '21102004', name: '박사원', department: '인사팀', correctAnswers: '8/10' },
    { employeeId: '21102005', name: '이사원', department: '총무팀', correctAnswers: '6/10' }
]);
</script>

<style scoped>
.white-box {
    width: 90%;
    margin: 0 auto;
}

.no-data {
    text-align: center;
    margin: 20px 0;
    font-size: 14px;
    color: #999;
}
</style>
