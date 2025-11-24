from typing import Optional

from pydantic import BaseModel, EmailStr


class UsuarioCreate(BaseModel):
    nome: str
    email: EmailStr
    senha: str


class UsuarioLogin(BaseModel):
    email: EmailStr
    senha: str


class UsuarioOut(BaseModel):
    id: int
    nome: str
    email: EmailStr

    class Config:
        from_attributes = True


class UniversoBase(BaseModel):
    nome: str
    descricao: Optional[str] = None


class UniversoCreate(UniversoBase):
    ...


class UniversoOut(UniversoBase):
    id: int

    class Config:
        from_attributes = True


class TipoBase(BaseModel):
    nome: str
    descricao: Optional[str] = None


class TipoCreate(TipoBase):
    ...


class TipoOut(TipoBase):
    id: int

    class Config:
        from_attributes = True


class PersonagemBase(BaseModel):
    nome: str
    idade: Optional[int] = None
    poder_principal: Optional[str] = None
    imagem_url: Optional[str] = None
    universo_id: int
    tipo_id: int


class PersonagemCreate(PersonagemBase):
    ...


class PersonagemUpdate(BaseModel):
    nome: Optional[str] = None
    idade: Optional[int] = None
    poder_principal: Optional[str] = None
    imagem_url: Optional[str] = None
    universo_id: Optional[int] = None
    tipo_id: Optional[int] = None


class PersonagemOut(PersonagemBase):
    id: int

    class Config:
        from_attributes = True
