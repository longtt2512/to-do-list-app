<template>
  <div class="h-[calc(100dvh-220px)] flex gap-6 px-16 overflow-hidden">
    <div class="h-full flex-1 flex flex-col w-[55%] border border-[#A1A3ABA1] rounded-xl overflow-hidden p-6 pr-2">
      <div class="flex items-center space-x-3 mb-3">
        <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
          <img src="../assets/icons/pending.svg" alt="pending" class="w-4 h-4">
        </div>
        <div>
          <h2 class="text-xl font-semibold text-[#FF6767]">My Tasks</h2>
          <div class="text-sm">
            <span class="text-[#000] mr-2">{{ todayDate }}</span>
            <span class="text-gray-500">.Today</span>
          </div>
        </div>
      </div>

      <!-- Task Cards -->
      <div class="space-y-4 flex-1 overflow-y-auto min-h-0 pr-4 py-2">
        <!-- Loading State -->
        <div v-if="isLoading" class="flex justify-center items-center h-32">
          <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-[#FF6767]"></div>
        </div>
        
        <!-- Error State -->
        <div v-else-if="hasError" class="flex flex-col items-center justify-center h-32 text-red-500">
          <p class="text-sm">{{ errorMessage }}</p>
          <button @click="fetchTasks" class="mt-2 px-4 py-2 bg-red-100 text-red-600 rounded-lg hover:bg-red-200 transition-colors">
            Thử lại
          </button>
        </div>
        
        <!-- Tasks List -->
        <template v-else-if="tasks.length > 0">
          <TaskCard v-for="task in tasks" :key="task.id" :task="task" @click="handleClickTask(task)" @deleteSuccess="handleDeleteSuccess" />
        </template>
        
        <!-- Empty State -->
        <div v-else class="flex flex-col items-center justify-center h-32 text-gray-500">
          <p class="text-sm">Chưa có task nào</p>
        </div>
      </div>
    </div>

    <!-- Right Column -->
    <div
      class="h-full flex-1 flex flex-col w-[45%] border border-[#A1A3ABA1] rounded-xl overflow-hidden p-6 pr-2">
      <!-- Task Detail -->
      <div v-if="task" class="flex gap-4 mb-3">
        <img :src="task.image || '/src/assets/avatar.png'" :alt="task.title" class="w-20 h-20 rounded-lg object-cover border border-gray-200" />
        <div class="flex flex-col justify-center">
          <h3 class="font-semibold text-[16px] mb-1">{{ task.title }}</h3>
          <div class="text-[13px] mb-1">
            <span class="font-medium">Priority:</span>
            <span class="text-[#FF6767] font-semibold ml-1">{{ getPriorityText(priorityTask) }}</span>
          </div>
          <div class="text-[13px] mb-1">
            <span class="font-medium">Status:</span>
            <span class="text-[#FF6767] font-semibold ml-1">{{ getStatusText(statusTask) }}</span>
          </div>
          <div class="text-[11px] text-gray-400">Created on: {{ formatDate(task.createdAt) }}</div>
        </div>
      </div>
      
      <!-- No Task Selected -->
      <div v-else class="flex flex-col items-center justify-center h-32 text-gray-500">
        <p class="text-sm">Chọn một task để xem chi tiết</p>
      </div>
      <div v-if="task" class="text-[13px] leading-relaxed text-[#222] flex-1">
        <div class="mb-2">
          <span class="font-semibold">Task Title:</span> {{ task.title }}
        </div>
        <div class="mb-2">
          <span class="font-semibold">Description:</span>
          {{ task.description || 'No description available' }}
        </div>
        <div class="mb-2">
          <span class="font-semibold">Priority:</span>
          {{ getPriorityText(priorityTask) }}
        </div>
        <div class="mb-2">
          <span class="font-semibold">Status:</span>
          {{ getStatusText(statusTask) }}
        </div>
        <div class="mb-2">
          <span class="font-semibold">Created At:</span>
          {{ formatDate(task.createdAt) }}
        </div>
        <div v-if="task.updatedAt" class="mb-2">
          <span class="font-semibold">Updated At:</span>
          {{ formatDate(task.updatedAt) }}
        </div>
      </div>
      <!-- <div v-if="task" class="flex justify-end gap-2 mt-4">
        <button
          class="w-9 h-9 flex items-center justify-center bg-[#FF6767] rounded-lg hover:bg-[#e05555] transition-colors"
          @click="editTask(task)">
          <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-white" fill="none" viewBox="0 0 24 24"
            stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
          </svg>
        </button>
        <button
          class="w-9 h-9 flex items-center justify-center bg-red-600 rounded-lg hover:bg-red-700 transition-colors"
          @click="deleteTask(task)">
          <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-white" fill="none" viewBox="0 0 24 24"
            stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
          </svg>
        </button>
      </div> -->
    </div>
  </div>
</template>

<script>
import TaskCard from '../components/TaskCard.vue'
import TaskCardCompleted from '../components/TaskCardCompleted.vue'
import { useTaskStore } from '@/stores/task'

export default {
  components: { TaskCard, TaskCardCompleted },
  setup() {
    const taskStore = useTaskStore()
    
    return {
      taskStore
    }
  },
  data() {
    return {
      selectedTask: null,
      todayDate: (() => {
        const date = new Date();
        const day = date.getDate();
        const month = date.toLocaleString('en-US', { month: 'long' });
        return `${day} ${month}`;
      })(),
    }
  },
  created() {
    this.fetchTasks()
  },
  computed: {
    // Sử dụng store getters
    tasks() {
      return this.taskStore.allTasks
    },
    task() {
      return this.selectedTask || (this.tasks.length > 0 ? this.tasks[0] : null)
    },
    isLoading() {
      return this.taskStore.isLoading
    },
    hasError() {
      return this.taskStore.hasError
    },
    errorMessage() {
      return this.taskStore.errorMessage
    },
    statusTask() {
      const selectionsStatus = this.selectedTask ? this.selectedTask.selections.find(selection => selection.categoryName === 'status') : '';
      return selectionsStatus ? selectionsStatus.value : '';
    },
    priorityTask() {
      const selectionsPriority = this.selectedTask ? this.selectedTask.selections.find(selection => selection.categoryName === 'priority') : '';
      return selectionsPriority ? selectionsPriority.value : '';
    }
  },
  methods: {
    async fetchTasks() {
      if (this.tasks.length > 0 && !this.selectedTask) {
        this.selectedTask = this.tasks[0]
      }
    },
    handleClickTask(dataTask) {
      this.selectedTask = dataTask
    },
    editTask(task) {
      console.log();
      
      // Navigate to edit page
      this.$router.push(`/tasks/${task.id}?edit=1`)
    },
    async deleteTask(task) {
      if (confirm(`Bạn có chắc chắn muốn xóa task '${task.title}'?`)) {
        try {
          const success = await this.taskStore.deleteTask(task.id)
          if (success) {
            // Clear selected task if it was deleted
            if (this.selectedTask && this.selectedTask.id === task.id) {
              this.selectedTask = this.tasks.length > 0 ? this.tasks[0] : null
            }
            console.log('Task deleted successfully')
          } else {
            alert('Có lỗi xảy ra khi xóa task')
          }
        } catch (error) {
          console.error('Error deleting task:', error)
          alert('Có lỗi xảy ra khi xóa task')
        }
      }
    },
    handleDeleteSuccess() {
      this.selectedTask = null
      setTimeout(() => {
        this.fetchTasks()
      }, 1000)
    },
    formatDate(dateString) {
      if (!dateString) return 'N/A';
      const date = new Date(dateString);
      return date.toLocaleDateString('en-GB', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      });
    },
    getStatusText(status) {
      const statusMap = {
        'completed': 'Completed',
        'in-progress': 'In Progress',
        'not-started': 'Not Started',
        'pending': 'Pending'
      };
      return statusMap[status] || 'Unknown';
    },
    getPriorityText(priority) {
      const priorityMap = {
        'HIGH': 'High',
        'MEDIUM': 'Medium',
        'LOW': 'Low'
      };
      return priorityMap[priority] || 'Medium';
    },
  }
}
</script>
