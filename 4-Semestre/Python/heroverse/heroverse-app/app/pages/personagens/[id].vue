<template>
  <div class="grid" style="gap: 16px">
    <div class="section-header">
      <div>
        <p class="muted" style="margin: 0">Detalhe</p>
        <h2 class="title" style="font-size: 24px">
          {{ personagem?.nome || 'Personagem' }}
          <span class="pill">Info</span>
        </h2>
      </div>
      <div class="form-actions">
        <NuxtLink class="btn btn-ghost" to="/personagens">Voltar</NuxtLink>
        <NuxtLink v-if="personagem" class="btn btn-primary" :to="`/personagens/${personagem.id}/editar`">
          Editar
        </NuxtLink>
      </div>
    </div>

    <div v-if="error" class="alert error">
      {{ error }}
    </div>

    <div class="card" v-if="personagem">
      <div class="media">
        <img v-if="personagem.imagem_url" :src="personagem.imagem_url" alt="Imagem do personagem" />
        <div v-else class="badge">Sem imagem</div>
        <div>
          <h3 style="margin: 0 0 8px; font-size: 22px">{{ personagem.nome }}</h3>
          <div class="muted">Poder principal: {{ personagem.poder_principal || '—' }}</div>
          <div class="muted">Idade: {{ personagem.idade || '—' }}</div>
        </div>
      </div>
      <div style="margin-top: 14px; display: flex; gap: 10px; flex-wrap: wrap">
        <span class="badge">Universo #{{ personagem.universo_id }}</span>
        <span class="badge">Tipo #{{ personagem.tipo_id }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Personagem } from '~/types/api'

const personagensApi = usePersonagens()
const route = useRoute()

const personagem = ref<Personagem | null>(null)
const error = ref('')

const load = async () => {
  error.value = ''
  try {
    personagem.value = await personagensApi.find(route.params.id as string)
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Personagem não encontrado.'
  }
}

onMounted(load)
</script>
