<template>
    <div class="h-[calc(100dvh-220px)] px-16 overflow-y-scroll">
        <div class="bg-white rounded-lg">
            <!-- Header -->
            <div class="flex justify-between items-center px-6 py-4 border-b border-gray-200">
                <h1 class="text-lg font-medium text-gray-900">Task Categories</h1>
                <div class="flex items-center space-x-3">
                    <button
                        @click="goBack"
                        class="text-gray-600 hover:text-gray-800 transition-colors text-sm"
                    >
                        Go Back
                    </button>
                </div>
            </div>

            <div class="p-6">
                <!-- Add Category Button -->
                <router-link :to="{ name: 'add-category' }"
                             class="bg-button-primary text-white px-4 py-2 rounded text-sm font-medium transition-colors mb-6">
                    Add Category
                </router-link>

                <!-- Categories List -->
                <div class="space-y-8">
                    <div v-for="category in categories" :key="category.id">
                        <!-- Category Header -->
                        <div class="flex justify-between items-center mb-3 mt-9">
                            <div class="flex items-center space-x-3">
                                <h2 class="text-base font-medium text-gray-900">{{ category.categoryName }}</h2>
                            </div>

                            <div class="flex items-center space-x-3">
                                <router-link
                                    :to="{ name: 'edit-category', params: { id: category.id } }"
                                    class="text-orange-500 hover:text-orange-600 text-sm transition-colors"
                                >
                                    + Edit {{ category.categoryName }}
                                </router-link>
                            </div>
                        </div>

                        <!-- Category Items Table -->
                        <div class="border border-gray-200 rounded-lg overflow-hidden">
                            <table class="w-full">
                                <thead class="bg-gray-50">
                                <tr>
                                    <th class="px-4 py-3 text-left text-sm font-medium text-gray-700 w-16">SN</th>
                                    <th class="px-4 py-3 text-left text-sm font-medium text-gray-700">{{
                                            category.categoryName
                                        }}
                                    </th>
                                    <th class="px-4 py-3 text-center text-sm font-medium text-gray-700 w-40">Action</th>
                                </tr>
                                </thead>
                                <tbody class="bg-white divide-y divide-gray-200">
                                <tr
                                    v-for="(item) in category.items"
                                    :key="item.id"
                                    class="hover:bg-gray-50 transition-colors"
                                >
                                    <td class="px-4 py-3 text-sm text-gray-900">{{ item.sortOrder + 1 }}</td>
                                    <td class="px-4 py-3 text-sm text-gray-900">
                                        <!-- Inline editing -->
                                        <div v-if="editingInlineItem.itemId === item.id && editingInlineItem.categoryId === category.id">
                                            <input
                                                v-model="editingInlineItem.newValue"
                                                @keyup.enter="saveInlineEdit"
                                                @keyup.escape="cancelInlineEdit"
                                                @blur="saveInlineEdit"
                                                ref="inlineEditInput"
                                                class="w-full px-2 py-1 border border-gray-300 rounded text-sm focus:outline-none focus:ring-2 focus:ring-orange-500"
                                            />
                                        </div>
                                        <div v-else>
                                            {{ item.value }}
                                        </div>
                                    </td>
                                    <td class="px-4 py-3">
                                        <div class="flex justify-center space-x-2">
                                            <button
                                                v-if="editingInlineItem.itemId !== item.id"
                                                @click="startInlineEdit(category.id, item.id, item.value)"
                                                class="bg-button-primary text-white px-4 py-2 rounded text-sm font-medium transition-colors"
                                            >
                                                Edit
                                            </button>
                                            <template v-else>
                                                <button
                                                    @click="saveInlineEdit"
                                                    class="bg-button-primary text-white px-4 py-2 rounded text-sm font-medium transition-colors"
                                                >
                                                    Save
                                                </button>
                                                <button
                                                    @click="cancelInlineEdit"
                                                    class="bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded text-sm font-medium transition-colors"
                                                >
                                                    Cancel
                                                </button>
                                            </template>
                                            <button
                                                v-if="editingInlineItem.itemId !== item.id"
                                                @click="openDeleteItemModal(category.id, item.id)"
                                                :disabled="category.items.length === 1"
                                                :class="{
                                                    'bg-button-primary hover:bg-red-600 text-white px-4 py-2 rounded text-sm font-medium transition-colors': category.items.length > 1,
                                                    'bg-gray-400 text-white px-4 py-2 rounded text-sm font-medium cursor-not-allowed transition-colors': category.items.length === 1
                                                }"
                                                :title="category.items.length === 1 ? 'Cannot delete the last item in category' : 'Delete item'"
                                            >
                                                Delete
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                                <tr v-if="!category.items || category.items.length === 0">
                                    <td colspan="3" class="px-4 py-6 text-center text-sm text-gray-500">
                                        No {{ category.categoryName.toLowerCase() }} items yet. Click "Add New {{
                                            category.categoryName
                                        }}" to get started.
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Empty State -->
                    <div v-if="categories.length === 0" class="text-center py-12">
                        <div class="text-gray-500 mb-4">
                            <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" viewBox="0 0 24 24"
                                 stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"/>
                            </svg>
                        </div>
                        <h3 class="text-sm font-medium text-gray-900 mb-2">No categories yet</h3>
                        <p class="text-sm text-gray-500 mb-4">Get started by creating your first task category.</p>
                        <router-link :to="{ name: 'add-category' }"
                                     class="bg-primary hover:bg-orange-600 text-white px-4 py-2 rounded text-sm font-medium transition-colors">
                            Add Your First Category
                        </router-link>
                    </div>
                </div>
            </div>


            <!-- Delete Confirmation Modal -->
            <div v-if="showDeleteModal"
                 class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
                <div class="bg-white rounded-lg shadow-xl w-96 max-w-md mx-4">
                    <div class="px-6 py-4 border-b border-gray-200">
                        <h3 class="text-lg font-medium text-gray-900">Confirm Delete</h3>
                    </div>
                    <div class="p-6">
                        <p class="text-sm text-gray-700">
                            {{ deleteModal.message }}
                        </p>
                    </div>
                    <div class="px-6 py-4 bg-gray-50 flex justify-end space-x-3 rounded-lg">
                        <button
                            @click="closeDeleteModal"
                            class="bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded text-sm font-medium transition-colors"
                        >
                            Cancel
                        </button>
                        <button
                            @click="confirmDelete"
                            class="bg-primary hover:bg-red-600 text-white px-4 py-2 rounded text-sm font-medium transition-colors"
                        >
                            Delete
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import {ref, onMounted, nextTick, inject} from 'vue'
import {categoryService} from '@/services/category-service'

