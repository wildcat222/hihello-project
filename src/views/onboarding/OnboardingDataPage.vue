<script setup>
import '@/styles/onboarding/OnboardingData.css'
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
      <form @submit.prevent="submitTemplate" class="onboarding-data-form">
        <div class="onboarding-data-line">
          <div class="onboarding-data-label">
            <label>템플릿 유형</label>
          </div>
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
        <div class="onboarding-data-line">
          <div class="onboarding-data-label">
            <label>체크 필요 여부</label>
          </div>
          <div class="onboarding-data-line">
            <input type="radio" :value="true" v-model="templateForm.templateCheckRequiredStatus" /> 예
          </div>
          <div class="onboarding-data-line">
            <input type="radio" :value="false" v-model="templateForm.templateCheckRequiredStatus" /> 아니오
          </div>
        </div>
        <div class="onboarding-data-line">
          <div class="onboarding-data-label">
            <label>템플릿 종료일</label>
          </div>
          <input type="datetime-local" v-model="templateForm.templateEndAt" required />
        </div>

        <!-- NORMAL Form -->
        <div v-if="templateForm.templateType === 'NORMAL'">
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>공통 교육</label>
            </div>
            <div class="onboarding-data-line">
              <input type="radio" :value="COMMON" v-model="templateForm.templateTrainingType" />공통 교육
            </div>
            <div class="onboarding-data-line">
              <input type="radio" :value="JOB" v-model="templateForm.templateTrainingType" />실무 교육
            </div>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>제목</label>
            </div>
            <input type="text" v-model="templateForm.templateTitle" required/>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>서브내용</label>
            </div>
            <input type="text" v-model="templateForm.templateSub" required>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>파일입력</label>
            </div>
            <label for="file" class="file_button">
              <img
                  src="https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/a66dd4ac-e739-4954-b754-f981f66a0b32_attach_file.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241220T015006Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAQXPZDBYQREV7D6US%2F20241220%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=3c518a60aa9f192a49d922c21d25773d32783486add47de80dede36d97b158df"
                  id="file-button"
              />
              <p v-if="templateForm.templateUrlName" id="file-name">{{ templateForm.templateUrlName }}</p>
            </label>
            <input id="file" type="file" accept=".jpg, .png, .pdf" @change="handleFileChange" style="display: none;">
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>상세 내용</label>
            </div>
            <textarea v-model="templateForm.templateDetail"></textarea>
          </div>
        </div>

        <!-- CHECKLIST Form -->
        <div v-if="templateForm.templateType === 'CHECKLIST'">
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>공통 교육</label>
            </div>
            <div class="onboarding-data-line">
              <input type="radio" :value="COMMON" v-model="templateForm.templateTrainingType" />공통 교육
            </div>
            <div class="onboarding-data-line">
              <input type="radio" :value="JOB" v-model="templateForm.templateTrainingType" />실무 교육
            </div>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>제목</label>
            </div>
            <input type="text" v-model="templateForm.templateTitle" required />
          </div>

          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>리스트 추가</label>
            </div>
            <!-- 새로운 항목을 위한 입력 필드 (실시간 바인딩 아님) -->
            <input
                type="text"
                v-model="newChecklistItem"
                placeholder="항목 이름"
            />
          </div>
          <div>
            <button type="button" @click="addChecklistItem" class="onboarding-check-list-add-button">
              <img src="https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/190b59ac-dec7-4879-b6b2-f36d2682df5d_plus.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241220T022346Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAQXPZDBYQREV7D6US%2F20241220%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=8d94d6d1ea506d0d50bdeef4e53583f346c26fafb8a81e028909e53cbccfea39">
              항목 추가</button>
          </div>
          <!-- 동적으로 추가된 입력 필드 리스트 -->
          <div class="onboarding-data-line">
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
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>공통 교육</label>
            </div>
            <div class="onboarding-data-line">
              <input type="radio" :value="COMMON" v-model="templateForm.templateTrainingType" />공통 교육
            </div>
            <div class="onboarding-data-line">
              <input type="radio" :value="JOB" v-model="templateForm.templateTrainingType" />실무 교육
            </div>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>제목</label>
            </div>
            <input type="text" v-model="templateForm.templateTitle" required/>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>서브내용</label>
            </div>
            <input type="text" v-model="templateForm.templateSub" required>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>URL입력</label>
            </div>
            <input type="text" required/>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>상세 내용</label>
            </div>
            <textarea v-model="templateForm.templateDetail"></textarea>
          </div>
        </div>

        <!-- TASK Form -->
        <div v-if="templateForm.templateType === 'TASK'">
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>공통 교육</label>
            </div>
            <div class="onboarding-data-line">
              <input type="radio" :value="COMMON" v-model="templateForm.templateTrainingType" />공통 교육
            </div>
            <div class="onboarding-data-line">
              <input type="radio" :value="JOB" v-model="templateForm.templateTrainingType" />실무 교육
            </div>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>차수</label>
            </div>
            <input type="text" v-model="templateForm.templateTaskRound"/>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>제목</label>
            </div>
            <input type="text" v-model="templateForm.templateTitle" required/>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>서브내용</label>
            </div>
            <input type="text" v-model="templateForm.templateSub" required>
          </div>
        </div>

        <!-- QUIZ Form -->
        <div v-if="templateForm.templateType === 'QUIZ'">
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>제목</label>
            </div>
            <input type="text" v-model="templateForm.templateTitle" required/>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>서브내용</label>
            </div>
            <input type="text" v-model="templateForm.templateSub" required>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>퀴즈 카테고리</label>
            </div>
            <select v-model="templateForm.quizCategorySeq">
<!--   추후 API 연동 할 것           -->
              <option value="1">안전 관리</option>
              <option value="1">소방 관리</option>
            </select>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>문제 개수</label>
            </div>
            <input type="text" v-model="templateForm.templateQuizQty">
          </div>
        </div>

        <div v-if="templateForm.templateType === 'BREAK' || templateForm.templateType === 'CF'">
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>제목</label>
            </div>
            <input type="text" v-model="templateForm.templateTitle" required/>
          </div>
          <div class="onboarding-data-line">
            <div class="onboarding-data-label">
              <label>서브내용</label>
            </div>
            <input type="text" v-model="templateForm.templateSub" required>
          </div>
        </div>



        <div class="onboarding-data-submit-container">
          <button type="submit" class="onboarding-data-submit">템플릿 저장</button>
        </div>

      </form>
    </div>
  </div>
</template>

<style scoped>
.form {
  width: 70%;
  padding: 20px;
  background: var(--white);
  border-radius: 0px 10px 10px 0px;
}

</style>
