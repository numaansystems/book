<template>
  <div>
    <div class="card">
      <div class="card-header">Snappy Management</div>

      <div v-if="error" class="alert alert-error">{{ error }}</div>
      <div v-if="success" class="alert alert-success">{{ success }}</div>

      <!-- Filters -->
      <div class="grid-2">
        <div class="form-group">
          <label class="form-label">Select Agency</label>
          <select v-model="selectedAgencyId" @change="onAgencyChange" class="form-control">
            <option value="">-- Select an Agency --</option>
            <option v-for="agency in agencies" :key="agency.id" :value="agency.id">
              {{ agency.name }}
            </option>
          </select>
        </div>

        <div class="form-group">
          <label class="form-label">Fiscal Year</label>
          <select v-model="selectedFiscalYear" @change="loadSnappies" class="form-control">
            <option value="">-- Select Fiscal Year --</option>
            <option v-if="biennium" :value="biennium.startYear">{{ biennium.startYear }}</option>
            <option v-if="biennium" :value="biennium.endYear">{{ biennium.endYear }}</option>
          </select>
        </div>
      </div>

      <!-- Snappy Form -->
      <div v-if="selectedAgencyId && selectedFiscalYear" class="form-section">
        <h3>{{ editingSnappy ? 'Edit Snappy' : 'Add New Snappy' }}</h3>
        
        <div v-if="isYear1ReadOnly" class="alert alert-error">
          Note: You are in the second year of the biennium. First year data is read-only.
        </div>

        <form @submit.prevent="saveSnappy">
          <div class="grid-2">
            <div class="form-group">
              <label class="form-label">Budget Code *</label>
              <select v-model="form.budgetCodeId" class="form-control" required>
                <option value="">-- Select Budget Code --</option>
                <option v-for="code in budgetCodes" :key="code.id" :value="code.id">
                  {{ code.code }} - {{ code.description }}
                </option>
              </select>
            </div>

            <div class="form-group">
              <label class="form-label">Category *</label>
              <select v-model="form.categoryId" class="form-control" required>
                <option value="">-- Select Category --</option>
                <option v-for="category in categories" :key="category.id" :value="category.id">
                  {{ category.title }}
                </option>
              </select>
            </div>
          </div>

          <div class="grid-2">
            <div class="form-group">
              <label class="form-label">Decision Status *</label>
              <select v-model="form.decisionStatus" class="form-control" required>
                <option value="">-- Select Status --</option>
                <option value="IN">IN</option>
                <option value="OUT">OUT</option>
              </select>
            </div>

            <div class="form-group">
              <label class="form-label">Sort Order</label>
              <input v-model.number="form.sortOrder" type="number" class="form-control" />
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">Title *</label>
            <input v-model="form.title" type="text" class="form-control" required />
          </div>

          <div class="form-group">
            <label class="form-label">Short Description</label>
            <textarea v-model="form.shortDescription" class="form-control" rows="2"></textarea>
          </div>

          <!-- Year 1 Amounts -->
          <h4>Year 1 Amounts</h4>
          <div class="grid-2">
            <div class="form-group">
              <label class="form-label">Recurring Requirements</label>
              <input v-model.number="form.year1RecurringRequirements" type="number" step="0.01" class="form-control" />
            </div>
            <div class="form-group">
              <label class="form-label">Non-Recurring Requirements</label>
              <input v-model.number="form.year1NonrecurringRequirements" type="number" step="0.01" class="form-control" />
            </div>
            <div class="form-group">
              <label class="form-label">Recurring Receipts</label>
              <input v-model.number="form.year1RecurringReceipts" type="number" step="0.01" class="form-control" />
            </div>
            <div class="form-group">
              <label class="form-label">Non-Recurring Receipts</label>
              <input v-model.number="form.year1NonrecurringReceipts" type="number" step="0.01" class="form-control" />
            </div>
            <div class="form-group">
              <label class="form-label">Recurring FTE (up to 3 decimal)</label>
              <input v-model.number="form.year1RecurringFte" type="number" step="0.001" class="form-control" />
            </div>
            <div class="form-group">
              <label class="form-label">Non-Recurring FTE (up to 3 decimal)</label>
              <input v-model.number="form.year1NonrecurringFte" type="number" step="0.001" class="form-control" />
            </div>
          </div>

          <!-- Year 2 Amounts -->
          <h4>Year 2 Amounts</h4>
          <div class="grid-2">
            <div class="form-group">
              <label class="form-label">Recurring Requirements</label>
              <input v-model.number="form.year2RecurringRequirements" type="number" step="0.01" class="form-control" />
            </div>
            <div class="form-group">
              <label class="form-label">Non-Recurring Requirements</label>
              <input v-model.number="form.year2NonrecurringRequirements" type="number" step="0.01" class="form-control" />
            </div>
            <div class="form-group">
              <label class="form-label">Recurring Receipts</label>
              <input v-model.number="form.year2RecurringReceipts" type="number" step="0.01" class="form-control" />
            </div>
            <div class="form-group">
              <label class="form-label">Non-Recurring Receipts</label>
              <input v-model.number="form.year2NonrecurringReceipts" type="number" step="0.01" class="form-control" />
            </div>
            <div class="form-group">
              <label class="form-label">Recurring FTE (up to 3 decimal)</label>
              <input v-model.number="form.year2RecurringFte" type="number" step="0.001" class="form-control" />
            </div>
            <div class="form-group">
              <label class="form-label">Non-Recurring FTE (up to 3 decimal)</label>
              <input v-model.number="form.year2NonrecurringFte" type="number" step="0.001" class="form-control" />
            </div>
          </div>

          <div class="actions">
            <button type="submit" class="btn btn-primary" :disabled="isYear1ReadOnly">
              {{ editingSnappy ? 'Update' : 'Create' }} Snappy
            </button>
            <button v-if="editingSnappy" type="button" @click="cancelEdit" class="btn btn-secondary">
              Cancel
            </button>
          </div>
        </form>
      </div>

      <!-- Snappies List -->
      <div v-if="selectedAgencyId && selectedFiscalYear && snappies.length > 0" class="snappies-list">
        <h3>Existing Snappies</h3>
        <table>
          <thead>
            <tr>
              <th>Title</th>
              <th>Budget Code</th>
              <th>Category</th>
              <th>Status</th>
              <th>Sort Order</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="snappy in snappies" :key="snappy.id">
              <td>{{ snappy.title }}</td>
              <td>{{ snappy.budgetCodeCode }}</td>
              <td>{{ snappy.categoryTitle }}</td>
              <td>{{ snappy.decisionStatus }}</td>
              <td>{{ snappy.sortOrder }}</td>
              <td>
                <div class="actions">
                  <button @click="editSnappy(snappy)" class="btn btn-primary" :disabled="isYear1ReadOnly">
                    Edit
                  </button>
                  <button @click="confirmDelete(snappy)" class="btn btn-danger" :disabled="isYear1ReadOnly">
                    Delete
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div v-else-if="selectedAgencyId && selectedFiscalYear && snappies.length === 0" class="loading">
        No snappies found for this agency and fiscal year.
      </div>
    </div>
  </div>
