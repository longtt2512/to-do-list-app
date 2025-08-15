import { defineStore } from 'pinia'

// Minimal JWT decode (no verification). Returns payload object or null.
function decodeJwt(token) {
  try {
    const parts = token.split('.')
    if (parts.length !== 3) return null
    const base64Url = parts[1]
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    )
    return JSON.parse(jsonPayload)
  } catch (e) {
    return null
  }
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    accessToken: null,
    user: null, // extracted from JWT payload (e.g., sub, name, email, roles)
    // ProfileResponse stored here once fetched
    profile: null,
    _profileLoaded: false, // internal flag to avoid duplicate fetches
  }),
  getters: {
    isAuthenticated: (state) => !!state.accessToken,
    // Common shortcuts
    username: (state) => state.user?.username || state.user?.name || state.user?.sub || null,
    email: (state) => state.user?.email || null,
    roles: (state) => state.user?.roles || state.user?.authorities || [],
  },
  actions: {
    initFromStorage() {
      try {
        const token = localStorage.getItem('accessToken') || null
        if (token) {
          this.setToken(token)
        } else {
          this.clearAuth()
        }
      } catch {
        this.clearAuth()
      }
    },
    setToken(token) {
      this.accessToken = token
      const payload = decodeJwt(token)
      // Attempt to map common claim names to a normalized user object
      this.user = payload ? {
        id: payload.id || payload.userId || payload.uid || null,
        username: payload.username || payload.preferred_username || payload.name || payload.sub || null,
        name: payload.name || null,
        email: payload.email || null,
        roles: payload.roles || payload.authorities || payload.scope || [],
        exp: payload.exp || null,
        iat: payload.iat || null,
        raw: payload,
      } : null
    },
    setProfile(profile) {
      // Store exactly the fields from ProfileResponse
      this.profile = profile ? {
        id: profile.id ?? null,
        userId: profile.userId ?? null,
        firstName: profile.firstName ?? null,
        lastName: profile.lastName ?? null,
        email: profile.email ?? null,
        contactNumber: profile.contactNumber ?? null,
        position: profile.position ?? null,
        avatarKey: profile.avatarKey ?? null,
        avatarUrl: profile.avatarUrl ?? null,
        createdAt: profile.createdAt ?? null,
        updatedAt: profile.updatedAt ?? null,
        createdBy: profile.createdBy ?? null,
        updatedBy: profile.updatedBy ?? null,
      } : null
      this._profileLoaded = true
    },
    async fetchMyProfileIfNeeded() {
      if (!this.isAuthenticated) {
        this.profile = null
        this._profileLoaded = true
        return null
      }
      if (this._profileLoaded && this.profile?.id) {
        return this.profile
      }
      try {
        const { getMyProfile } = await import('@/services/profile-service')
        const data = await getMyProfile()
        this.setProfile(data)
        return this.profile
      } catch (err) {
        console.warn('fetchMyProfileIfNeeded failed:', err)
        // Mark as attempted to avoid loops; keep profile as null on error
        this._profileLoaded = true
        return null
      }
    },
    clearAuth() {
      this.accessToken = null
      this.user = null
      this.profile = null
      this._profileLoaded = false
      try {
        localStorage.removeItem('accessToken')
        localStorage.removeItem('refreshToken')
      } catch {}
    }
  }
})

export default useAuthStore
