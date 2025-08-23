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
      { path: '', component: Dashboard, meta: { authenticated: true } },
      { path: '/tasks', component: MyTask, meta: { authenticated: true } },
      { path: '/tasks/add', component: AddTask, meta: { authenticated: true } },
      { path: '/tasks/:id', component: ViewTask, props: true, meta: { authenticated: true } },
      { path: '/categories', component: Categories, meta: { authenticated: true } },
      { path: '/categories/add', component: AddCategory, name: 'add-category', meta: { authenticated: true } },
      { path: '/categories/edit/:id', component: EditCategory, name: 'edit-category', meta: { authenticated: true } },
      { path: '/account', component: Account, meta: { authenticated: true } },
    ]
  },
  {
    path: '/login',
    component: AuthLayout,
    meta: { authenticated: false },
    children: [
      { path: '', component: Login }
    ]
  },
  {
    path: '/register',
    component: AuthLayout,
    meta: { authenticated: false },
    children: [
      { path: '', component: Register }
    ]
  },
  {
    path: '/about',
    component: AboutUs,
    meta: { authenticated: false },
  },
  {
    path: '/:pathMatch(.*)*',
    component: MainLayout,
    meta: { authenticated: false },
    children: [
      { path: '', redirect: '/' }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, _from, next) => {
  const isAuthenticated = localStorage.getItem('accessToken')
  if(to.meta.authenticated && !isAuthenticated) {
    next('/login')
  } else if(!to.meta.authenticated && isAuthenticated) {
    next('/')
  }
  next()
})
export default router
