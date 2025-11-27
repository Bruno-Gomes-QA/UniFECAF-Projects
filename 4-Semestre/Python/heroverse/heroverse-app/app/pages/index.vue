<template>
  <div class="dashboard">
    <section class="hero-banner">
      <div class="glow" />
      <div>
        <p class="muted" style="margin: 0">Bem-vindo de volta</p>
        <h1 class="title hero-title">
          Heroverse
          <span class="pill">Painel</span>
        </h1>
        <p class="muted" style="max-width: 620px">
          Dados em tempo real com neon azul e amarelo. Gerencie universos, tipos e personagens sem sair desta
          frequência.
        </p>
        <div class="hero-actions">
          <NuxtLink class="btn btn-primary" to="/personagens/novo">Criar personagem</NuxtLink>
          <NuxtLink class="btn btn-ghost" to="/personagens">Ver personagens</NuxtLink>
        </div>
        <div class="chip api-chip">
          <span class="status-dot" />
          API: {{ apiBase }}
        </div>
      </div>
      <div class="hero-card card">
        <div class="muted" style="margin-bottom: 6px">Olá, {{ auth.user.value?.nome || 'herói' }}</div>
        <h3 style="margin: 0 0 10px; font-size: 22px">Estado do painel</h3>
        <div class="grid hero-grid">
          <div class="stat-card">
            <div class="muted">Personagens</div>
            <div class="stat-number">{{ loading ? '...' : stats.personagens }}</div>
            <NuxtLink to="/personagens" class="stat-link">Gerenciar</NuxtLink>
          </div>
          <div class="stat-card">
            <div class="muted">Universos</div>
            <div class="stat-number">{{ loading ? '...' : stats.universos }}</div>
            <NuxtLink to="/universos" class="stat-link">Explorar</NuxtLink>
          </div>
          <div class="stat-card">
            <div class="muted">Tipos</div>
            <div class="stat-number">{{ loading ? '...' : stats.tipos }}</div>
            <NuxtLink to="/tipos" class="stat-link">Organizar</NuxtLink>
          </div>
        </div>
      </div>
    </section>

    <div class="grid grid-3">
      <div />
      <div />
      <div />
    </div>

    <div class="card" v-if="error">
      <div class="alert error">
        {{ error }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const auth = useAuth()
const personagensApi = usePersonagens()
const universosApi = useUniversos()
const tiposApi = useTipos()
const config = useRuntimeConfig()

const stats = reactive({
  personagens: 0,
  universos: 0,
  tipos: 0
})

const loading = ref(true)
const error = ref('')
const apiBase = computed(() => config.public.apiBase)

const loadCounts = async () => {
  error.value = ''
  loading.value = true
  try {
    const [p, u, t] = await Promise.all([
      personagensApi.list(),
      universosApi.list(),
      tiposApi.list()
    ])
    stats.personagens = p.length
    stats.universos = u.length
    stats.tipos = t.length
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao carregar dados do dashboard.'
  } finally {
    loading.value = false
  }
}

onMounted(loadCounts)
</script>
