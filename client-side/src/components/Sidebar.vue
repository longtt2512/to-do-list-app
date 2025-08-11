<template>
  <aside class="fixed left-0 top-0 bottom-0 w-64 bg-slate-800 text-slate-100 p-6 hidden md:block">
    <div class="text-xl font-semibold mb-4">TodoApp — Team</div>
    <div class="text-sm text-slate-300 mb-6">Organize your day, stress-free</div>
    <nav class="space-y-2">
      <router-link to="/" class="block py-2 px-3 rounded hover:bg-slate-700">Dashboard</router-link>
      <router-link to="/tasks" class="block py-2 px-3 rounded hover:bg-slate-700">My Tasks</router-link>
      <router-link to="/tasks/add" class="block py-2 px-3 rounded hover:bg-slate-700">Add Task</router-link>
      <router-link to="/categories" class="block py-2 px-3 rounded hover:bg-slate-700">Categories</router-link>
      <router-link to="/account" class="block py-2 px-3 rounded hover:bg-slate-700">Account</router-link>
      <router-link to="/team" class="block py-2 px-3 rounded hover:bg-slate-700">About Group</router-link>
      <router-link to="/login" class="block py-2 px-3 rounded hover:bg-slate-700">Logout</router-link>
    </nav>
  </aside>
</template>
