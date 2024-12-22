<script setup>
import '@/styles/task/TaskAddPage.css'
import { ref, onMounted } from 'vue';
import { useTask } from '@/services/TaskModifyApi.js';
import {useRoute} from "vue-router";

const {
  taskType,
  fileName,
  tableData,
  taskSubmitSeq,
  isLoading,
  taskTitle,
  taskContent,
  departmentSeq,
  templateSeq,
  handleFileChange,
  fetchData,
  addRow,
  updateTask,
  goToGroupingPage,
  fetchTaskData // 새로 추가한 fetchTaskData 함수
} = useTask();

const route = useRoute(); // useRoute 훅을 사용하여 URL 파라미터 접근

// 컴포넌트가 마운트되면 URL에서 taskSeq 값을 받아와서 해당 데이터를 가져오기
onMounted(() => {
  const taskSeq = route.params.taskSeq; // URL에서 taskSeq 파라미터를 가져옵니다.
  if (taskSeq) {
    fetchTaskData(taskSeq); // taskSeq를 API 요청에 넘겨서 데이터 조회
  } else {
    console.error("taskSeq가 URL에 존재하지 않습니다.");
  }
});
</script>

<template>
  <div class="task-add-page-container">
    <div class="taskAddHeader">
      <div class="header-title">
        과제 수정
      </div>
    </div>
    <div class="taskAddBody">
      <div class="taskAddContainer">
        <div class="container-line">
          <span class="task-name"> 과제 제목 </span>
          <input v-model="taskTitle" class="task-name-input">
        </div>
        <div class="container-line">
          <span class="task-depart"> 과제 부서 </span>
          <select v-model="departmentSeq" class="task-depart-input">
            <option value="1">교육팀</option>
            <option value="2">영업팀</option>
            <option value="3">총무팀</option>
            <option value="4">SW개발팀</option>
            <option value="5">인사팀</option>
          </select>
        </div>
        <div class="container-line">
          <span class="task-round">과제 지정</span>
          <select v-model="templateSeq" class="task-round-input">
            <option value="1주차">1주차</option>
            <option value="2주차">2주차</option>
            <option value="3주차">3주차</option>
          </select>
        </div>
        <div class="container-line">
          <span class="task-type">과제 유형</span>
          <select v-model="taskType" class="task-type-input">
            <option value="PERSONAL">개인과제</option>
            <option value="GROUP">그룹과제</option>
          </select>

          <!-- taskType이 "GROUP"일 때만 버튼이 보임 -->
          <button v-if="taskType === 'GROUP'" class="task-group-button" @click="goToGroupingPage">
            그룹 매칭
          </button>
        </div>
        <div class="container-line">
          <span class="task-content"> 과제 내용 </span>
          <input v-model="taskContent" class="task-content-input">
        </div>
        <div class="container-line">
          <span class="task-attach"> 과제 참고 자료 </span>
          <input
              id="fileInput"
              class="task-attach-input"
              type="file"
              @change="handleFileChange"
          >
          <label for="fileInput" class="file-upload-label">파일 선택</label>
          <span v-if="fileName" class="file-name">{{ fileName }}</span> <!-- 파일 이름을 표시하는 부분 -->
        </div>
        <div class="table-container">
          <div class="table-detail">
            <table>
              <thead>
              <tr>
                <td :rowspan="tableData.length + 1" class="evalList">평가 항목</td>
              </tr>
              <tr v-for="(item, index) in tableData" :key="index">
                <td>{{ item.evalIndContent }}</td>
                <td>
                  <input v-model="item.evalListContent" placeholder="내용을 입력하세요" class="evalContent"/>
                </td>
                <td>
                  <input v-model="item.evalListScore" type="number" placeholder="점수" min="0" class="evalscore"/>
                </td>
                <td>
                  <button @click="addRow(index)" class="evalButton">추가하기</button>
                </td>
              </tr>
              </thead>
            </table>
          </div>
        </div>
      </div>
      <div class="submitButton-container">
        <button @click="updateTask" class="task-submit-button">수정 등록</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
button:hover{
  background-color: #5a1dc2; /* 마우스 호버 시 배경색 변경 */
  cursor:pointer;
}
</style>
