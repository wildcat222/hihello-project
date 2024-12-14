<script setup>
import SearchBarComponent from "@/components/SearchBarComponent.vue";
import WhiteBoxListComponent from "@/components/WhiteBoxListComponent.vue";
import ListComponent from "@/components/ListComponent.vue";
import {onMounted, reactive} from "vue";
import {fetchTaskEvalResultList} from "@/services/TaskEvalResultApi.js";

const taskEvalResultList = reactive([]);

const fetchingTaskEvalResultList = async () => {
  try {
    const response = await fetchTaskEvalResultList();

    response.data.data.forEach(taskEvalResult => {
      taskEvalResultList.push({
        departmentName: taskEvalResult.departmentName,
        templateTaskRound: taskEvalResult.templateTaskRound,
        submitterName: taskEvalResult.submitterName,
        taskContent: taskEvalResult.taskContent,
        taskTotalScore: taskEvalResult.taskTotalScore
      })
    })
  } catch (error) {
    alert("과제 평가 결과 리스트를 조회하던 도중 오류가 발생했습니다.");
  }
}

onMounted(async () => {
  await fetchingTaskEvalResultList();
})
</script>

<template>
  <div class="task-eval-result-list-container">
    <div class="page-title">과제 평가 조회</div>
    <WhiteBoxListComponent>
      <ListComponent :items="taskEvalResultList">
        <template #header>
          <div class="task-eval-result-list-row-container">
            <div>과제 부서</div>
            <div>과제 지정</div>
            <div>제출자</div>
            <div>과제 내용</div>
            <div>평가</div>
          </div>
        </template>
        <template #item="{ item }">
          <div class="task-eval-result-list-row-container item">
            <div>{{ item.departmentName }}</div>
            <div>{{ item.templateTaskRound }}</div>
            <div>{{ item.submitterName }}</div>
            <div>{{ item.taskContent }}</div>
            <div>{{ item.taskTotalScore }}</div>
          </div>
        </template>
      </ListComponent>
    </WhiteBoxListComponent>
  </div>
</template>

<style scoped>
.task-eval-result-list-container {
  width: 70%;
}

.page-title {
  font-size: 35px;
  font-weight: bold;
  text-align: center;
  margin: 6.562rem 0 2.88rem 0;
}

.task-eval-result-list-row-container {
  display: grid;
  grid-template-columns: 8rem 10rem 10rem 30rem 8rem;
  text-align: center;
}

.item {
  margin: 1.3rem 2rem;
  cursor: pointer;
}
</style>