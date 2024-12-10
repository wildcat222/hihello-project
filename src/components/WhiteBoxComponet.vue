<template>
    <div class="white-box">
      <slot></slot>
    </div>
</template>
  
<style scoped>
  .white-box {
    background-color: white;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    padding: 20px;
    margin: 10px;
    width: 100%; 
    max-width: 600px; 
    text-align: center; 
  }
</style>
  