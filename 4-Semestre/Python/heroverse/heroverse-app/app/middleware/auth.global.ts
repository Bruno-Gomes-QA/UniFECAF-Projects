export default defineNuxtRouteMiddleware((to) => {
  const auth = useAuth()
  auth.syncFromCookies()

  const publicPaths = ['/login', '/register']
  const isPublic = publicPaths.includes(to.path)

  if (!auth.isAuthenticated.value && !isPublic) {
    return navigateTo('/login')
  }

  if (auth.isAuthenticated.value && isPublic) {
    return navigateTo('/')
  }
})
