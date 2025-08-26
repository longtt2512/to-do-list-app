<template>
    <div>
        <button 
            @click="handleEditTask"
            :disabled="disabled"
            :class="disabled ? 'bg-gray-400 cursor-not-allowed' : 'bg-button-primary hover:bg-orange-600'"
            class="text-white px-4 py-2 rounded text-sm font-medium transition-colors"
        >
            Edit Task
        </button>

        <!-- TaskModal for Editing -->
        <TaskModal 
            v-model="showModal"
            :task="null"
            :id="taskId"
            @taskUpdated="handleTaskUpdated"
        />
    </div>
</template>

<script setup>
import { ref } from 'vue'
import TaskModal from '@/components/TaskModal.vue'

const props = defineProps({
    taskId: {
        type: [String, Number],
        required: true
    },
    disabled: {
        type: Boolean,
        default: false
    }
})

const emit = defineEmits(['taskUpdated'])

const showModal = ref(false)

const handleEditTask = () => {
    if (!props.disabled) {
        showModal.value = true
    }
}

const handleTaskUpdated = (task) => {
    showModal.value = false
    emit('taskUpdated', task)
}
</script>
