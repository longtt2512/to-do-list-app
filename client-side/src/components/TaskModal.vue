<template>
    <Teleport to="body">
        <transition name="fade">
            <div
                v-if="props.modelValue"
                class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4"
            >
                <div
                    class="bg-white rounded-lg shadow-2xl w-[70%] max-w-4xl mx-auto overflow-hidden border border-gray-200 h-[80vh] flex flex-col"
                >
                    <!-- Header -->
                    <div class="flex justify-between items-center px-6 py-4 border-b border-gray-200 bg-gray-50 flex-shrink-0">
                        <h2 class="text-lg font-semibold text-gray-900">{{ isEditMode() ? 'Edit Task' : 'Add New Task' }}</h2>
                        <button
                            @click="close"
                            class="text-gray-500 hover:text-gray-700 text-sm font-medium transition-colors"
                        >
                            ✕
                        </button>
                    </div>

                    <!-- Loading State for Task Data -->
                    <div v-if="state.isLoadingTask" class="p-6 flex-1 flex items-center justify-center">
                        <div class="text-center">
                            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-orange-500 mx-auto mb-2"></div>
                            <p class="text-sm text-gray-600">Loading task data...</p>
                        </div>
                    </div>

                    <!-- Form -->
                    <div v-else class="p-6 flex-1 overflow-y-auto">
                        <form @submit.prevent="handleSubmit" class="border border-gray-200 rounded-lg p-4 bg-gray-50 space-y-4">
                            <!-- Title & Date -->
                            <div>
                                <label for="title" class="block text-sm font-medium text-gray-700 mb-1">
                                    Title <span class="text-red-500">*</span>
                                </label>
                                <input
                                    id="title"
                                    v-model="state.form.title"
                                    type="text"
                                    class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-sm bg-white"
                                    required
                                />
                            </div>
                     
                            <div>
                                <label for="startDate" class="block text-sm font-medium text-gray-700 mb-1">
                                    Start Date <span class="text-red-500">*</span>
                                </label>
                                <input
                                    id="startDate"
                                    v-model="state.form.startDate"
                                    type="date"
                                    class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-sm bg-white"
                                    required
                                />
                            </div>
                        
                            <!-- Categories -->
                            <template v-for="category in state.categories" :key="category.id">
                                <div v-if="category && category.items && category.items.length > 0">
                                    <label class="block text-sm font-medium text-gray-700 mb-2">
                                        {{ category.categoryName }}
                                    </label>
                                    <div class="flex space-x-4 flex-wrap">
                                        <label 
                                            class="flex items-center cursor-pointer mb-2"
                                            v-for="item in category.items" 
                                            :key="item.id"
                                        >
                                            <input
                                                v-model="state.form.selections[category.id]"
                                                type="radio"
                                                :value="item.id"
                                                class="sr-only"
                                            />
                                            <div class="flex items-center">
                                                <div
                                                    class="w-4 h-4 rounded-full border-2 mr-2"
                                                    :class="state.form.selections[category.id] === item.id ? 'border-blue-500 bg-blue-500' : 'border-gray-300'"
                                                >
                                                    <div v-if="state.form.selections[category.id] === item.id" class="w-2 h-2 bg-white rounded-full mx-auto mt-0.5"></div>
                                                </div>
                                                <span class="text-sm text-gray-700">{{ item.value }}</span>
                                            </div>
                                        </label>
                                    </div>
                                </div>
                            </template>

                            <!-- Description & Upload -->
                            <div class="grid grid-cols-2 gap-4">
                                <div>
                                    <label for="description" class="block text-sm font-medium text-gray-700 mb-1">
                                        Description
                                    </label>
                                    <textarea
                                        id="description"
                                        v-model="state.form.description"
                                        rows="4"
                                        class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent resize-none text-sm bg-white h-[300px]"
                                        placeholder="Enter task description..."
                                    ></textarea>
                                </div>

                                <div>
                                    <label class="block text-sm font-medium text-gray-700 mb-1">
                                        Upload Image
                                    </label>
                                    <div
                                        @click="triggerFileInput"
                                        @dragover.prevent
                                        @drop.prevent="handleFileDrop"
                                        class="border-2 border-dashed border-gray-300 rounded-md text-center cursor-pointer hover:border-blue-400 transition-colors h-24 flex flex-col items-center justify-center bg-white"
                                    >
                                        <svg class="w-6 h-6 text-gray-400 mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path>
                                        </svg>
                                        <p class="text-xs text-gray-500 mb-1">Drag&Drop files</p>
                                        <button
                                            type="button"
                                            class="bg-gray-500 hover:bg-gray-600 text-white px-3 py-1 rounded text-xs font-medium transition-colors"
                                        >
                                            Browse
                                        </button>
                                    </div>
                                    <input
                                        ref="fileInput"
                                        type="file"
                                        accept="image/*"
                                        @change="handleFileSelect"
                                        class="hidden"
                                    />

                                    <div v-if="state.uploadedImage" class="mt-2">
                                        <img :src="state.uploadedImage" alt="Preview" class="w-full h-40 object-cover rounded border border-gray-200" />
                                        <button
                                            @click="removeImage"
                                            type="button"
                                            class="bg-red-500 hover:bg-red-600 text-white px-2 py-1 rounded text-xs font-medium transition-colors mt-1 w-full"
                                        >
                                            Remove
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <!-- Error message -->
                            <div v-if="state.errorMessage" class="bg-red-50 border border-red-200 text-red-700 px-3 py-2 rounded text-sm">
                                {{ state.errorMessage }}
                            </div>

                            <!-- Action buttons -->
                            <div class="flex justify-end space-x-3 pt-2">
                                <button
                                    type="button"
                                    @click="close"
                                    class="bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded text-sm font-medium transition-colors"
                                >
                                    Cancel
                                </button>
                                <button
                                    type="submit"
                                    :disabled="state.isLoading"
                                    :class="state.isLoading ? 'bg-gray-400 cursor-not-allowed' : 'bg-primary hover:bg-orange-600'"
                                    class="text-white px-4 py-2 rounded text-sm font-medium transition-colors"
                                >
                                    {{ state.isLoading ? (isEditMode() ? 'Updating...' : 'Creating...') : (isEditMode() ? 'Update Task' : 'Create Task') }}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </transition>

        <!-- Toast Notification -->
        <transition name="toast">
            <div
                v-if="state.showToast"
                class="fixed top-4 right-4 z-[70] bg-green-500 text-white px-6 py-3 rounded-lg shadow-lg flex items-center space-x-2 max-w-md"
            >
                <svg class="w-5 h-5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
                </svg>
                <span class="font-medium">{{ state.toastMessage }}</span>
                <button @click="hideToast" class="ml-2 text-green-200 hover:text-white flex-shrink-0">
                    <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path>
                    </svg>
                </button>
            </div>
        </transition>
    </Teleport>
