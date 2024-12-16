<template>
    <div class="modal-overlay">
        <div class="modal-content">
            <h2>카테고리 추가</h2>
            <white-box>
                <input v-model="categoryName" type="text" placeholder="카테고리 이름 입력" />
                <div class="button-group">
                    <button @click="addCategory" class="add-button">추가</button>
                    <button @click="$emit('close')" class="close-button">닫기</button>
                </div>
            </white-box>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { postQuizCategory } from "@/services/QuizCategoryApi";
import WhiteBox from "@/components/WhiteBoxComponent.vue";

const emit = defineEmits(["close", "category-added"]);
const categoryName = ref("");

const addCategory = async () => {
    if (!categoryName.value.trim()) {
        alert("카테고리 이름을 입력해주세요.");
        return;
    }
    try {
        await postQuizCategory(categoryName.value);
        alert("카테고리가 성공적으로 추가되었습니다.");
        emit("category-added");
        categoryName.value = "";
    } catch (error) {
        console.error("카테고리 추가 실패:", error.message);
        alert("카테고리 추가 중 오류가 발생했습니다.");
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
    background: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
}

input {
    padding: 10px;
    width: 80%;
    margin-bottom: 15px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
}

.button-group {
    display: flex;
    justify-content: center;
    gap: 10px;
}

.add-button {
    background-color: #6c5ce7;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}

.close-button {
    background-color: #ccc;
    color: black;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}
</style>
