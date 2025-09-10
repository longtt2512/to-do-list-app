<template>
  <div class="h-screen flex flex-col">
    <Header />
    <main class="flex-1 flex">
      <Sidebar class="hidden md:block relative z-20" />
      <div class="flex-1 flex flex-col">
        <div class="flex-1 menu-container">
          <router-view />
        </div>
      </div>
    </main>
    <!-- <Footer theme="light"/> -->
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
import { categoryService } from '@/services/category-service'

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

onMounted(async () => {
  if (authStore.isAuthenticated) {
    taskStore.fetchTasks()
  }
  const response = await categoryService.getAll()
  if (response.success) {
    const categoryStatus = response.data.some(category => category.categoryName === 'status')
    const categoryPriority = response.data.some(category => category.categoryName === 'priority')
    if (!categoryStatus) {
      await categoryService.create({
        "categoryName": "status",
        "values": [
          {
            "value": "Completed",
            "sortOrder": 0
          },
          {
            "value": "In Progress",
            "sortOrder": 1
          },
          {
            "value": "Not Started",
            "sortOrder": 2
          }
        ]
      })
    }
    if (!categoryPriority) {
      await categoryService.create({
        "categoryName": "priority",
        "values": [
          {
            "value": "LOW",
            "sortOrder": 0
          },
          {
            "value": "MEDIUM",
            "sortOrder": 1
          },
          {
            "value": "HIGH",
            "sortOrder": 2
          }
        ]
      })
    }
  }
})
</script>

<style scoped>
.menu-container {
  padding-top: 70px;
  padding-bottom: 30px;
}
</style>