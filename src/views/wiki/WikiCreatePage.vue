<template>
  <div>
    <div>
      <input
          v-model="wikiTitle"
          type="text"
          placeholder="제목을 입력하세요">
    </div>
    <Editor v-model="wikiContent" />
    <button @click="creatingWiki">등록하기</button>
  </div>
</template>

<script setup>
// 서버로 이미지 파일을 업로드
import Editor from '@/components/EditorComponent.vue';
import {createWiki} from "@/services/WikiApi.js";
import {ref} from "vue";

const wikiTitle = ref('');
const wikiContent = ref('');

// 위키 생성
const creatingWiki = async () => {
  const wikiData = {
    wikiTitle: wikiTitle.value,
    wikiSnapshotContent: wikiContent.value
  }

  await createWiki(wikiData)
      .then(() => {
        alert("위키가 성공적으로 등록되었습니다.");
        wikiTitle.value = '';
        wikiContent.value = '';
      })
      .catch((error) => {
        console.error("위키 생성 도중 오류가 발생했습니다.", error);
        alert("위키 생성 도중 오류가 발생했습니다.");
      })
}
</script>

<style lang="scss">
</style>