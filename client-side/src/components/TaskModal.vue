<template>
    <Teleport to="body">
        <transition name="fade">
            <div
                v-if="modelValue"
                class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4"
            >
                <div
                    class="bg-white rounded-lg shadow-2xl w-[70%] max-w-4xl mx-auto overflow-hidden border border-gray-200 h-[80vh] flex flex-col"
                >
                    <!-- Header -->
                    <div class="flex justify-between items-center px-6 py-4 border-b border-gray-200 bg-gray-50 flex-shrink-0">
                        <h2 class="text-lg font-semibold text-gray-900">Add New Task</h2>
                        <button
                            @click="close"
                            class="text-gray-500 hover:text-gray-700 text-sm font-medium transition-colors"
                        >
                            ✕
                        </button>
                    </div>

                    <!-- Form -->
                    <div class="p-6 flex-1 overflow-y-auto">
                        <form @submit.prevent="handleSubmit" class="border border-gray-200 rounded-lg p-4 bg-gray-50 space-y-4">
                            <!-- Title & Date -->
                                <div>
                                    <label for="title" class="block text-sm font-medium text-gray-700 mb-1">
                                        Title <span class="text-red-500">*</span>
                                    </label>
                                    <input
                                        id="title"
                                        v-model="form.title"
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
                                        v-model="form.startDate"
                                        type="date"
                                        class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-sm bg-white"
                                        required
                                    />
                                </div>
                            
                            <!-- Categories -->
                            <template v-for="category in categories" :key="category.id">
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
                                                v-model="form.selections[category.id]"
                                                type="radio"
                                                :value="item.id"
                                                class="sr-only"
                                            />
                                            <div class="flex items-center">
                                                <div
                                                    class="w-4 h-4 rounded-full border-2 mr-2"
                                                    :class="form.selections[category.id] === item.id ? getPriorityClasses(item.value) : 'border-gray-300'"
                                                >
                                                    <div v-if="form.selections[category.id] === item.id" class="w-2 h-2 bg-white rounded-full mx-auto mt-0.5"></div>
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
                                        v-model="form.description"
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

                                    <div v-if="uploadedImage" class="mt-2">
                                        <img :src="uploadedImage" alt="Preview" class="w-full h-40 object-cover rounded border border-gray-200" />
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
                            <div v-if="errorMessage" class="bg-red-50 border border-red-200 text-red-700 px-3 py-2 rounded text-sm">
                                {{ errorMessage }}
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
                                    :disabled="isLoading"
                                    :class="isLoading ? 'bg-gray-400 cursor-not-allowed' : 'bg-primary hover:bg-orange-600'"
                                    class="text-white px-4 py-2 rounded text-sm font-medium transition-colors"
                                >
                                    {{ isLoading ? 'Creating...' : 'Create Task' }}
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
                v-if="showToast"
                class="fixed top-4 right-4 z-[70] bg-green-500 text-white px-6 py-3 rounded-lg shadow-lg flex items-center space-x-2 max-w-md"
            >
                <svg class="w-5 h-5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
                </svg>
                <span class="font-medium">{{ toastMessage }}</span>
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
import { ref, reactive, onMounted } from 'vue'
import { categoryService } from '@/services/category-service'
import { taskService } from '@/services/task-service'

const props = defineProps({
    modelValue: { type: Boolean, required: true }
})
const emit = defineEmits(['update:modelValue', 'taskAdded'])

const categories = ref([])
const isLoading = ref(false)
const errorMessage = ref('')
const showToast = ref(false)
const toastMessage = ref('')
const form = reactive({
    title: '',
    startDate: '',
    description: '',
    selections: {}
})
const fileInput = ref(null)
const uploadedImage = ref(null)

onMounted(async () => {
    await loadCategories()
})

