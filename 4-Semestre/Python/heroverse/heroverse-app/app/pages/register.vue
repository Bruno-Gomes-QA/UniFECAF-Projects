<template>
  <div>
    <h2 class="title" style="font-size: 24px">
      Criar conta
      <span class="pill">Novo</span>
    </h2>
    <p class="muted" style="margin-bottom: 18px">
      Conecte-se ao Heroverse com um visual de neon azul e amarelo. Depois de registrar, faça login e comece a
      criar heróis.
    </p>

    <div v-if="message" class="alert success" style="margin-bottom: 12px">
      {{ message }}
    </div>
    <div v-if="error" class="alert error" style="margin-bottom: 12px">
      {{ error }}
    </div>

    <form class="grid" style="gap: 14px" @submit.prevent="handleRegister">
      <div class="field">
        <label for="nome">Nome</label>
        <input id="nome" v-model="nome" type="text" required placeholder="Capitã Neon" />
      </div>

      <div class="field">
        <label for="email">Email</label>
        <input id="email" v-model="email" type="email" required placeholder="hero@exemplo.com" />
      </div>

      <div class="field">
        <label for="senha">Senha</label>
        <input id="senha" v-model="senha" type="password" required placeholder="••••••••" />
      </div>

      <button class="btn btn-primary" type="submit" :disabled="loading">
        {{ loading ? 'Registrando...' : 'Criar conta' }}
      </button>
    </form>

    <p class="muted" style="margin-top: 14px">
      Já tem conta?
      <NuxtLink to="/login">Entrar</NuxtLink>
    </p>
  </div>
</template>

<script setup lang="ts">
definePageMeta({
  layout: 'auth'
})

const auth = useAuth()
const router = useRouter()

const nome = ref('')
const email = ref('')
const senha = ref('')
const error = ref('')
const message = ref('')
const loading = ref(false)

const handleRegister = async () => {
  error.value = ''
  message.value = ''
  loading.value = true
  try {
    await auth.register(nome.value, email.value, senha.value)
    message.value = 'Conta criada com sucesso! Faça login para entrar.'
    setTimeout(() => router.push('/login'), 800)
  } catch (err: any) {
    error.value = err?.data?.error || err?.data?.detail || 'Erro ao registrar. Verifique os dados.'
  } finally {
    loading.value = false
  }
}
</script>
