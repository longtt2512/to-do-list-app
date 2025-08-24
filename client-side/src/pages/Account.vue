<template>
  <div class="h-[calc(100dvh-220px)] flex flex-col px-16 overflow-hidden">
    <!-- Main Content - Full Width White Box -->
    <div class="flex-1 bg-white rounded-xl p-8 border border-[#A1A3ABA1]">
      <div class="w-full">
        <!-- Go Back Button and Title on same line -->
        <div class="flex justify-between items-center mb-6">
          <div>
            <h2 class="text-2xl font-semibold text-black">{{ isChangePasswordForm ? 'Change Password' : 'Account Information' }}</h2>
            <div class="w-[200px] h-0.5 bg-[#FF6767] mt-2"></div>
          </div>
          <button class="text-[#FF6767] underline font-medium">Go Back</button>
        </div>
          
          <!-- User Profile Section -->
          <div class="flex items-center mb-8 p-4 bg-gray-50 rounded-lg">
            <img
              src="/src/assets/avatar.png"
              alt="Profile"
              class="w-16 h-16 rounded-full object-cover mr-4"
            />
            <div>
              <div class="font-semibold text-lg">{{ displayName }}</div>
              <div class="text-gray-600">{{ displayEmail }}</div>
            </div>
          </div>
          
          <!-- Update Info Form -->
          <form v-if="!isChangePasswordForm" @submit.prevent="save" class="space-y-6">
            <!-- First Name - separate row -->
            <div>
              <Input 
                v-model="user.firstname" 
                type="text" 
                placeholder="First Name" 
                :icon="editNameIcon" 
              />
            </div>
            
            <!-- Last Name - separate row -->
            <div>
              <Input 
                v-model="user.lastname" 
                type="text" 
                placeholder="Last Name" 
                :icon="editNameIcon" 
              />
            </div>
            
            <Input 
              v-model="user.email" 
              type="email" 
              placeholder="Email Address" 
              :icon="emailIcon" 
            />
            
            <Input 
              v-model="user.contactNumber" 
              type="tel" 
              placeholder="Contact Number" 
              :icon="phoneIcon" 
            />
            
            <Input 
              v-model="user.position" 
              type="text" 
              placeholder="Position" 
              :icon="positionIcon" 
            />

            <div v-if="errorMessage" class="text-red-600 text-sm bg-red-50 p-3 rounded-lg border border-red-200">
              {{ errorMessage }}
            </div>

            <div v-if="successMessage" class="text-green-600 text-sm bg-green-50 p-3 rounded-lg border border-green-200">
              {{ successMessage }}
            </div>
            
            <div class="flex gap-4 pt-4">
              <Button @click="save" :disabled="isLoading" class="flex-1">
                Update Info
              </Button>
              <Button @click="switchToChangePassword" class="flex-1 bg-gray-500">
                Change Password
              </Button>
            </div>
          </form>

          <!-- Change Password Form -->
          <form v-else @submit.prevent="updatePassword" class="space-y-6">
            <Input 
              v-model="passwordData.currentPassword" 
              type="password" 
              placeholder="Current Password" 
              :icon="editNameIcon" 
            />
            
            <Input 
              v-model="passwordData.newPassword" 
              type="password" 
              placeholder="New Password" 
              :icon="editNameIcon" 
            />
            
            <Input 
              v-model="passwordData.confirmPassword" 
              type="password" 
              placeholder="Confirm New Password" 
              :icon="editNameIcon" 
            />

            <div v-if="errorMessage" class="text-red-600 text-sm bg-red-50 p-3 rounded-lg border border-red-200">
              {{ errorMessage }}
            </div>

            <div v-if="successMessage" class="text-green-600 text-sm bg-green-50 p-3 rounded-lg border border-green-200">
              {{ successMessage }}
            </div>
            
            <div class="flex gap-4 pt-4">
              <Button @click="switchToUpdateInfo" class="flex-1 bg-gray-500">
                Update Info
              </Button>
              <Button @click="updatePassword" :disabled="isLoading" class="flex-1">
                Change Password
              </Button>
            </div>
          </form>
      </div>
    </div>
  </div>
</template>

<script>
import Button from '@/components/Button.vue'
import Input from '@/components/Input.vue'
import { useAuthStore } from '@/stores/auth'
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'

