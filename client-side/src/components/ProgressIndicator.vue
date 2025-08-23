<template>
  <div class="text-center">
    <div class="relative w-16 h-16 mx-auto mb-2">
      <svg class="w-16 h-16" viewBox="0 0 36 36">
        <path class="text-gray-200" stroke="currentColor" stroke-width="2" fill="none"
          d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831" />
        <path :class="progressColor" stroke="currentColor" stroke-width="2" fill="none"
          :stroke-dasharray="`${percent}, 100`"
          d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831" />
      </svg>
      <div class="absolute inset-0 flex items-center justify-center">
        <span :class="`text-sm font-semibold ${textColor}`">{{ percent }}%</span>
      </div>
    </div>
    <p :class="`text-sm ${textColor} font-medium`">{{ statusText }}</p>
  </div>
</template>

<script>
export default {
  name: 'ProgressIndicator',
  props: {
    percent: {
      type: Number,
      required: true,
      validator: (value) => value >= 0 && value <= 100
    },
    status: {
      type: String,
      default: 'completed',
      validator: (value) => ['completed', 'in-progress', 'not-started'].includes(value)
    }
  },
  computed: {
    progressColor() {
      const colors = {
        'completed': 'text-green-500',
        'in-progress': 'text-blue-500',
        'not-started': 'text-red-500'
      }
      return colors[this.status]
    },
    textColor() {
      const colors = {
        'completed': 'text-green-600',
        'in-progress': 'text-blue-600',
        'not-started': 'text-red-600'
      }
      return colors[this.status]
    },
    statusText() {
      const texts = {
        'completed': 'Completed',
        'in-progress': 'In Progress',
        'not-started': 'Not Started'
      }
      return texts[this.status]
    }
  }
}
</script>
