<template>
  <header class="bg-header h-header shadow-sm w-full">
    <div class="px-6 h-full flex items-center justify-between">
      <div class="flex items-center">
        <h1 class="text-xl font-bold">
          <span class="text-primary">To</span><span class="text-black">-Do</span>
        </h1>
      </div>
      
      <div class="flex items-center bg-search rounded-lg px-4 pr-0 shadow-sm h-[36px] w-full max-w-[700px] mx-8">
        <input 
          type="text" 
          placeholder="Search your task here…" 
          class="bg-transparent outline-none text-sm text-gray-700 placeholder-gray-400 flex-1"
        />
        <IconButton icon="search" alt="Search" @click="handleSearch" />
      </div>
      
      <div class="flex items-center gap-4">
        <IconButton icon="notification" alt="Notifications" @click="handleNotifications" />
        <IconButton icon="calendar" alt="Calendar" @click="handleCalendar" />
        <span class="text-sm text-blue-500">Tuesday 20/06/2023</span>
      </div>
    </div>
  </header>
</template>

<script>
import IconButton from './IconButton.vue'

export default {
  name: 'Header',
  components: {
    IconButton
  },
  methods: {
    handleSearch() {
      console.log('Search clicked')
    },
    handleNotifications() {
      console.log('Notifications clicked')
    },
    handleCalendar() {
      console.log('Calendar clicked')
    }
  }
}
</script>
