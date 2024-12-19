<template>
    <div class="modal-overlay">
        <div class="modal-content">
            <h2>데이터 수정</h2>
            <white-box>
                <input type="text" v-model="localItem.content" placeholder="내용을 입력하세요" />
                <div class="button-group">
                    <button @click="updateContent" class="chatbot-add-button">저장</button>
                    <button @click="$emit('close')" class="chatbot-close-button">닫기</button>
                </div>
            </white-box>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
import WhiteBox from "@/components/WhiteBoxComponent.vue";

// Props 정의
const props = defineProps({
    item: {
        type: Object,
        required: true,
    },
});

// 이벤트 정의
const emit = defineEmits(["close", "update"]);

// 로컬 상태 초기화
const localItem = ref({ ...props.item });

// 데이터 업데이트 함수
const updateContent = () => {
    if (!localItem.value.content.trim()) {
        alert("내용을 입력해주세요.");
        return;
    }
    emit("update", { 
        id: localItem.value.id,
        categorySeq: localItem.value.categorySeq,
        chatbotData: localItem.value.content // 'content'를 'chatbotData'로 변경
    });
    emit("close");
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

.button-group {
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
