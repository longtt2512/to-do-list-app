import axios from 'axios'

const getBaseURL = () => {
  return import.meta.env.VITE_API_URL || 'http://nhom4.choi.one:8081'
}

const createApiClient = () => {
  const client = axios.create({
    baseURL: getBaseURL(),
    timeout: 10000,
    headers: {
      'Content-Type': 'application/json',
    },
  })

  // Attach accessToken as Bearer for every request
  client.interceptors.request.use((config) => {
    const token = localStorage.getItem('accessToken')
    if (token) {
      config.headers = config.headers || {}
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  })

  client.interceptors.response.use(
    (response) => response.data,
    (error) => {
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

export const post = async (url, data) => {
  return apiClient.post(url, data)
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