<template>
  <Teleport to="body">
    <transition name="fade">
      <div
        v-if="modelValue"
        class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4"
        @click="handleBackdropClick"
      >
        <div
          class="bg-white rounded-lg shadow-2xl w-[90%] max-w-4xl mx-auto overflow-hidden border border-gray-200 h-[80vh] flex flex-col"
          @click.stop
        >
          <!-- Header -->
          <div class="flex justify-between items-center px-6 py-4 border-b border-gray-200 bg-gray-50 flex-shrink-0">
            <h2 class="text-xl font-semibold text-gray-900">{{ $t('invite.title') }}</h2>
            <button
              @click="close"
              class="text-gray-500 hover:text-gray-700 text-lg font-medium transition-colors p-1 rounded-full hover:bg-gray-100"
            >
              ✕
            </button>
          </div>

          <!-- Content -->
          <div class="p-6 flex-1 overflow-y-auto">
            <!-- Loading State -->
            <div v-if="isLoading" class="flex items-center justify-center py-8">
              <div class="text-center">
                <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-orange-500 mx-auto mb-2"></div>
                <p class="text-sm text-gray-600">{{ $t('invite.loading') }}</p>
              </div>
            </div>

            <!-- Error State -->
            <div v-else-if="error" class="text-center py-8">
              <div class="text-red-500 mb-4">
                <svg class="w-12 h-12 mx-auto" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L3.732 16.5c-.77.833.192 2.5 1.732 2.5z"></path>
                </svg>
              </div>
              <p class="text-gray-700 mb-4">{{ error }}</p>
              <button
                @click="loadMembers"
                class="px-4 py-2 bg-orange-500 text-white rounded-md hover:bg-orange-600 transition-colors"
              >
                {{ $t('invite.tryAgain') }}
              </button>
            </div>

            <!-- Members List -->
            <div v-else-if="members.length > 0" class="space-y-4">
              <div class="flex items-center justify-between mb-4">
                <p class="text-sm text-gray-600">{{ $t('invite.totalMembers', { count: members.length }) }}</p>
                <div class="relative">
                  <input
                    v-model="searchQuery"
                    type="text"
                    :placeholder="$t('invite.searchPlaceholder')"
                    class="pl-10 pr-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500 focus:border-transparent text-sm"
                  />
                  <svg class="w-5 h-5 text-gray-400 absolute left-3 top-2.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                  </svg>
                </div>
              </div>

              <div class="grid gap-4">
                <div
                  v-for="member in filteredMembers"
                  :key="member.id"
                  class="flex items-center justify-between p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
                >
                  <!-- Member Info -->
                  <div class="flex items-center space-x-4">
                    <!-- Avatar -->
                    <div class="w-12 h-12 rounded-full bg-gray-200 flex items-center justify-center overflow-hidden">
                      <img
                        v-if="member.avatarUrl"
                        :src="member.avatarUrl"
                        :alt="member.name"
                        class="w-full h-full object-cover"
                      />
                      <svg
                        v-else
                        class="w-6 h-6 text-gray-500"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                      >
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
                      </svg>
                    </div>

                    <!-- Member Details -->
                    <div>
                      <h3 class="font-medium text-gray-900">{{ member.name }}</h3>
                      <div class="flex items-center space-x-4 text-sm text-gray-600">
                        <span>{{ $t('invite.studentCode') }}: {{ member.code }}</span>
                        <span v-if="member.className">{{ $t('invite.class') }}: {{ member.className }}</span>
                        <span v-if="member.email">{{ member.email }}</span>
                      </div>
                      <div v-if="member.company && member.company.name" class="text-xs text-gray-500 mt-1">
                        {{ member.company.name }}
                      </div>
                    </div>
                  </div>

                                     <!-- Invite Button -->
                   <button
                     @click="inviteMember(member)"
                     class="px-4 py-2 rounded-md text-sm font-medium transition-colors hover:opacity-80"
                     :class="invitedMembers.has(member.id)
                       ? 'bg-red-500 text-white hover:bg-red-600 border border-red-500'
                       : 'bg-orange-500 text-white hover:bg-orange-600 border border-orange-500'"
                   >
                     <span v-if="invitedMembers.has(member.id)">
                       <svg class="w-4 h-4 inline mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                         <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                       </svg>
                       {{ $t('invite.uninvite') }}
                     </span>
                     <span v-else>
                       {{ $t('invite.invite') }}
                     </span>
                   </button>
                </div>
              </div>
            </div>

            <!-- Empty State -->
            <div v-else class="text-center py-8">
              <div class="text-gray-400 mb-4">
                <svg class="w-16 h-16 mx-auto" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"></path>
                </svg>
              </div>
              <p class="text-gray-600">{{ $t('invite.noMembers') }}</p>
            </div>
          </div>

          <!-- Footer -->
          <div class="flex justify-end items-center px-6 py-4 border-t border-gray-200 bg-gray-50 flex-shrink-0">
            <button
              @click="close"
              class="px-6 py-2 text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 transition-colors mr-3"
            >
              {{ $t('invite.close') }}
            </button>
            <button
              v-if="invitedMembers.size > 0"
              @click="sendInvites"
              class="px-6 py-2 bg-green-500 text-white rounded-md hover:bg-green-600 transition-colors"
            >
              {{ $t('invite.sendInvites', { count: invitedMembers.size }) }}
            </button>
          </div>
        </div>
      </div>
    </transition>
  </Teleport>
