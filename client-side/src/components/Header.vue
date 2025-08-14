<template>
  <header class="bg-white shadow-sm w-full">
    <div class="px-6 py-4 flex items-center justify-between">
      <div class="flex items-center gap-4">
        <h1 class="text-xl font-bold">
          <span class="text-red-500">To</span><span class="text-black">-Do</span>
        </h1>
        <div class="flex items-center bg-gray-50 rounded-full px-4 py-2 ml-4">
          <input 
            type="text" 
            placeholder="Search your task here…" 
            class="bg-transparent outline-none text-sm text-gray-700 placeholder-gray-400 w-64"
          />
          <button class="ml-2 w-8 h-8 bg-red-500 rounded-full flex items-center justify-center">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
              <path d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </button>
        </div>
      </div>
      <div class="flex items-center gap-4">
        <button class="w-8 h-8 bg-red-500 rounded-full flex items-center justify-center">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
            <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M13.73 21a2 2 0 0 1-3.46 0" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </button>
        <button class="w-8 h-8 bg-red-500 rounded-full flex items-center justify-center">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
            <rect x="3" y="4" width="18" height="18" rx="2" ry="2" stroke="white" stroke-width="2"/>
            <line x1="16" y1="2" x2="16" y2="6" stroke="white" stroke-width="2"/>
            <line x1="8" y1="2" x2="8" y2="6" stroke="white" stroke-width="2"/>
            <line x1="3" y1="10" x2="21" y2="10" stroke="white" stroke-width="2"/>
          </svg>
        </button>
        <span class="text-sm text-blue-500">Tuesday 20/06/2023</span>
      </div>
    </div>
  </header>
</template>
