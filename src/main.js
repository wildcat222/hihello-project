import { createApp } from 'vue'
import App from './App.vue'

document.title = import.meta.env.VITE_APP_TITLE || 'Default Title';

createApp(App).mount('#app')
