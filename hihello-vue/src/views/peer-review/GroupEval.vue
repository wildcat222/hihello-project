<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import ListComponent from "@/components/ListComponent.vue";
import { useGroupEval } from "@/services/GroupEvalAPI.js";
import {springAPI} from "@/services/axios.js";
import "@/styles/peer-review/GroupEval.css"

// taskGroupSeq를 URL 쿼리 파라미터로부터 추출
const route = useRoute();
const taskGroupSeq = route.params.taskGroupSeq;

if (!taskGroupSeq) {
  console.error('taskGroupSeq가 URL에 존재하지 않습니다.');
}

// useGroupEval 훅을 사용하여 데이터와 함수 가져오기
const {
  groupInfo,
  reviewItems,
  selectedRevieweeSeq,
  inputText,
  fetchGroupInfo,
  fetchReviewData
} = useGroupEval(taskGroupSeq);

// 컴포넌트가 마운트될 때 API 호출
onMounted(() => {
  fetchGroupInfo();
  fetchReviewData();
});

// 저장 버튼 클릭 시 처리할 함수
const handleSave = async () => {
  if (!selectedRevieweeSeq.value) {
    alert("평가할 동료를 선택하여 주세요")
    return;
  }

  const selectedReview = reviewItems.value.map(item => ({
    peerReviewListSeq: item.peerReviewListSeq,
    revieweeSeq: selectedRevieweeSeq.value, // 선택된 동료의 seq
    peerReviewScore: inputText.value[item.peerReviewListSeq], // 입력한 값
  }));

  try {
    const response = await springAPI.post(
        `mentee/task/group/${taskGroupSeq}`,
        selectedReview,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
          },
        }
    );
    if (response.data.success) {
      alert("저장에 성공하였습니다.")
    } else {
      alert("저장에 실패하였습니다.")
    }
  } catch (error) {
    console.error('저장 중 오류 발생:', error);
  }
};

const validateScore = (item) => {
  const enteredScore = inputText.value[item.peerReviewListSeq];
  if (enteredScore > item.peerReviewListScore) {
    alert("배점보다 낮은 점수를 입력하세요!");
    inputText.value[item.peerReviewListSeq] = item.peerReviewListScore; // 배점을 초과하지 않도록 초기화
  }
};
</script>

<template>
  <div class="group-eval-main-container">
    <div class="group-eval-header">동료 평가</div>
    <div class="group-eval-body">

      <!-- 동료 목록 -->
      <div class="group-eval-cowList">
        <div v-if="groupInfo.length > 0" class="group-eval-cowList-inner">
          <div v-for="member in groupInfo" :key="member.groupMemberSeq">
            <div class="group-eval-cow">
              <button
                  class="member-button"
                  @click="selectedRevieweeSeq = member.groupMemberSeq"
                  :class="{ selected: selectedRevieweeSeq === member.groupMemberSeq }"
              >
                {{ member.employeeName }}
              </button>
            </div>
          </div>
        </div>
        <div v-else>
          <p>정보를 불러오는 중...</p>
        </div>
      </div>

      <!-- 리뷰 항목 리스트 -->
      <div class="group-eval-list-container">
        <div class="group-eval-list">
          <ListComponent :items="reviewItems">
            <template #header >
              <div class="group-eval-title">
                <div class="group-eval-title-inner">
                  항목
                </div>
                <div class="group-eval-title-inner-content">
                  내용
                </div>
                <div class="group-eval-title-inner">
                  배점
                </div>
                <div class="group-eval-title-inner">
                  점수
                </div>
              </div>
            </template>

            <template #item="{ item, index }" >
              <div class="peerReviewListContainerBox">
                <div class="peerReviewListContainer">
                  <div class="peerReviewListContainerInner">
                    <div class="peerReviewListSeq">{{item.peerReviewListSeq}}</div>
                    <div class="peerReviewListContent">{{ item.peerReviewListContent }}</div>
                    <div>{{ item.peerReviewListScore }}</div>
                    <input
                        v-model="inputText[item.peerReviewListSeq]"
                        type="number"
                        placeholder="점수"
                        class="groupReviewInputBox"
                        @input="validateScore(item)"
                    />
                  </div>
                </div>
              </div>

            </template>
          </ListComponent>
        </div>
      </div>


      <!-- 저장 버튼 -->
      <div class="group-eval-saveButton">
        <button @click="handleSave" class="group-eval-saveButton-real">저장</button>
      </div>
    </div>
  </div>
</template>


