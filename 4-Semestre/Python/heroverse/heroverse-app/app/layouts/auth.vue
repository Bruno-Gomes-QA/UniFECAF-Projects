<template>
  <div class="auth-layout">
    <div class="floating-grid" />
    <div class="auth-hero">
      <div class="card auth-card">
        <p class="pill" style="display: inline-flex; align-items: center; gap: 8px; margin-bottom: 10px">
          <span class="status-dot" />
          Nova Era
        </p>
        <h1 class="title" style="font-size: 32px; line-height: 1.1">
          Heroverse
        </h1>
        <p class="muted" style="max-width: 520px">
          Painel moderno para criar, gerenciar e explorar heróis, universos e tipos.
        </p>
        <div class="grid grid-3" style="margin-top: 18px">
          <div class="card card-ghost">
            <div class="muted">Autenticação</div>
            <div style="font-weight: 700">JWT + Middleware</div>
          </div>
          <div class="card card-ghost">
            <div class="muted">CRUD Completo</div>
            <div style="font-weight: 700">Personagens e universos</div>
          </div>
          <div class="card card-ghost">
            <div class="muted">Experiência</div>
            <div style="font-weight: 700">Rápido e intuitivo</div>
          </div>
        </div>
      </div>
      <div class="card auth-card" style="position: relative">
        <slot />
      </div>
    </div>
  </div>
</template>
