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
import { ref, onMounted, onBeforeUnmount, watch, nextTick } from 'vue';
import { Client } from "@stomp/stompjs";
import { useUserStore } from "@/stores/UserStore.js";
import { getChatMessages } from "@/services/ChatApi.js";

// 상태 관리
const stompClient = ref(null);
const messages = ref([]);
const messageContent = ref("");
const roomId = "d76099ef-e34d-44e9-806a-19e631700c4c";
const socketUrl = "ws://localhost:8080/ws";
const connected = ref(false);
const currentUserCode = useUserStore().getEmployeeInfo().employeeSeq;

// WebSocket 연결
const connect = () => {
  const authStore = useUserStore();
  const token = authStore.accessToken;

  if (!token) {
    console.error("토큰이 누락되었습니다!");
    return;
  }

  // STOMP 클라이언트 생성 및 설정
  stompClient.value = new Client({
    brokerURL: socketUrl,
    connectHeaders: {
      Authorization: `Bearer ${token}`,  // 인증 토큰
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  stompClient.value.onConnect = () => {
    connected.value = true;
    subscribeToChat();
  };

  stompClient.value.onStompError = (frame) => {
    console.error("STOMP 연결 에러:", frame.headers["message"]);
    connected.value = false;
  };

  stompClient.value.onDisconnect = () => {
    connected.value = false;
  };

  // WebSocket 연결 활성화
  stompClient.value.activate();
};

// 채팅방 메시지 구독
const subscribeToChat = () => {
  if (!stompClient.value || !roomId) {
    console.error("STOMP Client 또는 Room ID가 없습니다.");
    return;
  }

  stompClient.value.subscribe(`/sub/${roomId}`, (message) => {
    try {
      const parsedMessage = JSON.parse(message.body);
      handleIncomingMessage(parsedMessage);
    } catch (error) {
      console.error("메시지 파싱 중 오류 발생:", error);
    }
  });
};

// 수신된 메시지 처리
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
    roomId,
    userCode: employeeSeq,
    message: messageContent.value,
    createdAt: new Date().toISOString(),
  };

  // STOMP 클라이언트를 통해 서버로 메시지 전송
  if (stompClient.value && token) {
    stompClient.value.publish({
      destination: `/pub/${roomId}`,
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
  }
};

const getChatMessageList = async () => {
  try {
    const response = await getChatMessages(roomId);
    if (response && response.data) {
      messages.value = response.data.map(msg => ({
        ...msg,
        userCode: msg.userCode,
      }));
    } else {
      messages.value = [];
    }
  } catch (error) {
    console.error("멘토링 채팅 조회 오류:", error);
    messages.value = [];
  }
};

// 메시지 목록 끝으로 스크롤
const scrollToBottom = () => {
  nextTick(() => {
    const container = document.querySelector(".messages");
    if (container) {
      container.scrollTop = container.scrollHeight;
    }
  });
};

// messages 배열의 변화를 감지
watch(messages, scrollToBottom);

// 페이지 마운트 시 WebSocket 연결 및 메시지 목록 로드
onMounted(() => {
  connect();  // WebSocket 연결 시도
  getChatMessageList();
});

// 페이지 언마운트 시 WebSocket 연결 종료
onBeforeUnmount(() => {
  disconnect();  // WebSocket 연결 종료
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
