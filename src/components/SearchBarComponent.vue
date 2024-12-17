<script setup>
import {ref, defineEmits} from 'vue';

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
</script>

<template>
  <div class="search_bar">
    <div class="search_bar_image_container">
      <img src="../assets/search-icon.png" class="search_bar_image" alt="검색 이미지">
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
  width: 100%;
  max-width: 780px;
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
</style>
