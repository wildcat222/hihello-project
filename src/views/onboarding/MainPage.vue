<script>
import axios from 'axios';
import "@/styles/user/MenteeOnboardingPage.css"
import {springAPI} from "@/services/axios.js";

export default {
  data() {
    return {
      onboardingList: [],  // 온보딩 리스트 데이터
      loading: true,       // 로딩 상태
      query: 'example',    // query 값
      completedCount: 0,   // 완료된 항목의 개수
      totalCount: 0,       // 전체 항목의 개수
    };
  },
  mounted() {
    this.fetchOnboardingData();
  },
  methods: {
    // API 호출 (체크리스트 항목 개별적으로 업데이트)
    async updateChecklistStatus(checklistStatusSeq, checklistSeq, listCheckedStatus) {
      try {
        const payload = {
          checklistStatusSeq,
          checklistSeq,
          listCheckedStatus,
        };

        const response = await springAPI.put('hr/onboarding/checklist', payload, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
          },
        });

        if (response.data.success) {
          console.log('체크리스트 상태 업데이트 성공:', response.data.message);
        } else {
          console.error('체크리스트 상태 업데이트 실패:', response.data.message);
        }
      } catch (error) {
        console.error('API 호출 에러:', error);
      }
    },

    async fetchOnboardingData() {
      try {
        const response = await springAPI.get('mentee/onboarding', {
          params: { taskContent: this.query },
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
          },
        });

        if (response.data.success) {
          this.onboardingList = this.groupChecklistByTemplate(response.data.data.onboardingList);

          // 완료된 항목과 전체 항목의 개수 계산
          this.completedCount = this.onboardingList.filter(item => item.onboardingCompletedStatus).length;
          this.totalCount = this.onboardingList.length;
        } else {
          console.error('데이터 호출 실패:', response.data.message);
        }
      } catch (error) {
        console.error('API 호출 에러:', error);
      } finally {
        this.loading = false;
      }
    },

    // checklist 항목들을 templateSub을 기준으로 그룹화하는 함수
    groupChecklistByTemplate(onboardingList) {
      const groupedItems = [];

      onboardingList.forEach(item => {
        let template = groupedItems.find(group => group.templateSub === item.templateSub);

        if (!template) {
          template = {
            templateSub: item.templateSub,
            templateDetail: item.templateDetail,
            templateType: item.templateType,
            checklistContent: [],
            onboardingCompletedStatus: item.onboardingCompletedStatus,
            templateUrlName:item.templateUrlName
          };
          groupedItems.push(template);
        }

        if (item.checklistContent) {
          template.checklistContent.push({
            checklistStatusSeq: item.checklistStatusSeq,
            checklistSeq: item.checklistSeq,
            checklistContent: item.checklistContent,
            listCheckedStatus: item.listCheckedStatus
          });
        }
      });

      return groupedItems;
    },

    goToUrl(url) {
      console.log('Received URL:', url); // URL 값 확인
      if (url) {
        window.open(url, '_blank'); // URL이 정상일 경우 새 탭에서 열기
      } else {
        console.error('URL is null or undefined.');
      }
    },

    // 체크리스트 항목의 상태 변경 (체크박스 개별적으로 처리)
    toggleChecklistStatus(item, content) {
      const updatedStatus = !content.listCheckedStatus;  // 상태 반전
      this.updateChecklistStatus(content.checklistStatusSeq, content.checklistSeq, updatedStatus);
      content.listCheckedStatus = updatedStatus;  // 상태 변경
    },
  },
};
</script>

<template>
  <div class="onboarding-page">
    <div class="header">
      <div v-if="loading" class="loading">로딩 중...</div>
      <div v-else>
        <!-- 완료된 항목을 게이지로 표시 -->
        <div class="status-gauge">
          <div class="alarm-container">
            <button class="alarm-button">
              <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="#FFC107" class="alarm-button-img" viewBox="0 0 16 16">
                <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2M8 1.918l-.797.161A4 4 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4 4 0 0 0-3.203-3.92zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5 5 0 0 1 13 6c0 .88.32 4.2 1.22 6"/>
              </svg>
            </button>
          </div>
          <div class="vr"></div>
          <div class="status-gauge-container">
            <progress :value="completedCount" :max="totalCount" class="progress-bar"></progress>
            <p class="progress-text">{{ completedCount }} / {{ totalCount }}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="onboarding-body-container">
      <div class="bar"/>
      <div class="onboarding-body">
        <div class="item-container-wrapper">
          <div
              class="item-box"
              v-for="(item, index) in onboardingList"
              :key="index"
              :class="{
              'first-item': index === 0,
              'last-item': index === onboardingList.length - 1
            }"
          >
            <div class="item-box-inner">
            <div class="item-header">
              <div class="templateTitle">{{ item.templateSub }}</div>
            </div>
            <div class="item-content" :class="{ completed: item.onboardingCompletedStatus }">
              <div class="templateDetail">{{ item.templateDetail }}</div>

              <div v-if="item.templateType === 'CHECKLIST'" class="checklist-bigBox">
                <div v-if="Array.isArray(item.checklistContent) && item.checklistContent.length > 0" class="checklist-container">
                  <div class="checklist-content" v-for="(content, index) in item.checklistContent" :key="index">
                    <label class="checklist-label">
                      <input
                          type="checkbox"
                          class="checklist-checkbox"
                          :checked="content.listCheckedStatus"
                          @change="toggleChecklistStatus(item, content)"
                      />
                    </label>
                    <div class="checklist-inner">{{ content.checklistContent }}</div>
                  </div>
                </div>
                <div v-else>
                  <div class="templateContent">{{ item.checklistContent || '체크리스트 항목이 없습니다.' }}</div>
                </div>
              </div>
              <div v-else>
                <button class="tempateButton" @click="goToUrl(item.templateUrlName)">
                  확인하기
                </button>
              </div>
            </div>
            </div>
          </div>
        </div>
      </div>
      <div class="bar2"/>
    </div>
  </div>
</template>