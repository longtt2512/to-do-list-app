<template>
  <div class="background">
    <div class="login-container">
      <div class="login-form" @keydown.enter="login">
        <h2 class="title">Sign In</h2>
        <Input
          v-model="username" 
          type="text" 
          placeholder="Username" 
          :icon="userIcon" 
        />
        <Input
          v-model="password" 
          type="password" 
          placeholder="Password"
          :icon="passwordIcon"
        />
        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>
        <div class="remember-me">
          <input type="checkbox" id="remember" />
          <label for="remember">Remember me</label>
        </div>
        <div class="button-container">
          <Button @click="login" :disabled="isLoading">
            {{ isLoading ? 'Logging in...' : 'Login' }}
          </Button>
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
import Input from '@/components/Input.vue'
import authService from '@/services/auth-service'
import { useAuthStore } from '@/stores/auth'

export default {
  components: {
    Button,
    Input
  },
  data() {
    return { 
      username: "", 
      password: "",
      errorMessage: "",
      isLoading: false,
      // Track if fields were interacted with (touched/dirty)
      touched: {
        username: false,
        password: false,
      },
      userIcon: new URL('@/assets/icons/user.svg', import.meta.url).href,
      passwordIcon: new URL('@/assets/icons/password.svg', import.meta.url).href
    };
  },
  watch: {
    username(val) {
      this.touched.username = true;
      if (this.errorMessage) this.errorMessage = "";
    },
    password(val) {
      this.touched.password = true;
      if (this.errorMessage) this.errorMessage = "";
    }
  },
  methods: {
    async login() {
      const auth = useAuthStore()
      if (!this.username || !this.password) {
        this.errorMessage = "Please enter both username and password";
        return;
      }

      this.isLoading = true;
      this.errorMessage = "";

      try {
        const result = await authService.login({
          username: this.username,
          password: this.password
        });

        if (result.success && result.status === 200) {
          const { accessToken, refreshToken } = result.data || {}
          if (accessToken && refreshToken) {
            // Tokens already saved in authService.login
            auth.initFromStorage()
            this.$router.push("/");
          } else {
            this.errorMessage = "Login failed: No token received";
          }
        } else {
          this.errorMessage = result?.error || "Username or password incorrect";
        }
      } catch (error) {
        this.errorMessage = error?.message || "Username or password incorrect";
      } finally {
        this.isLoading = false;
      }
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
  max-height: calc(100vh - 140px); /* Adjust for footer space */
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  overflow: hidden;
}

@media (max-height: 900px) {
  .login-container {
    max-height: calc(100vh - 120px);
    height: auto;
    min-height: 600px;
  }
}

@media (max-height: 700px) {
  .login-container {
    max-height: calc(100vh - 100px);
    min-height: 500px;
  }
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

@media (max-width: 1024px) {
  .login-container {
    width: 85%;
  }
  
  .login-image {
    display: none;
  }
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

.error-message {
  color: #dc3545;
  font-size: 14px;
  margin-bottom: 15px;
  padding: 8px 12px;
  background-color: #f8d7da;
  border: 1px solid #f5c6cb;
  border-radius: 4px;
}
</style>
