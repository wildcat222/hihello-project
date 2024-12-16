<template>
    <div class="category-tabs">
        <!-- 카테고리 버튼 리스트 -->
        <button v-for="(tab, index) in categories" :key="tab.quizCategorySeq"
            :class="['circle-button', { active: activeTab === tab.quizCategorySeq }]">
            {{ tab.quizCategoryName }}
        </button>
        <!-- 추가 버튼 -->
        <button class="add-button" @click="showModal = true">+</button>
    </div>

    <!-- 모달 컴포넌트 -->
    <add-category-modal 
        v-if="showModal" 
        @close="showModal = false" 
        @category-added="refreshCategories" />
</template>

<script>
import { fetchQuizCategory } from "@/services/QuizCategoryApi";
import AddCategoryModal from "@/components/AddCategoryModal.vue";

export default {
    components: { AddCategoryModal },
    data() {
        return {
            categories: [],
            activeTab: null,
            showModal: false, // 모달 표시 여부
        };
    },
    async created() {
        await this.loadCategories();
    },
    methods: {
        async loadCategories() {
            const response = await fetchQuizCategory();
            if (response.success) {
                this.categories = response.data;
                this.activeTab = this.categories[0]?.quizCategorySeq || null;
            }
        },
        refreshCategories() {
            this.loadCategories(); // 카테고리 새로고침
            this.showModal = false;
        },
    },
};
</script>

<style scoped>
.category-tabs {
    display: flex;
    gap: 10px;
}

.circle-button {
    padding: 5px 15px;
    border: none;
    border-radius: 20px;
    background-color: var(--white);
    color: #333;
    cursor: pointer;
    font-size: 14px;
    font-weight: bold;
    transition: background-color 0.3s, color 0.3s;
}

.circle-button.active {
    background-color: var(--purple);
    color: var(--white);
}
</style>