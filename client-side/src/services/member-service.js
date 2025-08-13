import { get } from './base-api.js'

export const getAllMembers = async () => {
  try {
    const response = await get('/api/members')
    return {
      success: true,
      data: response,
      error: null
    }
  } catch (error) {
    return {
      success: false,
      data: null,
      error: error.message || 'Failed to fetch members'
    }
  }
}

export default {
  getAllMembers,
}