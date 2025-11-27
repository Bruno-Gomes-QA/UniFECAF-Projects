<template>
  <div class="grid" style="gap: 16px">
    <div class="section-header">
      <div>
        <p class="muted" style="margin: 0">Cenários</p>
        <h2 class="title" style="font-size: 24px">
          Universos
          <span class="pill">CRUD</span>
        </h2>
      </div>
      <NuxtLink class="btn btn-primary" to="/universos/novo">Novo Universo</NuxtLink>
    </div>

    <div v-if="error" class="alert error">
      {{ error }}
    </div>

    <div class="card">
      <table class="table">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Descrição</th>
            <th />
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading">
            <td colspan="3" class="muted">Carregando...</td>
          </tr>
          <tr v-if="!loading && universos.length === 0">
            <td colspan="3" class="muted">Nenhum universo cadastrado.</td>
          </tr>
          <tr v-for="u in universos" :key="u.id">
            <td>{{ u.nome }}</td>
            <td class="muted">{{ u.descricao || '—' }}</td>
            <td style="display: flex; gap: 8px">
              <NuxtLink class="btn btn-ghost" :to="`/universos/${u.id}/editar`">Editar</NuxtLink>
              <button class="btn btn-danger" type="button" @click="handleDelete(u.id)">Excluir</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Universo } from '~/types/api'

const universosApi = useUniversos()
const universos = ref<Universo[]>([])
const loading = ref(true)
const error = ref('')

const load = async () => {
  error.value = ''
  loading.value = true
  try {
    universos.value = await universosApi.list()
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao carregar universos.'
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id: number) => {
  if (!confirm('Deseja remover este universo?')) return
  try {
    await universosApi.remove(id)
    await load()
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao excluir universo.'
  }
}

onMounted(load)
</script>
