<template>
    <div class="hr-quiz-container">
        <!-- 헤더 -->
        <h1>퀴즈 조회</h1>

        <!-- 검색 바 -->
        <div class="search-container">
            <div class="search-bar-wrapper">
                <search-bar></search-bar>
            </div>
            <button class="register-button" @click="showAddQuizModal = true">퀴즈 등록</button>
        </div>

        <!-- 퀴즈 등록 모달 -->
        <add-quiz-modal 
            v-if="showAddQuizModal" 
            :quiz-category-seq="selectedCategorySeq"
            @close="showAddQuizModal = false" 
            @quiz-added="addQuizToList" 
        />

        <!-- 퀴즈 수정 모달 -->
        <update-quiz-modal 
            v-if="showUpdateQuizModal" 
            :quiz-category-seq="selectedCategorySeq"
            :quiz-data="selectedQuizData" 
            @quiz-updated="applyQuizUpdate" 
            @close="showUpdateQuizModal = false" 
        />

        <!-- 퀴즈 카테고리 -->
        <div class="quiz-category-container">
            <quiz-category 
                ref="quizCategoryRef" 
                :show-delete="true" 
                @tab-selected="onTabSelected" 
                @delete-category="deleteCategory" 
            />
            <button class="add-button" @click="showAddCategoryModal = true">+</button>
        </div>

        <!-- 카테고리 추가 모달 -->
        <add-category-modal 
            v-if="showAddCategoryModal" 
            @close="showAddCategoryModal = false" 
            @category-added="quizCategoryRef?.loadCategories()" 
        />

        <!-- 리스트 컴포넌트 -->
        <white-box>
            <div class="list-wrapper">
                <div class="list-header">
                    <div class="column header-number">번호</div>
                    <div class="column header-question">질문</div>
                    <div class="column header-answer">정답</div>
                    <div class="column header-explanation">설명</div>
                    <div class="column header-actions">관리</div>
                </div>

                <div v-if="quizItems.length > 0">
                    <list-component :items="quizItems">
                        <template #item="{ item, index }">
                            <div class="quiz-list-item">
                                <div class="column item-number">{{ index + 1 }}</div>
                                <div class="column item-question">{{ item.quizQuestion }}</div>
                                <div class="column item-answer">{{ item.quizAnswer ? 'O' : 'X' }}</div>
                                <div class="column item-explanation">{{ item.quizExplanation }}</div>
                                <div class="column actions">
                                    <button class="edit-button" @click="editQuiz(item)">수정</button>
                                    <button class="delete-button" @click="deleteQuiz(item.quizSeq)">삭제</button>
                                </div>
                            </div>
                        </template>
                    </list-component>
                </div>
                <div v-else>
                    <p class="no-data">해당 카테고리에 퀴즈가 없습니다.</p>
                </div>
            </div>
        </white-box>
    </div>
</template>

<script setup>
import "@/styles/quiz/HrQuiz.css";
import { ref, onMounted } from "vue";
import SearchBar from "@/components/SearchBarComponent.vue";
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import ListComponent from "@/components/ListComponent.vue";
import QuizCategory from "@/components/QuizCategoryComponent.vue";
import AddCategoryModal from "@/views/quiz/AddQuizCategoryModal.vue";
import AddQuizModal from "@/views/quiz/AddQuizModal.vue";
import UpdateQuizModal from "@/views/quiz/UpdateQuizModal.vue";
import { deleteQuizCategory } from "@/services/QuizCategoryApi";
import { fetchHrQuiz, deleteHrQuiz } from "@/services/QuizApi";

const quizItems = ref([]);
const showAddCategoryModal = ref(false);
const showAddQuizModal = ref(false);
const showUpdateQuizModal = ref(false);
const quizCategoryRef = ref(null);
const selectedCategorySeq = ref(null);
const selectedQuizData = ref(null);

const addQuizToList = (newQuiz) => {
    quizItems.value.push(newQuiz);
    showAddQuizModal.value = false;
};

const editQuiz = (quiz) => {
    selectedQuizData.value = { ...quiz };
    showUpdateQuizModal.value = true;
};

const applyQuizUpdate = (updatedQuiz) => {
    const index = quizItems.value.findIndex((quiz) => quiz.quizSeq === updatedQuiz.quizSeq);
    if (index !== -1) quizItems.value[index] = { ...updatedQuiz };
};

const onTabSelected = async (quizCategorySeq) => {
    selectedCategorySeq.value = quizCategorySeq;
    try {
        const response = await fetchHrQuiz(quizCategorySeq);
        quizItems.value = response.data;
    } catch (error) {
        console.error("퀴즈 조회 중 오류:", error);
        alert("퀴즈를 조회하는 도중 문제가 발생했습니다.");
    }
};

const deleteCategory = async (categorySeq) => {
    if (!confirm("카테고리를 삭제하시겠습니까?")) return;
    try {
        await deleteQuizCategory(categorySeq);
        alert("카테고리가 삭제되었습니다.");
        quizCategoryRef.value?.loadCategories();
    } catch (error) {
        console.error("카테고리 삭제 실패:", error);
        alert("카테고리 삭제에 실패했습니다.");
    }
};

const deleteQuiz = async (quizSeq) => {
    if (!confirm("퀴즈를 삭제하시겠습니까?")) return;
    try {
        await deleteHrQuiz(selectedCategorySeq.value, quizSeq);
        quizItems.value = quizItems.value.filter((quiz) => quiz.quizSeq !== quizSeq);
        alert("퀴즈가 삭제되었습니다.");
    } catch (error) {
        console.error("퀴즈 삭제 실패:", error);
        alert("퀴즈 삭제에 실패했습니다.");
    }
};

onMounted(() => quizCategoryRef.value?.loadCategories());
</script>

<style scoped>
.white-box {
    width: 90%;
    margin: 0 auto;
}

.no-data {
    text-align: center;
    margin: 20px 0;
    font-size: 1.2em;
    color: #888;
}
</style>
