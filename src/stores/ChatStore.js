// import { defineStore } from 'pinia';
// import { ref } from 'vue';
// import {springAPI} from "@/services/axios.js";
//
// export const useChatStore = defineStore('chat', () => {
//     const chatrooms = ref([]);          // 사용자가 참여 중인 전체 채팅방 목록
//     const openedChatrooms = ref([]);    // 현재 창이 열려 있는 채팅방 목록
//
//     // 채팅방 목록 가져오기
//     const setChatrooms = (newChatrooms) => {
//         chatrooms.value = newChatrooms;
//     }
//
//     // 새로운 채팅방 추가
//
//
//     // 대화상대 초대 후 해당 채팅방 데이터 업데이트
//     const updateChatroomUsers = (updatedRoom) => {
//         // 전체 채팅방 목록 업데이트
//         const chatIndex = chatrooms.value.findIndex((room) => room.chatroomSeq === updatedRoom.chatroomSeq);
//         if(chatIndex !== -1) {
//             chatrooms.value[chatIndex] = {...chatrooms.value[chatIndex], ...updatedRoom};
//             console.log(`채팅방 ${updatedRoom.chatroomSeq}이 chatrooms에서 업데이트되었습니다:`, chatrooms.value[chatIndex]);
//         }
//
//         // 열린 채팅방 목록에서 업데이트
//         const openedIndex = openedChatrooms.value.findIndex((room) => room.chatroomSeq === updatedRoom.chatroomSeq);
//         if (openedIndex !== -1) {
//             openedChatrooms.value[openedIndex] = { ...openedChatrooms.value[openedIndex], ...updatedRoom };
//             console.log(`채팅방 ${updatedRoom.chatroomSeq}이 openedChatrooms에서 업데이트되었습니다:`, openedChatrooms.value[openedIndex]);
//         }
//     };
//
//     // 메시지 수신 시, 마지막 메시지 업데이트
//     const updateChatroomLastMessage = (chatroomSeq, lastMessage, lastMessageTime) => {
//         const chatroom = chatrooms.value.find((room) => room.chatroomSeq === chatroomSeq);
//         if(chatroom) {
//             chatroom.lastMessage = lastMessage;
//             chatroom.lastMessageTime = lastMessageTime;
//         } else {
//             console.error(`채팅방 ${chatroomSeq}를 찾을 수 없습니다.`);
//         }
//     };
//
//     // 열린 채팅방 추가
//     const openChatroom = (room) => {
//         if(!openedChatrooms.value.find((r) => r.chatroomSeq === room.chatroomSeq)) {
//             openedChatrooms.value.push(room);
//         }
//     };
//
//     // 열린 채팅방 닫기
//     const closeChatroom = (chatroomSeq) => {
//         openedChatrooms.value = openedChatrooms.value.filter((room) => room.chatroomSeq !== chatroomSeq);
//     };
//
//     return {
//         chatrooms,
//         setChatrooms,
//         updateChatroomUsers,
//         updateChatroomLastMessage,
//         openChatroom,
//         closeChatroom,
//     };
// });