</template>

<script setup>
import { watch, onMounted } from 'vue'
import { taskService } from '@/services/task-service'
import { useTaskModal } from '@/composables/useTaskModal'
import { useTaskStore } from '@/stores/task'

const props = defineProps({
    modelValue: { type: Boolean, required: true },
    task: { type: Object, default: null },
    id: { type: [String, Number], default: null }
})
const emit = defineEmits(['update:modelValue', 'taskAdded', 'taskUpdated'])
const taskStore = useTaskStore()
const {
    state,
    fileInput,
    isEditMode,
    loadCategories,
    populateFormWithTask,
    fetchAndPopulateTask,
    showToastNotification,
    hideToast,
    handleFileSelect,
    handleFileDrop,
    removeImage,
    triggerFileInput,
    resetForm
} = useTaskModal()

// Watch for task prop changes to populate form
watch(() => props.task, (newTask) => {
    if (newTask) {
        populateFormWithTask(newTask)
    }
}, { immediate: true })

// Watch for ID prop changes to fetch and populate task data
watch(() => props.id, async (newId) => {
    if (newId && props.modelValue) {
        await fetchAndPopulateTask(newId)
    }
}, { immediate: true })

watch(() => props.modelValue, async (isOpen) => {
    if (isOpen) {
        if (props.id && !props.task) {
            await fetchAndPopulateTask(props.id)
        } else if (!props.task && !props.id) {
            resetForm()
        }
    }
})

