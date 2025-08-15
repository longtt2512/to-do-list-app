<template>
  <div class="relative w-[300px] h-full flex-shrink-0">
    <!-- Two background halves -->
    <div class="absolute inset-x-0 top-0 h-[70px] bg-gray-100"></div>
    <div class="absolute inset-x-0 top-[70px] bottom-0 bg-primary rounded-r-lg menu shadow-[0_4px_12px_0_#00000014]"></div>

    <!-- Profile image centered between the two divisions -->
    <img
      src="/src/assets/avatar.png"
      alt="Profile"
      class="absolute left-1/2 top-[70px] -translate-x-1/2 -translate-y-1/2 w-[86px] h-[86px] rounded-full object-cover border-1 border-white shadow-lg z-20"
    />

    <!-- Content overlay -->
    <div class="relative z-10 text-sidebar p-6 flex flex-col h-full">
      <!-- User info under avatar -->
      <div class="pt-[120px] text-center">
        <div class="font-bold text-sidebar">{{ displayName }}</div>
        <div class="text-sm text-sidebar-secondary">{{ displayEmail }}</div>
      </div>

      <!-- Menu Items -->
      <nav class="space-y-3 mt-6">
        <router-link to="/" class="flex items-center py-3 px-3 rounded hover-primary transition-colors">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <polyline points="9,22 9,12 15,12 15,22" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          Dashboard
        </router-link>

        <router-link to="/tasks" class="flex items-center py-3 px-3 rounded hover-primary transition-colors">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <polyline points="14,2 14,8 20,8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <line x1="16" y1="13" x2="8" y2="13" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <line x1="16" y1="17" x2="8" y2="17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <polyline points="10,9 9,10 8,9" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          My Task
        </router-link>
        
        <router-link to="/categories" class="flex items-center py-3 px-3 rounded hover-primary transition-colors">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
            <path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          Task Categories
        </router-link>
        
        <router-link to="/account" class="flex items-center py-3 px-3 rounded hover-primary transition-colors">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
            <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
            <path d="M6 21v-2a4 4 0 0 1 4-4h4a4 4 0 0 1 4 4v2" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          Settings
        </router-link>
        
        <router-link to="/help" class="flex items-center py-3 px-3 rounded hover-primary transition-colors">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
            <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
            <path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M12 17h.01" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          Help
        </router-link>
      </nav>

      <!-- Logout at bottom -->
      <div class="mt-auto">
        <button @click="logout" class="w-full text-left flex items-center py-3 px-3 rounded hover-primary transition-colors">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <polyline points="16,17 21,12 16,7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <line x1="21" y1="12" x2="9" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          Logout
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '@/stores/auth'
import { computed } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'Sidebar',
  setup() {
    const auth = useAuthStore()
    const router = useRouter()
    const displayName = computed(() => auth.user?.name || auth.user?.username || 'User')
    const displayEmail = computed(() => auth.user?.email || '')

    const logout = () => {
      auth.clearAuth()
      router.push('/login')
    }

    return { auth, displayName, displayEmail, logout }
  }
}
</script>
