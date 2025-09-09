import { defineStore } from 'pinia'
import { taskService } from '@/services/task-service.js'

export const useTaskStore = defineStore('task', {
  state: () => ({
    tasks: [],
    currentTask: null,
    loading: false,
    loadingDelete: false,
    error: null,
    lastFetch: null
  }),

  getters: {
    // Lấy tất cả tasks
    allTasks: (state) => state.tasks,
    
    // Lấy task theo ID
    getTaskById: (state) => (id) => {
      return state.tasks.find(task => task.id === id) || state.currentTask
    },
    
    // Lấy tasks theo trạng thái
    completedTasks: (state) => state.tasks.filter(task => {
      const selectionsStatus = task.selections.find(selection => selection.categoryName === 'status');
      return selectionsStatus ? selectionsStatus.value === 'completed' : false
    }),
    inProgressTasks: (state) => state.tasks.filter(task => {
      const selectionsStatus = task.selections.find(selection => selection.categoryName === 'status');
      return selectionsStatus ? selectionsStatus.value === 'in-progress' : false
    }),
    notStartedTasks: (state) => state.tasks.filter(task => {
      const selectionsStatus = task.selections.find(selection => selection.categoryName === 'status');
      return selectionsStatus ? selectionsStatus.value === 'not-started' : false
    }),
    
    // Lấy tasks theo priority
    highPriorityTasks: (state) => state.tasks.filter(task => task?.priority === 'HIGH'),
    mediumPriorityTasks: (state) => state.tasks.filter(task => task?.priority === 'MEDIUM'),
    lowPriorityTasks: (state) => state.tasks.filter(task => task?.priority === 'LOW'),
    
    // Kiểm tra loading state
    isLoading: (state) => state.loading,
    isLoadingDelete: (state) => state.loadingDelete,
    
    // Kiểm tra error state
    hasError: (state) => !!state.error,
    
    // Lấy error message
    errorMessage: (state) => state.error
  },

  actions: {
    // Lấy tất cả tasks từ API
    async fetchTasks() {
      this.loading = true
      this.error = null
      
      try {
        const result = await taskService.getAll()
        
        if (result.success) {
          this.tasks = result.data || []
          
          // Gọi chi tiết từng task để cập nhật thông tin đầy đủ
          await this.fetchTasksDetails()
          
          this.lastFetch = new Date()
        } else {
          this.error = result.error
          this.tasks = []
        }
      } catch (error) {
        this.error = error.message || 'Failed to fetch tasks'
        this.tasks = []
      } finally {
        this.loading = false
      }
    },

    // Lấy chi tiết từng task theo ID và cập nhật vào list
    async fetchTasksDetails() {
      if (!this.tasks || this.tasks.length === 0) return
      
      try {
        // Tạo array các promise để gọi song song
        const detailPromises = this.tasks.map(async (task) => {
          try {
            const detailResult = await taskService.getTaskById(task.id)
            if (detailResult.success && detailResult.data) {
              return { id: task.id, data: detailResult.data }
            }
            return { id: task.id, data: task } // Giữ nguyên nếu không lấy được detail
          } catch (error) {
            console.warn(`Failed to fetch detail for task ${task.id}:`, error)
            return { id: task.id, data: task } // Giữ nguyên nếu có lỗi
          }
        })
        
        // Chờ tất cả các promise hoàn thành
        const detailResults = await Promise.all(detailPromises)
        
        // Cập nhật lại list với thông tin chi tiết
        this.tasks = this.tasks.map(task => {
          const detailResult = detailResults.find(result => result.id === task.id)
          return detailResult ? detailResult.data : task
        })
        
      } catch (error) {
        console.warn('Failed to fetch tasks details:', error)
        // Không throw error để không ảnh hưởng đến việc hiển thị list cơ bản
      }
    },

    // Lấy task theo ID
    async fetchTaskById(id) {
      this.loading = true
      this.error = null
      
      try {
        const result = await taskService.getTaskById(id)
        
        if (result.success) {
          this.currentTask = result.data
          
          // Cập nhật task trong list nếu đã tồn tại
          const index = this.tasks.findIndex(task => task.id === id)
          if (index !== -1) {
            this.tasks[index] = result.data
          }
        } else {
          this.error = result.error
          this.currentTask = null
        }
      } catch (error) {
        this.error = error.message || 'Failed to fetch task'
        this.currentTask = null
      } finally {
        this.loading = false
      }
    },

    // Tạo task mới
    async createTask(taskData) {
      this.loading = true
      this.error = null
      
      try {
        const result = await taskService.createTask(taskData)
        
        if (result.success) {
          // Thêm task mới vào đầu list
          this.tasks.unshift(result.data)
          this.currentTask = result.data
          return result.data
        } else {
          this.error = result.error
          return null
        }
      } catch (error) {
        this.error = error.message || 'Failed to create task'
        return null
      } finally {
        this.loading = false
      }
    },

    // Cập nhật task
    async updateTask(id, taskData) {
      // Không cần gọi API updateTask, chỉ update trực tiếp taskData vào list

      try {
        // Cập nhật task trong list bằng taskData truyền vào
        const index = this.tasks.findIndex(task => task.id === id)
        if (index !== -1) {
          this.tasks[index] = { ...this.tasks[index], ...taskData }
        }

        // Cập nhật currentTask nếu đang xem task này
        if (this.currentTask && this.currentTask.id === id) {
          this.currentTask = { ...this.currentTask, ...taskData }
        }

        return { ...taskData, id }
      } catch (error) {
        this.error = error.message || 'Failed to update task'
        return null
      }
    },

    addTask(data) {
      this.tasks.unshift(data)
      this.currentTask = data
    },
    // Xóa task
    async deleteTask(id) {
      this.loadingDelete = true
      this.error = null
      
      try {
        const result = await taskService.deleteTask(id)
        
        if (result.success) {
          // Xóa task khỏi list
          this.tasks = this.tasks.filter(task => task.id !== id)
          
          // Clear currentTask nếu đang xem task bị xóa
          if (this.currentTask && this.currentTask.id === id) {
            this.currentTask = null
          }
          
          return true
        } else {
          this.error = result.error
          return false
        }
      } catch (error) {
        this.error = error.message || 'Failed to delete task'
        return false
      } finally {
        this.loadingDelete = false
      }
    },

    // Upload hình ảnh cho task
    async uploadTaskImage(taskId, file) {
      this.loading = true
      this.error = null
      
      try {
        const result = await taskService.uploadTaskImage(taskId, file)
        
        if (result.success) {
          // Cập nhật task với URL hình ảnh mới
          const index = this.tasks.findIndex(task => task.id === taskId)
          if (index !== -1) {
            this.tasks[index] = { ...this.tasks[index], ...result.data }
          }
          
          if (this.currentTask && this.currentTask.id === taskId) {
            this.currentTask = { ...this.currentTask, ...result.data }
          }
          
          return result.data
        } else {
          this.error = result.error
          return null
        }
      } catch (error) {
        this.error = error.message || 'Failed to upload image'
        return null
      } finally {
        this.loading = false
      }
    },

    // Refresh tasks (gọi lại API)
    async refreshTasks() {
      return await this.fetchTasks()
    },

    // Chỉ cập nhật chi tiết các tasks hiện có
    async refreshTasksDetails() {
      if (this.tasks.length === 0) return
      
      this.loading = true
      try {
        await this.fetchTasksDetails()
      } finally {
        this.loading = false
      }
    },

    // Clear error
    clearError() {
      this.error = null
    },

    // Clear current task
    clearCurrentTask() {
      this.currentTask = null
    },

    // Reset store về trạng thái ban đầu
    reset() {
      this.tasks = []
      this.currentTask = null
      this.loading = false
      this.error = null
      this.lastFetch = null
    }
  }
})

export default useTaskStore
