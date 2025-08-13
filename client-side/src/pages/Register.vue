<template>
  <div class="background">
    <div class="login-container">
      <div class="login-image">
        <img src="@/assets/register1.png" alt="Register illustration" />
      </div>
      <div class="login-form">
        <h2 class="title">Sign Up</h2>
        <input v-model="firstname" type="text" placeholder="First Name" class="input-field" />
        <input v-model="lastname" type="text" placeholder="Last Name" class="input-field" />
        <input v-model="username" type="text" placeholder="Username" class="input-field" />
        <input v-model="email" type="email" placeholder="Email" class="input-field" />
        <input v-model="password" type="password" placeholder="Password" class="input-field" />
        <input v-model="confirmpassword" type="password" placeholder="Confirm Password" class="input-field" />
        <Button @click="register">Create Account</Button>
        <p class="signup-link">
          Already have an account? <router-link to="/login" class="register-link">Sign In</router-link>
        </p>
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
    return { 
      firstname: '', 
      lastname: '', 
      username: '', 
      email: '', 
      password: '', 
      confirmpassword: '' 
    };
  },
  methods: {
    register() {
      localStorage.setItem('user', JSON.stringify({ 
        firstname: this.firstname, 
        lastname: this.lastname, 
        username: this.username, 
        email: this.email 
      }));
      this.$router.push('/');
    }
  }
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

.login-button {
  width: 100%;
  padding: 15px;
  background-color: #FF6767;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  margin-bottom: 20px;
}

.login-button:hover {
  background-color: #e55555;
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