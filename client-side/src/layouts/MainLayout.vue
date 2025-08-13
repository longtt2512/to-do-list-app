<template>
  <div class="min-h-screen flex">
    <Sidebar class="hidden md:block" />
    <div class="flex-1 min-h-screen md:ml-64 flex flex-col">
      <Header />
      <main class="p-6 flex-1">
        <router-view />
      </main>
      <Footer theme="light" />
    </div>
  </div>
</template>

<script>
import Sidebar from '../components/Sidebar.vue'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'

export default {
  name: 'MainLayout',
  components: { Sidebar, Header, Footer }
}
</script>