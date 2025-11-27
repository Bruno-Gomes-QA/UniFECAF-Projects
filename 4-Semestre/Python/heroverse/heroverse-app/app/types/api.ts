export interface User {
  id: number
  nome: string
  email: string
}

export interface LoginResponse {
  access_token: string
  user: User
}

export interface PersonagemPayload {
  nome: string
  universo_id: number
  tipo_id: number
  idade?: number | null
  imagem_url?: string | null
  poder_principal?: string | null
}

export interface Personagem extends PersonagemPayload {
  id: number
}

export interface UniversoPayload {
  nome: string
  descricao?: string | null
}

export interface Universo extends UniversoPayload {
  id: number
}

export interface TipoPayload {
  nome: string
  descricao?: string | null
}

export interface Tipo extends TipoPayload {
  id: number
}
