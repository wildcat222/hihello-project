<script setup>
 import {ref} from "vue";

 const COMMON = 'COMMON';
 const JOB = 'JOB';
import {
  templateForm,
  templateList,
  newChecklistItem,
  removeChecklistItem,
  addChecklistItem,
  submitTemplate,
  loadTemplates,
  handleFileChange,
  deleteTemplate,
  drop,
  dragOver,
  dragStart,
  resetTemplateData,
} from '@/services/OnboardingDataApi.js';  // 상대 경로로 스크립트 파일을 import 합니다.
loadTemplates(); // 화면 로드시 템플릿 리스트 불러오기

</script>

<template>
  <div class="onboarding-total-container">
    <!-- 왼쪽 리스트 -->
    <div class="sidebar">
      <div class="onboarding-page-title">온보딩 절차</div>
      <div class="small-notice">드래그해서 순서를 변경해보세요.</div>
      <ul class="onboarding-ul">
        <li
            v-for="(template, index) in templateList"
            :key="template.id"
            :draggable="true"
            @dragstart="dragStart($event, index)"
            @dragover="dragOver($event)"
            @drop="drop($event, index)"
            class="onboarding-li"
        >
          <div class="onboarding-order">{{ template.templateProcedure }}</div>
          <div class="onboarding-title">{{ template.templateTitle }}</div>
          <button @click="deleteTemplate(template.templateSeq)" class="onboarding-list-delete">X</button>
        </li>
      </ul>
    </div>

    <!-- 템플릿 생성 폼 -->
    <div class="form">
      <h2>템플릿 생성</h2>
      <form @submit.prevent="submitTemplate">
        <div>
          <label>템플릿 유형</label>
          <select v-model="templateForm.templateType" @change="resetTemplateData">
            <option value="NORMAL">글,파일 탬플릿</option>
            <option value="BREAK">휴가 신청 탬플릿</option>
            <option value="CF">회의실 예약 탬플릿</option>
            <option value="CHECKLIST">CheckList 탬플릿</option>
            <option value="VIDEO">영상 탬플릿</option>
            <option value="TASK">과제</option>
            <option value="QUIZ">퀴즈</option>
          </select>
        </div>
        <div>
          <label>체크 필요 여부</label>
          <input type="radio" :value="true" v-model="templateForm.templateCheckRequiredStatus" /> 예
          <input type="radio" :value="false" v-model="templateForm.templateCheckRequiredStatus" /> 아니오
        </div>
        <div>
          <label>템플릿 종료일</label>
          <input type="datetime-local" v-model="templateForm.templateEndAt" required />
        </div>

        <!-- NORMAL Form -->
        <div v-if="templateForm.templateType === 'NORMAL'">
          <h3>NORMAL Form</h3>
          <div>
            <label>공통 교육</label>
            <input type="radio" :value="COMMON" v-model="templateForm.templateTrainingType" />공통 교육
            <input type="radio" :value="JOB" v-model="templateForm.templateTrainingType" />실무 교육
          </div>
          <div>
            <label>제목</label>
            <input type="text" v-model="templateForm.templateTitle" required/>
          </div>
          <div>
            <label>서브내용</label>
            <input type="text" v-model="templateForm.templateSub" required>
          </div>
          <div>
            <label>파일입력</label>
            <input type="file" accept=".jpg, .png, .pdf" @change="handleFileChange">
          </div>
          <div>
            <label>상세 내용</label>
            <textarea v-model="templateForm.templateDetail"></textarea>
          </div>
        </div>

        <!-- CHECKLIST Form -->
        <div v-if="templateForm.templateType === 'CHECKLIST'">
          <h3>CHECKLIST Form</h3>
          <div>
            <label>공통 교육</label>
            <input type="radio" value="COMMON" v-model="templateForm.templateTrainingType" />공통 교육
            <input type="radio" value="JOB" v-model="templateForm.templateTrainingType" />실무 교육
          </div>
          <div>
            <label>제목</label>
            <input type="text" v-model="templateForm.templateTitle" required />
          </div>

          <div>
            <label>리스트 추가</label>
            <!-- 새로운 항목을 위한 입력 필드 (실시간 바인딩 아님) -->
            <input
                type="text"
                v-model="newChecklistItem"
                placeholder="항목 이름"
            />
            <button type="button" @click="addChecklistItem">항목 추가</button>
          </div>

          <!-- 동적으로 추가된 입력 필드 리스트 -->
          <div>
            <ul>
              <li v-for="(item, index) in templateForm.checklistContent" :key="index">
                {{ item.checklistContent }}
                <button @click="removeChecklistItem(index)">삭제</button>
              </li>
            </ul>
          </div>
        </div>

        <!-- VIDEO Form -->
        <div v-if="templateForm.templateType === 'VIDEO'">
          <h3>VIDEO Form</h3>
          <div>
            <label>공통 교육</label>
            <input type="radio" :value="COMMON" v-model="templateForm.templateTrainingType" />공통 교육
            <input type="radio" :value="JOB" v-model="templateForm.templateTrainingType" />실무 교육
          </div>
          <div>
            <label>제목</label>
            <input type="text" v-model="templateForm.templateTitle" required/>
          </div>
          <div>
            <label>서브내용</label>
            <input type="text" v-model="templateForm.templateSub" required>
          </div>
          <div>
            <label>URL입력</label>
            <input type="text" required/>
          </div>
          <div>
            <label>상세 내용</label>
            <textarea v-model="templateForm.templateDetail"></textarea>
          </div>
        </div>

        <!-- TASK Form -->
        <div v-if="templateForm.templateType === 'TASK'">
          <h3>JOB Form</h3>
          <div>
            <label>공통 교육</label>
            <input type="radio" :value="COMMON" v-model="templateForm.templateTrainingType" />공통 교육
            <input type="radio" :value="JOB" v-model="templateForm.templateTrainingType" />실무 교육
          </div>
          <div>
            <label>차수</label>
            <input type="text" v-model="templateForm.templateTaskRound"/>
          </div>
          <div>
            <label>제목</label>
            <input type="text" v-model="templateForm.templateTitle" required/>
          </div>
          <div>
            <label>서브내용</label>
            <input type="text" v-model="templateForm.templateSub" required>
          </div>
        </div>

        <!-- COMMONTASK Form -->
        <div v-if="templateForm.templateType === 'COMMONTASK'">
          <h3>JOB Form</h3>
          <div>
            <label>제목</label>
            <input type="text" v-model="templateForm.templateTitle" required/>
          </div>
          <div>
            <label>서브내용</label>
            <input type="text" v-model="templateForm.templateSub" required>
          </div>
          <div>
            <label>퀴즈 카테고리</label>
            <select v-model="templateForm.quizCategorySeq">
