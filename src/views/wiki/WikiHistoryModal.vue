<script setup>
import {fetchWikiByWikiModContentSeq, fetchWikiHistory} from "@/services/WikiApi.js";
import {onMounted, reactive} from "vue";
import {useRoute} from "vue-router";
import router from "@/router/index.js";

const route = useRoute();
const wikiHistoryList = reactive([]);

const fetchingWikiHistory = async(wikiSeq) => {
  try {
    const response = await fetchWikiHistory(wikiSeq);

    response.data.forEach(wikiHistory => {
      wikiHistoryList.push({
        editorNum: wikiHistory.employeeNum,
        editorName: wikiHistory.employeeName,
        wikiModContentSeq: wikiHistory.wikiModContentSeq,
        latestModDate: wikiHistory.regDate
      })
    })
  } catch(error) {
    alert("위키 히스토리 리스트를 조회하던 도중 오류가 발생했습니다.");
  }
}

const formattingDateTime = (dateTime) => {
  return dateTime.replace("T", " ");
}

const navigateToRestoredWiki = async(wikiModContentSeq) => {
  const wikiSeq = props.wikiSeq;
  closeModal();
  await router.push(`/wiki/${wikiSeq}/ver/${wikiModContentSeq}`);
  await fetchWikiByWikiModContentSeq(wikiSeq, wikiModContentSeq);
}

const props = defineProps({
  wikiSeq: {
    type: [String, Number],
    required: true
  }
})

// Emit 정의
const emit = defineEmits(["close"]);

// 모달 닫기
const closeModal = () => {
  emit("close");
};

onMounted(async() => {
  const wikiSeq = route.params.wikiSeq;
  await fetchingWikiHistory(wikiSeq);
})
</script>

<template>
  <div class="wiki-history-container">
    <div v-for="wikiHistory in wikiHistoryList" :key="wikiHistory.wikiModContentSeq" class="wiki-history">
      <div>{{ formattingDateTime(wikiHistory.latestModDate) }}</div>
      <div>{{ wikiHistory.editorNum }}</div>
      <div>{{ wikiHistory.editorName }}</div>
      <button class="yellow-button" @click="navigateToRestoredWiki(wikiHistory.wikiModContentSeq)">보기</button>
    </div>
  </div>
</template>

<style scoped>
.wiki-history-container {
  width: 26rem;
  height: 32rem;
  background-color: var(--white);
  opacity: 90%;
  border-radius: 15px;
  border: 1px solid var(--light-gray);
  overflow: auto;
  position: absolute;
  left: 60%;
  z-index: 1000;
}

.wiki-history {
  display: flex;
  align-items: center;
  margin: 2rem;
}

.wiki-history > div:first-child {
  margin-right: 1.2rem;
}

.yellow-button {
  width: 3.5rem;
  height: 1.8rem;
  background-color: var(--yellow);
  border: transparent;
  border-radius: 15px;
  margin-left: 1rem;
}
</style>