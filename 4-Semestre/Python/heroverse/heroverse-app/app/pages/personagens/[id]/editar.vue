<template>
  <div class="grid" style="gap: 16px">
    <div class="section-header">
      <div>
        <p class="muted" style="margin: 0">Editar</p>
        <h2 class="title" style="font-size: 24px">
          {{ personagem?.nome || 'Personagem' }}
          <span class="pill">Edição</span>
        </h2>
      </div>
      <div class="form-actions">
        <NuxtLink class="btn btn-ghost" :to="personagem ? `/personagens/${personagem.id}` : '/personagens'">
          Cancelar
        </NuxtLink>
        <NuxtLink class="btn btn-primary" to="/personagens">Lista</NuxtLink>
      </div>
    </div>

    <div v-if="error" class="alert error">
      {{ error }}
    </div>
    <div v-if="success" class="alert success">
      {{ success }}
    </div>

    <div class="card" v-if="personagem">
      <PersonagemForm
        :initial="personagem"
        :universos="universos"
        :tipos="tipos"
        submit-label="Salvar alterações"
        :loading="loading"
        @submit="handleUpdate"
        @cancel="router.push(`/personagens/${personagem.id}`)"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Personagem, Tipo, Universo } from '~/types/api'

const personagensApi = usePersonagens()
const universosApi = useUniversos()
const tiposApi = useTipos()
const route = useRoute()
const router = useRouter()

const personagem = ref<Personagem | null>(null)
const universos = ref<Universo[]>([])
const tipos = ref<Tipo[]>([])
const error = ref('')
const success = ref('')
const loading = ref(false)

const loadData = async () => {
  error.value = ''
  try {
    const [p, u, t] = await Promise.all([
      personagensApi.find(route.params.id as string),
      universosApi.list(),
      tiposApi.list()
    ])
    personagem.value = p
    universos.value = u
    tipos.value = t
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao carregar dados.'
  }
}

const handleUpdate = async (payload: any) => {
  if (!personagem.value) return
  error.value = ''
  success.value = ''
  loading.value = true
  try {
    await personagensApi.update(personagem.value.id, payload)
    success.value = 'Personagem atualizado!'
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao atualizar personagem.'
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>
