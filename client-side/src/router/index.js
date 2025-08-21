import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../layouts/MainLayout.vue'
import AuthLayout from '../layouts/AuthLayout.vue'
import Dashboard from '../pages/Dashboard.vue'
import MyTask from '../pages/MyTask.vue'
import AddTask from '../pages/AddTask.vue'
import ViewTask from '../pages/ViewTask.vue'
import Categories from '../pages/Categories.vue'
import Account from '../pages/Account.vue'
import AboutUs from '../pages/AboutUs.vue'
import Login from '../pages/Login.vue'
import Register from '../pages/Register.vue'
import AddCategory from "@/pages/AddCategory.vue";
import EditCategory from "@/pages/EditCategory.vue";

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
      { path: '/categories/add', component: AddCategory, name: 'add-category' },
      { path: '/categories/edit/:id', component: EditCategory, name: 'edit-category' },
      { path: '/account', component: Account },
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
    path: '/about',
    component: AboutUs
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
