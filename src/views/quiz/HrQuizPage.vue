<template>
    <div>
        <h1>퀴즈 조회</h1>
        <search-bar></search-bar>
        <button>퀴즈 등록</button>
        <white-box>
            <list-component></list-component>
        </white-box>
    </div>
</template>

<script setup>
import SearchBar from '@/components/SearchBarComponent.vue';
import WhiteBox from '@/components/WhiteBoxComponent.vue';
import ListComponent from '@/components/ListComponent.vue';
</script>