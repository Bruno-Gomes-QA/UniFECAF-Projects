import type { Personagem, PersonagemPayload } from '~/types/api'

export const usePersonagens = () => {
  const api = useApi()

  const list = async () => api.get<Personagem[]>('/personagens/')
  const find = async (id: number | string) => api.get<Personagem>(`/personagens/${id}`)
  const create = async (payload: PersonagemPayload) => api.post<Personagem>('/personagens/', payload)
  const update = async (id: number | string, payload: Partial<PersonagemPayload>) =>
    api.put<Personagem>(`/personagens/${id}`, payload)
  const remove = async (id: number | string) => api.del<void>(`/personagens/${id}`)

  return { list, find, create, update, remove }
}
