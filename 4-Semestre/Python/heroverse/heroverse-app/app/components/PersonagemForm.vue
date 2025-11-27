<template>
  <form class="grid" style="gap: 16px" @submit.prevent="handleSubmit">
    <div class="field">
      <label for="nome">Nome</label>
      <input id="nome" v-model="form.nome" type="text" required placeholder="Homem Azul" />
    </div>

    <div class="grid grid-3">
      <div class="field">
        <label for="universo">Universo</label>
        <select id="universo" v-model.number="form.universo_id" required>
          <option value="" disabled>Selecione</option>
          <option v-for="u in universos" :key="u.id" :value="u.id">
            {{ u.nome }}
          </option>
        </select>
      </div>

      <div class="field">
        <label for="tipo">Tipo</label>
        <select id="tipo" v-model.number="form.tipo_id" required>
          <option value="" disabled>Selecione</option>
          <option v-for="t in tipos" :key="t.id" :value="t.id">
            {{ t.nome }}
          </option>
        </select>
      </div>

      <div class="field">
        <label for="idade">Idade</label>
        <input id="idade" v-model.number="form.idade" type="number" min="0" placeholder="30" />
      </div>
    </div>

    <div class="grid">
      <div class="field">
        <label for="poder">Poder principal</label>
        <input id="poder" v-model="form.poder_principal" type="text" placeholder="Voar, super força, lasers..." />
      </div>

      <div class="field">
        <label for="imagem">URL da imagem</label>
        <input id="imagem" v-model="form.imagem_url" type="url" placeholder="https://imagem.com/hero.png" />
      </div>
    </div>

    <div class="form-actions">
      <button class="btn btn-primary" type="submit" :disabled="loading">
        {{ submitLabel || 'Salvar' }}
      </button>
      <button class="btn btn-ghost" type="button" @click="$emit('cancel')">
        Cancelar
      </button>
    </div>
  </form>
</template>

<script setup lang="ts">
import type { Personagem, PersonagemPayload, Tipo, Universo } from '~/types/api'

const props = defineProps<{
  initial?: Partial<Personagem>
  universos: Universo[]
  tipos: Tipo[]
  submitLabel?: string
  loading?: boolean
}>()

const emit = defineEmits<{
  submit: [PersonagemPayload]
  cancel: []
}>()

const form = reactive<{
  nome: string
  universo_id: number | ''
  tipo_id: number | ''
  idade: number | ''
  imagem_url: string
  poder_principal: string
}>({
  nome: '',
  universo_id: '',
  tipo_id: '',
  idade: '',
  imagem_url: '',
  poder_principal: ''
})

const primeUniverso = () => {
  if (!form.universo_id && props.universos.length) {
    form.universo_id = props.universos[0].id
  }
}

const primeTipo = () => {
  if (!form.tipo_id && props.tipos.length) {
    form.tipo_id = props.tipos[0].id
  }
}

watch(
  () => props.initial,
  (value) => {
    if (value) {
      form.nome = value.nome || ''
      form.universo_id = value.universo_id ?? ''
      form.tipo_id = value.tipo_id ?? ''
      form.idade = value.idade ?? ''
      form.imagem_url = value.imagem_url || ''
      form.poder_principal = value.poder_principal || ''
    }
    primeUniverso()
    primeTipo()
  },
  { immediate: true }
)

watch(
  () => props.universos,
  () => primeUniverso(),
  { deep: true }
)

watch(
  () => props.tipos,
  () => primeTipo(),
  { deep: true }
)

const handleSubmit = () => {
  if (!form.nome || !form.universo_id || !form.tipo_id) return

  const payload: PersonagemPayload = {
    nome: form.nome,
    universo_id: Number(form.universo_id),
    tipo_id: Number(form.tipo_id),
    idade: form.idade === '' ? undefined : Number(form.idade),
    imagem_url: form.imagem_url || undefined,
    poder_principal: form.poder_principal || undefined
  }

  emit('submit', payload)
}
</script>
