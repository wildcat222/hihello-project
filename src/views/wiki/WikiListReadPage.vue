<script setup>
import ListComponent from "@/components/ListComponent.vue";
import {onMounted, reactive} from "vue";
import {fetchWikiList} from "@/services/WikiApi.js";
import WhiteBoxListComponent from "@/components/WhiteBoxListComponent.vue";
import SearchBarComponent from "@/components/SearchBarComponent.vue";
import router from "@/router/index.js";

const wikiList = reactive([]);

const fetchingWikiList = async () => {
  try {
    const response = await fetchWikiList();

    response.data.forEach(wiki => {
      wikiList.push({
        wikiSeq: wiki.wikiSeq,
        wikiTitle: wiki.wikiTitle,
        editorNum: wiki.editorNum,
        editorName: wiki.editorName,
        latestModDate: wiki.latestModDate
      })
    })
  } catch (error) {
    alert("위키 리스트를 조회하던 도중 오류가 발생했습니다.");
  }
}

const navigateToWikiNewPost = () => {
  router.push('/wiki/newPost');
}

const navigateToWiki = (wikiSeq) => {
  console.log(wikiSeq);
  router.push(`/wiki/${wikiSeq}`);
}

const formattingDateTime = (dateTime) => {
  return dateTime.replace("T", " ");
}

onMounted(async () => {
  await fetchingWikiList();
})
</script>

<template>
  <div class="content_box">
    <div class="title">인턴위키</div>
    <div class="search_bar_container">
      <SearchBarComponent/>
      <button class="purple_button" @click="navigateToWikiNewPost">문서작성</button>
    </div>
    <WhiteBoxListComponent>
      <ListComponent :items="wikiList">
        <template #header>
          <div class="left_list">문서</div>
          <div class="right_list">
            <div>수정자</div>
            <div>수정시간</div>
          </div>
        </template>

        <template #item="{ item }">
          <div class="wiki_list_row_container" @click="navigateToWiki(item.wikiSeq)">
            <div class="left_list">{{ item.wikiTitle }}</div>
            <div class="right_list">
              <div>{{ item.editorNum }} {{ item.editorName }}</div>
              <div>{{ formattingDateTime(item.latestModDate) }}</div>
            </div>
          </div>

        </template>
      </ListComponent>
    </WhiteBoxListComponent>
  </div>
</template>

<style scoped>
.content_box {
  width: 70%;
  margin: 0 auto;
}

.title {
  font-size: 35px;
  font-weight: bold;
  text-align: center;
  margin: 62px 0 49px;
}

.search_bar_container {
  display: flex;
  width: 60%;
  margin: 29px auto;
  align-items: center;
}

.purple_button {
  background-color: var(--purple);
  width: 111px;
  height: 43px;
  color: var(--white);
  border-radius: 15px;
  border: none;
  margin-left: 18px;
  cursor: pointer;
}

.wiki_list_row_container {
  display: flex;
  font-size: 15px;
  margin: 22px 20px;
  justify-content: space-between;
  cursor: pointer;
}

.left_list {
  text-align: left;
  white-space: nowrap;
}

.right_list {
  display: flex;
  text-align: right;
}

.right_list > div:first-child {
  width: 200px;
  text-align: center;
}

.right_list > div:last-child {
  width: 180px;
  text-align: center;
}
</style>