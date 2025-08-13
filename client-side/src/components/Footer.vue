<template>
  <footer class="py-3 px-4 mt-auto">
    <div class="max-w-6xl mx-auto text-center">
      <p :class="[textColor, 'text-xs mb-2']">
        A simple and efficient task management application to help you organize your daily activities and boost productivity.
      </p>
      <div :class="['flex justify-center items-center space-x-2 text-xs', textColor]">
        <router-link 
          to="/about" 
          :class="[linkColor, 'transition-colors']"
        >
          About Us
        </router-link>
        <span :class="separatorColor">|</span>
        <span>Contact: support@todoapp.com</span>
        <span :class="separatorColor">|</span>
        <span>© 2024 To-Do List App</span>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer',
  props: {
    theme: {
      type: String,
      default: 'light' // 'light' for MainLayout, 'dark' for AuthLayout
    }
  },
  computed: {
    textColor() {
      return this.theme === 'dark' ? 'text-white' : 'text-gray-600'
    },
    linkColor() {
      return this.theme === 'dark' 
        ? 'text-white hover:text-gray-200' 
        : 'text-[#FF6767] hover:text-red-500'
    },
    separatorColor() {
      return this.theme === 'dark' ? 'text-white/70' : 'text-gray-400'
    }
  }
}
</script>