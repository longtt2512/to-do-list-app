<template>
  <div class="w-64 bg-red-500 text-white p-6 flex flex-col h-full">
    <!-- Profile Section -->
    <div class="mb-8">
      <div class="flex items-center mb-4">
        <img src="/src/assets/avatar1.png" class="w-12 h-12 rounded-full object-cover mr-3" alt="Profile" />
        <div>
          <div class="font-bold text-white">Sundar Gurung</div>
          <div class="text-sm text-red-100">sundar@example.com</div>
        </div>
      </div>
    </div>

    <!-- Menu Items -->
    <nav class="space-y-3">
      <router-link to="/" class="flex items-center py-3 px-3 rounded hover:bg-red-600 transition-colors">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
          <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          <polyline points="9,22 9,12 15,12 15,22" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
        Dashboard
      </router-link>
      
      <router-link to="/vital-tasks" class="flex items-center py-3 px-3 rounded hover:bg-red-600 transition-colors">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
          <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          <polyline points="22,4 12,14.01 9,11.01" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
        Vital Task
      </router-link>
      
      <router-link to="/tasks" class="flex items-center py-3 px-3 rounded hover:bg-red-600 transition-colors">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
          <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          <polyline points="14,2 14,8 20,8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          <line x1="16" y1="13" x2="8" y2="13" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          <line x1="16" y1="17" x2="8" y2="17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          <polyline points="10,9 9,10 8,9" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
        My Task
      </router-link>
      
      <router-link to="/categories" class="flex items-center py-3 px-3 rounded hover:bg-red-600 transition-colors">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
          <path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
        Task Categories
      </router-link>
      
      <router-link to="/account" class="flex items-center py-3 px-3 rounded hover:bg-red-600 transition-colors">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
          <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
          <path d="M6 21v-2a4 4 0 0 1 4-4h4a4 4 0 0 1 4 4v2" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
        Settings
      </router-link>
      
      <router-link to="/help" class="flex items-center py-3 px-3 rounded hover:bg-red-600 transition-colors">
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
      <router-link to="/login" class="flex items-center py-3 px-3 rounded hover:bg-red-600 transition-colors">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-3">
          <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          <polyline points="16,17 21,12 16,7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          <line x1="21" y1="12" x2="9" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
        Logout
      </router-link>
    </div>
  </div>
</template>
