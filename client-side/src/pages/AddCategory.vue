<template>
    <div class="h-[calc(100dvh-220px)] px-16">
        <div class="bg-white rounded-lg">
            <!-- Header -->
            <div class="flex justify-between items-center px-6 py-4 border-b border-gray-200">
                <h1 class="text-lg font-medium text-gray-900">Add New Category</h1>
                <button
                    @click="goBack"
                    class="text-gray-600 hover:text-gray-800 transition-colors text-sm"
                >
                    Go Back
                </button>
            </div>

            <!-- Add Form -->
            <div class="bg-white shadow rounded-lg p-6 mb-6">
                <div class="space-y-4">
                    <!-- Category Name -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Category Name</label>
                        <input
                            v-model="form.categoryName"
                            type="text"
                            class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500"
                            placeholder="Enter category name"
                        />
                    </div>

                    <!-- Values Repeater -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Values</label>
                        <div
                            v-for="(val, idx) in form.values"
                            :key="idx"
                            class="flex items-center space-x-2 mb-2"
                        >
                            <input
                                v-model="val.value"
                                type="text"
                                class="flex-1 px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500"
                                :placeholder="`Value ${idx + 1}`"
                            />
                            <button
                                type="button"
                                @click="removeValue(idx)"
                                :disabled="form.values.length === 1"
                                :class="{
                                    'bg-primary hover:bg-red-600 text-white px-4 py-2 rounded text-sm font-medium transition-colors': form.values.length > 1,
                                    'bg-gray-400 text-white px-4 py-2 rounded text-sm font-medium cursor-not-allowed transition-colors': form.values.length === 1
                                }"
                            >
                                Remove
                            </button>
                        </div>
                        <button
                            type="button"
                            @click="addValue"
                            class="bg-button-primary text-white px-4 py-2 rounded text-sm font-medium transition-colors"
                        >
                            + Add Value
                        </button>
                    </div>

                    <!-- Error -->
                    <p v-if="form.error" class="text-red-500 text-sm">{{ form.error }}</p>
                </div>

                <!-- Actions -->
                <div class="flex justify-end space-x-3 mt-6">
                    <button
                        @click="resetForm"
                        class="bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded text-sm font-medium transition-colors"
                    >
                        Reset
                    </button>
                    <button
                        @click="saveCategory"
                        :disabled="isLoading"
                        :class="{
                            'bg-button-primary text-white px-4 py-2 rounded text-sm font-medium transition-colors': !isLoading,
                            'bg-gray-400 text-white px-4 py-2 rounded text-sm font-medium cursor-not-allowed transition-colors': isLoading
                        }"
                    >
                        {{ isLoading ? 'Adding...' : 'Add Category' }}
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import {ref, inject} from 'vue'
import {categoryService} from '@/services/category-service'
import {useRouter} from 'vue-router'

const goBack = inject('goBack')
const router = useRouter()

const isLoading = ref(false)

const form = ref({
    categoryName: '',
    values: [{value: '', sortOrder: 0}],
    error: ''
})

const resetForm = () => {
    form.value = {categoryName: '', values: [{value: '', sortOrder: 0}], error: ''}
}

const addValue = () => {
    form.value.values.push({value: '', sortOrder: form.value.values.length})
}

const removeValue = (idx) => {
    form.value.values.splice(idx, 1)
    form.value.values.forEach((v, i) => (v.sortOrder = i))
}

const saveCategory = async () => {
    if (!form.value.categoryName.trim()) {
        form.value.error = 'Category name is required'
        return
    }
    if (form.value.values.some(v => !v.value.trim())) {
        form.value.error = 'All values must be filled'
        return
    }

    const payload = {
        categoryName: form.value.categoryName.trim(),
        values: form.value.values.map((v, i) => ({value: v.value.trim(), sortOrder: i}))
    }

    try {
        isLoading.value = true
        const response = await categoryService.createCategory(payload)
        
        if (response.success) {
            // Redirect to categories page after successful creation
            router.push('/categories')
        } else {
            form.value.error = response.error || 'Failed to create category'
        }
    } catch (err) {
        form.value.error = 'An error occurred while saving'
        console.error('Error saving category:', err)
    } finally {
        isLoading.value = false
    }
}
</script>
