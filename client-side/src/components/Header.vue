<template>
  <header class="bg-header h-header shadow-sm w-full">
    <div class="px-16 h-full flex items-center justify-between">
      <div class="flex items-center">
        <h1 class="text-3xl font-bold">
          <span class="text-primary">To</span><span class="text-black">-</span><span class="text-black">Do</span>
        </h1>
      </div>
      
      <div class="relative flex items-center bg-search rounded-lg px-4 pr-0 shadow-sm h-[36px] w-full max-w-[700px] mx-8">
        <input 
          ref="searchInput"
          v-model="searchQuery"
          type="text" 
          placeholder="Search your task here…" 
          class="bg-transparent outline-none text-sm text-gray-700 placeholder-gray-400 flex-1"
          @focus="handleInputFocus"
          @blur="handleInputBlur"
          @input="handleInputChange"
          @click="handleInputClick"
        />
        <IconButton icon="search" alt="Search" @click="handleSearch" />
        
        <!-- Dropdown Search Results -->
        <div 
          v-if="showDropdown && filteredTasks.length > 0" 
          class="absolute top-full left-0 right-0 mt-1 bg-white rounded-lg shadow-lg border border-gray-200 z-50 max-h-80 overflow-y-auto"
        >
          <div class="p-2">
            <div class="text-xs text-gray-500 mb-2 px-2">
              {{ filteredTasks.length }} task(s) found
            </div>
            <div 
              v-for="task in filteredTasks" 
              :key="task.id"
              class="px-3 py-2 hover:bg-gray-100 rounded-md cursor-pointer transition-colors"
              @click="selectTask(task)"
            >
              <div class="font-medium text-sm text-gray-900 truncate">
                {{ task.title }}
              </div>
              <div class="text-xs text-gray-500 mt-1">
                {{ getTaskStatus(task) }} • {{ getTaskPriority(task) }}
              </div>
            </div>
          </div>
        </div>
        
        <!-- No Results -->
        <div 
          v-if="showDropdown && searchQuery && filteredTasks.length === 0" 
          class="absolute top-full left-0 right-0 mt-1 bg-white rounded-lg shadow-lg border border-gray-200 z-50"
        >
          <div class="p-4 text-center text-gray-500 text-sm">
            No tasks found for "{{ searchQuery }}"
          </div>
        </div>
      </div>
      
      <div class="flex items-center gap-4">
        <!-- <IconButton icon="notification" alt="Notifications" @click="handleNotifications" />
        <IconButton icon="calendar" alt="Calendar" @click="handleCalendar" />
        <span class="text-sm text-blue-500">Tuesday 20/06/2023</span> -->
      </div>
    </div>
  </header>
</template>

<script>
import IconButton from './IconButton.vue'
import { useSearchTask } from '@/composables/useSearchTask'

export default {
  name: 'Header',
  components: {
    IconButton
  },
  setup() {
    const searchTask = useSearchTask()
    return {
      ...searchTask
    }
  },
  methods: {
    handleNotifications() {
      console.log('Notifications clicked')
    },
    handleCalendar() {
      console.log('Calendar clicked')
    }
  }
}
</script>