</template>

<script>
import api from '../services/api'

export default {
  name: 'SnappyManagement',
  data() {
    return {
      agencies: [],
      budgetCodes: [],
      categories: [],
      snappies: [],
      biennium: null,
      selectedAgencyId: '',
      selectedFiscalYear: '',
      editingSnappy: null,
      form: {
        budgetCodeId: '',
        categoryId: '',
        decisionStatus: '',
        sortOrder: null,
        title: '',
        shortDescription: '',
        year1RecurringRequirements: null,
        year1NonrecurringRequirements: null,
        year1RecurringReceipts: null,
        year1NonrecurringReceipts: null,
        year1RecurringFte: null,
        year1NonrecurringFte: null,
        year2RecurringRequirements: null,
        year2NonrecurringRequirements: null,
        year2RecurringReceipts: null,
        year2NonrecurringReceipts: null,
        year2RecurringFte: null,
        year2NonrecurringFte: null
      },
      error: '',
      success: ''
    }
  },
  computed: {
    isYear1ReadOnly() {
      if (!this.biennium || !this.selectedFiscalYear) return false
      return this.biennium.currentFiscalYear === this.biennium.endYear && 
             this.selectedFiscalYear === this.biennium.startYear
    }
  },
  mounted() {
    this.loadAgencies()
    this.loadBiennium()
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
    async loadBiennium() {
      try {
        const response = await api.getActiveBiennium()
        this.biennium = response.data
      } catch (err) {
        this.error = 'Failed to load biennium: ' + err.message
      }
    },
    async onAgencyChange() {
      if (!this.selectedAgencyId) return
      
      try {
        this.error = ''
        const [budgetCodesRes, categoriesRes] = await Promise.all([
          api.getBudgetCodesByAgency(this.selectedAgencyId),
          api.getCategoriesByAgency(this.selectedAgencyId)
        ])
        this.budgetCodes = budgetCodesRes.data
        this.categories = categoriesRes.data
        
        if (this.selectedFiscalYear) {
          await this.loadSnappies()
        }
      } catch (err) {
        this.error = 'Failed to load data: ' + err.message
      }
    },
    async loadSnappies() {
      if (!this.selectedAgencyId || !this.selectedFiscalYear) return
      
      try {
        this.error = ''
        const response = await api.getSnappies(this.selectedAgencyId, this.selectedFiscalYear)
        this.snappies = response.data
      } catch (err) {
        this.error = 'Failed to load snappies: ' + err.message
      }
    },
    async saveSnappy() {
      if (this.isYear1ReadOnly) {
        this.error = 'Cannot modify first year data in second year'
        return
      }

      try {
        this.error = ''
        this.success = ''

        const data = {
          ...this.form,
          agencyId: this.selectedAgencyId,
          fiscalYear: this.selectedFiscalYear
        }

        if (this.editingSnappy) {
          await api.updateSnappy(this.editingSnappy.id, data)
          this.success = 'Snappy updated successfully!'
        } else {
          await api.createSnappy(data)
          this.success = 'Snappy created successfully!'
        }

        this.resetForm()
        await this.loadSnappies()
      } catch (err) {
        this.error = err.response?.data?.message || 'Failed to save snappy: ' + err.message
      }
    },
    editSnappy(snappy) {
      this.editingSnappy = snappy
      this.form = {
        budgetCodeId: snappy.budgetCodeId,
        categoryId: snappy.categoryId,
        decisionStatus: snappy.decisionStatus,
        sortOrder: snappy.sortOrder,
        title: snappy.title,
        shortDescription: snappy.shortDescription,
        year1RecurringRequirements: snappy.year1RecurringRequirements,
        year1NonrecurringRequirements: snappy.year1NonrecurringRequirements,
        year1RecurringReceipts: snappy.year1RecurringReceipts,
        year1NonrecurringReceipts: snappy.year1NonrecurringReceipts,
        year1RecurringFte: snappy.year1RecurringFte,
        year1NonrecurringFte: snappy.year1NonrecurringFte,
        year2RecurringRequirements: snappy.year2RecurringRequirements,
        year2NonrecurringRequirements: snappy.year2NonrecurringRequirements,
        year2RecurringReceipts: snappy.year2RecurringReceipts,
        year2NonrecurringReceipts: snappy.year2NonrecurringReceipts,
        year2RecurringFte: snappy.year2RecurringFte,
        year2NonrecurringFte: snappy.year2NonrecurringFte
      }
      window.scrollTo({ top: 0, behavior: 'smooth' })
    },
    cancelEdit() {
      this.resetForm()
    },
    async confirmDelete(snappy) {
      if (this.isYear1ReadOnly) {
        this.error = 'Cannot modify first year data in second year'
        return
      }

      if (confirm(`Are you sure you want to delete "${snappy.title}"?`)) {
        try {
          this.error = ''
          this.success = ''
          await api.deleteSnappy(snappy.id)
          this.success = 'Snappy deleted successfully!'
          await this.loadSnappies()
        } catch (err) {
          this.error = 'Failed to delete snappy: ' + err.message
        }
      }
    },
    resetForm() {
      this.editingSnappy = null
      this.form = {
        budgetCodeId: '',
        categoryId: '',
        decisionStatus: '',
        sortOrder: null,
        title: '',
        shortDescription: '',
        year1RecurringRequirements: null,
        year1NonrecurringRequirements: null,
        year1RecurringReceipts: null,
        year1NonrecurringReceipts: null,
        year1RecurringFte: null,
        year1NonrecurringFte: null,
        year2RecurringRequirements: null,
        year2NonrecurringRequirements: null,
        year2RecurringReceipts: null,
        year2NonrecurringReceipts: null,
        year2RecurringFte: null,
        year2NonrecurringFte: null
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

.snappies-list {
  margin-top: 2rem;
  padding-top: 2rem;
  border-top: 1px solid #ddd;
}

h3, h4 {
  margin-bottom: 1rem;
  color: #2c3e50;
}

h4 {
  margin-top: 1.5rem;
  font-size: 1.1rem;
}
</style>
