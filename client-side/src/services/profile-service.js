// src/services/profile-service.js
// Service for Profile-related API calls
// Exposes: getMyProfile(), updateMyProfile()

import { useAuthStore } from '@/stores/auth'

const BASE_URL = import.meta.env.VITE_API_URL?.replace(/\/$/, '') || ''

/**
 * Fetch current user's profile
 * GET /api/profile/me
 * Expected response shape (ProfileResponse):
 * {
 *   id: string,
 *   userId: string,
 *   firstName: string,
 *   lastName: string,
 *   email: string,
 *   contactNumber: string,
 *   position: string,
 *   avatarKey: string,
 *   avatarUrl: string,
 *   createdAt: string,
 *   updatedAt: string,
 *   createdBy: string,
 *   updatedBy: string
 * }
 */
export async function getMyProfile() {
  const auth = useAuthStore()
  const token = auth.accessToken

  if (!BASE_URL) {
    console.warn('VITE_API_URL is not configured; profile-service cannot call API')
  }

  const url = `${BASE_URL}/api/profile/me`

  const res = await fetch(url, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
    },
    credentials: 'include',
  })

  if (!res.ok) {
    const text = await res.text().catch(() => '')
    const error = new Error(`Failed to fetch profile: ${res.status} ${res.statusText}${text ? ' - ' + text : ''}`)
    error.status = res.status
    throw error
  }

  const data = await res.json()
  return data
}

/**
 * Update current user's profile
 * PUT /api/profile/me
 * Expected request payload:
 * {
 *   firstName: string,
 *   lastName: string,
 *   email: string,
 *   contactNumber: string,
 *   position: string
 * }
 */
export async function updateMyProfile(profileData) {
  const auth = useAuthStore()
  const token = auth.accessToken

  if (!BASE_URL) {
    console.warn('VITE_API_URL is not configured; profile-service cannot call API')
  }

  const url = `${BASE_URL}/api/profile/me`

  const res = await fetch(url, {
    method: 'PUT',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
    },
    credentials: 'include',
    body: JSON.stringify(profileData),
  })

  if (!res.ok) {
    const text = await res.text().catch(() => '')
    const error = new Error(`Failed to update profile: ${res.status} ${res.statusText}${text ? ' - ' + text : ''}`)
    error.status = res.status
    throw error
  }

  const data = await res.json()
  return data
}

export default { getMyProfile, updateMyProfile }
