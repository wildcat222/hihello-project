<template>
    <div class="modal-overlay">
        <div class="modal-content">
            <h2>데이터 추가</h2>
            <white-box>
                <input v-model="chatbotContent" type="text" placeholder="챗봇 데이터 입력" />
                <div class="chatbot-button-group">
                    <button @click="addContent" class="chatbot-add-button">추가</button>
                    <button @click="$emit('close')" class="chatbot-close-button">닫기</button>
                </div>
            </white-box>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import { addChatbotData } from "@/services/ChatbotApi";

// Props 정의
const props = defineProps({
    categorySeq: {
        type: Number,
        required: true,
    },
});

const emit = defineEmits(["close", "data-added"]);
const chatbotContent = ref("");

const addContent = async () => {
    if (!chatbotContent.value.trim()) {
        alert("데이터를 입력해주세요.");
        return;
    }

    try {
        const response = await addChatbotData(props.categorySeq, {
            chatbotData: chatbotContent.value,
        });

        if (response.message === "Chatbot data added successfully.") {
            alert("데이터가 성공적으로 추가되었습니다.");
            emit("data-added"); // 데이터 추가 완료 이벤트 발생
            emit("close"); // 모달 닫기
            chatbotContent.value = ""; // 입력 필드 초기화
        }
    } catch (error) {
        console.error("데이터 추가 실패:", error.response?.data || error.message);
        alert("데이터 추가 중 오류가 발생했습니다.");
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
    width: 40vw;
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

.chatbot-add-button {
    background-color: #6c5ce7;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}

.chatbot-close-button {
    background-color: #ccc;
    color: black;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}
</style>
