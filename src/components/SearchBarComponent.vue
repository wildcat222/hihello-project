<script setup>

</script>

<template>
  <div class="search_bar">
    <div class="search_bar_image_container">
      <img src="../assets/search-icon.png" class="search_bar_image" alt="검색 이미지">
    </div>
    <div class="input_container">
      <input class="search" type="text">
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