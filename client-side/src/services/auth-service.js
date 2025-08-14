export const authService = {
  async login(credentials) {
    // Mock API delay
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    // Mock user credentials
    const validUsers = [
      { username: 'admin', password: 'admin123' },
      { username: 'user', password: 'user123' },
      { username: 'test', password: 'test123' }
    ]
    
    // Check credentials
    const user = validUsers.find(u => 
      u.username === credentials.username && u.password === credentials.password
    )
    
    if (user) {
      // Mock successful response
      return {
        success: true,
        data: {
          token: `mock-jwt-token-${Date.now()}`,
          user: {
            username: user.username,
            id: Math.floor(Math.random() * 1000)
          }
        }
      }
    } else {
      // Mock error response
      return {
        success: false,
        error: 'Invalid credentials'
      }
    }
  },

  async register(userData) {
    // Mock API delay
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    // Mock existing users for email validation
    const existingUsers = [
      'admin@example.com',
      'user@example.com',
      'test@example.com'
    ]
    
    // Check if email already exists
    if (existingUsers.includes(userData.email)) {
      return {
        success: false,
        error: 'Email already exists'
      }
    }
    
    // Mock successful registration
    return {
      success: true,
      data: {
        token: `mock-jwt-token-${Date.now()}`,
        user: {
          email: userData.email,
          username: userData.username,
          id: Math.floor(Math.random() * 1000)
        }
      }
    }
  },
  
  saveToken(token) {
    localStorage.setItem('token', token)
  },
  
  getToken() {
    return localStorage.getItem('token')
  },
  
  removeToken() {
    localStorage.removeItem('token')
  },
  
  isAuthenticated() {
    return !!this.getToken()
  }
}

export default authService