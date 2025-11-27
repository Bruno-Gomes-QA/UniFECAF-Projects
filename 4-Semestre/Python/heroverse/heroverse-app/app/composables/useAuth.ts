import type { LoginResponse, User } from '~/types/api'

export const useAuth = () => {
  const api = useApi()
  const token = useState<string | null>('auth_token', () => null)
  const user = useState<User | null>('auth_user', () => null)

  const tokenCookie = useCookie<string | null>('auth_token', { sameSite: 'lax' })
  const userCookie = useCookie<User | null>('auth_user', { sameSite: 'lax' })

  const syncFromCookies = () => {
    if (tokenCookie.value && !token.value) {
      token.value = tokenCookie.value
    }
    if (userCookie.value && !user.value) {
      user.value = userCookie.value
    }
  }

  syncFromCookies()

  const persistAuth = (payload?: LoginResponse) => {
    if (!payload) return
    token.value = payload.access_token
    user.value = payload.user
    tokenCookie.value = payload.access_token
    userCookie.value = payload.user
  }

  const clearAuth = () => {
    token.value = null
    user.value = null
    tokenCookie.value = null
    userCookie.value = null
  }

  const login = async (email: string, senha: string) => {
    const data = await api.post<LoginResponse>('/auth/login', { email, senha })
    persistAuth(data)
    return data
  }

  const register = async (nome: string, email: string, senha: string) =>
    await api.post('/auth/register', { nome, email, senha })

  const logout = () => clearAuth()

  const isAuthenticated = computed(() => Boolean(token.value))

  return { token, user, isAuthenticated, login, register, logout, clearAuth, persistAuth, syncFromCookies }
}
