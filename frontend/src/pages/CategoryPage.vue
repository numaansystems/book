<template>
  <div>
    <h2>Category Management</h2>
    <div>
      <label>Agency ID: <input v-model="agencyId" type="number" /></label>
      <button @click="load">Load</button>
    </div>
    <ul>
      <li v-for="c in categories" :key="c.id">{{ c.title }} - {{ c.description }}</li>
    </ul>
    <h3>Create Category</h3>
    <div>
      <input v-model="newTitle" placeholder="title" />
      <input v-model="newDescription" placeholder="description" />
      <button @click="create">Create</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return { agencyId: 1, categories: [], newTitle: '', newDescription: '' }
  },
  methods: {
    async load() {
      const res = await axios.get(`/api/categories/by-agency/${this.agencyId}`)
      this.categories = res.data
    },
    async create() {
      const payload = { title: this.newTitle, description: this.newDescription, createdBy: 'frontend' }
      await axios.post(`/api/categories?agencyId=${this.agencyId}`, payload)
      await this.load()
      this.newTitle = ''
      this.newDescription = ''
    }
  },
  mounted() { this.load() }
}
</script>
