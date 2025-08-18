<template>
  <div class="h-[calc(100dvh-220px)] px-16">
    <div class="bg-white rounded-lg">
      <!-- Header -->
      <div class="flex justify-between items-center px-6 py-4 border-b border-gray-200">
        <h1 class="text-lg font-medium text-gray-900">Task Categories</h1>
        <button
            @click="goBack"
            class="text-gray-600 hover:text-gray-800 transition-colors text-sm"
        >
          Go Back
        </button>
      </div>

      <div class="p-6">
        <!-- Add Category Button -->
        <button
            @click="openAddCategoryModal"
            class="bg-primary hover:bg-orange-600 text-white px-4 py-2 rounded text-sm font-medium transition-colors mb-6"
        >
          Add Category
        </button>

        <!-- Categories List -->
        <div class="space-y-8">
          <div v-for="category in categories" :key="category.id">
            <!-- Category Header -->
            <div class="flex justify-between items-center mb-3">
              <div class="flex items-center space-x-3">
                <h2 class="text-base font-medium text-gray-900">{{ category.name }}</h2>
                <IconButton icon="edit" alt="Edit Category" @click="openEditCategoryModal(category.id)"/>
                <IconButton icon="delete" alt="Delete Category" @click="openDeleteCategoryModal(category.id)"/>
              </div>

              <div class="flex items-center space-x-3">
                <button
                    @click="openAddItemModal(category.id)"
                    class="text-orange-500 hover:text-orange-600 text-sm transition-colors"
                >
                  + Add New {{ category.name }}
                </button>
              </div>
            </div>

            <!-- Category Items Table -->
            <div class="border border-gray-200 rounded-lg overflow-hidden">
              <table class="w-full">
                <thead class="bg-gray-50">
                <tr>
                  <th class="px-4 py-3 text-left text-sm font-medium text-gray-700 w-16">SN</th>
                  <th class="px-4 py-3 text-left text-sm font-medium text-gray-700">{{ category.name }}</th>
                  <th class="px-4 py-3 text-center text-sm font-medium text-gray-700 w-40">Action</th>
                </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                <tr
                    v-for="(item, index) in category.items"
                    :key="item.id"
                    class="hover:bg-gray-50 transition-colors"
                >
                  <td class="px-4 py-3 text-sm text-gray-900">{{ index + 1 }}</td>
                  <td class="px-4 py-3 text-sm text-gray-900">{{ item.name }}</td>
                  <td class="px-4 py-3">
                    <div class="flex justify-center space-x-2">
                      <button
                          @click="openEditItemModal(category.id, item.id)"
                          class="bg-primary hover:bg-orange-600 text-white px-3 py-1.5 rounded text-xs font-medium transition-colors inline-flex items-center"
                      >
                        Edit
                      </button>
                      <button
                          @click="openDeleteItemModal(category.id, item.id)"
                          class="bg-primary hover:bg-orange-600 text-white px-3 py-1.5 rounded text-xs font-medium transition-colors inline-flex items-center"
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
                <tr v-if="category.items.length === 0">
                  <td colspan="3" class="px-4 py-6 text-center text-sm text-gray-500">
                    No {{ category.name.toLowerCase() }} items yet. Click "Add New {{ category.name }}" to get started.
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- Empty State -->
          <div v-if="categories.length === 0" class="text-center py-12">
            <div class="text-gray-500 mb-4">
              <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"/>
              </svg>
            </div>
            <h3 class="text-sm font-medium text-gray-900 mb-2">No categories yet</h3>
            <p class="text-sm text-gray-500 mb-4">Get started by creating your first task category.</p>
            <button
                @click="openAddCategoryModal"
                class="bg-primary hover:bg-orange-600 text-white px-4 py-2 rounded text-sm font-medium transition-colors"
            >
              Add Your First Category
            </button>
          </div>
        </div>
      </div>

      <!-- Add/Edit Category Modal -->
      <div v-if="showCategoryModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div class="bg-white rounded-lg shadow-xl w-96 max-w-md mx-4">
          <div class="px-6 py-4 border-b border-gray-200">
            <h3 class="text-lg font-medium text-gray-900">
              {{ editingCategory ? 'Edit Category' : 'Add New Category' }}
            </h3>
          </div>
          <div class="p-6">
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Category Name
            </label>
            <input
                id="categoryNameInput"
                v-model="categoryForm.name"
                type="text"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500 focus:border-orange-500"
                placeholder="Enter category name"
                @keyup.enter="saveCategoryModal"
            />
            <p v-if="categoryForm.error" class="text-red-500 text-xs mt-1">{{ categoryForm.error }}</p>
          </div>
          <div class="px-6 py-4 bg-gray-50 flex justify-end space-x-3 rounded-lg">
            <button
                @click="closeCategoryModal"
                class="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-200 hover:bg-gray-300 rounded transition-colors"
            >
              Cancel
            </button>
            <button
                @click="saveCategoryModal"
                class="px-4 py-2 text-sm font-medium text-white bg-primary hover:bg-orange-600 rounded transition-colors"
            >
              {{ editingCategory ? 'Update' : 'Add' }}
            </button>
          </div>
        </div>
      </div>

      <!-- Add/Edit Item Modal -->
      <div v-if="showItemModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div class="bg-white rounded-lg shadow-xl w-96 max-w-md mx-4">
          <div class="px-6 py-4 border-b border-gray-200">
            <h3 class="text-lg font-medium text-gray-900">
              {{ editingItem ? 'Edit Item' : `Add New ${getCurrentCategoryName()}` }}
            </h3>
          </div>
          <div class="p-6">
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Item Name
            </label>
            <input
                id="itemNameInput"
                v-model="itemForm.name"
                type="text"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500 focus:border-orange-500"
                placeholder="Enter item name"
                @keyup.enter="saveItemModal"
            />
            <p v-if="itemForm.error" class="text-red-500 text-xs mt-1">{{ itemForm.error }}</p>
          </div>
          <div class="px-6 py-4 bg-gray-50 flex justify-end space-x-3 rounded-lg">
            <button
                @click="closeItemModal"
                class="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-200 hover:bg-gray-300 rounded transition-colors"
            >
              Cancel
            </button>
            <button
                @click="saveItemModal"
                class="px-4 py-2 text-sm font-medium text-white bg-primary hover:bg-orange-600 rounded transition-colors"
            >
              {{ editingItem ? 'Update' : 'Add' }}
            </button>
          </div>
        </div>
      </div>

      <!-- Delete Confirmation Modal -->
      <div v-if="showDeleteModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
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
                class="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-200 hover:bg-gray-300 rounded transition-colors"
            >
              Cancel
            </button>
            <button
                @click="confirmDelete"
                class="px-4 py-2 text-sm font-medium text-white bg-red-500 hover:bg-red-600 rounded transition-colors"
            >
              Delete
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref, nextTick} from 'vue'
import IconButton from "../components/IconButton.vue";

