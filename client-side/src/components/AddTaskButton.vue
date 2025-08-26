<template>
    <div>
        <button 
            @click="handleAddTask"
            class="bg-button-primary hover:bg-orange-600 text-white px-4 py-2 rounded text-sm font-medium transition-colors"
        >
            + Add Task
        </button>

        <!-- TaskModal for Adding -->
        <TaskModal 
            v-model="showModal"
            :task="null"
            :id="null"
            @taskAdded="handleTaskAdded"
        />
    </div>
</template>

<script setup>
import { ref } from 'vue'
import TaskModal from '@/components/TaskModal.vue'

const emit = defineEmits(['taskAdded'])

const showModal = ref(false)

const handleAddTask = () => {
    showModal.value = true
}

const handleTaskAdded = (task) => {
    showModal.value = false
    emit('taskAdded', task)
}
</script>
