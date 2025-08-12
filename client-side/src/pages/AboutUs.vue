<template>
  <div class="min-h-screen bg-white">
    <!-- Navigation Bar -->
    <nav class="bg-white shadow-sm border-b border-gray-100">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <div class="flex items-center">
            <h1 class="text-2xl font-bold text-gray-900">Project To-Do</h1>
          </div>
          <div class="flex items-center space-x-4">
            <router-link to="/" class="text-gray-600 hover:text-[#FF6767] px-3 py-2 rounded-md text-sm font-medium transition-colors">
              Back to App
            </router-link>
          </div>
        </div>
      </div>
    </nav>

    <!-- Hero Section -->
    <div class="bg-gradient-to-r from-white to-red-50 py-20 px-4">
      <div class="max-w-6xl mx-auto text-center">
        <h1 class="text-5xl md:text-6xl font-bold text-gray-800 mb-6">About Our Team</h1>
        <p class="text-xl text-gray-600 max-w-3xl mx-auto leading-relaxed">
          Meet the passionate developers behind this innovative to-do list application. 
          We are dedicated students working together to create amazing digital experiences 
          that help people organize their lives more effectively.
        </p>
        <div class="mt-10 w-32 h-1 bg-[#FF6767] mx-auto rounded-full"></div>
      </div>
    </div>

    <!-- Team Members Grid -->
    <div class="py-20 px-4">
      <div class="max-w-7xl mx-auto">
        <!-- Section Title -->
        <div class="text-center mb-16">
          <h2 class="text-3xl font-bold text-gray-800 mb-4">Our Team Members</h2>
          <p class="text-lg text-gray-600 max-w-2xl mx-auto">
            Each member brings unique skills and perspectives to make this project successful
          </p>
        </div>

        <!-- Loading State -->
        <div v-if="loading" class="flex justify-center items-center py-20">
          <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-[#FF6767]"></div>
          <span class="ml-3 text-gray-600">Loading team members...</span>
        </div>

        <!-- Error State -->
        <div v-else-if="error" class="text-center py-20">
          <div class="text-red-500 mb-4">
            <svg class="w-16 h-16 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.96-.833-2.732 0L3.732 16.5c-.77.833.192 2.5 1.732 2.5z"></path>
            </svg>
            <h3 class="text-xl font-semibold mb-2">Failed to load team members</h3>
            <p class="text-gray-600 mb-4">{{ error }}</p>
            <button 
              @click="fetchMembers" 
              class="px-4 py-2 bg-[#FF6767] text-white rounded-lg hover:bg-red-500 transition-colors"
            >
              Try Again
            </button>
          </div>
        </div>

        <!-- Team Grid -->
        <div v-else class="flex flex-wrap justify-center gap-10 max-w-6xl mx-auto">
          <div 
            v-for="member in members" 
            :key="member.id" 
            class="group bg-white rounded-3xl shadow-xl hover:shadow-2xl transition-all duration-500 transform hover:-translate-y-3 border border-gray-50 overflow-hidden w-full sm:w-80 md:w-72 lg:w-80"
          >
            <!-- Photo Section with Gradient Border -->
            <div class="relative bg-gradient-to-br from-[#FF6767] to-red-400 p-2 m-4 rounded-2xl">
              <div class="bg-white rounded-xl p-6 text-center">
                <img 
                  :src="member.avatarUrl" 
                  :alt="member.name"
                  class="w-28 h-28 mx-auto rounded-full object-cover shadow-lg cursor-pointer border-4 border-white group-hover:scale-105 transition-transform duration-300"
                  @click="openModal(member.avatarUrl)"
                />
              </div>
            </div>

            <!-- Content Section -->
            <div class="px-6 pb-8 space-y-4">
              <h3 class="text-xl font-bold text-gray-800 text-center mb-6">{{ member.name }}</h3>
              
              <div class="space-y-3 text-sm">
                <div class="flex items-center justify-between py-2 border-b border-gray-100">
                  <span class="text-gray-500 font-medium min-w-[100px]">Student Code:</span>
                  <span class="text-[#FF6767] font-bold text-right">{{ member.code }}</span>
                </div>
                
                <div class="flex items-center justify-between py-2 border-b border-gray-100">
                  <span class="text-gray-500 font-medium min-w-[100px]">Class:</span>
                  <span class="text-gray-700 font-medium text-right">{{ member.className }}</span>
                </div>
                
                <div class="flex items-center justify-between py-2 border-b border-gray-100">
                  <span class="text-gray-500 font-medium min-w-[100px]">Date of Birth:</span>
                  <span class="text-gray-700 font-medium text-right">{{ formatDate(member.dob) }}</span>
                </div>
                
                <div class="flex items-center justify-between py-2 border-b border-gray-100">
                  <span class="text-gray-500 font-medium min-w-[100px]">Phone:</span>
                  <span class="text-gray-700 font-medium text-right">{{ member.phone }}</span>
                </div>
                
                <div class="flex items-center justify-between py-2 border-b border-gray-100">
                  <span class="text-gray-500 font-medium min-w-[100px]">Email:</span>
                  <span class="text-blue-600 font-medium break-words text-right text-xs">{{ member.email }}</span>
                </div>
                
                <div class="flex items-center justify-between py-2 border-b border-gray-100">
                  <span class="text-gray-500 font-medium min-w-[100px]">Company:</span>
                  <span class="text-gray-700 font-medium text-right break-words">{{ member.company?.name || 'N/A' }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Mission Statement Section -->
    <div class="bg-gray-50 py-20 px-4 border-t">
      <div class="max-w-5xl mx-auto text-center">
        <h3 class="text-3xl font-bold text-gray-800 mb-8">Our Mission</h3>
        <p class="text-lg text-gray-600 leading-relaxed mb-8">
          Our diverse team brings together different perspectives, skills, and experiences to create 
          innovative solutions. We believe in collaboration, continuous learning, and delivering 
          high-quality software that makes a real difference in people's daily lives.
        </p>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mt-12">
          <div class="text-center">
            <div class="w-16 h-16 bg-[#FF6767] rounded-full flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"></path>
              </svg>
            </div>
            <h4 class="text-xl font-semibold text-gray-800 mb-2">Collaboration</h4>
            <p class="text-gray-600">Working together to achieve common goals</p>
          </div>
          <div class="text-center">
            <div class="w-16 h-16 bg-[#FF6767] rounded-full flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"></path>
              </svg>
            </div>
            <h4 class="text-xl font-semibold text-gray-800 mb-2">Innovation</h4>
            <p class="text-gray-600">Creating cutting-edge solutions</p>
          </div>
          <div class="text-center">
            <div class="w-16 h-16 bg-[#FF6767] rounded-full flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z"></path>
              </svg>
            </div>
            <h4 class="text-xl font-semibold text-gray-800 mb-2">Quality</h4>
            <p class="text-gray-600">Delivering excellence in every project</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Footer -->
    <footer class="bg-white py-12 px-4 border-t">
      <div class="max-w-4xl mx-auto text-center">
        <p class="text-gray-500 text-sm">
          © 2024 Project To-Do Team. Built with passion and dedication.
        </p>
        <div class="mt-4">
          <router-link to="/" class="text-[#FF6767] hover:text-red-500 font-medium transition-colors">
            ← Return to Application
          </router-link>
        </div>
      </div>
    </footer>

    <!-- Modal for Image Zoom -->
    <ModalImage :src="modalImageSrc" @close="modalImageSrc = null" />
  </div>
</template>

<script>
import ModalImage from '../components/ModalImage.vue'

export default {
  name: 'AboutUsStandalone',
  components: {
    ModalImage
  },
  data() {
    return {
      modalImageSrc: null,
      members: [],
      loading: false,
      error: null
    }
  },
  async mounted() {
    await this.fetchMembers()
  },
  methods: {
    async fetchMembers() {
      this.loading = true
      this.error = null
      
      try {
        const response = await fetch('http://localhost:8081/api/members')
        
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`)
        }
        
        const data = await response.json()
        this.members = data
        
      } catch (error) {
        console.error('Failed to fetch members:', error)
        this.error = error.message || 'Failed to load team members. Please check your internet connection.'
      } finally {
        this.loading = false
      }
    },
    openModal(imageSrc) {
      this.modalImageSrc = imageSrc
    },
    formatDate(dateString) {
      if (!dateString) return 'N/A'
      const options = { year: 'numeric', month: 'short', day: 'numeric' }
      return new Date(dateString).toLocaleDateString('en-US', options)
    }
  }
}
</script>

<style scoped>
/* Custom scrollbar for better aesthetics */
::-webkit-scrollbar {
  width: 8px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
}

::-webkit-scrollbar-thumb {
  background: #FF6767;
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: #e55555;
}
</style>