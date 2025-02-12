<template>
    <div class="modal-overlay">
        <div class="modal-content">
            <h2>카테고리 추가</h2>
            <white-box>
                <input v-model="categoryName" type="text" placeholder="챗봇 카테고리 입력" />
                <div class="chatbot-button-group">
                    <button @click="handleAddCategory" class="chatbot-category-add-button">
                        {{ isAdding ? "추가 중..." : "추가" }}
                    </button>
                    <button @click="$emit('close')" class="chatbot-category-close-button">닫기</button>
                </div>
            </white-box>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import { addChatbotCategory } from "@/services/ChatbotApi";

const emit = defineEmits(["close", "category-added"]);

const categoryName = ref("");
const isAdding = ref(false);

const handleAddCategory = async () => {
    if (!categoryName.value.trim()) {
        alert("카테고리 이름을 입력해주세요.");
        return;
    }

    try {
        isAdding.value = true; 
        const requestData = { ChatbotCategoryContent: categoryName.value };

        const response = await addChatbotCategory(requestData);

        if (response?.success) {
            alert("카테고리가 성공적으로 추가되었습니다.");
            emit("category-added"); // Notify Parent Component
            emit("close"); // Close Modal
            categoryName.value = ""; // Reset Input
        } else {
            alert("카테고리 추가에 실패했습니다.");
            console.error("API 응답 실패:", response);
        }
    } catch (error) {
        console.error("카테고리 추가 오류:", error.response?.data || error.message);
        alert("카테고리 추가 중 오류가 발생했습니다.");
    } finally {
        isAdding.value = false; // End Loading State
    }
};
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.6);
    display: flex;
    align-items: center;
    justify-content: center;
}

.modal-content {
    background: var(--ivory);
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
    width: 30vw;
}

input {
    padding: 10px;
    width: 80%;
    margin-bottom: 15px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
}

.chatbot-button-group {
    display: flex;
    justify-content: center;
    gap: 10px;
}

.chatbot-category-add-button {
    background-color: #6c5ce7;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}

.chatbot-category-add-button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}

.chatbot-category-close-button {
    background-color: #ccc;
    color: black;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}
</style>
