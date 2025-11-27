<template>
  <header class="topbar">
    <div class="topbar-inner">
      <NuxtLink to="/" class="brand">
        <span class="brand-badge">HV</span>
        <span>Heroverse</span>
      </NuxtLink>

      <nav class="nav">
        <NuxtLink
          v-for="item in menu"
          :key="item.to"
          :to="item.to"
          :class="{ active: isActive(item.to) }"
        >
          {{ item.label }}
        </NuxtLink>
      </nav>

      <div class="user-pill" v-if="auth.user.value">
        <span class="status-dot" />
        <div>
          <div class="muted" style="font-size: 12px">
            Logado
          </div>
          <div style="font-weight: 700">
            {{ auth.user.value?.nome }}
          </div>
        </div>
        <button class="btn btn-ghost" @click="handleLogout">
          Sair
        </button>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
const route = useRoute()
const router = useRouter()
const auth = useAuth()

const menu = [
  { to: '/', label: 'Dashboard' },
  { to: '/personagens', label: 'Personagens' },
  { to: '/universos', label: 'Universos' },
  { to: '/tipos', label: 'Tipos' }
]

const isActive = (path: string) => route.path === path || route.path.startsWith(`${path}/`)

const handleLogout = () => {
  auth.logout()
  router.push('/login')
}
</script>
