<template>
  <div class="max-w-xl bg-white p-6 rounded shadow">
    <h2 class="text-xl font-semibold mb-4">Account Info</h2>
    <form @submit.prevent="save" class="space-y-3">
      <div><input v-model="user.name" class="w-full border px-3 py-2 rounded" placeholder="Full name" /></div>
      <div><input v-model="user.email" class="w-full border px-3 py-2 rounded" placeholder="Email" /></div>
      <div><input v-model="user.phone" class="w-full border px-3 py-2 rounded" placeholder="Phone" /></div>
      <div class="flex gap-3"><button class="px-4 py-2 rounded bg-indigo-600 text-white">Save</button><router-link to="/" class="px-4 py-2 rounded border">Cancel</router-link></div>
    </form>
  </div>
</template>

<script>
export default {
  data(){ return { user: { name:'', email:'', phone:'' } } },
  created(){ this.user = JSON.parse(localStorage.getItem('user')||'{}') },
  methods:{ save(){ localStorage.setItem('user', JSON.stringify(this.user)); alert('Saved') } }
}
</script>
