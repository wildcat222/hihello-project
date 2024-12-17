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
      <img
          src="https://hi-hello-bucket.s3.ap-northeast-2.amazonaws.com/c0802cd3-1389-4ce5-b539-8d6bc35ea23d_search-icon.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241217T064206Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAQXPZDBYQREV7D6US%2F20241217%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=1b23fdb117f1613d5f57811c81340adbbe9e06de499cee383931e3c331829169"
          class="search_bar_image"
          alt="검색 이미지">
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