const goBack = inject('goBack')

const categories = ref([])
const emit = defineEmits(['categoriesUpdated'])

// Inline editing states
const editingInlineItem = ref({
    categoryId: null,
    itemId: null,
    newValue: '',
    originalValue: ''
})

// Delete modal states
const showDeleteModal = ref(false)
const deleteModal = ref({
    message: '',
    type: '',
    categoryId: null,
    itemId: 0
})

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
            emit('categoriesUpdated', categories.value)
        }
    } catch (err) {
        console.error('Error loading categories:', err)
    }
}

// Inline editing functions
const startInlineEdit = (categoryId, itemId, currentValue) => {
    editingInlineItem.value = {
        categoryId,
        itemId,
        newValue: currentValue,
        originalValue: currentValue
    }
    nextTick(() => {
        const input = document.querySelector('input[ref="inlineEditInput"]')
        if (input) {
            input.focus()
            input.select()
        }
    })
}

const cancelInlineEdit = () => {
    editingInlineItem.value = {
        categoryId: null,
        itemId: null,
        newValue: '',
        originalValue: ''
    }
}

const saveInlineEdit = async () => {
    if (!editingInlineItem.value.newValue.trim()) {
        editingInlineItem.value.newValue = editingInlineItem.value.originalValue
        return
    }

    if (editingInlineItem.value.newValue === editingInlineItem.value.originalValue) {
        cancelInlineEdit()
        return
    }

    try {
        const categoryResponse = await categoryService.getCategoryById(editingInlineItem.value.categoryId)
        if (!categoryResponse.success) {
            throw new Error('Failed to get category data')
        }

        const category = categories.value.find(c => c.id === editingInlineItem.value.categoryId)
        if (!category) {
            throw new Error('Category not found')
        }

        const updatedItems = categoryResponse.data.map(item => {
            if (item.id === editingInlineItem.value.itemId) {
                return {
                    value: editingInlineItem.value.newValue.trim(),
                    sortOrder: item.sortOrder
                }
            }
            return {
                value: item.value,
                sortOrder: item.sortOrder
            }
        })

        const updateData = {
            categoryName: category.categoryName,
            values: updatedItems
        }

        const response = await categoryService.updateCategory(editingInlineItem.value.categoryId, updateData)

        if (response.success) {
            const categoryIndex = categories.value.findIndex(c => c.id === editingInlineItem.value.categoryId)
            if (categoryIndex !== -1) {
                const itemIndex = categories.value[categoryIndex].items.findIndex(i => i.id === editingInlineItem.value.itemId)
                if (itemIndex !== -1) {
                    categories.value[categoryIndex].items[itemIndex].value = editingInlineItem.value.newValue.trim()
                }
            }
            
            emit('categoriesUpdated', categories.value)
            cancelInlineEdit()
        } else {
            editingInlineItem.value.newValue = editingInlineItem.value.originalValue
            console.error('Failed to update item:', response.error)
        }
    } catch (error) {
        editingInlineItem.value.newValue = editingInlineItem.value.originalValue
        console.error('Error updating item:', error)
    }
}

