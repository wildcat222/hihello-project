<template>
  <div class="chat-container">
    <h2>Chat Room</h2>
    <div class="messages">
      <div v-for="(msg, index) in messages" :key="index" class="message">
        <strong>{{ msg.user }}:</strong> {{ msg.content }}
      </div>
    </div>
    <div class="input-container">
      <input
          type="text"
          v-model="messageContent"
          placeholder="Type your message..."
          @keyup.enter="sendMessage"
      />
      <button @click="sendMessage" :disabled="!connected">Send</button>
    </div>
    <p class="connection-status">
      {{ connected ? 'Connected to WebSocket' : 'Connecting to WebSocket...' }}
    </p>
  </div>
</template>

<script>
import { Client } from "@stomp/stompjs";
import { useUserStore } from "@/stores/UserStore.js";

export default {
  data() {
    return {
      stompClient: null,
      messages: [], // 메시지 목록
      messageContent: "", // 입력된 메시지 내용
      roomId: "d76099ef-e34d-44e9-806a-19e631700c4c", // 채팅방 ID
      socketUrl: "ws://localhost:8253/ws", // WebSocket 서버 URL
      connected: false, // WebSocket 연결 상태
    };
  },
  methods: {
    /**
     * WebSocket 연결 설정
     */
    connect() {
      console.log("WebSocket 연결을 시도합니다...");
      const authStore = useUserStore();
      const token = authStore.accessToken;

      if (!token) {
        console.error("토큰이 누락되었습니다!");
        return;
      }
      console.log(token)
      // WebSocket Client 생성 및 설정
      this.stompClient = new Client({
        brokerURL: this.socketUrl, // WebSocket 서버 URL
        connectHeaders: {
          Authorization: `Bearer ${token}`, // 인증 토큰
        },
        reconnectDelay: 5000, // 재연결 시도 간격 (ms)
        heartbeatIncoming: 4000, // 클라이언트로의 heartbeat 수신 간격
        heartbeatOutgoing: 4000, // 서버로의 heartbeat 전송 간격
      });

      console.log("STOMP Client 설정 완료.");
      console.log("STOMP Client connectHeaders:", this.stompClient.connectHeaders);

      // WebSocket 연결 성공 시 실행
      this.stompClient.onConnect = () => {
        console.log("WebSocket 연결 성공");
        this.connected = true;
        this.subscribeToChat(); // 채팅방 구독
      };

      // WebSocket 연결 오류 시 실행
      this.stompClient.onStompError = (frame) => {
        console.error("STOMP 연결 에러:", frame.headers["message"]);
        this.connected = false;
      };

      // WebSocket 연결 종료 시 실행
      this.stompClient.onDisconnect = () => {
        console.log("WebSocket 연결이 종료되었습니다.");
        this.connected = false;
      };

      // WebSocket 활성화
      this.stompClient.activate();
    },

    /**
     * 채팅방 메시지 구독 설정
     */
    subscribeToChat() {
      if (!this.stompClient || !this.roomId) {
        console.error("STOMP Client 또는 Room ID가 없습니다.");
        return;
      }

      console.log(`채팅방 [${this.roomId}] 메시지 구독 중...`);

      this.stompClient.subscribe(`/sub/${this.roomId}`, (message) => {
        try {
          const parsedMessage = JSON.parse(message.body);
          this.handleIncomingMessage(parsedMessage); // 수신 메시지 처리
        } catch (error) {
          console.error("메시지 파싱 중 오류 발생:", error);
        }
      });

      console.log(`채팅방 [${this.roomId}] 메시지 구독 완료`);
    },

    /**
     * 수신된 메시지 처리
     * @param {Object} message - 수신된 메시지
     */
    handleIncomingMessage(message) {
      this.messages.push({
        user: message.userCode,
        content: message.message,
      });
      console.log("현재 메시지 목록:", this.messages);
    },

    /**
     * 메시지 전송 처리
     */
    sendMessage() {
      if (!this.messageContent.trim()) {
        console.log("빈 메시지 전송 시도는 무시됩니다.");
        return;
      }

      const authStore = useUserStore();
      const token = authStore.accessToken;
      const employeeSeq = authStore.getEmployeeInfo().employeeSeq;

      const message = {
        roomId: this.roomId,
        userCode: employeeSeq, // 예시 사용자 코드
        message: this.messageContent,
        createdAt: new Date().toISOString(),
      };

      // 클라이언트에서 토큰 확인 후 메시지 발송
      if (this.stompClient && token) {
        console.log("메시지 발송 중...");

        // 'headers'을 추가하여 'Authorization' 토큰 전달
        this.stompClient.publish({
          destination: `/pub/${this.roomId}`,  // STOMP 목적지
          body: JSON.stringify(message),
          headers: {
            Authorization: `Bearer ${token}`, // 여기서 Authorization 헤더 설정
          }
        });

        this.messageContent = ""; // 입력창 초기화
      } else {
        console.log("토큰 없음");
      }
    },


    /**
     * WebSocket 연결 해제
     */
    disconnect() {
      if (this.stompClient) {
        console.log("WebSocket 연결을 종료합니다...");
        this.stompClient.deactivate();
        this.connected = false;
      }
    },
  },
  /**
   * 컴포넌트가 마운트될 때 WebSocket 연결
   */
  mounted() {
    console.log("Vue 컴포넌트가 마운트되었습니다. WebSocket 연결을 시작합니다...");
    this.connect();
  },
  /**
   * 컴포넌트가 언마운트되기 전에 WebSocket 연결 해제
   */
  beforeUnmount() {
    console.log("Vue 컴포넌트가 언마운트됩니다. WebSocket 연결을 종료합니다...");
    this.disconnect();
  },
};

</script>

<style scoped>
.chat-container {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background: #f9f9f9;
  font-family: Arial, sans-serif;
}

.messages {
  max-height: 300px;
  overflow-y: auto;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  padding: 10px;
  background: #fff;
  border-radius: 4px;
}

.message {
  margin-bottom: 5px;
}

.input-container {
  display: flex;
  gap: 10px;
}

input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  padding: 10px 15px;
  border: none;
  background-color: #007bff;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.connection-status {
  text-align: center;
  color: #f00;
  font-weight: bold;
  margin-top: 10px;
}
</style>
