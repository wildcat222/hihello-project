<script setup>
import "@/styles/task/MentorTaskDetailReadPage.css"
import WhiteBoxComponent from "@/components/WhiteBoxComponent.vue";
import {computed, onMounted, reactive, ref} from "vue";
import {fetchSubmittedTask} from "@/services/TaskApi.js";
import {useRoute} from "vue-router";
import {fetchEvalLists} from "@/services/EvalListApi.js";
import {createTaskEval} from "@/services/TaskEvalApi.js";

const route = useRoute();

const taskSeq = ref('');
const taskTitle = ref('');
const taskFileName = ref('');
const taskFileUrl = ref('');
const taskContent = ref('');
const taskSubmitContent = ref('');
const taskSubmitFileName = ref('');
const taskSubmitFileUrl = ref('');
const taskSubmitDate = ref('');
const taskEvalResultDetailList = reactive([]);

// 제출된 과제 내용 조회하기
const fetchingSubmittedTask = async(taskSubmitSeq) => {
  try {
    const response = await fetchSubmittedTask(taskSubmitSeq);

    taskSeq.value = response.data.taskSeq;
    taskTitle.value = response.data.taskTitle;
    taskFileName.value = response.data.taskFileName;
    taskFileUrl.value = response.data.taskFileUrl;
    taskContent.value = response.data.taskContent;
    taskSubmitContent.value = response.data.taskSubmitContent;
    taskSubmitFileName.value = response.data.taskSubmitFileName;
    taskSubmitFileUrl.value = response.data.taskSubmitFileUrl;
    taskSubmitDate.value = response.data.taskSubmitDate;
  } catch (error) {
    alert("제출된 과제 내용을 불러오던 도중 오류가 발생했습니다.");
  }
}

// 과제 평가 항목 조회하기
const fetchingTaskEvalDetailResult = async (taskSeq) => {
  try {
    const response = await fetchEvalLists(taskSeq);

    taskEvalResultDetailList.splice(0, taskEvalResultDetailList.length); // 초기화
    response.data.data.forEach((taskEvalDetail) => {
      taskEvalResultDetailList.push({
        evalIndSeq: taskEvalDetail.evalIndSeq,
        evalIndContent: taskEvalDetail.evalIndContent,
        evalListSeq: taskEvalDetail.evalListSeq,
        evalListContent: taskEvalDetail.evalListContent,
        evalListScore: taskEvalDetail.evalListScore,
        taskScore: null
      });
    });

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

const creatingTaskEvals = async() => {
  try {
    const taskSubmitSeq = route.params.taskSubmitSeq;
    const taskEvalsData = taskEvalResultDetailList.map(item => ({
      evalListSeq: item.evalListSeq,
      taskScore: item.taskScore
    }))

    await createTaskEval(taskSubmitSeq, taskEvalsData);
    alert("과제 평가가 성공적으로 등록되었습니다.");
  } catch(error) {
    alert("과제 평가 등록 중 오류가 발생했습니다.");
  }
}

onMounted(async() => {
  const taskSubmitSeq = route.params.taskSubmitSeq;
  await fetchingSubmittedTask(taskSubmitSeq);
  await fetchingTaskEvalDetailResult(taskSeq.value);
})
</script>

<template>
  <div class="page-container">
    <div class="page-title">과제</div>
    <WhiteBoxComponent class="white-box-component">
      <div class="left">
        <div class="task-info-container">
          <div class="mentor-task-page-title">{{ taskTitle }}</div>
          <div class="flex">
            <div class="task-file-text">과제 파일</div>
            <div class="task-file-name-container">{{ taskFileName }}</div>
          </div>
          <div class="left mentor-task-content">{{ taskContent }}</div>
        </div>
        <hr class="light-gray-hr">
        <div class="mentor-task-submit-container">
          <div class="mentor-task-page-title">과제 제출</div>
          <div v-if="taskSubmitFileUrl !== null" class="flex">
            <div class="task-file-text">제출 파일</div>
            <div class="task-file-name-container">{{ taskSubmitFileName }}</div>
          </div>
          <div>{{ taskSubmitContent }}</div>
        </div>
        <hr class="light-gray-hr">
        <div class="mentor-eval-container">
          <div class="mentor-task-page-title">멘토 평가</div>
          <table class="mentor-task-eval-table">
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
                  <td>
                    <input
                        type="text"
                        class="task-eval-score-input"
                        :placeholder="item.evalListScore"
                        v-model.muber = "item.taskScore"
                    >점
                  </td>
                </tr>
              </template>
            </template>
            <tr v-else>
              <td colspan="3">데이터가 없습니다.</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div>
        <button class="button purple-button" @click="creatingTaskEvals">평가하기</button>
      </div>
    </WhiteBoxComponent>
  </div>
</template>