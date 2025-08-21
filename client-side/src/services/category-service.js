import { get, post, put } from './base-api.js'

const API_URL = '/api/task-categories'

export const categoryService = {
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
                error: error.message || 'Failed to fetch categories'
            }
        }
    },
    
    async getCategoryById(id) {
        try {
            const res = await get(`${API_URL}/${id}`);
            return {
                success: true,
                data: res?.values || [],
                error: null
            };
        } catch (error) {
            return {
                success: false,
                data: null,
                error: error.message || 'Failed to fetch category by ID'
            };
        }
    },

    
    async createCategory(data) {
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
                error: error.message || 'Failed to create category'
            }
        }
    },

    async updateCategory(id, data) {
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
                error: error.message || 'Failed to update category'
            }
        }
    },
}