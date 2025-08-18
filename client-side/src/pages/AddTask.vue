<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="bg-white rounded-sm shadow-xl w-full max-w-7xl mx-4 p-6">
      <!-- Header -->
      <div class="flex justify-between items-center mb-6">
        <h2 class="text-xl font-semibold text-gray-800">Add New Task</h2>
        <button
            @click="$emit('close')"
            class="text-gray-500 hover:text-gray-700 text-sm font-medium"
        >
          Go Back
        </button>
      </div>

      <!-- Form -->
      <form @submit.prevent="handleSubmit" class="space-y-6">
        <!-- Title -->
        <div>
          <label for="title" class="block text-sm font-medium text-gray-700 mb-2">
            Title
          </label>
          <input
              id="title"
              v-model="form.title"
              type="text"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500 focus:border-transparent"
              placeholder="Enter task title"
          />
        </div>

        <!-- Date -->
        <div>
          <label for="date" class="block text-sm font-medium text-gray-700 mb-2">
            Date
          </label>
          <div class="relative">
            <input
                id="date"
                v-model="form.date"
                type="date"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500 focus:border-transparent"
            />
          </div>
        </div>

        <!-- Priority -->
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-3">
            Priority
          </label>
          <div class="flex space-x-6">
            <label class="flex items-center">
              <input
                  v-model="form.priority"
                  type="radio"
                  value="extreme"
                  class="sr-only"
              />
              <div class="flex items-center cursor-pointer">
                <div
                    class="w-4 h-4 rounded-full border-2 mr-2"
                    :class="form.priority === 'extreme' ? 'bg-red-500 border-red-500' : 'border-red-500'"
                ></div>
                <span class="text-sm text-gray-700">Extreme</span>
              </div>
            </label>

            <label class="flex items-center">
              <input
                  v-model="form.priority"
                  type="radio"
                  value="moderate"
                  class="sr-only"
              />
              <div class="flex items-center cursor-pointer">
                <div
                    class="w-4 h-4 rounded-full border-2 mr-2"
                    :class="form.priority === 'moderate' ? 'bg-yellow-500 border-yellow-500' : 'border-yellow-500'"
                ></div>
                <span class="text-sm text-gray-700">Moderate</span>
              </div>
            </label>

            <label class="flex items-center">
              <input
                  v-model="form.priority"
                  type="radio"
                  value="low"
                  class="sr-only"
              />
              <div class="flex items-center cursor-pointer">
                <div
                    class="w-4 h-4 rounded-full border-2 mr-2"
                    :class="form.priority === 'low' ? 'bg-green-500 border-green-500' : 'border-green-500'"
                ></div>
                <span class="text-sm text-gray-700">Low</span>
              </div>
            </label>
          </div>
        </div>

        <!-- Task Description and Upload Image -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <!-- Task Description -->
          <div>
            <label for="description" class="block text-sm font-medium text-gray-700 mb-2">
              Task Description
            </label>
            <textarea
                id="description"
                v-model="form.description"
                rows="6"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500 focus:border-transparent resize-none"
                placeholder="Start writing here..."
            ></textarea>
          </div>

          <!-- Upload Image -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Upload Image
            </label>
            <div
                @click="triggerFileInput"
                @dragover.prevent
                @drop.prevent="handleFileDrop"
                class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center cursor-pointer hover:border-gray-400 transition-colors"
            >
              <div class="text-gray-400 mb-4">
                <svg class="mx-auto h-12 w-12" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                </svg>
              </div>
              <p class="text-sm text-gray-500 mb-2">Drag&Drop files here</p>
              <button
                  type="button"
                  class="px-4 py-2 bg-gray-100 text-gray-700 rounded-md text-sm hover:bg-gray-200 transition-colors"
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

            <!-- Preview uploaded image -->
            <div v-if="uploadedImage" class="mt-4">
              <img :src="uploadedImage" alt="Uploaded preview" class="w-full h-32 object-cover rounded-md" />
              <button
                  @click="removeImage"
                  type="button"
                  class="mt-2 text-sm text-red-600 hover:text-red-800"
              >
                Remove image
              </button>
            </div>
          </div>
        </div>

        <!-- Submit Button -->
        <div class="flex justify-start">
          <button
              type="submit"
              class="px-6 py-2 bg-primary text-white rounded-md hover:bg-orange-600 focus:outline-none focus:ring-2 focus:ring-orange-500 focus:ring-offset-2 transition-colors"
          >
            Done
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'

// Define emits
const emit = defineEmits(['close', 'submit'])

// Form data
const form = reactive({
  title: '',
  date: '',
  priority: '',
  description: ''
})

// File handling
const fileInput = ref(null)
const uploadedImage = ref(null)

// Methods
const triggerFileInput = () => {
  fileInput.value?.click()
}

const handleFileSelect = (event) => {
  const file = event.target.files[0]
  if (file && file.type.startsWith('image/')) {
    const reader = new FileReader()
    reader.onload = (e) => {
      uploadedImage.value = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

const handleFileDrop = (event) => {
  const files = event.dataTransfer.files
  if (files.length > 0) {
    const file = files[0]
    if (file.type.startsWith('image/')) {
      const reader = new FileReader()
      reader.onload = (e) => {
        uploadedImage.value = e.target.result
      }
      reader.readAsDataURL(file)
    }
  }
}

const removeImage = () => {
  uploadedImage.value = null
  if (fileInput.value) {
    fileInput.value.value = ''
  }
}

const handleSubmit = () => {
  // Basic validation
  if (!form.title.trim()) {
    alert('Please enter a task title')
    return
  }

  // Emit the form data
  emit('submit', {
    ...form,
    image: uploadedImage.value
  })

  // Reset form
  Object.assign(form, {
    title: '',
    date: '',
    priority: '',
    description: ''
  })
  uploadedImage.value = null
}
</script>

<style scoped>
/* Custom radio button styling is handled through Tailwind classes */
</style>