<template>
  <div>
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <div class="bg-white p-6 rounded-lg shadow">
        <h3 class="text-sm text-slate-500">Total Tasks</h3>
        <div class="text-3xl font-bold mt-2">{{ tasks.length }}</div>
        <div class="text-sm text-slate-400 mt-1">Active • {{ activeCount }}  Completed • {{ completedCount }}</div>
      </div>
      <div class="bg-white p-6 rounded-lg shadow">
        <h3 class="text-sm text-slate-500">Upcoming</h3>
        <ul class="mt-3 space-y-2">
          <li v-for="t in upcoming.slice(0,3)" :key="t.id" class="text-sm">{{ t.title }} • {{ t.dueDate }}</li>
          <li v-if="upcoming.length===0" class="text-sm text-slate-400">No upcoming tasks</li>
        </ul>
      </div>
      <div class="bg-white p-6 rounded-lg shadow">
        <h3 class="text-sm text-slate-500">Quick Actions</h3>
        <div class="mt-4 flex gap-3">
          <router-link to="/tasks/add" class="px-4 py-2 rounded bg-indigo-600 text-white">Add Task</router-link>
          <router-link to="/tasks" class="px-4 py-2 rounded border">View All</router-link>
        </div>
      </div>
    </div>

    <div class="bg-white p-6 rounded-lg shadow mt-6">
      <h3 class="text-lg font-semibold">Recent tasks</h3>
      <div class="mt-4 space-y-3">
        <TaskCard v-for="t in recent" :key="t.id" :task="t" @view="view" @edit="edit" />
        <div v-if="recent.length===0" class="text-slate-400">No tasks yet</div>
      </div>
    </div>
  </div>
</template>

<script>
import TaskCard from '../components/TaskCard.vue'
export default {
  components: { TaskCard },
  data(){ return { tasks: [] } },
  created(){ this.tasks = JSON.parse(localStorage.getItem('tasks')||'[]') },
  computed:{
    completedCount(){ return this.tasks.filter(t=>t.done).length },
    activeCount(){ return this.tasks.length - this.completedCount },
    upcoming(){ return this.tasks.filter(t=>t.dueDate).sort((a,b)=> new Date(a.dueDate)-new Date(b.dueDate)) },
    recent(){ return this.tasks.slice().reverse().slice(0,5) }
  },
  methods:{ view(id){ this.$router.push(`/tasks/${id}`) }, edit(id){ this.$router.push(`/tasks/${id}?edit=1`) }
  }
}
</script>
