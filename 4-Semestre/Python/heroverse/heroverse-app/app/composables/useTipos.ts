import type { Tipo, TipoPayload } from '~/types/api'

export const useTipos = () => {
  const api = useApi()

  const list = async () => api.get<Tipo[]>('/tipos/')
  const find = async (id: number | string) => api.get<Tipo>(`/tipos/${id}`)
  const create = async (payload: TipoPayload) => api.post<Tipo>('/tipos/', payload)
  const update = async (id: number | string, payload: Partial<TipoPayload>) =>
    api.put<Tipo>(`/tipos/${id}`, payload)
  const remove = async (id: number | string) => api.del<void>(`/tipos/${id}`)

  return { list, find, create, update, remove }
}
