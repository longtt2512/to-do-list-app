<template>
  <div class="max-w-md mx-auto">
    <div class="bg-white p-6 rounded shadow">
      <h2 class="text-xl font-semibold mb-4">Register</h2>
      <form @submit.prevent="register" class="space-y-3">
        <input v-model="name" class="w-full border px-3 py-2 rounded" placeholder="Full name" required />
        <input v-model="email" type="email" class="w-full border px-3 py-2 rounded" placeholder="Email" required />
        <input v-model="password" type="password" class="w-full border px-3 py-2 rounded" placeholder="Password" required />
        <div class="flex gap-3">
          <button class="px-4 py-2 rounded bg-indigo-600 text-white">Create account</button>
          <router-link to="/login" class="px-4 py-2 rounded border">Back</router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data(){ return { name:'', email:'', password:'' } },
  methods:{ register(){ localStorage.setItem('user', JSON.stringify({ name:this.name, email:this.email })); this.$router.push('/') } }
}
</script>
