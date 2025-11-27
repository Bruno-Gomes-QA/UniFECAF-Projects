<template>
  <div class="grid" style="gap: 16px">
    <div class="section-header">
      <div>
        <p class="muted" style="margin: 0">Criar</p>
        <h2 class="title" style="font-size: 24px">
          Novo Universo
          <span class="pill">Cadastro</span>
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
          {{ loading ? 'Salvando...' : 'Salvar' }}
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
const universosApi = useUniversos()
const router = useRouter()

const form = reactive({
  nome: '',
  descricao: ''
})

const error = ref('')
const success = ref('')
const loading = ref(false)

const handleSubmit = async () => {
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await universosApi.create({
      nome: form.nome,
      descricao: form.descricao || undefined
    })
    success.value = 'Universo criado!'
    setTimeout(() => router.push('/universos'), 700)
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao criar universo.'
  } finally {
    loading.value = false
  }
}
</script>
