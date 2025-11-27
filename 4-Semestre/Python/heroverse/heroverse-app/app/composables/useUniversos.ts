import type { Universo, UniversoPayload } from '~/types/api'

export const useUniversos = () => {
  const api = useApi()

  const list = async () => api.get<Universo[]>('/universos/')
  const find = async (id: number | string) => api.get<Universo>(`/universos/${id}`)
  const create = async (payload: UniversoPayload) => api.post<Universo>('/universos/', payload)
  const update = async (id: number | string, payload: Partial<UniversoPayload>) =>
    api.put<Universo>(`/universos/${id}`, payload)
  const remove = async (id: number | string) => api.del<void>(`/universos/${id}`)

  return { list, find, create, update, remove }
}
