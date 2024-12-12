<script setup>
import AsideComponent from '@/components/AsideComponent.vue';
</script>

<template>
  <div id="app">
    <AsideComponent />
    <div class="router-container">
      <router-view />
    </div>
  </div>
</template>

<style scoped>
#app {
  display: flex;
}

.router-container {
  flex-grow: 1;
  background-color: var(--ivory);
}
</style>

