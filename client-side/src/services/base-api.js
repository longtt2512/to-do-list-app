import axios from 'axios'

const getBaseURL = () => {
  return import.meta.env.VITE_API_URL || 'http://nhom4.choi.one:8081'
}

// Resolve preferred locale for API calls
// Priority: explicitly saved app locale -> browser locale -> 'en'
const resolveLocale = () => {
  try {
    const saved = localStorage.getItem('locale') || localStorage.getItem('lang')
    const locale = (saved || navigator.language || navigator.userLanguage || 'en')
    return locale
  } catch {
    return 'en'
  }
}

const createApiClient = () => {
  const client = axios.create({
    baseURL: getBaseURL(),
    timeout: 10000,
    headers: {
      'Content-Type': 'application/json',
    },
  })

  // Attach accessToken and locale for every request
  client.interceptors.request.use((config) => {
    config.headers = config.headers || {}

    // Bearer token
    const token = localStorage.getItem('accessToken')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }

    // Locale header for backend i18n
    const locale = resolveLocale()
    if (locale) {
      config.headers['Accept-Language'] = locale
    }

    return config
  })

  client.interceptors.response.use(
    (response) => response.data,
    (error) => {
      // Handle 401 status code - clear tokens and redirect to login
      if (error.response?.status === 401) {
        // Clear tokens from localStorage
        localStorage.removeItem('accessToken')
        localStorage.removeItem('refreshToken')
        
        // Redirect to login page
        window.location.href = '/login'
        
        return Promise.reject(new Error('Unauthorized - Session expired'))
      }
      
      const message = error.response?.data?.message || error.message || 'An error occurred'
      return Promise.reject(new Error(message))
    }
  )

  return client
}

const apiClient = createApiClient()

export const get = async (url) => {
  return apiClient.get(url)
}

export const post = async (url, data = {}, config = {}) => {
  // If data is FormData, create a special config without Content-Type
  if (data instanceof FormData) {
    return apiClient.post(url, data, {
      ...config,
      headers: {
        ...config.headers,
        // Don't set Content-Type for FormData - let browser handle it
      },
      transformRequest: [(data) => data] // Prevent axios from transforming FormData
    })
  }
  
  return apiClient.post(url, data, config)
}

export const put = async (url, data) => {
  return apiClient.put(url, data)
}

export const del = async (url) => {
  return apiClient.delete(url)
}

export default {
  get,
  post,
  put,
  del
}