// Delete functions
const openDeleteItemModal = (categoryId, itemId) => {
    const category = categories.value.find(c => c.id === categoryId)
    const item = category?.items.find(i => i.id === itemId)
    if (item) {
        deleteModal.value = {
            message: `Delete "${item.value}"?`,
            type: 'item',
            categoryId,
            itemId
        }
        showDeleteModal.value = true
    }
}

const closeDeleteModal = () => {
    showDeleteModal.value = false
    deleteModal.value = {
        message: '',
        type: '',
        categoryId: null,
        itemId: 0
    }
}

const confirmDelete = async () => {
    try {
        if (deleteModal.value.type === 'item') {
            const categoryResponse = await categoryService.getCategoryById(deleteModal.value.categoryId)
            if (!categoryResponse.success) {
                console.error('Failed to get category data:', categoryResponse.error)
                closeDeleteModal()
                return
            }

            const category = categories.value.find(c => c.id === deleteModal.value.categoryId)
            if (!category) {
                console.error('Category not found in local data')
                closeDeleteModal()
                return
            }

            const updatedItems = categoryResponse.data
                .filter(item => item.id !== deleteModal.value.itemId)
                .map((item, index) => ({
                    value: item.value,
                    sortOrder: index
                }))

            const updateData = {
                categoryName: category.categoryName,
                values: updatedItems
            }

            const response = await categoryService.updateCategory(deleteModal.value.categoryId, updateData)
            
            if (response.success) {
                const refreshedCategoryResponse = await categoryService.getCategoryById(deleteModal.value.categoryId)
                if (refreshedCategoryResponse.success) {
                    const categoryIndex = categories.value.findIndex(c => c.id === deleteModal.value.categoryId)
                    if (categoryIndex !== -1) {
                        categories.value[categoryIndex].items = refreshedCategoryResponse.data
                    }
                }
                emit('categoriesUpdated', categories.value)
            } else {
                console.error('Failed to delete item:', response.error)
            }
        }
        closeDeleteModal()
    } catch (err) {
        console.error('Error in confirmDelete:', err)
        closeDeleteModal()
    }
}
</script>