interface CategoryItem {
  id: number
  name: string
}

interface Category {
  id: number
  name: string
  items: CategoryItem[]
}

// Reactive data
const categories = ref<Category[]>([
  {
    id: 1,
    name: 'Task Status',
    items: [
      {id: 1, name: 'Completed'},
      {id: 2, name: 'In Progress'},
      {id: 3, name: 'Not Started'}
    ]
  },
  {
    id: 2,
    name: 'Task Priority',
    items: [
      {id: 1, name: 'Extreme'},
      {id: 2, name: 'Moderate'},
      {id: 3, name: 'Low'}
    ]
  }
])

// Modal states
const showCategoryModal = ref(false)
const showItemModal = ref(false)
const showDeleteModal = ref(false)

// Form states
const categoryForm = ref({
  name: '',
  error: ''
})

const itemForm = ref({
  name: '',
  error: '',
  categoryId: 0
})

const deleteModal = ref({
  message: '',
  type: '', // 'category' or 'item'
  categoryId: 0,
  itemId: 0
})

// Editing states
const editingCategory = ref<number | null>(null)
const editingItem = ref<number | null>(null)

// Define emits
const emit = defineEmits<{
  goBack: []
  categoriesUpdated: [categories: Category[]]
}>()

// Helper functions
const getCurrentCategoryName = () => {
  const category = categories.value.find(c => c.id === itemForm.value.categoryId)
  return category?.name || 'Item'
}

const resetCategoryForm = () => {
  categoryForm.value = {name: '', error: ''}
  editingCategory.value = null
}

const resetItemForm = () => {
  itemForm.value = {name: '', error: '', categoryId: 0}
  editingItem.value = null
}

const resetDeleteModal = () => {
  deleteModal.value = {message: '', type: '', categoryId: 0, itemId: 0}
}

// Category modal functions
const openAddCategoryModal = () => {
  resetCategoryForm()
  showCategoryModal.value = true
  nextTick(() => {
    const input = document.querySelector('#categoryNameInput') as HTMLInputElement
    input?.focus()
  })
}

