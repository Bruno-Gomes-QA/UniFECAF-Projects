<template>
  <div class="grid" style="gap: 16px">
    <div class="section-header">
      <div>
        <p class="muted" style="margin: 0">Coleção</p>
        <h2 class="title" style="font-size: 24px">
          Personagens
          <span class="pill">CRUD</span>
        </h2>
      </div>
      <NuxtLink class="btn btn-primary" to="/personagens/novo">Novo Personagem</NuxtLink>
    </div>

    <div v-if="error" class="alert error">
      {{ error }}
    </div>

    <div class="card">
      <table class="table">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Universo</th>
            <th>Tipo</th>
            <th>Poder</th>
            <th />
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading">
            <td colspan="5" class="muted">Carregando...</td>
          </tr>
          <tr v-if="!loading && personagens.length === 0">
            <td colspan="5" class="muted">Nenhum personagem cadastrado.</td>
          </tr>
          <tr v-for="p in personagens" :key="p.id">
            <td>{{ p.nome }}</td>
            <td>
              <span class="badge">Universo #{{ p.universo_id }}</span>
            </td>
            <td>
              <span class="badge">Tipo #{{ p.tipo_id }}</span>
            </td>
            <td>{{ p.poder_principal || '—' }}</td>
            <td style="display: flex; gap: 8px; flex-wrap: wrap">
              <NuxtLink class="btn btn-primary" :to="`/personagens/${p.id}`">Ver</NuxtLink>
              <NuxtLink class="btn btn-ghost" :to="`/personagens/${p.id}/editar`">Editar</NuxtLink>
              <button class="btn btn-danger" type="button" @click="handleDelete(p.id)">
                Excluir
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Personagem } from '~/types/api'

const personagensApi = usePersonagens()
const personagens = ref<Personagem[]>([])
const loading = ref(true)
const error = ref('')

const load = async () => {
  error.value = ''
  loading.value = true
  try {
    personagens.value = await personagensApi.list()
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao carregar personagens.'
  } finally {
    loading.value = false
  }
}

const handleDelete = async (id: number) => {
  if (!confirm('Deseja mesmo remover este personagem?')) return
  try {
    await personagensApi.remove(id)
    await load()
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao excluir personagem.'
  }
}

onMounted(load)
</script>
