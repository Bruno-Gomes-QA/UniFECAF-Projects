export const useApi = () => {
  const config = useRuntimeConfig()
  const token = useState<string | null>('auth_token', () => null)

  const request = async <T>(method: string, url: string, body?: Record<string, any>, options?: Record<string, any>) => {
    const headers: Record<string, string> = {
      ...(options?.headers || {})
    }

    if (token.value) {
      headers.Authorization = `Bearer ${token.value}`
    }

    const payload: Record<string, any> = {
      baseURL: config.public.apiBase,
      method,
      headers,
      ...options
    }

    if (body !== undefined) {
      payload.body = body
    }

    return await $fetch<T>(url, payload)
  }

  const get = async <T>(url: string, options?: Record<string, any>) => request<T>('GET', url, undefined, options)
  const post = async <T>(url: string, body?: Record<string, any>, options?: Record<string, any>) =>
    request<T>('POST', url, body, options)
  const put = async <T>(url: string, body?: Record<string, any>, options?: Record<string, any>) =>
    request<T>('PUT', url, body, options)
  const del = async <T>(url: string, options?: Record<string, any>) => request<T>('DELETE', url, null, options)

  return { get, post, put, del }
}
