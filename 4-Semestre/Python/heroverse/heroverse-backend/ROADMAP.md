# ROADMAP.md

> Guia passo a passo para montar e rodar o projeto do **CRUD 1–N–1 com autenticação**
> pedido no projeto final da disciplina.

O professor solicita:

- Um **CRUD** de um relacionamento **1 – N – 1** envolvendo **3 tabelas relacionadas**.  
- Algum tipo de **autenticação** (login/senha, OAuth etc.; vamos usar login/senha). fileciteturn0file0L1-L9

Este roadmap assume o uso de **Flask + SQLAlchemy + Pydantic + SQLite + JWT** gerenciados com **Poetry**.

---

## 1. Preparar o ambiente

1. Instalar **Python 3.11+**.
2. Instalar **Poetry**:
   ```bash
   pip install poetry
   ```
3. Criar pasta do projeto:
   ```bash
   mkdir projeto-personagens
   cd projeto-personagens
   ```

---

## 2. Criar o projeto com Poetry

1. Iniciar o projeto:
   ```bash
   poetry init
   ```

   - Responda às perguntas ou apenas aceite os padrões e depois edite o `pyproject.toml`.

2. Adicionar dependências principais:

   ```bash
   poetry add flask sqlalchemy pydantic pydantic[email] python-dotenv
   poetry add flask-jwt-extended passlib[bcrypt]
   ```

3. (Opcional, mas recomendado) Adicionar dependência de desenvolvimento:

   ```bash
   poetry add --group dev black isort
   ```

---

## 3. Criar estrutura de pastas

Dentro da pasta do projeto (`projeto-personagens/`), crie:

```text
projeto-personagens/
├─ pyproject.toml
├─ AGENTS.md
├─ ROADMAP.md
└─ app/
   ├─ __init__.py
   ├─ main.py
   ├─ config.py
   ├─ database.py
   ├─ models.py
   ├─ schemas.py
   ├─ auth.py
   ├─ routes/
   │  ├─ __init__.py
   │  ├─ usuarios.py
   │  ├─ universos.py
   │  ├─ tipos.py
   │  └─ personagens.py
   └─ static/
```

Crie os arquivos vazios primeiro; o conteúdo vem nos próximos passos.

---

## 4. Configuração básica do Flask e SQLite

### 4.1 `app/config.py`

Crie uma classe de configuração simples:

```python
import os
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent.parent

class Settings:
    SECRET_KEY = os.getenv("SECRET_KEY", "dev-secret-key")
    SQLALCHEMY_DATABASE_URL = f"sqlite:///{BASE_DIR / 'database.db'}"

settings = Settings()
```

### 4.2 `app/database.py`

Configurar SQLAlchemy:

```python
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base
from .config import settings

engine = create_engine(settings.SQLALCHEMY_DATABASE_URL, echo=True, future=True)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()
```

---

## 5. Criar os modelos (tabelas) – `app/models.py`

Implementar as tabelas com base no SQL fornecido, adicionando o campo `imagem_url`:

```python
from sqlalchemy import Column, Integer, String, Text, ForeignKey
from sqlalchemy.orm import relationship
from .database import Base

class Usuario(Base):
    __tablename__ = "usuarios"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)
    email = Column(String(120), unique=True, nullable=False, index=True)
    senha = Column(String(255), nullable=False)


class Universo(Base):
    __tablename__ = "universos"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)
    descricao = Column(Text, nullable=True)

    personagens = relationship("Personagem", back_populates="universo")


class TipoPersonagem(Base):
    __tablename__ = "tipos_personagem"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)
    descricao = Column(Text, nullable=True)

    personagens = relationship("Personagem", back_populates="tipo")


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
```

Criar as tabelas no banco:

```python
# arquivo: app/main.py (ou script separado)
from app.database import Base, engine
from app.models import Usuario, Universo, TipoPersonagem, Personagem

if __name__ == "__main__":
    Base.metadata.create_all(bind=engine)
```

Rodar:

```bash
poetry run python -m app.main
```

Isso criará o arquivo `database.db` (SQLite) com as 4 tabelas.

---

## 6. Schemas Pydantic – `app/schemas.py`

Criar classes para validar entrada/saída:

```python
from pydantic import BaseModel, EmailStr
from typing import Optional

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


class UniversoOut(UniversoBase):
    id: int

    class Config:
        from_attributes = True


class TipoBase(BaseModel):
    nome: str
    descricao: Optional[str] = None


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


class PersonagemOut(PersonagemBase):
    id: int

    class Config:
        from_attributes = True
```

---

## 7. Autenticação e segurança das rotas

### 7.1 Configurar JWT – `app/__init__.py`

```python
from flask import Flask
from flask_jwt_extended import JWTManager
from .config import settings

jwt = JWTManager()

def create_app():
    app = Flask(__name__)
    app.config["JWT_SECRET_KEY"] = settings.SECRET_KEY

    jwt.init_app(app)

    # registrar blueprints aqui
    from .routes.usuarios import bp as usuarios_bp
    from .routes.universos import bp as universos_bp
    from .routes.tipos import bp as tipos_bp
    from .routes.personagens import bp as personagens_bp

    app.register_blueprint(usuarios_bp)
    app.register_blueprint(universos_bp, url_prefix="/universos")
    app.register_blueprint(tipos_bp, url_prefix="/tipos")
    app.register_blueprint(personagens_bp, url_prefix="/personagens")

    return app
```

### 7.2 Hash de senha e rotas de login/registro – `app/auth.py` e `app/routes/usuarios.py`

- Usar `passlib.hash.bcrypt` para gerar/verificar hash.
- Criar rotas:
  - `POST /register` – cria usuário.
  - `POST /login` – devolve token JWT.

Nas rotas de CRUD, usar o decorator:

```python
from flask_jwt_extended import jwt_required

@bp.get("/")
@jwt_required()
def listar_universos():
    ...
```

Assim você garante que **todas as operações de CRUD só funcionem para usuários autenticados**, atendendo ao requisito de autenticação. fileciteturn0file0L10-L18

---

## 8. CRUD das tabelas (1–N–1)

Criar um *blueprint* por entidade na pasta `app/routes/`.

### 8.1 Universos – `app/routes/universos.py`

Rotas típicas (todas com `@jwt_required()`):

- `GET /universos` – listar
- `GET /universos/<int:id>` – detalhar
- `POST /universos` – criar
- `PUT /universos/<int:id>` – atualizar
- `DELETE /universos/<int:id>` – excluir

### 8.2 Tipos de personagem – `app/routes/tipos.py`

Mesma ideia do CRUD de universos.

### 8.3 Personagens – `app/routes/personagens.py`

- **Relacionamento 1–N–1** fica claro porque:
  - Cada personagem aponta para **um** universo (`universo_id`).
  - Cada personagem aponta para **um** tipo (`tipo_id`).
- Campos de entrada incluem `imagem_url` (string com URL da imagem).

Rotas:

- `GET /personagens`
- `GET /personagens/<int:id>`
- `POST /personagens`
- `PUT /personagens/<int:id>`
- `DELETE /personagens/<int:id>`

Em `GET /personagens`, você pode fazer join para retornar também o nome do universo e do tipo, o que ajuda a mostrar o relacionamento na apresentação para o professor.

---

## 9. Inicialização da aplicação – `app/main.py`

```python
from . import create_app

app = create_app()

if __name__ == "__main__":
    app.run(debug=True)
```

Rodar o projeto:

```bash
poetry run python -m app.main
```

O Flask deve subir em `http://127.0.0.1:5000`.

---

## 10. Testando tudo antes da apresentação

1. **Criar tabelas** (rodar o script que chama `Base.metadata.create_all`).  
2. **Registrar um usuário** via `POST /register` (Postman, Insomnia ou `curl`).  
3. **Logar** via `POST /login` e copiar o token JWT da resposta.  
4. Usar o token para chamar as rotas protegidas:
   - Criar **universos**.
   - Criar **tipos de personagem**.
   - Criar **personagens** relacionando um universo e um tipo e preenchendo `imagem_url`.
5. Testar **listar, atualizar, deletar** para cada entidade.

Na hora de mostrar para o professor:

- Prove que sem token não acessa o CRUD (erro 401).
- Mostre o relacionamento 1–N–1 (universos – personagens – tipos).
- Mostre que há pelo menos **3 tabelas relacionadas** e que todas possuem CRUD completo.