<!--   추후 API 연동 할 것           -->
              <option value=""></option>
            </select>
          </div>
          <div>
            <label>문제 개수</label>
            <input type="text" v-model="templateForm.templateQuizQty">
          </div>
        </div>

        <button type="submit">템플릿 저장</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.onboarding-page-title{
  font-weight: 700;
  font-size: 20px;
}
.small-notice{
  font-weight: 400;
  font-size: 10px;
  color: var(--gray);
}
.onboarding-total-container{
  display: flex;
  width: 80%;
}
.onboarding-list-delete{
  color: var(--red);
  border: none;
  border-radius: 100%;
  background-color: var(--white);

}
.onboarding-title{
  font-size: 15px;
}
.onboarding-ul{
  padding: 0;
  margin: 0;
}
.onboarding-li{
  display: flex;
  align-items: center;
}
.sidebar {
  width: 25%;
  background: var(--white);
  padding: 20px;
  padding-left: 50px;
  border-radius: 10px 0px 0px 10px;
  border-right: 2px solid var(--gray);
}
.form {
  width: 70%;
  padding: 20px;
  background: var(--white);
  border-radius: 0px 10px 10px 0px;
}
.onboarding-order{
  list-style: none;
  padding: 7px;
  background-color: var(--purple);
  border-radius: 100%;
  height: 20px;
  width: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  margin: 10px;
}
</style>
