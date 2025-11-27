<template>
  <div>
    <h2 class="title" style="font-size: 24px">
      Entrar no Heroverse
      <span class="pill">Acesso</span>
    </h2>
    <p class="muted" style="margin-bottom: 18px">
      Use suas credenciais para acessar o painel neon. Segurança JWT com rotas protegidas.
    </p>

    <div v-if="error" class="alert error" style="margin-bottom: 12px">
      {{ error }}
    </div>

    <form class="grid" style="gap: 14px" @submit.prevent="handleLogin">
      <div class="field">
        <label for="email">Email</label>
        <input id="email" v-model="email" type="email" required placeholder="hero@exemplo.com" />
      </div>

      <div class="field">
        <label for="senha">Senha</label>
        <input id="senha" v-model="senha" type="password" required placeholder="••••••••" />
      </div>

      <button class="btn btn-primary" type="submit" :disabled="loading">
        {{ loading ? 'Entrando...' : 'Entrar' }}
      </button>
    </form>

    <p class="muted" style="margin-top: 14px">
      Ainda não tem conta?
      <NuxtLink to="/register">Registrar</NuxtLink>
    </p>
  </div>
</template>

<script setup lang="ts">
definePageMeta({
  layout: 'auth'
})

const auth = useAuth()
const router = useRouter()

const email = ref('')
const senha = ref('')
const error = ref('')
const loading = ref(false)

const handleLogin = async () => {
  error.value = ''
  loading.value = true
  try {
    await auth.login(email.value, senha.value)
    router.push('/')
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Não foi possível entrar. Verifique as credenciais.'
  } finally {
    loading.value = false
  }
}
</script>
