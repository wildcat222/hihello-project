import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';
import './styles/reset.css';
import 'normalize.css';

document.title = import.meta.env.VITE_APP_TITLE || 'Default Title';

const app = createApp(App);
const pinia = createPinia();

app.use(pinia)
app.use(router);
app.mount('#app');
