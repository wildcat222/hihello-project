<template>
    <div class="category-tabs">
        <button v-for="(tab) in categories" :key="tab.chatbotCategorySeq"
            :class="['circle-button', { active: activeTab === tab.chatbotCategorySeq }]"
            @click.stop="selectCategory(tab.chatbotCategorySeq)">
            {{ tab.chatbotCategoryContent }}
            <!-- 삭제 아이콘 (showDelete가 true일 때만 표시) -->
            <span v-if="showDelete" class="delete-icon" @click.stop="$emit('delete-category', tab.chatbotCategorySeq)">
                ✖
            </span>
        </button>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

// Props
defineProps({
    showDelete: {
        type: Boolean,
        default: false, 
    },
});

// Emits
const emit = defineEmits(["tab-selected", "delete-category"]);

const categories = ref([
    { chatbotCategorySeq: 1, chatbotCategoryContent: "일반 문의" },
    { chatbotCategorySeq: 2, chatbotCategoryContent: "기술 지원" },
    { chatbotCategorySeq: 3, chatbotCategoryContent: "제품 안내" },
    { chatbotCategorySeq: 4, chatbotCategoryContent: "기타" }
]);
const activeTab = ref(null);

// 카테고리 불러오기
const loadCategories = async () => {

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
