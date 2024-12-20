<script setup>
import {onMounted, ref, watch, watchEffect} from "vue";
import {deleteWiki, fetchWiki, fetchWikiByWikiModContentSeq} from "@/services/WikiApi.js";
import {useRoute} from "vue-router";
import WhiteBoxListComponent from "@/components/WhiteBoxListComponent.vue";
import WikiHistoryModal from "@/views/wiki/WikiHistoryModal.vue";
import router from "@/router/index.js";

const route = useRoute();
const wikiTitle = ref('');
const latestModDate = ref('');
const wikiContent = ref('');

defineProps({
  wikiSeq: {
    type: [String, Number],
    required: true,
  },
});

const fetchingWiki = async (wikiSeq) => {
  try {
    const response = await fetchWiki(wikiSeq);

    wikiTitle.value = response.data.wikiTitle;
    latestModDate.value = response.data.modDate;
    wikiContent.value = response.data.wikiContent;
  } catch (error) {
    alert("위키를 조회하던 도중 오류가 발생했습니다.");
  }
}

const fetchingWikiByWikiModContentSeq = async(wikiSeq, wikiModContentSeq) => {
  try {
    const response = await fetchWikiByWikiModContentSeq(wikiSeq, wikiModContentSeq);

    wikiTitle.value = response.data.wikiTitle;
    latestModDate.value = response.data.modDate;
    wikiContent.value = response.data.wikiContent;
  } catch (error) {
    alert("위키를 조회하던 도중 오류가 발생했습니다.");
  }
}

const formattingDateTime = (dateTime) => {
  return dateTime.replace("T", " ");
}

const isModalOpen = ref(false);

const toggleModal = () => {
  isModalOpen.value = !isModalOpen.value;
}

const closeModal = () => {
  isModalOpen.value = false;
};

const navigateToWikiUpdatePage = () => {
  const wikiSeq = route.params.wikiSeq;
  router.push(`/wiki/newpost/${wikiSeq}`)
}

const deletingWiki = async () => {
  if (!confirm("위키를 삭제하시겠습니까?")) return;
  try {
    const wikiSeq = route.params.wikiSeq;
    await deleteWiki(wikiSeq);
    alert("위키가 삭제되었습니다.");
  } catch (error) {
    console.error("위키 삭제에 실패했습니다.");
    throw error;
  }
}

watch(() => route.params.wikiSeq, async () => {
  const wikiSeq = route.params.wikiSeq;
  const wikiModContentSeq = route.params.wikiModContentSeq;

  if (wikiModContentSeq === undefined) {
    await fetchingWiki(wikiSeq);
  } else {
    await fetchingWikiByWikiModContentSeq(wikiSeq, wikiModContentSeq);
  }
});

// 라우트 변경 시 데이터 새로고침 (watchEffect 사용)
watchEffect(async () => {
  const wikiSeq = route.params.wikiSeq;
  const wikiModContentSeq = route.params.wikiModContentSeq;

  if (wikiModContentSeq === undefined) {
    await fetchingWiki(wikiSeq);
  } else {
    await fetchingWikiByWikiModContentSeq(wikiSeq, wikiModContentSeq);
  }
});

onMounted(async () => {
  const wikiSeq = route.params.wikiSeq;
  const wikiModContentSeq = route.params.wikiModContentSeq;

  if(wikiModContentSeq === undefined) {
    await fetchingWiki(wikiSeq);
  } else {
    await fetchingWikiByWikiModContentSeq(wikiSeq, wikiModContentSeq);
  }
})
</script>

<template>
  <div class="wiki-container">
    <div class="wiki-page-title">인턴위키</div>
    <WhiteBoxListComponent>
      <div class="wiki-header-container">
        <div class="wiki-title"> {{ wikiTitle }}</div>
        <div class="flex">
          <button class="button purple-border" @click="toggleModal">히스토리</button>
          <button class="button purple-background" @click="navigateToWikiUpdatePage">편집</button>
          <button class="button purple-background" @click="deletingWiki">삭제</button>
        </div>
      </div>
      <WikiHistoryModal
          :wikiSeq="wikiSeq"
          v-if="isModalOpen"
          @close="closeModal"
          @click.stop
      />
      <div class="latest-mod-date">최근 수정 시각: {{ formattingDateTime(latestModDate) }}</div>
      <div v-html="wikiContent" class="wiki-content"></div>
    </WhiteBoxListComponent>
  </div>
</template>

<style scoped>
.wiki-container {
  width: 70%;
  min-width: 640px;
  margin: 0 auto;
}

.wiki-page-title {
  font-size: 1.875rem;
  font-weight: bold;
  text-align: center;
  margin: 6.565rem 0 2.88rem 0;
}

.wiki-title {
  font-size: 2rem;
  font-weight: bold;
}

.wiki-header-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.latest-mod-date {
  font-size: 0.9rem;
  color: var(--gray);
  margin: 0.8rem 0;
}

.button {
  border: transparent;
  border-radius: 10px;
  width: 5rem;
  height: 2.2rem;
  font-size: 15px;
  margin: 0 0.1rem;
  cursor: pointer;
}

.purple-border {
  background-color: var(--white);
  color: var(--black);
  border: 1px solid var(--purple);
}

.purple-background {
  background-color: var(--purple);
  color: var(--white);
}
</style>