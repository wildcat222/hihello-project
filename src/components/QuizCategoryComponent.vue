<template>
    <div class="category-tabs">
        <button v-for="(tab, index) in categories" :key="tab.quizCategorySeq"
            :class="['circle-button', { active: activeTab === tab.quizCategorySeq }]"
            @click="selectCategory(tab.quizCategorySeq)">
            {{ tab.quizCategoryName }}
        </button>
    </div>
</template>

<script>
import { fetchQuizCategory } from "@/services/QuizCategoryApi";

export default {
    data() {
        return {
            categories: [],
            activeTab: null,
        };
    },
    async created() {
        this.loadCategories();
    },
    methods: {
        async loadCategories() {
            try {
                const response = await fetchQuizCategory();
                if (response.success) {
                    this.categories = response.data;
                    this.activeTab = this.categories[0]?.quizCategorySeq || null;

                    if (this.activeTab) {
                        this.$emit("tab-selected", this.activeTab);
                    }
                }
            } catch (error) {
                console.error("카테고리 로드 실패:", error);
            }
        },
        selectCategory(categorySeq) {
            this.activeTab = categorySeq;
            this.$emit("tab-selected", categorySeq);
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