const openEditCategoryModal = (categoryId: number) => {
  const category = categories.value.find(c => c.id === categoryId)
  if (category) {
    categoryForm.value.name = category.name
    categoryForm.value.error = ''
    editingCategory.value = categoryId
    showCategoryModal.value = true
    nextTick(() => {
      const input = document.querySelector('#categoryNameInput') as HTMLInputElement
      input?.focus()
    })
  }
}

const closeCategoryModal = () => {
  showCategoryModal.value = false
  resetCategoryForm()
}

const saveCategoryModal = () => {
  if (!categoryForm.value.name.trim()) {
    categoryForm.value.error = 'Category name is required'
    return
  }

  if (editingCategory.value) {
    // Edit existing category
    const category = categories.value.find(c => c.id === editingCategory.value)
    if (category) {
      category.name = categoryForm.value.name.trim()
    }
  } else {
    // Add new category
    const newId = Math.max(...categories.value.map(c => c.id), 0) + 1
    categories.value.push({
      id: newId,
      name: categoryForm.value.name.trim(),
      items: []
    })
  }

  emit('categoriesUpdated', categories.value)
  closeCategoryModal()
}

// Item modal functions
const openAddItemModal = (categoryId: number) => {
  resetItemForm()
  itemForm.value.categoryId = categoryId
  showItemModal.value = true
  nextTick(() => {
    const input = document.querySelector('#itemNameInput') as HTMLInputElement
    input?.focus()
  })
}

const openEditItemModal = (categoryId: number, itemId: number) => {
  const category = categories.value.find(c => c.id === categoryId)
  const item = category?.items.find(i => i.id === itemId)
  if (item) {
    itemForm.value.name = item.name
    itemForm.value.error = ''
    itemForm.value.categoryId = categoryId
    editingItem.value = itemId
    showItemModal.value = true
    nextTick(() => {
      const input = document.querySelector('#itemNameInput') as HTMLInputElement
      input?.focus()
    })
  }
}

const closeItemModal = () => {
  showItemModal.value = false
  resetItemForm()
}

const saveItemModal = () => {
  if (!itemForm.value.name.trim()) {
    itemForm.value.error = 'Item name is required'
    return
  }

  const category = categories.value.find(c => c.id === itemForm.value.categoryId)
  if (!category) return

  if (editingItem.value) {
    // Edit existing item
    const item = category.items.find(i => i.id === editingItem.value)
    if (item) {
      item.name = itemForm.value.name.trim()
    }
  } else {
    // Add new item
    const newId = Math.max(...category.items.map(i => i.id), 0) + 1
    category.items.push({id: newId, name: itemForm.value.name.trim()})
  }

  emit('categoriesUpdated', categories.value)
  closeItemModal()
}

// Delete modal functions
const openDeleteCategoryModal = (categoryId: number) => {
  const category = categories.value.find(c => c.id === categoryId)
  if (category) {
    deleteModal.value = {
      message: `Are you sure you want to delete "${category.name}" category and all its items? This action cannot be undone.`,
      type: 'category',
      categoryId: categoryId,
      itemId: 0
    }
    showDeleteModal.value = true
  }
}

const openDeleteItemModal = (categoryId: number, itemId: number) => {
  const category = categories.value.find(c => c.id === categoryId)
  const item = category?.items.find(i => i.id === itemId)
  if (item) {
    deleteModal.value = {
      message: `Are you sure you want to delete "${item.name}"? This action cannot be undone.`,
      type: 'item',
      categoryId: categoryId,
      itemId: itemId
    }
    showDeleteModal.value = true
  }
}

const closeDeleteModal = () => {
  showDeleteModal.value = false
  resetDeleteModal()
}

const confirmDelete = () => {
  if (deleteModal.value.type === 'category') {
    categories.value = categories.value.filter(c => c.id !== deleteModal.value.categoryId)
  } else if (deleteModal.value.type === 'item') {
    const category = categories.value.find(c => c.id === deleteModal.value.categoryId)
    if (category) {
      category.items = category.items.filter(i => i.id !== deleteModal.value.itemId)
    }
  }

  emit('categoriesUpdated', categories.value)
  closeDeleteModal()
}

const goBack = () => {
  emit('goBack')
}

// Props for initial data (optional)
interface Props {
  initialCategories?: Category[]
}

const props = withDefaults(defineProps<Props>(), {
  initialCategories: () => []
})

// Initialize with props if provided
if (props.initialCategories.length > 0) {
  categories.value = props.initialCategories
}
</script>