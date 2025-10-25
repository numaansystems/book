import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import CategoryPage from './pages/CategoryPage.vue'
import SnappyPage from './pages/SnappyPage.vue'

const routes = [
  { path: '/', component: CategoryPage },
  { path: '/snappies', component: SnappyPage }
]

const router = createRouter({ history: createWebHistory(), routes })

const app = createApp({})
app.use(router)
app.mount('#app')
