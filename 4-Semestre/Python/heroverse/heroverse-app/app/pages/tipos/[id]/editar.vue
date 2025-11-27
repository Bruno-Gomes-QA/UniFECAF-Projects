<template>
  <div class="grid" style="gap: 16px">
    <div class="section-header">
      <div>
        <p class="muted" style="margin: 0">Editar</p>
        <h2 class="title" style="font-size: 24px">
          {{ form.nome || 'Tipo' }}
          <span class="pill">Edição</span>
        </h2>
      </div>
      <NuxtLink class="btn btn-ghost" to="/tipos">Voltar</NuxtLink>
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
          <input id="nome" v-model="form.nome" type="text" required placeholder="Herói" />
        </div>
        <div class="field">
          <label for="descricao">Descrição</label>
          <textarea id="descricao" v-model="form.descricao" placeholder="Personagem do bem" rows="3" />
        </div>
        <button class="btn btn-primary" type="submit" :disabled="loading">
          {{ loading ? 'Salvando...' : 'Salvar alterações' }}
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Tipo } from '~/types/api'

const tiposApi = useTipos()
const route = useRoute()

const form = reactive<Pick<Tipo, 'nome' | 'descricao'>>({
  nome: '',
  descricao: ''
})

const error = ref('')
const success = ref('')
const loading = ref(false)

const load = async () => {
  error.value = ''
  try {
    const data = await tiposApi.find(route.params.id as string)
    form.nome = data.nome
    form.descricao = data.descricao || ''
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao carregar tipo.'
  }
}

const handleSubmit = async () => {
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await tiposApi.update(route.params.id as string, {
      nome: form.nome,
      descricao: form.descricao || undefined
    })
    success.value = 'Tipo atualizado!'
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao atualizar tipo.'
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>
