# AGENTS.md

> Projeto final – CRUD 1–N–1 com autenticação  
> Stack: **Python + Poetry + Flask + Pydantic + SQLAlchemy + SQLite + JWT (segurança de rotas)**

Este arquivo descreve os “agentes” (módulos/componentes) da aplicação, suas
responsabilidades e como eles se comunicam. Use isso como guia para manter o código
organizado e fácil de explicar para o professor.

---

## 1. Visão geral da arquitetura

- **Camada de apresentação (Flask)**  
  Define as rotas HTTP (endpoints) e recebe/devolve JSON ou HTML.

- **Camada de validação (Pydantic)**  
  Garante que os dados que entram e saem da API estejam no formato correto.

- **Camada de persistência (SQLAlchemy + SQLite)**  
  Representa as tabelas em classes Python, faz consultas e salva dados no banco.

- **Camada de segurança (Auth + JWT)**  
  Responsável por cadastro, login, hashing de senha e proteção das rotas.

Relacionamento 1–N–1 implementado:

- `usuarios` (autenticação)
- `universos` (1)  
- `tipos_personagem` (1)  
- `personagens` (N) – faz a ligação entre **universos** e **tipos_personagem**

Tabela `personagens` ainda contém um campo de **URL de imagem**:

```sql
CREATE TABLE personagens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(120) NOT NULL,
    idade INT,
    poder_principal VARCHAR(150),
    imagem_url VARCHAR(255),
    universo_id INT NOT NULL,
    tipo_id INT NOT NULL,
    FOREIGN KEY (universo_id) REFERENCES universos(id),
    FOREIGN KEY (tipo_id) REFERENCES tipos_personagem(id)
);
```

---

## 2. Estrutura sugerida de pastas

```text
projeto-personagens/
├─ pyproject.toml
├─ README.md
├─ AGENTS.md
├─ ROADMAP.md
└─ app/
   ├─ __init__.py
   ├─ config.py
   ├─ models.py          # SQLAlchemy models (Usuarios, Universos, TiposPersonagem, Personagens)
   ├─ schemas.py         # Pydantic schemas
   ├─ auth.py            # Lógica de autenticação e proteção de rotas
   ├─ database.py        # Conexão com SQLite e session
   ├─ routes/
   │  ├─ __init__.py
   │  ├─ usuarios.py     # rotas de registro/login
   │  ├─ universos.py    # CRUD de universos
   │  ├─ tipos.py        # CRUD de tipos_personagem
   │  └─ personagens.py  # CRUD de personagens (1–N–1)
   └─ main.py            # ponto de entrada Flask
```

---

## 3. Agente **App / Bootstrap** (`app/__init__.py`, `app/main.py`)

### Responsabilidades

- Criar a instância do Flask (`Flask(__name__)`).
- Carregar configurações (ex.: `SECRET_KEY`, string do banco SQLite).
- Inicializar extensões:
  - SQLAlchemy
  - JWT (ou outro método de autenticação)
- Registrar *blueprints* de rotas.

### Entradas / saídas

- **Entrada:** configurações de ambiente (`.env`, variáveis locais).  
- **Saída:** objeto `app` rodando com todas as rotas e middlewares prontos.

---

## 4. Agente **Database** (`app/database.py`, `app/models.py`)

### Responsabilidades

- Criar o `engine` e o `SessionLocal` do SQLAlchemy apontando para um arquivo SQLite.
- Definir a classe `Base = declarative_base()`.
- Declarar os modelos:

  - `Usuario`
  - `Universo`
  - `TipoPersonagem`
  - `Personagem` (com `universo_id`, `tipo_id` e `imagem_url`).

- Fornecer função utilitária para criar todas as tabelas (`Base.metadata.create_all`).

### Entradas / saídas

- **Entrada:** configurações de conexão (URL SQLite).  
- **Saída:** sessão do banco para ser usada nas rotas e serviços.

---

## 5. Agente **Schemas / Validação** (`app/schemas.py`)

### Responsabilidades

- Definir modelos Pydantic para entrada/saída de dados na API, por exemplo:

  - `UsuarioCreate`, `UsuarioLogin`, `UsuarioOut`
  - `UniversoBase`, `UniversoCreate`, `UniversoOut`
  - `TipoPersonagemBase`, `TipoPersonagemOut`
  - `PersonagemBase`, `PersonagemCreate`, `PersonagemOut`

