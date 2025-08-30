<template>
  <div
    class="bg-white relative border border-[#A1A3AB] rounded-xl px-[40px] py-[15px] hover:shadow-md transition-shadow">
    <!-- Status Indicator -->
    <div class="w-4 h-4 rounded-full border-2 mt-2 absolute top-2 left-3" :class="getStatusClasses(task.status)">
    </div>

    <!-- Three-dot menu button -->
    <div class="absolute top-1 right-3 w-6 h-6">
      <button 
        @click="toggleDropdown"
        class="w-6 h-6 flex items-center justify-center hover:bg-gray-100 rounded-full transition-colors"
        ref="menuButton"
      >
        <img src="../assets/icons/3-dot.svg" alt="3-dot" class="w-4 h-4">
      </button>
      
      <!-- Dropdown menu -->
      <div 
        v-if="showDropdown"
        class="absolute right-0 mt-2 w-32 bg-white rounded-md shadow-lg border border-gray-200 py-1 z-10"
        ref="dropdownMenu"
      >
        <button
          @click="handleEdit"
          class="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 flex items-center gap-2 transition-colors"
        >
          <svg class="w-4 h-4 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
          </svg>
          Edit
        </button>
        <button
          @click="handleDelete"
          class="w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-red-50 flex items-center gap-2 transition-colors"
        >
          <svg class="w-4 h-4 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
          </svg>
          Delete
        </button>
      </div>
    </div>
    <!-- Top Section -->
    <div class="flex items-start justify-between mb-1 gap-3">
      <!-- Task Details -->
      <div class="flex-1">
        <h3 class="font-bold text-[16px]">{{ task.title }}</h3>
        <p class="text-[14px] text-[#747474] leading-relaxed">{{ task.description || 'No description available' }}</p>
      </div>
      <img :src="task.image || '/src/assets/avatar.png'" :alt="task.title" class="w-[88px] h-[88px] rounded-xl overflow-hidden object-cover mt-5">
    </div>

    <!-- Bottom Section - Metadata -->
    <div class="flex items-center justify-between space-y-2 text-[10px]">
      <div class="flex items-center justify-between gap-1">
        <span>Priority:</span>
        <span class="text-[#42ADE2]">{{ getPriorityText(task.priority) }}</span>
      </div>

      <div class="flex items-center justify-between gap-1">
        <span>Status:</span>
        <span :class="getStatusTextClasses(task.status)">
          {{ getStatusText(task.status) }}
        </span>
      </div>

      <div class="flex items-center justify-between gap-1 text-[#A1A3AB]">
        <span>Created on:</span>
        <span>{{ formatDate(task.createdAt) }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TaskCard',
  props: ['task'],
  emits: ['edit', 'delete'],
  data() {
    return {
      showDropdown: false,
    };
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
    
    handleEdit() {
      this.showDropdown = false;
      this.$emit('edit', this.task);
    },

    handleDelete() {
      this.showDropdown = false;
      this.$emit('delete', this.task);
    },
    getStatusClasses(status) {
      const classes = {
        'completed': 'border-green-300',
        'in-progress': 'border-blue-300',
        'not-started': 'border-red-300',
        'pending': 'border-yellow-300'
      };
      return classes[status] || 'border-gray-300';
    },

    getStatusTextClasses(status) {
      const classes = {
        'completed': 'text-green-600',
        'in-progress': 'text-blue-600',
        'not-started': 'text-red-600',
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
    }
  }
}
</script>
