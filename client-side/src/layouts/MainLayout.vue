<template>
  <div class="min-h-screen flex flex-col">
    <Header />
    <main class="flex-1 flex">
      <Sidebar class="hidden md:block" />
      <div class="flex-1 flex flex-col">
        <div class="flex-1 p-6">
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