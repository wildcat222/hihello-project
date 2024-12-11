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
  background-color: #F4F2EE; /* 원하는 배경색으로 변경 */
}
</style>
