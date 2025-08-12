import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../layouts/MainLayout.vue'
import AuthLayout from '../layouts/AuthLayout.vue'
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
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: '', component: Dashboard },
      { path: '/tasks', component: MyTask },
      { path: '/tasks/add', component: AddTask },
      { path: '/tasks/:id', component: ViewTask, props: true },
      { path: '/categories', component: Categories },
      { path: '/account', component: Account },
      { path: '/team', component: Team }
    ]
  },
  {
    path: '/login',
    component: AuthLayout,
    children: [
      { path: '', component: Login }
    ]
  },
  {
    path: '/register',
    component: AuthLayout,
    children: [
      { path: '', component: Register }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    component: MainLayout,
    children: [
      { path: '', redirect: '/' }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })
export default router
