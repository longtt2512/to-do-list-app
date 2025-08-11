<template>
  <div class="min-h-screen flex">
    <Sidebar class="hidden md:block" />
    <div class="flex-1 min-h-screen md:ml-64">
      <Header />
      <main class="p-6">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script>
import Sidebar from './components/Sidebar.vue'
import Header from './components/Header.vue'
export default { components: { Sidebar, Header } }
</script>
