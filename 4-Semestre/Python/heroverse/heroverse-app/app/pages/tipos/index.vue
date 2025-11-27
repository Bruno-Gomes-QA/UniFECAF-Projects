<template>
  <div class="grid" style="gap: 16px">
    <div class="section-header">
      <div>
        <p class="muted" style="margin: 0">Classificações</p>
        <h2 class="title" style="font-size: 24px">
          Tipos
          <span class="pill">CRUD</span>
        </h2>
      </div>
      <NuxtLink class="btn btn-primary" to="/tipos/novo">Novo Tipo</NuxtLink>
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
          <tr v-if="!loading && tipos.length === 0">
            <td colspan="3" class="muted">Nenhum tipo cadastrado.</td>
          </tr>
          <tr v-for="t in tipos" :key="t.id">
            <td>{{ t.nome }}</td>
            <td class="muted">{{ t.descricao || '—' }}</td>
            <td style="display: flex; gap: 8px">
              <NuxtLink class="btn btn-ghost" :to="`/tipos/${t.id}/editar`">Editar</NuxtLink>
              <button class="btn btn-danger" type="button" @click="handleDelete(t.id)">Excluir</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Tipo } from '~/types/api'

const tiposApi = useTipos()
const tipos = ref<Tipo[]>([])
const loading = ref(true)
const error = ref('')

const load = async () => {
  error.value = ''
  loading.value = true
  try {
    tipos.value = await tiposApi.list()
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao carregar tipos.'
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id: number) => {
  if (!confirm('Deseja remover este tipo?')) return
  try {
    await tiposApi.remove(id)
    await load()
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao excluir tipo.'
  }
}

onMounted(load)
</script>
