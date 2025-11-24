from sqlalchemy import Column, ForeignKey, Integer, String, Text
from sqlalchemy.orm import relationship

from .database import Base


class Usuario(Base):
    __tablename__ = "usuarios"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)
    email = Column(String(120), nullable=False, unique=True, index=True)
    senha = Column(String(255), nullable=False)


class Universo(Base):
    __tablename__ = "universos"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)
    descricao = Column(Text, nullable=True)

    personagens = relationship("Personagem", back_populates="universo", cascade="all,delete")


class TipoPersonagem(Base):
    __tablename__ = "tipos_personagem"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)
    descricao = Column(Text, nullable=True)

    personagens = relationship("Personagem", back_populates="tipo", cascade="all,delete")


class Personagem(Base):
    __tablename__ = "personagens"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(120), nullable=False)
    idade = Column(Integer, nullable=True)
    poder_principal = Column(String(150), nullable=True)
    imagem_url = Column(String(255), nullable=True)

    universo_id = Column(Integer, ForeignKey("universos.id"), nullable=False)
    tipo_id = Column(Integer, ForeignKey("tipos_personagem.id"), nullable=False)

    universo = relationship("Universo", back_populates="personagens")
    tipo = relationship("TipoPersonagem", back_populates="personagens")
