<script setup>
import '@/styles/task/TaskAddPage.css'
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useTask } from '@/services/TaskAdd.js';

const {
  taskType,
  fileName,
  tableData,
  taskSubmitSeq,
  isLoading,
  taskTitle,
  taskContent,
  departmentSeq,
  round,
  handleFileChange,
  fetchData,
  addRow,
  submitTask,
} = useTask();

onMounted(fetchData);
</script>

<template>
  <div class="taskAddHeader">
    <div class="header-title">
      과제 등록
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
          <option value="1">1주차</option>
          <option value="2">2주차</option>
          <option value="3">3주차</option>
        </select>
      </div>
      <div class="container-line">
        <span class="task-type">과제 유형</span>
        <select v-model="taskType" class="task-type-input">
          <option value="PERSONAL">개인과제</option>
          <option value="GROUP">그룹과제</option>
        </select>

        <!-- taskType이 "GROUP"일 때만 버튼이 보임 -->
        <button v-if="taskType === 'GROUP'" class="task-group-button">
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
        <button @click="submitTask" class="task-submit-button">과제 등록</button>
      </div>
  </div>
</template>
