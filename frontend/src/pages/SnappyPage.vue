<template>
  <div>
    <h2>Snappy Management</h2>
    <div>
      <label>Agency ID: <input v-model="agencyId" type="number" /></label>
      <button @click="load">Load</button>
    </div>
    <ul>
      <li v-for="s in snappies" :key="s.id">{{ s.title }} - {{ s.decisionStatus }}</li>
    </ul>

    <h3>Create Snappy</h3>
    <div>
      <label>BudgetCodeId: <input v-model.number="budgetCodeId" type="number" /></label>
      <label>CategoryId: <input v-model.number="categoryId" type="number" /></label>
      <label>Title: <input v-model="title" /></label>
      <label>Decision: <select v-model="decision"><option>in</option><option>out</option></select></label>
      <button @click="create">Create Snappy</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() { return { agencyId:1, snappies: [], budgetCodeId:null, categoryId:null, title:'', decision:'in' } },
  methods: {
    async load() { const res = await axios.get(`/api/snappies/by-agency/${this.agencyId}`); this.snappies = res.data },
    async create() {
      const payload = { agency: { id: this.agencyId }, budgetCode: { id: this.budgetCodeId }, category: { id: this.categoryId }, title: this.title, decisionStatus: this.decision }
      await axios.post('/api/snappies', payload)
      await this.load()
      this.title = ''
    }
  },
  mounted() { this.load() }
}
</script>