<template>
    <div class="category-tabs">
        <button v-for="(tab, index) in categories" :key="tab.quizCategorySeq"
            :class="['circle-button', { active: activeTab === tab.quizCategorySeq }]"
            @click.stop="selectCategory(tab.quizCategorySeq)">
            {{ tab.quizCategoryName }}
            <!-- 삭제 아이콘 (showDelete가 true일 때만 표시) -->
            <span v-if="showDelete" class="delete-icon" @click.stop="$emit('delete-category', tab.quizCategorySeq)">
                ✖
            </span>
        </button>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { fetchQuizCategory } from "@/services/QuizCategoryApi";

// Props
defineProps({
    showDelete: {
        type: Boolean,
        default: false, // 기본값: 삭제 버튼 숨김
    },
});

// Emits
const emit = defineEmits(["tab-selected", "delete-category"]);

const categories = ref([]);
const activeTab = ref(null);

// 카테고리 불러오기
const loadCategories = async () => {
    try {
        const response = await fetchQuizCategory();
        console.log("API 응답:", response); // API 응답 확인

        if (response.success) {
            categories.value = response.data;
            console.log("로드된 카테고리:", categories.value); // 로드된 데이터 확인
            activeTab.value = categories.value[0]?.quizCategorySeq || null;

            if (activeTab.value) {
                emit("tab-selected", activeTab.value);
            }
        } else {
            console.error("API 응답 실패: success가 false입니다.");
        }
    } catch (error) {
        console.error("카테고리 로드 실패:", error);
    }
};

const selectCategory = (categorySeq) => {
    activeTab.value = categorySeq;
    emit("tab-selected", categorySeq);
};

onMounted(() => {
    loadCategories();
});

defineExpose({ loadCategories });
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

.delete-icon {
    margin-left: 8px;
    color: red;
    font-size: 12px;
    cursor: pointer;
    transition: color 0.3s ease;
}

.delete-icon:hover {
    color: darkred;
}
</style>
