import { ref, reactive } from 'vue'
import { categoryService } from '@/services/category-service'
import { taskService } from '@/services/task-service'

// Global state - shared across all instances
const globalState = reactive({
    showModal: false,
    selectedTaskId: null,
    selectedTask: null,
    categories: [],
    isLoading: false,
    isLoadingTask: false,
    errorMessage: '',
    showToast: false,
    toastMessage: '',
    form: {
        title: '',
        startDate: '',
        description: '',
        selections: {}
    },
    uploadedImage: null
})

export function useTaskModal() {
    const fileInput = ref(null)

    // Computed properties
    const isEditMode = () => !!globalState.selectedTask || !!globalState.selectedTaskId

    // Methods
    const openModal = (taskOrId = null) => {
        if (typeof taskOrId === 'object' && taskOrId !== null) {
            globalState.selectedTask = taskOrId
            globalState.selectedTaskId = null
        } else if (taskOrId) {
            globalState.selectedTaskId = taskOrId
            globalState.selectedTask = null
        } else {
            globalState.selectedTask = null
            globalState.selectedTaskId = null
        }
        globalState.showModal = true
    }

    const closeModal = () => {
        resetForm()
        globalState.showModal = false
        globalState.selectedTask = null
        globalState.selectedTaskId = null
    }

    const resetForm = () => {
        Object.assign(globalState.form, { 
            title: '', 
            startDate: '', 
            description: '', 
            selections: {} 
        })
        globalState.uploadedImage = null
        globalState.errorMessage = ''
    }

    const loadCategories = async () => {
        try {
            const response = await categoryService.getAll()
            if (response.success) {
                globalState.categories = await Promise.all(
                    response.data.map(async (category) => {
                        const itemsResponse = await categoryService.getCategoryById(category.id)
                        return {
                            ...category,
                            items: itemsResponse.success ? itemsResponse.data : []
                        }
                    })
                )
            }
        } catch (err) {
            console.error('Error loading categories:', err)
        }
    }

    const populateFormWithTask = (task) => {
        globalState.form.title = task.title || ''
        globalState.form.startDate = task.startDate || ''
        globalState.form.description = task.description || ''
        
        // Reset selections first
        globalState.form.selections = {}
        
        // Populate selections based on task data
        if (task.selections && Array.isArray(task.selections)) {
            task.selections.forEach(selection => {
                globalState.form.selections[selection.categoryId] = selection.valueId
            })
        }
        
        // Handle existing image
        globalState.uploadedImage = task.imageKey || null
    }

    const fetchAndPopulateTask = async (taskId) => {
        try {
            globalState.isLoadingTask = true
            globalState.errorMessage = ''
            
            const response = await taskService.getTaskById(taskId)
            if (response.success) {
                populateFormWithTask(response.data)
            } else {
                globalState.errorMessage = response.error || 'Failed to load task data'
            }
        } catch (err) {
            console.error('Error fetching task:', err)
            globalState.errorMessage = 'An error occurred while loading task data'
        } finally {
            globalState.isLoadingTask = false
        }
    }

    const showToastNotification = (message) => {
        globalState.toastMessage = message
        globalState.showToast = true
        
        setTimeout(() => {
            hideToast()
        }, 5000)
    }

    const hideToast = () => {
        globalState.showToast = false
        globalState.toastMessage = ''
    }

    const handleFileSelect = (e) => {
        const file = e.target.files[0]
        if (file && file.type.startsWith('image/')) {
            const reader = new FileReader()
            reader.onload = (ev) => (globalState.uploadedImage = ev.target.result)
            reader.readAsDataURL(file)
        }
    }

    const handleFileDrop = (e) => {
        const file = e.dataTransfer.files[0]
        if (file && file.type.startsWith('image/')) {
            const reader = new FileReader()
            reader.onload = (ev) => (globalState.uploadedImage = ev.target.result)
            reader.readAsDataURL(file)
        }
    }

    const removeImage = () => {
        globalState.uploadedImage = null
        if (fileInput.value) fileInput.value.value = ''
    }

    const triggerFileInput = () => fileInput.value?.click()

    return {
        // State
        state: globalState,
        fileInput,
        
        // Computed
        isEditMode,
        
        // Methods
        openModal,
        closeModal,
        resetForm,
        loadCategories,
        populateFormWithTask,
        fetchAndPopulateTask,
        showToastNotification,
        hideToast,
        handleFileSelect,
        handleFileDrop,
        removeImage,
        triggerFileInput
    }
}