</template>

<script>
import { ref, computed, onMounted, watch } from 'vue'
import { useI18n } from 'vue-i18n'
import { getAllMembers } from '../services/member-service'

export default {
  name: 'InviteModal',
  props: {
    modelValue: {
      type: Boolean,
      default: false
    }
  },
  emits: ['update:modelValue', 'invite-sent'],
  setup(props, { emit }) {
    const { t } = useI18n()
    
    const members = ref([])
    const isLoading = ref(false)
    const error = ref(null)
    const searchQuery = ref('')
    const invitedMembers = ref(new Set())

    // Computed properties
    const filteredMembers = computed(() => {
      if (!searchQuery.value) return members.value
      
      const query = searchQuery.value.toLowerCase()
      return members.value.filter(member => 
        member.name.toLowerCase().includes(query) ||
        member.code.toLowerCase().includes(query) ||
        member.email.toLowerCase().includes(query) ||
        (member.className && member.className.toLowerCase().includes(query))
      )
    })

    // Methods
    const loadMembers = async () => {
      isLoading.value = true
      error.value = null
      
      try {
        const result = await getAllMembers()
        if (result.success) {
          members.value = result.data
        } else {
          error.value = result.error
        }
      } catch (err) {
        error.value = err.message || 'Failed to load members'
      } finally {
        isLoading.value = false
      }
    }

    const inviteMember = (member) => {
      if (invitedMembers.value.has(member.id)) {
        invitedMembers.value.delete(member.id)
      } else {
        invitedMembers.value.add(member.id)
      }
    }

    const sendInvites = () => {
      const invitedMembersList = members.value.filter(member => 
        invitedMembers.value.has(member.id)
      )
      
      emit('invite-sent', invitedMembersList)
      close()
    }

    const close = () => {
      emit('update:modelValue', false)
      // Reset state when closing
      setTimeout(() => {
        invitedMembers.value.clear()
        searchQuery.value = ''
        error.value = null
      }, 300)
    }

    const handleBackdropClick = (event) => {
      if (event.target === event.currentTarget) {
        close()
      }
    }

    // Watch for modal open to load members
    watch(() => props.modelValue, (newValue) => {
      if (newValue) {
        loadMembers()
      }
    })

    return {
      members,
      isLoading,
      error,
      searchQuery,
      invitedMembers,
      filteredMembers,
      loadMembers,
      inviteMember,
      sendInvites,
      close,
      handleBackdropClick
    }
  }
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
