import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';
import piniaPluginPersistedState from 'pinia-plugin-persistedstate';
import './styles/reset.css';
import 'normalize.css';

document.title = import.meta.env.VITE_APP_TITLE || 'Default Title';

const app = createApp(App);
const pinia = createPinia();
pinia.use(piniaPluginPersistedState);

app.use(pinia)
app.use(router);
app.mount('#app');
