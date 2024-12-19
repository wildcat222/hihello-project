<template>
    <div class="chatbot-container">
        <!-- 헤더 -->
        <h1 class="chatbot-header">챗봇 커스텀</h1>

        <!-- 검색 바 -->
        <div class="chatbot-search-container">
            <div class="chatbot-search-bar-wrapper">
                <search-bar></search-bar>
            </div>
            <button class="chatbot-register-button" @click="showAddModal = true">데이터 등록</button>
        </div>

        <!-- 데이터 추가 모달 -->
        <add-chatbot-modal v-if="showAddModal" @close="showAddModal = false" />

        <!-- 챗봇 카테고리 -->
        <div class="chatbot-category-container">
            <chatbot-category ref="chatbotCategoryRef" :show-delete="true" @tab-selected="onTabSelected"
                @delete-category="deleteCategory" />
            <button class="chatbot-add-button" @click="showAddCategoryModal = true">+</button>
        </div>

        <!-- 카테고리 추가 모달 -->
        <add-category-modal v-if="showAddCategoryModal" @close="showAddCategoryModal = false"
            @category-added="chatbotCategoryRef?.loadCategories()" />

        <!-- 수정 모달 -->
        <update-chatbot-modal v-if="showUpdateModal" :item="selectedItem" @close="showUpdateModal = false"
            @update="updateItem" />

        <!-- 리스트 컴포넌트 -->
        <white-box>
            <div class="chatbot-list-wrapper">
                <div class="chatbot-list-header">
                    <div class="chatbot-column chatbot-header-number">순서</div>
                    <div class="chatbot-column chatbot-header-content">내용</div>
                    <div class="chatbot-column chatbot-header-actions"></div>
                </div>
            </div>

            <div v-if="chatbotItems.length > 0">
                <list-component :items="chatbotItems">
                    <template #item="{ item, index }">
                        <div class="chatbot-list-item">
                            <div class="chatbot-column chatbot-item-number">{{ index + 1 }}</div>
                            <div class="chatbot-column chatbot-item-content">{{ item.content }}</div>
                            <div class="chatbot-column chatbot-actions">
                                <button class="chatbot-edit-button" @click="openUpdateModal(item)">수정</button>
                                <button class="chatbot-delete-button" @click="deleteItem(index)">삭제</button>
                            </div>
                        </div>
                    </template>
                </list-component>
            </div>
            <div v-else>
                <p class="no-data">해당 카테고리에 데이터가 없습니다.</p>
            </div>
        </white-box>
    </div>
</template>

<script setup>
import "@/styles/chatbot/ChatbotCustom.css";
import { ref, onMounted } from "vue";
import SearchBar from "@/components/SearchBarComponent.vue";
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import ListComponent from "@/components/ListComponent.vue";
import ChatbotCategory from "@/views/chatbot/ChatbotCategoryComponent.vue";
import AddCategoryModal from "@/views/chatbot/AddChatbotCategoryModal.vue";
import AddChatbotModal from "@/views/chatbot/AddChatbotModal.vue";
import UpdateChatbotModal from "@/views/chatbot/UpdateChatbotModal.vue";
import { deleteChatbotCategory } from "@/services/ChatbotApi";

const showAddCategoryModal = ref(false);
const showAddModal = ref(false);
const showUpdateModal = ref(false);
const selectedItem = ref(null);
const chatbotCategoryRef = ref(null);

const chatbotItems = ref([
    { id: 1, content: "안녕하세요! 무엇을 도와드릴까요?" },
    { id: 2, content: "챗봇에 대한 설명을 듣고 싶으신가요?" },
    { id: 3, content: "현재 날씨는 화창합니다." },
    { id: 4, content: "오늘의 추천 메뉴는 파스타입니다." },
    { id: 5, content: "궁금한 사항이 있다면 말씀해주세요." },
]);

// 수정 모달 열기
function openUpdateModal(item) {
    selectedItem.value = { ...item };
    showUpdateModal.value = true;
}

// 수정된 아이템 반영
function updateItem(updatedItem) {
    const index = chatbotItems.value.findIndex((item) => item.id === updatedItem.id);
    if (index !== -1) {
        chatbotItems.value[index] = { ...updatedItem };
    }
    showUpdateModal.value = false;
}

const deleteCategory = async (categorySeq) => {
    if (!confirm("정말로 이 카테고리를 삭제하시겠습니까?")) return;

    try {
        const response = await deleteChatbotCategory(categorySeq);

        if (response?.success) {
            alert("카테고리가 성공적으로 삭제되었습니다.");
            chatbotCategoryRef.value?.loadCategories(); // 삭제 후 카테고리 목록 갱신
        } else {
            alert("카테고리 삭제에 실패했습니다.");
            console.error("API 응답 실패:", response);
        }
    } catch (error) {
        console.error("카테고리 삭제 실패:", error.response?.data || error.message);
        alert("카테고리 삭제 중 오류가 발생했습니다.");
    }
};

onMounted(() => chatbotCategoryRef.value?.loadCategories());
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
