import { get, post, put, del } from './base-api.js'

const API_URL = '/api/tasks'

export const taskService = {
    async getAll() {
        try {
            const res = await get(API_URL)
            return {
                success: true,
                data: res?.content || [],
                error: null
            }
        } catch (error) {
            return {
                success: false,
                data: null,
                error: error.message || 'Failed to fetch tasks'
            }
        }
    },
    async getTaskById(id) {
        try {
            const res = await get(`${API_URL}/${id}`)
            return {
                success: true,
                data: res || [],
                error: null
            }
        } catch (error) {
            return {
                success: false,
                data: null,
                error: error.message || 'Failed to fetch task by ID'
            }
        }
    },
    async createTask(data) {
        try {
            const res = await post(API_URL, data)
            return {
                success: true,
                data: res,
                error: null
            }
        } catch (error) {
            return {
                success: false,
                data: null,
                error: error.message || 'Failed to create task'
            }
        }
    },

    async updateTask(id, data) {
        try {
            const res = await put(`${API_URL}/${id}`, data)
            return {
                success: true,
                data: res,
                error: null
            }
        } catch (error) {
            return {
                success: false,
                data: null,
                error: error.message || 'Failed to update task'
            }
        }
    },

    async deleteTask(id) {
        try {
            const res = await del(`${API_URL}/${id}`)
            return {
                success: true,
                data: res,
                error: null
            }
        } catch (error) {
            return {
                success: false,
                data: null,
                error: error.message || 'Failed to delete task'
            }
        }
    },

    async uploadTaskImage(taskId, file) {
        const formData = new FormData();
        formData.append('file', file);
        try {
            const res = await post(`${API_URL}/${taskId}/image`, formData);
            return {
                success: true,
                data: res,
                error: null
            }
        } catch (error) {
            console.error('Upload image error:', error);
            return {
                success: false,
                data: null,
                error: error.message || 'Failed to upload task image'
            }
        }
    }
}
