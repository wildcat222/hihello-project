<script setup>
defineProps(['items']);
</script>

<template>
  <div>
    <!-- 헤더 -->
    <div class="header">
      <slot name="header"></slot>
    </div>
    <hr class="gray_hr">

    <!-- 아이템 -->
    <div>
      <div v-for="(item, index) in items" :key="index">
        <div>
          <slot name="item" :item="item" :index="index"></slot>
        </div>
        <hr class="light_gray_hr">
      </div>
    </div>
  </div>
</template>

<style scoped>
.header {
  display: flex;
  font-size: 17px;
  font-weight: bold;
  margin: 0 20px 14px 20px;
  justify-content: space-between;
  flex-direction: row;
  height: 40px;
}

.gray_hr {
  border: 1px var(--gray) solid;
}

.light_gray_hr {
  border: 1px var(--light-gray) solid;
}
</style>