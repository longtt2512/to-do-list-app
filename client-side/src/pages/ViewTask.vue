<template>
  <div class="max-w-2xl bg-white p-6 rounded shadow">
    <div v-if="task">
      <h2 class="text-2xl font-semibold">{{ task.title }}</h2>
      <div class="text-sm text-slate-500">{{ task.category }} • {{ task.dueDate || 'No deadline' }}</div>
      <p class="mt-4">{{ task.desc }}</p>
      <div class="mt-4 flex gap-3"><button @click="edit" class="px-4 py-2 rounded bg-indigo-600 text-white">Edit</button><router-link to="/tasks" class="px-4 py-2 rounded border">Back</router-link></div>
    </div>
    <div v-else class="text-slate-400">Task not found</div>
  </div>
</template>

<script>
export default {
  data(){ return { task:null, tasks:[] } },
  created(){ this.tasks = JSON.parse(localStorage.getItem('tasks')||'[]'); const id=this.$route.params.id; this.task=this.tasks.find(t=>String(t.id)===String(id)) },
  methods:{ edit(){ this.$router.push(`/tasks/${this.task.id}?edit=1`) } }
}
</script>
