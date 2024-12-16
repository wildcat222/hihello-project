<template>
    <div class="hr-quiz-container">
        <!-- 헤더 -->
        <h1>퀴즈 조회</h1>

        <!-- 검색 바 -->
        <div class="search-container">
            <div class="search-bar-wrapper">
                <search-bar></search-bar>
            </div>
            <button class="register-button">퀴즈 등록</button>
        </div>

        <!-- 퀴즈 카테고리 및 추가 버튼 -->
        <div class="quiz-category-container">
            <quiz-category @tab-selected="onTabSelected"></quiz-category>
            <button class="add-button">+</button>
        </div>

        <!-- 리스트 컴포넌트 -->
        <white-box>
            <div class="list-wrapper">
                <!-- 헤더 -->
                <div class="list-header">
                    <div class="column header-number">번호</div>
                    <div class="column header-content">내용</div>
                    <div class="column header-answer">답안</div>
                    <div class="column header-actions">관리</div>
                </div>

                <!-- 아이템 목록 -->
                <list-component :items="quizItems">
                    <template #item="{ item, index }">
                        <div class="list-item">
                            <div class="column item-number">{{ index + 1 }}</div>
                            <div class="column item-content">{{ item.content }}</div>
                            <div class="column item-answer">{{ item.answer }}</div>
                            <div class="column actions">
                                <button class="edit-button">수정</button>
                                <button class="delete-button">삭제</button>
                            </div>
                        </div>
                    </template>
                </list-component>
            </div>
        </white-box>

        <!-- 저장 버튼 -->
        <div class="save-container">
            <button class="save-button">저장</button>
        </div>
    </div>
</template>

<script setup>
import "@/styles/quiz/HrQuiz.css"
import SearchBar from "@/components/SearchBarComponent.vue";
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import ListComponent from "@/components/ListComponent.vue";
import QuizCategory from "@/components/QuizCategoryComponent.vue";
import { ref } from "vue";

// 더미 데이터
const quizItems = ref([
    { content: "우리 기업은 이지화된 업무 프로세스를 사용한다.", answer: "O" },
    {
        content: "우리 기업은 다양한 복지 정책이 존재하고 이를 통해 임직원에게 더 나은 근무 환경을 제공하고 있다.",
        answer: "O",
    },
]);

const onTabSelected = (tab) => {
    console.log("선택된 탭:", tab);
};
</script>

<style scoped>
.white-box {
    width: 90%;
    margin: 0 auto;
}
</style>