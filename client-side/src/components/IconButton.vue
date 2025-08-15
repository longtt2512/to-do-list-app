<template>
  <button 
    class="min-w-[36px] min-h-[36px] max-w-full max-h-full bg-primary rounded-lg flex items-center justify-center border-2 border-gray-300 hover:opacity-80 transition-opacity"
    @click="$emit('click')"
  >
    <img 
      :src="iconSrc" 
      :alt="iconAlt"
      class="w-4 h-4"
    />
  </button>
</template>

<script>
export default {
  name: 'IconButton',
  props: {
    icon: {
      type: String,
      required: true,
    },
    alt: {
      type: String,
      default: ''
    }
  },
  computed: {
    iconSrc() {
      return `/src/assets/icons/${this.icon}.svg`
    },
    iconAlt() {
      return this.alt || `${this.icon} icon`
    }
  },
  emits: ['click']
}
</script>