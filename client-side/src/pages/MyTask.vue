<template>
  <div>
    <div class="flex justify-between items-center">
      <h2 class="text-xl font-semibold">My Tasks</h2>
      <div class="w-72"><input v-model="q" placeholder="Search tasks..." class="w-full border rounded px-3 py-2" /></div>
    </div>
    <div class="mt-4 space-y-3">
      <div v-for="t in filtered" :key="t.id" class="bg-white p-4 rounded shadow flex justify-between items-center">
        <div>
          <input type="checkbox" v-model="t.done" @change="save" /> 
          <span :class="{'line-through text-slate-400': t.done}" class="ml-2 font-medium">{{ t.title }}</span>
          <div class="text-sm text-slate-500">{{ t.category }} • {{ t.dueDate || 'No deadline' }}</div>
        </div>
        <div class="flex gap-2">
          <button @click="view(t.id)" class="px-3 py-1 rounded bg-indigo-600 text-white">View</button>
          <button @click="edit(t.id)" class="px-3 py-1 rounded border">Edit</button>
          <button @click="remove(t.id)" class="px-3 py-1 rounded border">Delete</button>
        </div>
      </div>
      <div v-if="filtered.length===0" class="text-slate-400">No tasks match</div>
    </div>
  </div>
</template>

<script>
export default {
  data(){ return { tasks: [], q: '' } },
  created(){ this.tasks = JSON.parse(localStorage.getItem('tasks')||'[]') },
  computed:{ filtered(){ const q=this.q.toLowerCase(); return this.tasks.filter(t=>t.title.toLowerCase().includes(q) || (t.category||'').toLowerCase().includes(q)) }},
  methods:{
    save(){ localStorage.setItem('tasks', JSON.stringify(this.tasks)) },
    view(id){ this.$router.push(`/tasks/${id}`) },
    edit(id){ this.$router.push(`/tasks/${id}?edit=1`) },
    remove(id){ if(confirm('Delete task?')) { this.tasks=this.tasks.filter(t=>t.id!==id); this.save() } }
  }
}
</script>