const loadCategories = async () => {
    try {
        const response = await categoryService.getAll()
        if (response.success) {
            categories.value = await Promise.all(
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

const triggerFileInput = () => fileInput.value?.click()

const handleFileSelect = (e) => {
    const file = e.target.files[0]
    if (file && file.type.startsWith('image/')) {
        const reader = new FileReader()
        reader.onload = (ev) => (uploadedImage.value = ev.target.result)
        reader.readAsDataURL(file)
    }
}

const handleFileDrop = (e) => {
    const file = e.dataTransfer.files[0]
    if (file && file.type.startsWith('image/')) {
        const reader = new FileReader()
        reader.onload = (ev) => (uploadedImage.value = ev.target.result)
        reader.readAsDataURL(file)
    }
}

const removeImage = () => {
    uploadedImage.value = null
    if (fileInput.value) fileInput.value.value = ''
}

const showToastNotification = (message) => {
    toastMessage.value = message
    showToast.value = true
    
    setTimeout(() => {
        hideToast()
    }, 5000)
}

const hideToast = () => {
    showToast.value = false
    toastMessage.value = ''
}

const handleSubmit = async () => {
    errorMessage.value = ''
    
    if (!form.title.trim()) {
        errorMessage.value = 'Please enter a task title'
        return
    }
    
    if (!form.startDate) {
        errorMessage.value = 'Please select a start date'
        return
    }
    
    const selectedItems = Object.entries(form.selections).filter(([categoryId, valueId]) => valueId)
    if (selectedItems.length === 0) {
        errorMessage.value = 'Please select at least one option from the categories'
        return
    }

    try {
        isLoading.value = true
        
        const taskData = {
            title: form.title.trim(),
            startDate: form.startDate,
            description: form.description.trim(),
            selections: selectedItems.map(([categoryId, valueId]) => ({
                categoryId: categoryId,
                valueId: valueId
            }))
        }

        const response = await taskService.createTask(taskData)
        
        if (response.success) {
            let taskTitle = form.title
            let successMessage = `Task "${taskTitle}" created successfully!`
            
            // Upload image if exists
            if (uploadedImage.value && fileInput.value && fileInput.value.files[0]) {
                const imageResponse = await taskService.uploadTaskImage(response.data.id, fileInput.value.files[0])
                if (imageResponse.success) {
                    successMessage = `Task "${taskTitle}" created and image uploaded successfully!`
                } else {
                    successMessage = `Task "${taskTitle}" created successfully, but image upload failed.`
                }
            }

            showToastNotification(successMessage)
            
            emit('taskAdded', response.data)
            
            // Reset form first
            resetForm()
            
            // Close modal with slight delay to ensure toast renders
            setTimeout(() => {
                emit('update:modelValue', false)
            }, 200)
            
        } else {
            errorMessage.value = response.error || 'Failed to create task'
        }
    } catch (err) {
        console.error('Error creating task:', err)
        errorMessage.value = 'An error occurred while creating the task'
    } finally {
        isLoading.value = false
    }
}

const resetForm = () => {
    Object.assign(form, { 
        title: '', 
        startDate: '', 
        description: '', 
        selections: {} 
    })
    uploadedImage.value = null
    errorMessage.value = ''
}

const close = () => {
    resetForm()
    emit('update:modelValue', false)
    // Keep toast visible even after closing modal
}

const getPriorityClasses = (value) => {
    const normalizedValue = value.toLowerCase()
    
    if (normalizedValue.includes('extreme') || normalizedValue.includes('high') || normalizedValue.includes('urgent')) {
        return 'border-red-500 bg-red-500'
    } else if (normalizedValue.includes('moderate') || normalizedValue.includes('medium') || normalizedValue.includes('normal')) {
        return 'border-yellow-500 bg-yellow-500'
    } else if (normalizedValue.includes('low') || normalizedValue.includes('minor')) {
        return 'border-green-500 bg-green-500'
    } else {
        return 'border-blue-500 bg-blue-500'
    }
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