- Mapear campos equivalentes aos modelos SQLAlchemy, incluindo `imagem_url`.
- Garantir tipos corretos (`int`, `str`, `EmailStr`, etc.) e validações de tamanho.

### Entradas / saídas

- **Entrada:** dados crus da requisição (JSON ou form).  
- **Saída:** objetos Pydantic validados, prontos para virar modelos SQLAlchemy ou resposta JSON.

---

## 6. Agente **Auth / Segurança** (`app/auth.py`, `app/routes/usuarios.py`)

### Responsabilidades

- Cadastro de usuário (`POST /register`):
  - Validar com Pydantic.
  - Verificar se e‑mail já está cadastrado.
  - Gerar hash seguro de senha (ex.: `passlib` ou `werkzeug.security`).
  - Salvar novo usuário.

- Login (`POST /login`):
  - Validar credenciais.
  - Verificar hash da senha.
  - Gerar **token JWT** com ID do usuário.

- Middleware / decorator de proteção (ex.: `@jwt_required()`):
  - Bloquear acesso às rotas protegidas se não houver token válido.

### Entradas / saídas

- **Entrada:** e‑mail, senha e token JWT na requisição.  
- **Saída:** tokens e mensagens de erro ou sucesso.  
- **Erro:** acessar rotas protegidas sem token ou com token inválido.

---

## 7. Agente **CRUD Universos** (`app/routes/universos.py`)

### Responsabilidades

Rotas protegidas por autenticação:

- `GET /universos` – lista todos os universos.
- `GET /universos/<id>` – retorna um universo específico.
- `POST /universos` – cria novo universo.
- `PUT /universos/<id>` – atualiza universo existente.
- `DELETE /universos/<id>` – remove universo.

### Entradas / saídas

- **Entrada:** dados do universo (nome, descrição) via JSON.  
- **Saída:** objeto universo criado/atualizado ou mensagem de confirmação.

---

## 8. Agente **CRUD Tipos de Personagem** (`app/routes/tipos.py`)

### Responsabilidades

Rotas protegidas por autenticação:

- `GET /tipos` – lista todos os tipos de personagem.
- `GET /tipos/<id>` – retorna um tipo específico.
- `POST /tipos` – cria novo tipo.
- `PUT /tipos/<id>` – atualiza tipo.
- `DELETE /tipos/<id>` – remove tipo.

### Entradas / saídas

Semelhante ao agente de universos, mas com os campos de `tipos_personagem`.

---

## 9. Agente **CRUD Personagens** (`app/routes/personagens.py`)

### Responsabilidades

Rotas protegidas por autenticação:

- `GET /personagens` – lista todos os personagens, com join opcional de universo e tipo.
- `GET /personagens/<id>` – retorna um personagem específico.
- `POST /personagens` – cria novo personagem linkando `universo_id` e `tipo_id`.
- `PUT /personagens/<id>` – atualiza personagem.
- `DELETE /personagens/<id>` – remove personagem.

Campos principais:

- `nome` (str, obrigatório)
- `idade` (int, opcional)
- `poder_principal` (str, opcional)
- `imagem_url` (str, opcional – URL da imagem)
- `universo_id` (int, FK)
- `tipo_id` (int, FK)

### Entradas / saídas

- **Entrada:** JSON com dados do personagem e IDs de referência.  
- **Saída:** personagem completo, incluindo dados resumidos de universo e tipo se desejado.

---

## 10. Agente **Segurança adicional / Boas práticas**

### Responsabilidades

- Garantir que **todas** as rotas de CRUD estejam protegidas por autenticação (exceto registro/login).
- Validar sempre os IDs passados na URL (404 se não existir).
- Retornar códigos de status corretos:
  - 200 / 201 – sucesso
  - 400 – erro de validação
  - 401 – não autenticado
  - 404 – não encontrado
- Logar erros importantes (para conseguir explicar em aula).

---

## 11. Agente **Dev / Utilitários** (opcional)

### Possíveis responsabilidades

- Script para popular dados de teste (universos, tipos, personagens).
- Script para recriar o banco do zero.
- Arquivo `Makefile` ou scripts no `pyproject.toml` para comandos como:
  - `poetry run dev`
  - `poetry run create-db`

---

Com este AGENTS.md você consegue explicar para o professor:

1. Onde está a autenticação.
2. Onde está o relacionamento 1–N–1.
3. Quais módulos cuidam de cada parte (rotas, validação, banco, segurança).
