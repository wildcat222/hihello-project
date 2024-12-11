<script setup>
import AsideComponent from '@/components/AsideComponent.vue';
</script>

<template>
    <div id="app">
        <AsideComponent/>
        <router-view/>
    </div>
</template>

<style scoped>
#app {
  background-color: var(--ivory); /* 원하는 배경색으로 변경 */
}
</style>
