<template>
  <div class="grid" style="gap: 16px">
    <div class="section-header">
      <div>
        <p class="muted" style="margin: 0">Editar</p>
        <h2 class="title" style="font-size: 24px">
          {{ form.nome || 'Universo' }}
          <span class="pill">Edição</span>
        </h2>
      </div>
      <NuxtLink class="btn btn-ghost" to="/universos">Voltar</NuxtLink>
    </div>

    <div v-if="error" class="alert error">
      {{ error }}
    </div>
    <div v-if="success" class="alert success">
      {{ success }}
    </div>

    <div class="card">
      <form class="grid" style="gap: 14px" @submit.prevent="handleSubmit">
        <div class="field">
          <label for="nome">Nome</label>
          <input id="nome" v-model="form.nome" type="text" required placeholder="Terra-616" />
        </div>
        <div class="field">
          <label for="descricao">Descrição</label>
          <textarea
            id="descricao"
            v-model="form.descricao"
            placeholder="Universo principal, linha do tempo padrão..."
            rows="3"
          />
        </div>
        <button class="btn btn-primary" type="submit" :disabled="loading">
          {{ loading ? 'Salvando...' : 'Salvar alterações' }}
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Universo } from '~/types/api'

const universosApi = useUniversos()
const route = useRoute()

const form = reactive<Pick<Universo, 'nome' | 'descricao'>>({
  nome: '',
  descricao: ''
})

const error = ref('')
const success = ref('')
const loading = ref(false)

const load = async () => {
  error.value = ''
  try {
    const data = await universosApi.find(route.params.id as string)
    form.nome = data.nome
    form.descricao = data.descricao || ''
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao carregar universo.'
  }
}

const handleSubmit = async () => {
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await universosApi.update(route.params.id as string, {
      nome: form.nome,
      descricao: form.descricao || undefined
    })
    success.value = 'Universo atualizado!'
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao atualizar universo.'
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>
