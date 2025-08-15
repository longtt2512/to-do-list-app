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
        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>
        <div class="button-container">
          <Button @click="register" :disabled="isLoading">
            {{ isLoading ? 'Creating Account...' : 'Create Account' }}
          </Button>
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
import authService from '@/services/auth-service'

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
      errorMessage: '',
      isLoading: false,
      editNameIcon: new URL('@/assets/icons/edit-name.svg', import.meta.url).href,
      userIcon: new URL('@/assets/icons/user.svg', import.meta.url).href,
      emailIcon: new URL('@/assets/icons/baseline-email.svg', import.meta.url).href,
      passwordIcon: new URL('@/assets/icons/password.svg', import.meta.url).href,
      passwordOutlineIcon: new URL('@/assets/icons/password-outline.svg', import.meta.url).href
    };
  },
  methods: {
    validateForm() {
      // Check if all fields are filled
      if (!this.firstname || !this.lastname || !this.username || !this.email || !this.password || !this.confirmpassword) {
        return 'All fields are required';
      }

      // Validate email format
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(this.email)) {
        return 'Please enter a valid email address';
      }

      // Validate password length
      if (this.password.length < 6) {
        return 'Password must be at least 6 characters long';
      }

      // Check if passwords match
      if (this.password !== this.confirmpassword) {
        return 'Passwords do not match';
      }

      return null;
    },

    async register() {
      this.errorMessage = '';
      
      // Validate form
      const validationError = this.validateForm();
      if (validationError) {
        this.errorMessage = validationError;
        return;
      }

      this.isLoading = true;

      try {
        const result = await authService.register({
          firstname: this.firstname,
          lastname: this.lastname,
          username: this.username,
          email: this.email,
          password: this.password
        });

        if (result.success) {
          // If API indicates resource created (201), redirect to Login page
          if (result.status === 201 || result?.data?.status === 201) {
            this.$router.push('/login');
          } else if (result.data && result.data.token) {
            // Fallback for flows where a token is returned on registration
            authService.saveToken(result.data.token);
            if (result.data.user) {
              localStorage.setItem('user', JSON.stringify(result.data.user));
            }
            this.$router.push('/');
          } else {
            // If no token and not a 201-created flow, show an error message
            this.errorMessage = 'Registration successful. Please log in.';
            this.$router.push('/login');
          }
        } else {
          this.errorMessage = result.error;
        }
      } catch (error) {
        this.errorMessage = 'Registration failed. Please try again.';
      } finally {
        this.isLoading = false;
      }
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
  min-height: 600px;
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
    min-height: 600px;
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