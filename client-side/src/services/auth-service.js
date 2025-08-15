export const authService = {
  async login(credentials) {
    try {
      const baseUrl = import.meta.env.VITE_API_URL || 'http://nhom4.choi.one:8081'
      const locale = (localStorage.getItem('locale') || localStorage.getItem('lang') || navigator.language || 'en')
      const res = await fetch(`${baseUrl}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Accept-Language': locale },
        body: JSON.stringify({
          username: credentials.username,
          password: credentials.password
        })
      })

      const data = await res.json().catch(() => ({}))

      if (res.ok && res.status === 200) {
        const { accessToken, refreshToken } = data || {}
        if (accessToken && refreshToken) {
          this.saveTokens(accessToken, refreshToken)
        }
        return {
          success: true,
          status: res.status,
          data
        }
      }

      return {
        success: false,
        status: res.status,
        error: data?.message || 'Login failed'
      }
    } catch (err) {
      return {
        success: false,
        error: err.message || 'Login failed'
      }
    }
  },

  async register(userData) {
    // Real API call to signup endpoint
    try {
      // Map incoming UI fields to API expected payload
      const payload = {
        firstName: userData.firstName || userData.firstname || '',
        lastName: userData.lastName || userData.lastname || '',
        username: userData.username || '',
        email: userData.email || '',
        password: userData.password || '',
        confirmPassword: userData.confirmPassword || userData.confirmpassword || userData.password || ''
      }

      // Use fetch here to preserve HTTP status (Axios interceptor in base-api strips it)
      const baseUrl = import.meta.env.VITE_API_URL || 'http://nhom4.choi.one:8081'
      const locale = (localStorage.getItem('locale') || localStorage.getItem('lang') || navigator.language || 'en')
      const res = await fetch(`${baseUrl}/auth/signup`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Accept-Language': locale },
        body: JSON.stringify(payload)
      })

      const data = await res.json().catch(() => ({}))

      if (res.ok || res.status === 201) {
        return {
          success: true,
          status: res.status,
          data
        }
      }

      // Non-2xx response
      return {
        success: false,
        error: data?.message || `Registration failed with status ${res.status}`
      }
    } catch (err) {
      return {
        success: false,
        error: err.message || 'Registration failed'
      }
    }
  },
  
  saveTokens(accessToken, refreshToken) {
    localStorage.setItem('accessToken', accessToken)
    localStorage.setItem('refreshToken', refreshToken)
  },
  
  getAccessToken() {
    return localStorage.getItem('accessToken')
  },
  
  getRefreshToken() {
    return localStorage.getItem('refreshToken')
  },
  
  removeTokens() {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
  },
  
  isAuthenticated() {
    return !!this.getAccessToken()
  }
}

export default authService