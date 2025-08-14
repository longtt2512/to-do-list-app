<template>
  <div class="auth-layout">
    <div class="auth-content">
      <router-view />
    </div>
    <div class="auth-footer">
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

<style scoped>
.auth-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  position: relative;
}

.auth-content {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 0;
  padding-bottom: 120px; /* Reserve space for footer */
}

.auth-footer {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 10;
  height: 120px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* For screens shorter than 900px, adjust layout */
@media (max-height: 900px) {
  .auth-content {
    padding-bottom: 100px;
  }
  
  .auth-footer {
    height: 100px;
  }
}

/* For very short screens, reduce footer height further */
@media (max-height: 700px) {
  .auth-content {
    padding-bottom: 80px;
  }
  
  .auth-footer {
    height: 80px;
  }
}
</style>