export default {
  components: {
    Button,
    Input
  },
  data() {
    return { 
      user: {
        firstname: '',
        lastname: '',
        email: '',
        contactNumber: '',
        position: ''
      },
      passwordData: {
        currentPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      errorMessage: '',
      successMessage: '',
      isLoading: false,
      editNameIcon: new URL('@/assets/icons/edit-name.svg', import.meta.url).href,
      emailIcon: new URL('@/assets/icons/baseline-email.svg', import.meta.url).href,
      phoneIcon: new URL('@/assets/icons/user.svg', import.meta.url).href, // Using user icon as fallback for phone
      positionIcon: new URL('@/assets/icons/user.svg', import.meta.url).href
    }
  },
  computed: {
    isChangePasswordForm() {
      return this.$route.path === '/account/change-password'
    },
    displayName() {
      const auth = useAuthStore()
      const p = auth.profile
      const first = (p?.firstName || '').trim()
      const last = (p?.lastName || '').trim()
      const full = `${first} ${last}`.trim()
      return full || auth.user?.name || auth.user?.username || 'User'
    },
    displayEmail() {
      const auth = useAuthStore()
      return auth.profile?.email || auth.user?.email || ''
    }
  },
  created() {
    this.loadUserProfile()
  },
  methods: {
    loadUserProfile() {
      const auth = useAuthStore()
      // Load from auth store profile first, then fallback to localStorage
      if (auth.profile) {
        this.user.firstname = auth.profile.firstName || ''
        this.user.lastname = auth.profile.lastName || ''
        this.user.email = auth.profile.email || ''
        this.user.contactNumber = auth.profile.contactNumber || auth.profile.phone || ''
        this.user.position = auth.profile.position || ''
      } else {
        // Fallback to localStorage
        const savedUser = JSON.parse(localStorage.getItem('user') || '{}')
        this.user = {
          firstname: savedUser.firstname || savedUser.firstName || '',
          lastname: savedUser.lastname || savedUser.lastName || '',
          email: savedUser.email || '',
          contactNumber: savedUser.contactNumber || savedUser.phone || '',
          position: savedUser.position || ''
        }
      }
    },
    
    validateForm() {
      if (!this.user.firstname || !this.user.lastname) {
        return 'First name and last name are required'
      }
      
      if (!this.user.email) {
        return 'Email address is required'
      }
      
      // Validate email format
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      if (!emailRegex.test(this.user.email)) {
        return 'Please enter a valid email address'
      }
      
      return null
    },
    
    async save() {
      this.errorMessage = ''
      this.successMessage = ''
      
      const validationError = this.validateForm()
      if (validationError) {
        this.errorMessage = validationError
        return
      }
      
      this.isLoading = true
      
      try {
        // Save to localStorage
        const userToSave = {
          firstname: this.user.firstname,
          lastName: this.user.lastname,
          firstName: this.user.firstname,
          lastname: this.user.lastname,
          email: this.user.email,
          contactNumber: this.user.contactNumber,
          phone: this.user.contactNumber,
          position: this.user.position
        }
        
        localStorage.setItem('user', JSON.stringify(userToSave))
        
        // Update auth store if available
        const auth = useAuthStore()
        if (auth.updateProfile) {
          await auth.updateProfile(userToSave)
        }
        
        this.successMessage = 'Profile updated successfully!'
        
        // Clear success message after 3 seconds
        setTimeout(() => {
          this.successMessage = ''
        }, 3000)
        
      } catch (error) {
        this.errorMessage = 'Failed to update profile. Please try again.'
        console.error('Profile update error:', error)
      } finally {
        this.isLoading = false
      }
    },
    
    switchToChangePassword() {
      this.$router.push('/account/change-password')
    },
    
    switchToUpdateInfo() {
      this.$router.push('/account')
    },
    
    validatePasswordForm() {
      if (!this.passwordData.currentPassword) {
        return 'Current password is required'
      }
      
      if (!this.passwordData.newPassword) {
        return 'New password is required'
      }
      
      if (this.passwordData.newPassword.length < 6) {
        return 'New password must be at least 6 characters long'
      }
      
      if (this.passwordData.newPassword !== this.passwordData.confirmPassword) {
        return 'New passwords do not match'
      }
      
      if (this.passwordData.currentPassword === this.passwordData.newPassword) {
        return 'New password must be different from current password'
      }
      
      return null
    },
    
    async updatePassword() {
      this.errorMessage = ''
      this.successMessage = ''
      
      const validationError = this.validatePasswordForm()
      if (validationError) {
        this.errorMessage = validationError
        return
      }
      
      this.isLoading = true
      
      try {
        // Here you would typically make an API call to update the password
        // For now, we'll simulate a successful password update
        
        await new Promise(resolve => setTimeout(resolve, 1000)) // Simulate API call
        
        this.successMessage = 'Password updated successfully!'
        
        // Reset password form
        this.passwordData = {
          currentPassword: '',
          newPassword: '',
          confirmPassword: ''
        }
        
        // Clear success message after 3 seconds
        setTimeout(() => {
          this.successMessage = ''
        }, 3000)
        
      } catch (error) {
        this.errorMessage = 'Failed to update password. Please try again.'
        console.error('Password update error:', error)
      } finally {
        this.isLoading = false
      }
    }
  }
}
</script>
