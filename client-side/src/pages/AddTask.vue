<template>
  <div class="max-w-2xl">
    <h2 class="text-xl font-semibold mb-4">{{ isEdit ? 'Edit Task' : 'Add Task' }}</h2>
    <form @submit.prevent="submit" class="space-y-3 bg-white p-6 rounded shadow">
      <div><label class="block text-sm text-slate-600">Title</label><input v-model="title" class="w-full border rounded px-3 py-2" required /></div>
      <div><label class="block text-sm text-slate-600">Description</label><textarea v-model="desc" class="w-full border rounded px-3 py-2"></textarea></div>
      <div class="grid grid-cols-2 gap-3">
        <div><label class="block text-sm text-slate-600">Due date</label><input v-model="dueDate" type="date" class="w-full border rounded px-3 py-2" /></div>
        <div><label class="block text-sm text-slate-600">Category</label><input v-model="category" class="w-full border rounded px-3 py-2" /></div>
      </div>
      <div class="flex gap-3"><button class="px-4 py-2 rounded bg-indigo-600 text-white">{{ isEdit ? 'Save' : 'Add' }}</button><router-link to="/tasks" class="px-4 py-2 rounded border">Cancel</router-link></div>
    </form>
  </div>
</template>

<script>
export default {
  data(){ return { title:'', desc:'', dueDate:'', category:'', tasks: [], isEdit:false, id:null } },
  created(){ this.tasks = JSON.parse(localStorage.getItem('tasks')||'[]'); const id=this.$route.params.id; if(id){ const t=this.tasks.find(x=>String(x.id)===String(id)); if(t){ this.isEdit=true; this.id=t.id; this.title=t.title; this.desc=t.desc; this.dueDate=t.dueDate; this.category=t.category } } },
  methods:{ submit(){ if(!this.title.trim()){ alert('Title required'); return } if(this.isEdit){ const idx=this.tasks.findIndex(x=>x.id===this.id); if(idx>-1){ this.tasks[idx]={ ...this.tasks[idx], title:this.title, desc:this.desc, dueDate:this.dueDate, category:this.category } } } else { this.tasks.push({ id: Date.now(), title:this.title, desc:this.desc, dueDate:this.dueDate, category:this.category, done:false }) } localStorage.setItem('tasks', JSON.stringify(this.tasks)); this.$router.push('/tasks') } }
}
</script>
