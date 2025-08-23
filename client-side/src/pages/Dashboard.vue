<template>
  <div class="h-[calc(100dvh-220px)] flex flex-col px-16 overflow-hidden">
    <!-- Header Section -->
    <div class="flex justify-between items-center mb-5">
      <div class="flex items-center">
        <h1 class="text-[36px] font-semibold mr-3">Welcome back, {{ welcomeLastName }}</h1>
        <img src="../assets/icons/hand-wave.svg" alt="hand-wave" class="w-10 h-10">
      </div>

      <div class="flex items-center space-x-4">
        <!-- User Avatars -->
        <div class="flex space-x-1">
          <div v-for="(member, index) in members.slice(0, 3)" :key="index" class="w-10 h-10 rounded-md overflow-hidden">
            <img :src="member?.avatarUrl" :alt="member?.name" class="w-full h-full object-cover">
          </div>
          <div v-if="members.length > 3"
            class="relative w-10 h-10 rounded-md overflow-hidden flex items-center justify-center text-sm font-medium text-gray-600">
            <img
              :src="member?.avatarUrl"
              alt="plus" class="w-full h-full object-cover" :class="{ 'blur-[1px]': members.length > 4 }">
            <span
              v-if="members.length > 4"
              class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 rounded-full text-sm font-medium text-white">
              +{{ members.length - 4 }}
            </span>
          </div>
        </div>

        <!-- Invite Button -->
        <button
          class="bg-white border border-[#FF6767] text-[#FF6767] px-4 py-2 rounded-lg flex items-center space-x-2 transition-colors">
          <img src="../assets/icons/invite-user.svg" alt="invite-user" class="w-4 h-4">
          <span>Invite</span>
        </button>
      </div>
    </div>

    <!-- Main Content -->
    <div class="flex-1 flex gap-6 border border-[#A1A3ABA1] rounded-xl p-6 overflow-hidden">

      <!-- Left Column - To-Do Section -->
      <div class="h-full flex-1 flex flex-col w-[55%] shadow-lg rounded-xl overflow-hidden p-6 pr-2">
        <div class="flex justify-between items-center mb-6 flex-shrink-0 pr-4">
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
              <img src="../assets/icons/pending.svg" alt="pending" class="w-4 h-4">
            </div>
            <div>
              <h2 class="text-xl font-semibold text-[#FF6767]">To-Do</h2>
              <div class="text-sm">
                <span class="text-[#000] mr-2">{{ todayDate }}</span>
                <span class="text-gray-500">.Today</span>
              </div>
            </div>
          </div>
          <button
            class="hover:bg-[#b4b4b475] hover:text-white px-4 py-2 rounded-lg flex items-center space-x-2 transition-colors">
            <svg class="w-3 h-3" fill="none" stroke="#F24E1E" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
            </svg>
            <span class="text-[12px] text-[#A1A3AB]">Add task</span>
          </button>
        </div>

        <!-- Task Cards -->
        <div class="space-y-4 flex-1 overflow-y-auto min-h-0 pr-4 py-2">
          <TaskCard v-for="task in tasks" :key="task.id" :task="task" @view="view" @edit="edit" />
        </div>
      </div>

      <!-- Right Column -->
      <div class="h-full flex-1 flex flex-col space-y-6 w-[45%]">
        <!-- Task Status Section -->
        <div class="shadow-lg rounded-xl p-6">
          <div class="flex items-center space-x-3 mb-6">
            <div class="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center">
              <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z">
                </path>
              </svg>
            </div>
            <h2 class="text-xl font-semibold text-[#FF6767]">Task Status</h2>
          </div>

          <!-- Progress Indicators -->
          <div class="grid grid-cols-3 gap-4 mb-6">
            <ProgressIndicator :percent="84" status="completed" />
            <ProgressIndicator :percent="46" status="in-progress" />
            <ProgressIndicator :percent="13" status="not-started" />
          </div>

          <!-- Legend -->
          <div class="flex justify-center space-x-6 text-sm">
            <div class="flex items-center space-x-2">
              <div class="w-2 h-2 bg-green-500 rounded-full"></div>
              <span class="text-gray-600">Completed</span>
            </div>
            <div class="flex items-center space-x-2">
              <div class="w-2 h-2 bg-blue-500 rounded-full"></div>
              <span class="text-gray-600">In Progress</span>
            </div>
            <div class="flex items-center space-x-2">
              <div class="w-2 h-2 bg-primary rounded-full"></div>
              <span class="text-gray-600">Not Started</span>
            </div>
          </div>
        </div>

        <!-- Completed Tasks Section -->
        <div class="flex-1 flex flex-col h-full min-h-0 rounded-xl shadow-lg p-6 pr-2">
          <div class="flex items-center space-x-3 mb-6 pr-4">
            <div class="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center">
              <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
            </div>
            <h2 class="text-xl font-semibold text-[]">Completed Task</h2>
          </div>

          <!-- Completed Task Cards -->
          <div class="space-y-4 overflow-y-auto min-h-0 pr-4 py-2">
            <TaskCardCompleted v-for="task in completedTasks" :key="task.id" :task="task" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TaskCard from '../components/TaskCard.vue'
import TaskCardCompleted from '../components/TaskCardCompleted.vue'
import ProgressIndicator from '../components/ProgressIndicator.vue'
import { useAuthStore } from '@/stores/auth'
import { taskService } from '../services/task-service'
import { getAllMembers } from '../services/member-service'
export default {
  components: { TaskCard, TaskCardCompleted, ProgressIndicator },
  data() {
    return {
      todayDate: (() => {
        const date = new Date();
        const day = date.getDate();
        const month = date.toLocaleString('en-US', { month: 'long' });
        return `${day} ${month}`;
      })(),
      members: [],
      tasks: [],
    }
  },
  created() {
    taskService.getAll().then(res => {
      this.tasks = res.data
    })
    getAllMembers().then(res => {
      this.members = res.data
    })
  },
  computed: {
    completedTasks: () => {
      return taskService.getAll().then(res => {
        return res.data.filter(t => t.status === 'completed')
      })
    },
    welcomeLastName() {
      const auth = useAuthStore()
      const last = (auth.profile?.lastName || '').trim()
      // Fallbacks if profile is not yet loaded
      return last || auth.user?.username || auth.user?.name || 'User'
    },
  },
  methods: {
    view(id) { this.$router.push(`/tasks/${id}`) }, edit(id) { this.$router.push(`/tasks/${id}?edit=1`) }
  }
}
</script>
