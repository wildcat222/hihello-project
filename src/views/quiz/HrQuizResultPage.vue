<template>
    <div class="hr-quiz-result-container">
        <!-- 헤더 -->
        <div class="hr-quiz-result-page-title">퀴즈 결과 조회</div>

        <!-- 검색 바 -->
        <div class="search-container">
            <search-bar></search-bar>
        </div>

        <!-- 퀴즈 카테고리 -->
        <div class="quiz-category-container">
            <!-- tab-selected 이벤트를 받아 handleTabSelected 메서드 호출 -->
            <quiz-category :show-delete="false" @tab-selected="handleTabSelected"></quiz-category>
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
                            <div class="hr-quiz-result-item">
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
import { fetchHrQuizResult } from "@/services/QuizApi";

// quizResults 초기값 빈배열
const quizResults = ref([]);

// 퀴즈 카테고리 변경 시 호출되는 메서드
const handleTabSelected = async (quizCategorySeq) => {
    try {
        const response = await fetchHrQuizResult(quizCategorySeq);

        if (response.success && response.data) {
            quizResults.value = response.data.map(item => ({
                employeeId: item.employeeNum,
                name: item.employeeName,
                department: item.departmentName,
                correctAnswers: `${item.quizCorrectQty}/${item.quizQty}`,
            }));
        } else {
            quizResults.value = [];
        }
    } catch (error) {
        console.error("퀴즈 결과 조회 실패:", error);
        quizResults.value = [];
    }
};
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
