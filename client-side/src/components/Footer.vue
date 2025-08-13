<template>
  <footer class="bg-white py-8 px-4 border-t border-gray-200 mt-auto">
    <div class="max-w-4xl mx-auto">
      <div class="text-center">
        <h3 class="text-lg font-semibold text-gray-800 mb-4">To-Do List App</h3>
        <p class="text-gray-600 text-sm mb-4">
          A simple and efficient task management application to help you organize your daily activities and boost productivity.
        </p>
        <div class="flex justify-center space-x-6 text-sm">
          <router-link 
            to="/about" 
            class="text-[#FF6767] hover:text-red-500 font-medium transition-colors"
          >
            About Us
          </router-link>
          <span class="text-gray-400">|</span>
          <span class="text-gray-500">Contact: support@todoapp.com</span>
          <span class="text-gray-400">|</span>
          <span class="text-gray-500">© 2024 To-Do List App</span>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer'
}
</script>