<script setup>
import {onMounted, reactive, ref} from 'vue';
import {useRouter} from 'vue-router';
import {springAPI} from '@/services/axios.js';
import '@/styles/user/MenteeOnboardingPage.css';

const router = useRouter();
// 상태 변수 정의
const onboardingList = ref([]); // 온보딩 리스트 데이터
const loading = ref(true); // 로딩 상태
const query = ref('example'); // query 값
const completedCount = ref(0); // 완료된 항목의 개수
const totalCount = ref(0); // 전체 항목의 개수
const isModalOpen = ref(false); // 모달 열림 여부
const selectedItem = ref(null); // 선택된 항목 저장
// 온보딩 박스 및 선 연결 관련 상태 변수 정의
const boxPositions = reactive([]);  // 카드의 위치 정보를 담는 객체
const lines = ref([]);
const canvas = ref(null);
let draggingCard = null;
let offsetX = 0;
let offsetY = 0;

// 체크리스트 상태 업데이트
const updateChecklistStatus = async (checklistStatusSeq, checklistSeq, listCheckedStatus) => {
  try {
    const payload = {
      checklistStatusSeq,
      checklistSeq,
      listCheckedStatus,
    };

    const response = await springAPI.put('hr/onboarding/checklist', payload, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
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
};

// 온보딩 데이터 가져오기
const fetchOnboardingData = async () => {
  try {
    const response = await springAPI.get('mentee/onboarding', {
      params: {taskContent: query.value},
      headers: {
        Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
      },
    });

    if (response.data.success) {
      onboardingList.value = groupChecklistByTemplate(response.data.data.onboardingList);

      // 완료된 항목과 전체 항목의 개수 계산
      completedCount.value = onboardingList.value.filter(
          (item) => item.onboardingCompletedStatus
      ).length;
      totalCount.value = onboardingList.value.length;

    } else {
      console.error('데이터 호출 실패:', response.data.message);
    }
  } catch (error) {
    console.error('API 호출 에러:', error);
  } finally {
    loading.value = false;
  }
};

// 모달 열기/닫기
const openModal = (item) => {
  selectedItem.value = {
    taskSeq: item.taskSeq,
    taskGroupSeq: item.taskGroupSeq,
  }; // taskSeq와 taskGroupSeq 저장
  isModalOpen.value = true; // 모달 열기
};

const closeModal = () => {
  isModalOpen.value = false; // 모달 닫기
};

// 페이지 이동
const goToTaskPage = () => {
  if (selectedItem.value) {
    router.push(`/task/${selectedItem.value.taskSeq}`);
  } else {
    console.error('taskSeq 값이 없습니다.');
  }
};

const goToGroupEvalPage = () => {
  if (selectedItem.value && selectedItem.value.taskGroupSeq) {
    router.push(`/onboarding/groupEval/${selectedItem.value.taskGroupSeq}`);
  } else {
    console.error('taskGroupSeq 값이 없습니다.');
  }
};

// 체크리스트를 templateSub 기준으로 그룹화
const groupChecklistByTemplate = (onboardingList) => {
  const groupedItems = [];

  onboardingList.forEach((item) => {
    let template = groupedItems.find(
        (group) => group.templateSub === item.templateSub
    );

    if (!template) {
      template = {
        templateTitle: item.templateTitle,
        templateSub: item.templateSub,
        templateDetail: item.templateDetail,
        templateType: item.templateType,
        checklistContent: [],
        onboardingCompletedStatus: item.onboardingCompletedStatus,
        templateUrlName: item.templateUrlName,
        taskSeq: item.taskSeq,
        taskGroupSeq: item.taskGroupSeq,
      };
      groupedItems.push(template);
    }

    if (item.checklistContent) {
      template.checklistContent.push({
        checklistStatusSeq: item.checklistStatusSeq,
        checklistSeq: item.checklistSeq,
        checklistContent: item.checklistContent,
        listCheckedStatus: item.listCheckedStatus,
      });
    }
  });

  return groupedItems;
};

// URL 열기
const goToUrl = (url) => {
  console.log('Received URL:', url);
  if (url) {
    window.open(url, '_blank'); // 새 탭에서 열기
  } else {
    console.error('URL is null or undefined.');
  }
};

// 체크리스트 항목 상태 변경
const toggleChecklistStatus = (item, content) => {
  const updatedStatus = !content.listCheckedStatus; // 상태 반전
  updateChecklistStatus(
      content.checklistStatusSeq,
      content.checklistSeq,
      updatedStatus
  );
  content.listCheckedStatus = updatedStatus; // 상태 변경
};

/* 온보딩 박스 위치 및 선 연결에 관한 메서드들 */
const updateBoxPositions = () => {
  const savedPositions = localStorage.getItem('boxPositions');

  // boxPositions 초기화
  boxPositions.length = 0;

  if (savedPositions) {
    const positions = JSON.parse(savedPositions);

    // 현재 onboardingList와 일치하도록 boxPositions 동기화
    onboardingList.value.forEach((_, index) => {
      if (positions[index]) {
        // 기존 항목 유지
        boxPositions.push(positions[index]);
      } else {
        // 추가된 항목에 대한 위치 계산
        boxPositions.push({top: 50 + index * 150, left: 100 + index * 200});
      }
    });

    // 길이가 줄어든 경우 처리
    if (boxPositions.length > onboardingList.value.length) {
      boxPositions.splice(onboardingList.value.length);
    }

    // 변경된 boxPositions을 로컬 스토리지에 저장
    localStorage.setItem('boxPositions', JSON.stringify(boxPositions));
  } else {
    // 로컬 스토리지에 데이터가 없을 때 초기화
    const boxWidthGap = 500; // 박스의 가로 간격
    const boxHeightGap = 250; // 박스의 세로 간격

    // 박스 위치 계산 로직
    onboardingList.value.forEach((_, index) => {
      const row = Math.floor(index / 3); // 3개씩 한 줄로 배치
      const col = index % 3;

      const left = row % 2 === 0 ? 200 + col * boxWidthGap : 200 + (2 - col) * boxWidthGap; // 왼쪽 또는 오른쪽으로 배치
      const top = 100 + row * boxHeightGap; // 세로 방향 간격

      boxPositions.push({top, left});
    });

    // 초기화된 boxPositions을 로컬 스토리지에 저장
    localStorage.setItem('boxPositions', JSON.stringify(boxPositions));

  }
};

const calculatePath = (startCard, endCard) => {
  if (!startCard || !endCard) return '';

  const startRect = startCard.getBoundingClientRect();
  const endRect = endCard.getBoundingClientRect();
  const canvasRect = canvas.value.getBoundingClientRect();
  const canvasScrollLeft = canvas.value.scrollLeft;
  const canvasScrollTop = canvas.value.scrollTop;

  // 시작 점 (박스의 중앙)
  const startX = startRect.left + startRect.width / 2 - canvasRect.left + canvasScrollLeft;
  const startY = startRect.top + startRect.height / 2 - canvasRect.top + canvasScrollTop;

  // 끝 점 (박스의 중앙)
  const endX = endRect.left + endRect.width / 2 - canvasRect.left + canvasScrollLeft;
  const endY = endRect.top + endRect.height / 2 - canvasRect.top + canvasScrollTop;

  // S자 형태의 곡선 커브 세로 방향 크기 조정
  const verticalOffset = 100;  // S자 커브의 세로 방향 크기 조정
  const middleX = (startX + endX) / 2; // 중간 지점 X

  // 시작과 끝 박스의 Y 위치를 기준으로 제어점 위치 계산
  const control1Y = startY < endY ? startY : startY;
  const control2Y = startY < endY ? endY : endY;

  return `M ${startX} ${startY}
          C ${middleX + verticalOffset} ${control1Y} ${middleX - verticalOffset} ${control2Y} ${endX} ${endY}`;
};

const updateLines = (movedIndex) => {
  const linesToUpdate = [];

  if (movedIndex !== undefined) {
    // 이동한 박스 앞선 연결 선 업데이트
    if (movedIndex > 0) {
      const prevCard = document.querySelector(`#item-${movedIndex - 1}`);
      const currentCard = document.querySelector(`#item-${movedIndex}`);
      const path = calculatePath(prevCard, currentCard);
      if (path) {
        linesToUpdate.push({index: movedIndex, d: path});
      }
    }

    // 이동한 박스 뒷선 연결 업데이트
    if (movedIndex < onboardingList.value.length - 1) {
      const prevCard = document.querySelector(`#item-${movedIndex}`);
      const currentCard = document.querySelector(`#item-${movedIndex + 1}`);
      const path = calculatePath(prevCard, currentCard);
      if (path) {
        linesToUpdate.push({index: movedIndex + 1, d: path});
      }
    }

    // 기존 선과 겹치는 선만 갱신
    lines.value = lines.value.filter(line => !linesToUpdate.some(updatedLine => updatedLine.index === line.index));
    lines.value.push(...linesToUpdate);
  } else {
    // 전체 선을 그리는 경우
    lines.value = [];
    onboardingList.value.forEach((_, index) => {
      if (index === 0) return;
      const prevCard = document.querySelector(`#item-${index - 1}`);
      const currentCard = document.querySelector(`#item-${index}`);
      const path = calculatePath(prevCard, currentCard);
      if (path) {
        lines.value.push({index, d: path});
      }
    });
  }
};

const startDrag = (index, event) => {
  draggingCard = index;
  offsetX = event.clientX - boxPositions[index].left;
  offsetY = event.clientY - boxPositions[index].top;

  document.addEventListener('mousemove', onDrag);
  document.addEventListener('mouseup', stopDrag);
};

const onDrag = (event) => {
  if (draggingCard === null) return;
  boxPositions[draggingCard].left = event.clientX - offsetX;
  boxPositions[draggingCard].top = event.clientY - offsetY;
  updateLines();
};

const stopDrag = () => {
  draggingCard = null;
  document.removeEventListener('mousemove', onDrag);
  document.removeEventListener('mouseup', stopDrag);
  localStorage.setItem('boxPositions', JSON.stringify(boxPositions));
};

onMounted(async () => {
  await fetchOnboardingData();
  await updateBoxPositions()
  await updateLines();
  window.addEventListener('scroll', updateLines);
});
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
              <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="#FFC107" class="alarm-button-img"
                   viewBox="0 0 16 16">
                <path
                    d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2M8 1.918l-.797.161A4 4 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4 4 0 0 0-3.203-3.92zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5 5 0 0 1 13 6c0 .88.32 4.2 1.22 6"/>
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
      <div ref="canvas" class="canvas">
        <svg class="line-container">
          <path
              v-for="(line, index) in lines"
              :key="index"
              :d="line.d"
              stroke="var(--purple)"
              stroke-width="5"
              fill="transparent"
              stroke-dasharray="9, 10"
              stroke-linecap="round"
          />
        </svg>
        <div
            v-for="(item, index) in onboardingList"
            :key="index"
            :id="`item-${index}`"
            class="item-box"
            :style="{
                top: boxPositions[index] && boxPositions[index].top ? boxPositions[index].top + 'px' : '0px',
                left: boxPositions[index] && boxPositions[index].left ? boxPositions[index].left + 'px' : '0px'
              }"
            @mousedown="startDrag(index, $event)"
            :class="{
              'first-item': index === 0,
              'last-item': index === onboardingList.length - 1
            }"
        >
          <div class="item-box-inner">
            <div class="item-header">
              <div class="templateTitle">{{ item.templateTitle }}</div>
            </div>
            <div
                class="item-content"
                :class="{ completed: item.onboardingCompletedStatus, 'checklist-item-box-container': item.templateType === 'CHECKLIST'}">
              <div class="templateDetail">{{ item.templateSub }}</div>
              <div v-if="item.templateType === 'CHECKLIST'" class="checklist-bigBox">
                <div
                    v-if="Array.isArray(item.checklistContent) && item.checklistContent.length > 0"
                    class="checklist-container">
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
                <div>
                  <button class="tempateButton" @click="openModal(item)">
                    확인하기
                  </button>
                  <!-- 모달 -->
                  <div v-if="isModalOpen" class="modal">
                    <div class="modal-content">
                      <p>{{ item.templateDetail }}</p>
                      <div class="onboarding-button-container">
                        <button
                            v-if="selectedItem && selectedItem.taskSeq !== null"
                            @click="goToTaskPage(selectedItem)"
                            class="check-task-button">
                          과제확인
                        </button>
                        <button
                            v-if="selectedItem.taskGroupSeq !== null"
                            @click="goToGroupEvalPage"
                            class="cw-eval-button">
                          동료평가
                        </button>
                        <button @click="closeModal" class="close-button">닫기</button>
                      </div>
                    </div>
                  </div>
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