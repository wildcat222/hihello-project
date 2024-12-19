<template>
  <div class="wiki-update-page-container">
    <div class="wiki-update-page-title">인턴위키 수정</div>
    <div>
      <div class="wiki-update-page-wiki-title">{{ wikiTitle }}</div>
      <Editor v-model="wikiContent"></Editor>
      <div class="wiki-update-button-container">
        <button @click="updatingWiki(wikiSeq)" class="wiki-update-button">수정하기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import "@/styles/wiki/WikiUpdatePage.css"
import Editor from '@/components/EditorComponent.vue';
import {onMounted, ref} from "vue";
import {fetchWiki, updateWiki} from "@/services/WikiApi.js";
import {useRoute} from "vue-router";

const route = useRoute();
const wikiSeq = ref(null);
const wikiTitle = ref('');
const wikiContent = ref('');
const wikiOriginalContent = ref('');

// 위키 수정 페이지에 들어가면 이전의 데이터가 조회되고, 거기서 수정이 가능하다.
const readingWiki = async (seq) => {
  const response = await fetchWiki(seq)
      .catch((error) => {
        console.error("위키 내용 조회 도중 오류가 발생했습니다.", error);
        alert("위키 내용 조회 도중 오류가 발생했습니다.");
      });

  wikiTitle.value = response.data.wikiTitle;
  wikiContent.value = response.data.wikiContent;
  wikiOriginalContent.value = response.data.wikiContent;
  wikiSeq.value = seq;
}

const updatingWiki = async (seq) => {
  try {
    // 수정된 내용이 없으면 alert
    if (wikiContent.value === wikiOriginalContent.value) {
      alert("내용을 수정해주세요.");
      return;
    }

    const wikiData = {
      wikiModContent: wikiContent.value
    }

    const response = await updateWiki(seq, wikiData);
    if (response.status === 200) { // 백엔드의 성공 상태 코드 확인
      alert("위키가 성공적으로 수정되었습니다.");
    } else {
      alert("위키 수정 도중 오류가 발생했습니다.");
    }
  } catch (error) {
    alert("위키 수정 도중 오류가 발생했습니다.");
  }
}

onMounted(async () => {
  const { wikiSeq:seq } = route.params;  // wikiSeq라는 속성 값을 seq라는 변수에 저장 (const seq = route.params.wikiSeq 와 같다.)
  await readingWiki(seq);
})
</script>