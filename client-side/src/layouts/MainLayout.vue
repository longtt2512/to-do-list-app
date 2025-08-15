<template>
  <div class="h-screen flex flex-col">
    <Header />
    <main class="flex-1 flex">
      <Sidebar class="hidden md:block relative z-20" />
      <div class="flex-1 flex flex-col">
        <div class="flex-1 menu-container">
          <router-view />
        </div>
        <Footer theme="light" />
      </div>
    </main>
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

<style scoped>
.menu-container {
  padding-top: 70px;
  padding-bottom: 30px;
}
</style>