onMounted(async () => {
    await loadCategories()
})

const handleSubmit = async () => {
    state.errorMessage = ''
    
    if (!state.form.title.trim()) {
        state.errorMessage = 'Please enter a task title'
        return
    }
    
    if (!state.form.startDate) {
        state.errorMessage = 'Please select a start date'
        return
    }
    
    const selectedItems = Object.entries(state.form.selections).filter(([categoryId, valueId]) => valueId)
    if (selectedItems.length === 0) {
        state.errorMessage = 'Please select at least one option from the categories'
        return
    }

    try {
        state.isLoading = true
        
        const taskData = {
            title: state.form.title.trim(),
            startDate: state.form.startDate,
            description: state.form.description.trim(),
            selections: selectedItems.map(([categoryId, valueId]) => ({
                categoryId: categoryId,
                valueId: valueId
            }))
        }

        let response
        let successMessage
        const taskId = props.task?.id || props.id

        if (isEditMode() && taskId) {
            response = await taskService.updateTask(taskId, taskData)
            
            if (response.success) {
                let taskTitle = state.form.title
                successMessage = `Task "${taskTitle}" updated successfully!`
                
                if (state.uploadedImage && fileInput.value && fileInput.value.files[0]) {
                    const imageResponse = await taskService.uploadTaskImage(taskId, fileInput.value.files[0])
                    if (imageResponse.success) {
                        successMessage = `Task "${taskTitle}" updated and image uploaded successfully!`
                    } else {
                        successMessage = `Task "${taskTitle}" updated successfully, but image upload failed.`
                    }
                }

                showToastNotification(successMessage)
                taskStore.updateTask(taskId, response.data)
                emit('taskUpdated', response.data)
            } else {
                state.errorMessage = response.error || 'Failed to update task'
                return
            }
        } else {
            response = await taskService.createTask(taskData)
            
            if (response.success) {
                let taskTitle = state.form.title
                successMessage = `Task "${taskTitle}" created successfully!`
                
                if (state.uploadedImage && fileInput.value && fileInput.value.files[0]) {
                    const imageResponse = await taskService.uploadTaskImage(response.data.id, fileInput.value.files[0])
                    if (imageResponse.success) {
                        successMessage = `Task "${taskTitle}" created and image uploaded successfully!`
                    } else {
                        successMessage = `Task "${taskTitle}" created successfully, but image upload failed.`
                    }
                }

                showToastNotification(successMessage)
                taskStore.addTask(response.data)
                emit('taskAdded', response.data)
            } else {
                state.errorMessage = response.error || 'Failed to create task'
                return
            }
        }
        
        resetForm()
        
        setTimeout(() => {
            emit('update:modelValue', false)
        }, 200)
        
    } catch (err) {
        console.error('Error saving task:', err)
        state.errorMessage = `An error occurred while ${isEditMode() ? 'updating' : 'creating'} the task`
    } finally {
        state.isLoading = false
    }
}

const close = () => {
    resetForm()
    emit('update:modelValue', false)
}
</script>

<style>
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.2s;
}
.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}

.toast-enter-active,
.toast-leave-active {
    transition: all 0.4s ease-out;
}
.toast-enter-from {
    opacity: 0;
    transform: translateX(100%) scale(0.8);
}
.toast-leave-to {
    opacity: 0;
    transform: translateX(100%) scale(0.8);
}
</style>
