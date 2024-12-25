<script setup>
import {ref, defineEmits, watch} from 'vue';

// 검색어 상태 변수
const searchQuery = ref('');

// emit 설정
const emit = defineEmits();

// 검색 이벤트 발생
const onSearch = () => {
  if (searchQuery.value.trim() !== '') {
    // search 이벤트 발생, 부모 컴포넌트에서 처리
    emit('search', searchQuery.value);
  } else {
    emit('search', '');
  }
};

// 엔터 키 눌렀을 때 호출
const handleKeyUp = (event) => {
  if (event.key === 'Enter') {
    onSearch();
  }
};

watch(searchQuery, () => {
  emit('update:searchQuery', searchQuery.value)
})
</script>

<template>
  <div class="search_bar">
    <div class="search_bar_image_container">
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi-bi-search" viewBox="0 0 16 16">
        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
      </svg>
    </div>
    <div class="input_container">
      <input
          class="search"
          type="text"
          v-model="searchQuery"
          @keyup="handleKeyUp"
          placeholder="검색어를 입력하세요"
      />
    </div>
  </div>
</template>

<style scoped>
.search_bar {
  width: 80%;
  height: 50px;
  background-color: var(--white);
  box-shadow: 2px 2px 4px 0 var(--gray);
  border-radius: 15px;
  display: flex;
  margin: 0 auto;
}

.search_bar_image_container {
  display: flex;
  margin: 13px 20px;
}

.search_bar_image {
  width: 24px;
  height: 24px;
  align-items: center;
}

.input_container {
  width: 100%;
  margin-right: 20px;
}

.search {
  background-color: transparent;
  border: none;
  padding: 0;
  height: 50px;
  width: 100%;
}
.bi-bi-search{
  margin-top: 5px;
}
</style>
