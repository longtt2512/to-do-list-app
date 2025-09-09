<template>
  <div class="h-screen flex flex-col">
    <Header/>
    <main class="flex-1 flex">
      <Sidebar class="hidden md:block relative z-20"/>
      <div class="flex-1 flex flex-col">
        <div class="flex-1 menu-container">
          <router-view/>
        </div>
      </div>
    </main>
    <Footer theme="light"/>
  </div>
</template>

<script setup>
import Sidebar from '../components/Sidebar.vue'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { useRouter } from 'vue-router'
import { provide, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useTaskStore } from '@/stores/task'

const router = useRouter()
const authStore = useAuthStore()
const taskStore = useTaskStore()

const goBack = () => {
  if (window.history.length > 1) {
    router.back()
  } else {
    router.push('/')
  }
}
provide('goBack', goBack)

onMounted(() => {
  if (authStore.isAuthenticated) {
    taskStore.fetchTasks()
  }
})
</script>

<style scoped>
.menu-container {
  padding-top: 70px;
  padding-bottom: 30px;
}
</style>