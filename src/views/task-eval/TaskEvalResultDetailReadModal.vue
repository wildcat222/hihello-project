<script setup>
import { fetchTaskEvalDetailResult } from "@/services/TaskEvalResultApi.js";
import { computed, reactive, watch } from "vue";

const taskEvalResultDetailList = reactive([]);

const fetchingTaskEvalDetailResult = async (taskSubmitSeq) => {
  try {
    const response = await fetchTaskEvalDetailResult(taskSubmitSeq);
    taskEvalResultDetailList.splice(0, taskEvalResultDetailList.length); // 초기화
    response.data.data.forEach((taskEvalDetail) => {
      taskEvalResultDetailList.push({
        taskEvalSeq: taskEvalDetail.taskEvalSeq,
        evalIndSeq: taskEvalDetail.evalIndSeq,
        evalIndContent: taskEvalDetail.evalIndContent,
        evalListSeq: taskEvalDetail.evalListSeq,
        evalListContent: taskEvalDetail.evalListContent,
        taskScore: taskEvalDetail.taskScore,
      });
    });
    console.log(response)
  } catch (error) {
    alert("과제 평가 조회 상세 결과를 조회하던 도중 오류가 발생했습니다.");
  }
};

// 평가 지표 항목별로 그룹핑된 데이터 생성
const groupingTaskEvalDetailResult = computed(() => {
  const groups = [];

  if (!taskEvalResultDetailList.length) return groups; // 데이터가 없으면 빈 배열 반환

  taskEvalResultDetailList.forEach((item) => {
    const existingGroup = groups.find(
        (group) => group.evalIndContent === item.evalIndContent
    );
    if (existingGroup) {
      existingGroup.items.push(item);
    } else {
      groups.push({
        evalIndContent: item.evalIndContent,
        items: [item],
      });
    }
  });
  return groups;
});


// Props 정의
const props = defineProps({
  isOpen: Boolean,
  taskSubmitSeq: Number,
  taskData: Object,
});

// Emit 정의
const emit = defineEmits(["close"]);

// 모달 닫기
const closeModal = () => {
  emit("close");
};

const closeModalOutside = (event) => {
  if (event.target === event.currentTarget) {
    closeModal();
  }
}

// 모달 열릴 때 데이터 로드
watch(
    () => props.isOpen,
    async (newValue) => {
      if (newValue) {
        await fetchingTaskEvalDetailResult(props.taskData.taskSubmitSeq);
      }
    }
);
</script>

<template>
  <div v-if="isOpen" class="modal-container flex" @click="closeModalOutside">
    <div class="modal-content-container" @click.stop>
      <table class="font18">
        <tbody>
          <tr class="table-row">
            <td>제출자</td>
            <td class="flex">
              <div>{{ props.taskData.submitterName }}</div>
              <button class="button purple-button">그룹조회</button>
            </td>
          </tr>
          <tr class="table-row">
            <td>과제 부서</td>
            <td>{{ props.taskData.departmentName }}</td>
          </tr>
          <tr class="table-row">
            <td>과제 지정</td>
            <td>{{ props.taskData.templateTaskRound }}</td>
          </tr>
          <tr class="table-row">
            <td>과제 내용</td>
            <td>{{ props.taskData.taskContent }}</td>
          </tr>
        </tbody>
      </table>
      <hr class="gray-hr">
      <table class="task-eval-table">
        <colgroup>
          <col style="width: 15%;">
          <col style="width: 54%;">
          <col style="width: 10%;">
        </colgroup>
        <thead>
          <tr>
            <th>평가 지표</th>
            <th>평가 항목</th>
            <th>점수</th>
          </tr>
        </thead>
        <tbody>
          <template v-if="groupingTaskEvalDetailResult.length > 0">
            <template
                v-for="(group, groupIndex) in groupingTaskEvalDetailResult"
                :key="'group-' + groupIndex"
            >
              <tr v-for="(item, index) in group.items" :key="'item-' + groupIndex + '-' + index">
                <td v-if="index === 0" :rowspan="group.items.length">
                  {{ group.evalIndContent }}
                </td>
                <td>{{ item.evalListContent }}</td>
                <td>{{ item.taskScore }}점</td>
              </tr>
            </template>
          </template>
          <tr v-else>
            <td colspan="3">데이터가 없습니다.</td>
          </tr>
        </tbody>
      </table>
      <div class="yellow-button-container">
        <button @click="closeModal" class="button yellow-button">닫기</button>
      </div>
    </div>
  </div>
</template>