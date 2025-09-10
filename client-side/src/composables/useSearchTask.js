import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useTaskStore } from '@/stores/task'
import { useRouter } from 'vue-router'

export function useSearchTask() {
  const taskStore = useTaskStore()
  const router = useRouter()
  
  // State
  const searchQuery = ref('')
  const showDropdown = ref(false)
  const searchTimeout = ref(null)
  const searchInput = ref(null)
  
  // Computed
  const tasks = computed(() => taskStore.tasks)
  
  const filteredTasks = computed(() => {
    if (!searchQuery.value.trim()) {
      return []
    }
    
    const query = searchQuery.value.toLowerCase().trim()
    return tasks.value.filter(task => 
      task.title && task.title.toLowerCase().includes(query)
    ).slice(0, 10)
  })
  
  // Methods
  const getTaskStatus = (task) => {
    const statusSelection = task.selections?.find(selection => 
      selection.categoryName === 'status'
    )
    return statusSelection ? statusSelection.value : 'Unknown'
  }
  
  const getTaskPriority = (task) => {
    const prioritySelection = task.selections?.find(selection => 
      selection.categoryName === 'priority'
    )
    return prioritySelection ? prioritySelection.value : 'Unknown'
  }
  
  const handleInputFocus = () => {
    if (searchQuery.value.trim()) {
      showDropdown.value = true
    }
  }
  
  const handleInputBlur = () => {
    setTimeout(() => {
      showDropdown.value = false
    }, 200)
  }
  
  const handleInputChange = () => {
    if (searchTimeout.value) {
      clearTimeout(searchTimeout.value)
    }
    
    searchTimeout.value = setTimeout(() => {
      if (searchQuery.value.trim()) {
        showDropdown.value = true
      } else {
        showDropdown.value = false
      }
    }, 300)
  }
  
  const handleInputClick = () => {
    if (searchQuery.value.trim()) {
      showDropdown.value = true
    }
  }
  
  const selectTask = (task) => {
    searchQuery.value = ''
    showDropdown.value = false
    
    // Navigate to task detail page
    router.push({
      name: 'tasks',
      query: {
        task_id: task.id
      }
    })
  }
  
  const handleSearch = () => {
    if (searchQuery.value.trim()) {
      // Nếu có kết quả, chọn task đầu tiên
      if (filteredTasks.value.length > 0) {
        selectTask(filteredTasks.value[0])
      }
    }
  }
  
  const closeDropdown = () => {
    showDropdown.value = false
  }
  
  const clearSearch = () => {
    searchQuery.value = ''
    showDropdown.value = false
  }
  
  // Click outside handler
  const handleClickOutside = (e) => {
    if (!searchInput.value?.contains(e.target)) {
      showDropdown.value = false
    }
  }
  
  // Lifecycle
  onMounted(() => {
    document.addEventListener('click', handleClickOutside)
  })
  
  onUnmounted(() => {
    if (searchTimeout.value) {
      clearTimeout(searchTimeout.value)
    }
    document.removeEventListener('click', handleClickOutside)
  })
  
  return {
    // State
    searchQuery,
    showDropdown,
    searchInput,
    
    // Computed
    tasks,
    filteredTasks,
    
    // Methods
    getTaskStatus,
    getTaskPriority,
    handleInputFocus,
    handleInputBlur,
    handleInputChange,
    handleInputClick,
    selectTask,
    handleSearch,
    closeDropdown,
    clearSearch
  }
}
