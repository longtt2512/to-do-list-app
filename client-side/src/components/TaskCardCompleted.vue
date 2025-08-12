<template>
  <div
    class="relative bg-white border border-gray-200 rounded-lg px-[40px] py-[15px] hover:shadow-md transition-shadow">
    <!-- Status Indicator -->
    <div class="w-3 h-3 rounded-full border-2 mt-2 absolute top-3 left-3" :class="getStatusClasses(task.status)">
    </div>

    <button
      class="absolute top-1 right-3 w-6 h-6 flex items-center justify-center hover:bg-gray-100 rounded-full transition-colors">
      <img src="../assets/icons/3-dot.svg" alt="3-dot" class="w-4 h-4">
    </button>
    <!-- Top Section -->
    <div class="flex items-start justify-between mb-4 gap-3">
      <!-- Task Details -->
      <div class="flex-1">
        <h3 class="font-bold text-[16px]">{{ task.title }}</h3>
        <p class="text-[14px] text-[#747474] leading-relaxed">{{ task.description || 'No description available' }}</p>
      </div>
      <img :src="task.image || '/src/assets/avatar1.png'" :alt="task.title" class="w-[88px] h-[88px] rounded-xl overflow-hidden object-cover mt-5">
    </div>

    <!-- Bottom Section - Metadata -->
    <div class="flex flex-col space-y-2 text-[10px]">
      <div class="flex items-center gap-1">
        <span>Status:</span>
        <span :class="getStatusTextClasses(task.status)">
          {{ getStatusText(task.status) }}
        </span>
      </div>

      <span>Completed 2 days ago.</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TaskCard',
  props: ['task'],
  methods: {
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
