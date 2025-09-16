<template>
  <div
    class="bg-white relative border border-[#A1A3AB] rounded-xl px-[40px] py-[10px] hover:shadow-md transition-shadow">
    <!-- Status Indicator -->
    <div class="w-4 h-4 rounded-full border-2 mt-2 absolute top-2 left-3" :class="getStatusBorderClasses(statusTask)">
    </div>

    <!-- Three-dot menu button -->
    <div class="absolute top-1 right-3 w-6 h-6" @click.stop>
      <button @click="toggleDropdown"
        class="w-6 h-6 flex items-center justify-center hover:bg-gray-100 rounded-full transition-colors"
        ref="menuButton">
        <img src="../assets/icons/3-dot.svg" alt="3-dot" class="w-4 h-4">
      </button>

      <!-- Dropdown menu -->
      <div v-if="showDropdown"
        class="absolute right-0 mt-2 w-32 bg-white rounded-md shadow-lg border border-gray-200 py-1 z-10"
        ref="dropdownMenu">
        <button @click="handleEdit"
          class="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 flex items-center gap-2 transition-colors">
          <svg class="w-4 h-4 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z">
            </path>
          </svg>
          Edit
        </button>
        <button @click="handleDelete"
          class="w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-red-50 flex items-center gap-2 transition-colors">
          <svg class="w-4 h-4 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16">
            </path>
          </svg>
          Delete
        </button>
      </div>
    </div>
    <!-- Top Section -->
    <div class="flex items-start justify-between gap-2">
      <!-- Task Details -->
      <div class="flex-1 flex flex-col gap-2">
        <h3 class="font-bold text-[16px]">{{ task.title }}</h3>
        <p class="text-[14px] text-[#747474] leading-relaxed line-clamp-2"
          style="-webkit-line-clamp:2; display:-webkit-box; -webkit-box-orient:vertical; overflow:hidden;">
          {{ task.description || 'No description available' }}
        </p>
        <div class="flex items-center gap-1 text-[10px]">
          <span>Status:</span>
          <span class="font-medium" :class="getStatusClasses(statusTask)">
            {{ getStatusText(statusTask) }}
          </span>
        </div>

        <span class="text-[10px] text-[#A1A3AB]">Completed {{ getCompletedTimeAgo() }}</span>
      </div>
      <img :src="currentImageSrc" :alt="task.title" @error="handleImageError"
        class="w-[88px] h-[88px] rounded-xl overflow-hidden object-cover mt-5">
    </div>

    <!-- Loading overlay for delete -->
    <div v-if="loadingDelete"
      class="absolute top-0 left-0 w-full h-full bg-white bg-opacity-50 flex items-center justify-center">
      <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-500"></div>
    </div>
    <TaskModal v-if="showModalAddTask" v-model="showModalAddTask" :task="task" :id="taskSelected"
      @taskUpdated="handleTaskUpdated" />

    <Confirm v-if="showConfirm" v-model="showConfirm" title="Xóa Task"
      :message="`Bạn có chắc chắn muốn xóa task '${task.title}'?`" ok-text="Xóa" cancel-text="Hủy" @ok="confirmDelete"
      @cancel="cancelDelete" />
  </div>
</template>

<script>
import { useTaskStore } from '@/stores/task'
import TaskModal from './TaskModal.vue'
import Confirm from './Comfirm.vue'

