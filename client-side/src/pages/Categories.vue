<template>
  <div class="max-w-xl">
    <h2 class="text-xl font-semibold mb-4">Categories</h2>
    <div class="bg-white p-4 rounded shadow space-y-2">
      <div v-for="(c,i) in categories" :key="i" class="flex justify-between items-center">
        <div>{{ c }}</div>
        <button @click="remove(i)" class="px-3 py-1 rounded border">Delete</button>
      </div>
      <form @submit.prevent="add" class="mt-3 flex gap-2">
        <input v-model="newcat" class="flex-1 border rounded px-3 py-2" placeholder="New category" />
        <button class="px-3 py-2 rounded bg-indigo-600 text-white">Add</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data(){ return { categories: [], newcat: '' } },
  created(){ this.categories = JSON.parse(localStorage.getItem('categories')||'[]') },
  methods:{ add(){ if(!this.newcat.trim()) return; this.categories.push(this.newcat.trim()); localStorage.setItem('categories', JSON.stringify(this.categories)); this.newcat='' }, remove(i){ this.categories.splice(i,1); localStorage.setItem('categories', JSON.stringify(this.categories)) } }
}
</script>
