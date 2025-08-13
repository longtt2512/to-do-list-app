<template>
  <div class="background">
    <div class="login-container">
      <div class="login-image">
        <img src="@/assets/register1.png" alt="Register illustration" />
      </div>
      <div class="login-form">
        <h2 class="title">Sign Up</h2>
        <TextInput v-model="firstname" type="text" placeholder="First Name" :icon="editNameIcon" />
        <TextInput v-model="lastname" type="text" placeholder="Last Name" :icon="editNameIcon" />
        <TextInput v-model="username" type="text" placeholder="Username" :icon="userIcon" />
        <TextInput v-model="email" type="email" placeholder="Email" :icon="emailIcon" />
        <TextInput v-model="password" type="password" placeholder="Password" :icon="passwordIcon" />
        <TextInput v-model="confirmpassword" type="password" placeholder="Confirm Password" :icon="passwordOutlineIcon" />
        <div class="button-container">
          <Button @click="register">Create Account</Button>
        </div>
        <p class="signup-link">
          Already have an account? <router-link to="/login" class="register-link">Sign In</router-link>
        </p>
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
      firstname: '', 
      lastname: '', 
      username: '', 
      email: '', 
      password: '', 
      confirmpassword: '',
      editNameIcon: new URL('@/assets/icons/edit-name.svg', import.meta.url).href,
      userIcon: new URL('@/assets/icons/user.svg', import.meta.url).href,
      emailIcon: new URL('@/assets/icons/baseline-email.svg', import.meta.url).href,
      passwordIcon: new URL('@/assets/icons/password.svg', import.meta.url).href,
      passwordOutlineIcon: new URL('@/assets/icons/password-outline.svg', import.meta.url).href
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
</style>