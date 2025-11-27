<template>
  <div class="grid" style="gap: 16px">
    <div class="section-header">
      <div>
        <p class="muted" style="margin: 0">Criar</p>
        <h2 class="title" style="font-size: 24px">
          Novo Personagem
          <span class="pill">Cadastro</span>
        </h2>
      </div>
      <NuxtLink class="btn btn-ghost" to="/personagens">Voltar</NuxtLink>
    </div>

    <div v-if="error" class="alert error">
      {{ error }}
    </div>
    <div v-if="success" class="alert success">
      {{ success }}
    </div>

    <div class="card">
      <PersonagemForm
        :universos="universos"
        :tipos="tipos"
        submit-label="Salvar"
        :loading="loading"
        @submit="handleCreate"
        @cancel="router.push('/personagens')"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Tipo, Universo } from '~/types/api'

const personagensApi = usePersonagens()
const universosApi = useUniversos()
const tiposApi = useTipos()
const router = useRouter()

const universos = ref<Universo[]>([])
const tipos = ref<Tipo[]>([])
const error = ref('')
const success = ref('')
const loading = ref(false)

const loadOptions = async () => {
  try {
    ;[universos.value, tipos.value] = await Promise.all([universosApi.list(), tiposApi.list()])
  } catch (err: any) {
    error.value = err?.data?.detail || 'Erro ao carregar opções de universo/tipo.'
  }
}

const handleCreate = async (payload: any) => {
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    const created = await personagensApi.create(payload)
    success.value = 'Personagem criado com sucesso!'
    setTimeout(() => router.push(`/personagens/${created.id}`), 800)
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao criar personagem.'
  } finally {
    loading.value = false
  }
}

onMounted(loadOptions)
</script>
