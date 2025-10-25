<template>
  <div>
    <div class="card">
      <div class="card-header">Category Management</div>

      <div v-if="error" class="alert alert-error">{{ error }}</div>
      <div v-if="success" class="alert alert-success">{{ success }}</div>

      <!-- Agency Selection -->
      <div class="form-group">
        <label class="form-label">Select Agency</label>
        <select v-model="selectedAgencyId" @change="loadCategories" class="form-control">
          <option value="">-- Select an Agency --</option>
          <option v-for="agency in agencies" :key="agency.id" :value="agency.id">
            {{ agency.name }}
          </option>
        </select>
      </div>

      <!-- Category Form -->
      <div v-if="selectedAgencyId" class="form-section">
        <h3>{{ editingCategory ? 'Edit Category' : 'Add New Category' }}</h3>
        <form @submit.prevent="saveCategory">
          <div class="form-group">
            <label class="form-label">Title *</label>
            <input 
              v-model="form.title" 
              type="text" 
              class="form-control" 
              required
              placeholder="Enter category title"
            />
          </div>

          <div class="form-group">
            <label class="form-label">Description</label>
            <textarea 
              v-model="form.description" 
              class="form-control" 
              rows="3"
              placeholder="Enter category description"
            ></textarea>
          </div>

          <div class="form-group">
            <label class="form-label">Created/Updated By</label>
            <input 
              v-model="form.createdBy" 
              type="text" 
              class="form-control"
              placeholder="Enter your name"
            />
          </div>

          <div class="actions">
            <button type="submit" class="btn btn-primary">
              {{ editingCategory ? 'Update' : 'Create' }} Category
            </button>
            <button 
              v-if="editingCategory" 
              type="button" 
              @click="cancelEdit" 
              class="btn btn-secondary"
            >
              Cancel
            </button>
          </div>
        </form>
      </div>

      <!-- Categories List -->
      <div v-if="selectedAgencyId && categories.length > 0" class="categories-list">
        <h3>Existing Categories</h3>
        <table>
          <thead>
            <tr>
              <th>Title</th>
              <th>Description</th>
              <th>Created By</th>
              <th>Updated By</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="category in categories" :key="category.id">
              <td>{{ category.title }}</td>
              <td>{{ category.description }}</td>
              <td>{{ category.createdBy }}</td>
              <td>{{ category.updatedBy }}</td>
              <td>
                <div class="actions">
                  <button @click="editCategory(category)" class="btn btn-primary">Edit</button>
                  <button @click="confirmDelete(category)" class="btn btn-danger">Delete</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div v-else-if="selectedAgencyId && categories.length === 0" class="loading">
        No categories found for this agency.
      </div>
    </div>
  </div>
</template>

<script>
import api from '../services/api'

export default {
  name: 'CategoryManagement',
  data() {
    return {
      agencies: [],
      categories: [],
      selectedAgencyId: '',
      editingCategory: null,
      form: {
        title: '',
        description: '',
        createdBy: ''
      },
      error: '',
      success: ''
    }
  },
  mounted() {
    this.loadAgencies()
  },
  methods: {
    async loadAgencies() {
      try {
        const response = await api.getAgencies()
        this.agencies = response.data
      } catch (err) {
        this.error = 'Failed to load agencies: ' + err.message
      }
    },
    async loadCategories() {
      if (!this.selectedAgencyId) return
      
      try {
        this.error = ''
        const response = await api.getCategoriesByAgency(this.selectedAgencyId)
        this.categories = response.data
      } catch (err) {
        this.error = 'Failed to load categories: ' + err.message
      }
    },
    async saveCategory() {
      try {
        this.error = ''
        this.success = ''

        const data = {
          ...this.form,
          agencyId: this.selectedAgencyId,
          updatedBy: this.form.createdBy
        }

        if (this.editingCategory) {
          await api.updateCategory(this.editingCategory.id, data)
          this.success = 'Category updated successfully!'
        } else {
          await api.createCategory(data)
          this.success = 'Category created successfully!'
        }

        this.resetForm()
        await this.loadCategories()
      } catch (err) {
        this.error = err.response?.data?.message || 'Failed to save category: ' + err.message
      }
    },
    editCategory(category) {
      this.editingCategory = category
      this.form = {
        title: category.title,
        description: category.description,
        createdBy: category.updatedBy || category.createdBy
      }
      window.scrollTo({ top: 0, behavior: 'smooth' })
    },
    cancelEdit() {
      this.resetForm()
    },
    async confirmDelete(category) {
      if (confirm(`Are you sure you want to delete "${category.title}"?`)) {
        try {
          this.error = ''
          this.success = ''
          await api.deleteCategory(category.id)
          this.success = 'Category deleted successfully!'
          await this.loadCategories()
        } catch (err) {
          this.error = 'Failed to delete category: ' + err.message
        }
      }
    },
    resetForm() {
      this.editingCategory = null
      this.form = {
        title: '',
        description: '',
        createdBy: ''
      }
    }
  }
}
</script>

<style scoped>
.form-section {
  margin-top: 2rem;
  padding-top: 2rem;
  border-top: 1px solid #ddd;
}

.categories-list {
  margin-top: 2rem;
  padding-top: 2rem;
  border-top: 1px solid #ddd;
}

h3 {
  margin-bottom: 1rem;
  color: #2c3e50;
}
</style>
