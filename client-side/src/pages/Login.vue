<template>
  <div class="background">
    <div class="login-container">
      <div class="login-form">
        <h2 class="title">Sign In</h2>
        <input v-model="email" type="text" placeholder="Username" class="input-field" />
        <input v-model="password" type="password" placeholder="Password" class="input-field" />
        <div class="remember-me">
          <input type="checkbox" id="remember" />
          <label for="remember">Remember me</label>
        </div>
        <Button @click="login">Login</Button>
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

export default {
  components: {
    Button
  },
  data() {
    return { email: "", password: "" };
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
  border-radius: 8px;
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

.input-field {
  width: 100%;
  padding: 15px;
  margin-bottom: 20px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
  box-sizing: border-box;
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
  text-align: center;
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
</style>
