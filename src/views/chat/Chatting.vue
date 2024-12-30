<template>
  <div class="chat-container">
    <!-- 메시지 목록 -->
    <div ref="messagesContainer" class="messages">
      <div
          v-for="(msg, index) in messages"
          :key="index"
          class="message"
          :class="{
            'message-right': msg.userCode === Number(currentUserCode.trim()),
            'message-left': msg.userCode !== Number(currentUserCode.trim())
          }">
        <div class="chat-name">
          {{ msg.userName }}
          <div class="chat-date">{{ msg.createdAt }}</div>
        </div>
        <div class="chat-message-content">{{ msg.message }}</div>
      </div>
    </div>

    <!-- 메시지 입력란 -->
    <div class="input-container">
      <input
          type="text"
          v-model="messageContent"
          placeholder="메시지를 입력하세요"
          @keyup.enter="sendMessage"
      />
      <button @click="sendMessage" :disabled="!connected">보내기</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, watch, nextTick, defineProps } from 'vue';
import { Client } from "@stomp/stompjs";
import { useUserStore } from "@/stores/UserStore.js";
import { getChatMessages } from "@/services/ChatApi.js";

const props = defineProps({
  chatRoomSeq: String,  // prop에서 roomSeq 값을 받습니다.
});

// 상태 관리
const stompClient = ref(null);
const messages = ref([]);
const messageContent = ref("");
const roomId = ref(props.chatRoomSeq);
const socketUrl = "ws://localhost:8080/ws"; // WebSocket URL
const connected = ref(false);
const currentUserCode = useUserStore().getEmployeeInfo().employeeSeq;

// WebSocket 연결 함수
const connect = () => {
  const authStore = useUserStore();
  const token = authStore.accessToken;

  if (!roomId.value) {
    console.error("roomId가 누락되었습니다.");
    return;
  }

  if (!token) {
    console.error("토큰이 누락되었습니다!");
    return;
  }

  // STOMP 클라이언트 설정
  stompClient.value = new Client({
    brokerURL: socketUrl,
    connectHeaders: {
      Authorization: `Bearer ${token}`,
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  stompClient.value.onConnect = () => {
    connected.value = true;
    subscribeToChat(); // 채팅 방 구독 시작
  };

  stompClient.value.onStompError = (frame) => {
    console.error("STOMP 연결 에러:", frame.headers["message"]);
    connected.value = false;
  };

  stompClient.value.onDisconnect = () => {
    connected.value = false;
  };

  stompClient.value.activate();
};

// 채팅방 구독을 시작하는 함수
const subscribeToChat = () => {
  if (!stompClient.value || !roomId.value) {
    console.error("STOMP Client 또는 Room ID가 없습니다.");
    return;
  }

  stompClient.value.subscribe(`/sub/${roomId.value}`, (message) => {
    try {
      const parsedMessage = JSON.parse(message.body);
      handleIncomingMessage(parsedMessage);
    } catch (error) {
      console.error("메시지 파싱 중 오류 발생:", error);
    }
  });
};

// 메시지 수신 처리
const handleIncomingMessage = (message) => {
  messages.value.push({
    message: message.message,
    userCode: message.userCode,
    userName: message.userName,
    createdAt: message.createdAt,
  });
};

// 메시지 전송 처리
const sendMessage = () => {
  if (!messageContent.value.trim()) {
    console.log("빈 메시지 전송 시도는 무시됩니다.");
    return;
  }

  const authStore = useUserStore();
  const token = authStore.accessToken;
  const employeeSeq = currentUserCode;

  const message = {
    roomId: roomId.value,  // roomId를 .value로 사용
    userCode: employeeSeq,
    message: messageContent.value,
    createdAt: new Date().toISOString(),
  };

  if (stompClient.value && token) {
    stompClient.value.publish({
      destination: `/pub/${roomId.value}`,
      body: JSON.stringify(message),
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    messageContent.value = "";
  }
};

// WebSocket 연결 해제
const disconnect = () => {
  if (stompClient.value) {
    stompClient.value.deactivate();
    connected.value = false;
    console.log("연결 해제");
  }
};

// 채팅 메시지 목록 가져오기
const getChatMessageList = async () => {
  try {
    console.log("현재 roomId 값은:", roomId.value);

    // 이전 메시지 초기화
    messages.value = [];

    const response = await getChatMessages(roomId.value);  // roomId.value로 API 호출

    if (response && response.data) {
      messages.value = response.data.map(msg => ({
        ...msg,
        userCode: msg.userCode,
      }));
    } else {
      messages.value = [];
    }
  } catch (error) {
    console.error("채팅 조회 오류:", error);
    messages.value = [];
  }
};

// 채팅방 변경 시 roomId 업데이트
watch(() => roomId.value, async (newRoomId, oldRoomId) => {
  if (newRoomId !== oldRoomId) {
    console.log("roomId 값이 변경되었습니다:", newRoomId);

    // 기존 WebSocket 연결 해제 및 데이터 초기화
    disconnect();  // 기존 연결 종료

    // 메시지 리스트를 비우고 채팅 데이터를 가져옵니다.
    messages.value = [];
    await getChatMessageList();  // 새로운 roomId로 데이터 불러오기

    connect();  // 새로운 roomId에 대해 WebSocket 연결 시작
  }
}, { immediate: true });


// 페이지 마운트 시 WebSocket 연결 및 데이터 로딩
onMounted(() => {
  connect();  // WebSocket 연결
  getChatMessageList();  // 처음 로드 시 채팅 메시지 목록 가져오기
});

// 페이지 언마운트 시 WebSocket 연결 해제
onBeforeUnmount(() => {
  disconnect();
});
</script>


<style scoped>
.chat-container {
  max-width: 700px;
  padding: 0px 20px;
}

.chat-name {
  font-weight: 600;
  font-size: 15px;
  display: flex;
}

.messages {
  max-height: 350px;
  overflow-y: auto;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 4px;
}

.chat-message-content {
  margin: 5px 0px;
}

.chat-date {
  font-size: 13px;
  font-weight: 300;
  color: var(--gray);
  padding-left: 10px;
}

.message {
  margin-bottom: 10px;
  border-radius: 10px;
  padding: 8px;
}

.message-right {
  margin-left: 50px;
  background-color: var(--light-purple);
}

.message-left {
  margin-right: 50px;
  border: 2px solid var(--purple);
}

.input-container {
  display: flex;
  gap: 10px;
}

input {
  flex: 1;
  padding: 10px;
  border: 1px solid var(--gray);
  border-radius: 4px;
}

button {
  padding: 10px 15px;
  border: none;
  background-color: var(--purple);
  color: var(--white);
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: var(--black);
}

button:disabled {
  background-color: var(--gray);
  cursor: not-allowed;
}
</style>
