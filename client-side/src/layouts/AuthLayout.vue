<template>
  <div class="h-screen flex flex-col overflow-hidden relative">
    <div class="flex-1 flex items-center justify-center min-h-0">
      <router-view />
    </div>
    <div class="absolute bottom-0 left-0 right-0 z-10">
      <Footer theme="dark" />
    </div>
  </div>
</template>

<script>
import Footer from '../components/Footer.vue'

export default {
  name: 'AuthLayout',
  components: { Footer }
}
</script>