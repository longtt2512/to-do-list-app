<template>
  <div class="bg-white p-4 rounded-lg shadow-sm flex justify-between items-center">
    <div>
      <div class="font-semibold">{{ task.title }}</div>
      <div class="text-sm text-slate-500">{{ task.category }} • {{ task.dueDate || 'No deadline' }}</div>
    </div>
    <div class="flex gap-2 items-center">
      <button @click="$emit('view', task.id)" class="px-3 py-1 rounded bg-indigo-600 text-white text-sm">View</button>
      <button @click="$emit('edit', task.id)" class="px-3 py-1 rounded border text-sm">Edit</button>
    </div>
  </div>
</template>

<script>
export default { name: 'TaskCard', props: ['task'] }
</script>
