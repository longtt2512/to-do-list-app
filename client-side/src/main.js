import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import i18n from './i18n'
import './assets/tailwind.css'
import { useAuthStore } from './stores/auth'

const app = createApp(App)
const pinia = createPinia()
app.use(pinia).use(router).use(i18n)

// Initialize auth store from localStorage before mounting
const auth = useAuthStore()
auth.initFromStorage()

app.mount('#app')
