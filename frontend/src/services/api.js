import axios from 'axios'

const API_BASE_URL = '/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

export default {
  // Agency APIs
  getAgencies() {
    return api.get('/agencies')
  },

  // Budget Code APIs
  getBudgetCodesByAgency(agencyId) {
    return api.get('/budget-codes', { params: { agencyId } })
  },

  // Category APIs
  getCategoriesByAgency(agencyId) {
    return api.get('/categories', { params: { agencyId } })
  },

  createCategory(data) {
    return api.post('/categories', data)
  },

  updateCategory(id, data) {
    return api.put(`/categories/${id}`, data)
  },

  deleteCategory(id) {
    return api.delete(`/categories/${id}`)
  },

  // Snappy APIs
  getSnappies(agencyId, fiscalYear) {
    return api.get('/snappies', { params: { agencyId, fiscalYear } })
  },

  createSnappy(data) {
    return api.post('/snappies', data)
  },

  updateSnappy(id, data) {
    return api.put(`/snappies/${id}`, data)
  },

  deleteSnappy(id) {
    return api.delete(`/snappies/${id}`)
  },

  // Biennium APIs
  getActiveBiennium() {
    return api.get('/biennium/active')
  }
}
