<template>
  <div class="background">
    <div class="login-container">
      <div class="login-form">
        <h2 class="title">Sign In</h2>
        <TextInput 
          v-model="email" 
          type="text" 
          placeholder="Username" 
          :icon="userIcon" 
        />
        <TextInput 
          v-model="password" 
          type="password" 
          placeholder="Password"
          :icon="passwordIcon"
        />
        <div class="remember-me">
          <input type="checkbox" id="remember" />
          <label for="remember">Remember me</label>
        </div>
        <div class="button-container">
          <Button @click="login">Login</Button>
        </div>
        <p class="signup-link">
          Don't have an account? <router-link to="/register" class="register-link">Create one</router-link>
        </p>
      </div>
      <div class="login-image">
        <img src="@/assets/login2.png" alt="Login illustration" />
      </div>
    </div>
  </div>
</template>

<script>
import Button from '@/components/Button.vue'
import TextInput from '@/components/TextInput.vue'

export default {
  components: {
    Button,
    TextInput
  },
  data() {
    return { 
      email: "", 
      password: "",
      userIcon: new URL('@/assets/icons/user.svg', import.meta.url).href,
      passwordIcon: new URL('@/assets/icons/password.svg', import.meta.url).href
    };
  },
  methods: {
    login() {
      localStorage.setItem("user", JSON.stringify({ email: this.email }));
      this.$router.push("/");
    },
  },
};
</script>

<style lang="css" scoped>
.background {
  background-image: url("@/assets/login1.png");
  background-color: #FF6767;
  width: 100vw;
  height: 100vh;
  position: absolute;
  top: 0;
  left: 0;
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-container {
  width: 100%;
  height: 800px;
  max-width: 1200px;
  max-height: 90vh;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  overflow: hidden;
}

.login-form {
  flex: 1;
  padding: 60px 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.title {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 40px;
  color: #333;
}


.remember-me {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
}

.remember-me input {
  margin-right: 8px;
}


.signup-link {
  text-align: left;
  color: #666;
}

.register-link {
  color: #FF6767;
  text-decoration: none;
}

.register-link:hover {
  text-decoration: underline;
}

.login-image {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.login-image img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.button-container {
  display: flex;
  justify-content: flex-start;
  margin-bottom: 20px;
}
</style>
