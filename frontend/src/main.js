import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import App from './App.vue'
import CategoryManagement from './views/CategoryManagement.vue'
import SnappyManagement from './views/SnappyManagement.vue'

const routes = [
  { path: '/', redirect: '/categories' },
  { path: '/categories', component: CategoryManagement },
  { path: '/snappies', component: SnappyManagement }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

const app = createApp(App)
app.use(router)
app.mount('#app')
