import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

document.title = import.meta.env.VITE_APP_TITLE || 'Default Title';

const app = createApp(App);

app.use(router);
app.mount('#app')
