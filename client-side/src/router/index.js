import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '../pages/Dashboard.vue'
import MyTask from '../pages/MyTask.vue'
import AddTask from '../pages/AddTask.vue'
import ViewTask from '../pages/ViewTask.vue'
import Categories from '../pages/Categories.vue'
import Account from '../pages/Account.vue'
import Team from '../pages/Team.vue'
import Login from '../pages/Login.vue'
import Register from '../pages/Register.vue'

const routes = [
  { path: '/', component: Dashboard },
  { path: '/tasks', component: MyTask },
  { path: '/tasks/add', component: AddTask },
  { path: '/tasks/:id', component: ViewTask, props: true },
  { path: '/categories', component: Categories },
  { path: '/account', component: Account },
  { path: '/team', component: Team },
  { path: '/login', component: Login },
  { path: '/register', component: Register }
]

const router = createRouter({ history: createWebHistory(), routes })
export default router
