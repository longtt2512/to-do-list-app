<template>
  <div class="max-w-md mx-auto">
    <div class="bg-white p-6 rounded shadow">
      <h2 class="text-xl font-semibold mb-4">Login</h2>
      <form @submit.prevent="login" class="space-y-3">
        <input v-model="email" type="email" class="w-full border px-3 py-2 rounded" placeholder="Email" required />
        <input v-model="password" type="password" class="w-full border px-3 py-2 rounded" placeholder="Password" required />
        <div class="flex gap-3">
          <button class="px-4 py-2 rounded bg-indigo-600 text-white">Login</button>
          <router-link to="/register" class="px-4 py-2 rounded border">Register</router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data(){ return { email:'', password:'' } },
  methods:{ login(){ localStorage.setItem('user', JSON.stringify({ email:this.email })); this.$router.push('/') } }
}
</script>