export default {
  name: 'TaskCardCompleted',
  components: { TaskModal, Confirm },
  props: ['task'],
  emits: ['click', 'deleteSuccess'],
  setup() {
    const taskStore = useTaskStore()

    return {
      taskStore
    }
  },
  data() {
    return {
      showDropdown: false,
      showModalAddTask: false,
      taskSelected: null,
      showConfirm: false,
      loadingDelete: false,
      currentImageSrc: this.task.imageKey || '/src/assets/avatar.png',
    }
  },
  computed: {
    statusTask() {
      const selectionsStatus = this.task.selections.find(selection => selection.categoryName === 'status');
      return selectionsStatus ? selectionsStatus.value : '';
    },
  },
  mounted() {
    // Close dropdown when clicking outside
    document.addEventListener('click', this.handleClickOutside);
  },
  beforeUnmount() {
    document.removeEventListener('click', this.handleClickOutside);
  },
  methods: {
    toggleDropdown() {
      this.showDropdown = !this.showDropdown;
    },

    handleClickOutside(event) {
      if (this.$refs.menuButton && !this.$refs.menuButton.contains(event.target) &&
        this.$refs.dropdownMenu && !this.$refs.dropdownMenu.contains(event.target)) {
        this.showDropdown = false;
      }
    },

    async handleEdit() {
      this.showDropdown = false;
      // Mở modal edit với task hiện tại
      this.taskSelected = this.task.id;
      this.showModalAddTask = true;
    },

    handleDelete() {
      this.showDropdown = false;
      this.showConfirm = true;
    },

    async confirmDelete() {
      if (this.loadingDelete) {
        return;
      }
      this.loadingDelete = true;
      try {
        const success = await this.taskStore.deleteTask(this.task.id);
        if (success) {
          // Task đã được xóa khỏi store, UI sẽ tự động cập nhật
          console.log('Task deleted successfully');
          this.loadingDelete = false;
          this.$emit('deleteSuccess');
        } else {
          alert('Có lỗi xảy ra khi xóa task');
          this.loadingDelete = false;
        }
      } catch (error) {
        this.loadingDelete = false;
        console.error('Error deleting task:', error);
        alert('Có lỗi xảy ra khi xóa task');
      }
    },

    cancelDelete() {
      this.showConfirm = false;
    },

    // Xử lý khi task được cập nhật
    async handleTaskUpdated() {
      this.showModalAddTask = false;
      this.taskSelected = null;
    },
    getStatusBorderClasses(status) {
      const classes = {
        'not-started': 'border-gray-600',
        'in-progress': 'border-blue-600',
        'completed': 'border-green-600',
        'overdue': 'border-red-600',
        'pending': 'border-yellow-600'
      };
      return classes[status] || 'border-gray-600';
    },

    getStatusClasses(status) {
      const classes = {
        'completed': 'text-green-600',
        'in-progress': 'text-blue-600',
        'not-started': 'text-gray-600',
        'overdue': 'text-red-600',
        'pending': 'text-yellow-600'
      };
      return classes[status] || 'text-gray-600';
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
        'high': 'High',
        'medium': 'Moderate',
        'low': 'Low'
      };
      return priorityMap[priority] || 'Medium';
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

    handleImageError() {
      this.currentImageSrc = '/src/assets/avatar.png';
    },

    // Tính toán thời gian hoàn thành
    getCompletedTimeAgo() {
  if (!this.task.updatedAt) return 'recently';

  let utcString = this.task.updatedAt;

  // Nếu không có ký hiệu timezone ở cuối -> ép thành UTC
  if (!utcString.endsWith('Z') && !/[+-]\d{2}:?\d{2}$/.test(utcString)) {
    utcString += 'Z';
  }

  const updatedDate = new Date(utcString); // parse về local time
  const now = new Date();

  const diffTime = now.getTime() - updatedDate.getTime();
  const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

  if (diffDays === 0) {
    const diffHours = Math.floor(diffTime / (1000 * 60 * 60));
    if (diffHours === 0) {
      const diffMinutes = Math.floor(diffTime / (1000 * 60));
      return diffMinutes <= 1 ? 'just now' : `${diffMinutes} minutes ago`;
    }
    return diffHours === 1 ? '1 hour ago' : `${diffHours} hours ago`;
  } else if (diffDays === 1) {
    return '1 day ago';
  } else if (diffDays < 7) {
    return `${diffDays} days ago`;
  } else if (diffDays < 30) {
    const weeks = Math.floor(diffDays / 7);
    return weeks === 1 ? '1 week ago' : `${weeks} weeks ago`;
  } else if (diffDays < 365) {
    const months = Math.floor(diffDays / 30);
    return months === 1 ? '1 month ago' : `${months} months ago`;
  } else {
    const years = Math.floor(diffDays / 365);
    return years === 1 ? '1 year ago' : `${years} years ago`;
  }
}


  }
}
</script>
