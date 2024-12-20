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
import { fetchChatbotCategory } from "@/services/ChatbotApi";

// Props
defineProps({
    showDelete: {
        type: Boolean,
        default: false,
    },
});

// Emits
const emit = defineEmits(["tab-selected", "delete-category"]);

const categories = ref([]);
const activeTab = ref(null);

// 카테고리 불러오기
const loadCategories = async () => {
    try {
        // API 호출
        const response = await fetchChatbotCategory();

        // 응답 데이터가 배열인지 확인 후 처리
        if (Array.isArray(response) && response.length > 0) {
            categories.value = response; // API 응답 배열 그대로 설정
            activeTab.value = categories.value[0]?.chatbotCategorySeq || null;

            if (activeTab.value) {
                emit("tab-selected", activeTab.value); // 첫 번째 탭 선택 이벤트 전달
            }
        } else {
            console.error("API 응답 실패: 데이터가 없습니다.");
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
