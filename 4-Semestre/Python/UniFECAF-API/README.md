# UniFECAF API

API Flask simples que expõe um catálogo de personagens lendários, com suporte a CRUD, documentação automática via OpenAPI/Swagger e uma página web responsiva para visualização.

## Funcionalidades
- Catálogo em memória de personagens com operações REST (listar, detalhar, criar, editar e remover).
- Documentação OpenAPI gerada automaticamente em `/openapi.json` e interface Swagger UI em `/docs`.
- Front-end responsivo em `/` com busca instantânea usando HTML, CSS e JavaScript vanilla.
- Suporte a CORS e configuração simples por variáveis do Flask.

## Estrutura do Projeto
```
UniFECAF-API/
├── main.py                # Ponto de entrada da aplicação
├── pyproject.toml         # Configuração Poetry
├── poetry.lock            # Lockfile Poetry
├── requirements.txt       # Dependências para uso com pip
├── unifecaf_api/
│   ├── __init__.py        # Factory `create_app`
│   ├── openapi.py         # Geração OpenAPI + Swagger UI
│   ├── storage.py         # "Banco" em memória com personagens
│   ├── templates/
│   │   └── characters.html
│   ├── static/
│   │   └── css/characters.css
│   └── resources/
│       ├── __init__.py
│       ├── characters.py  # Endpoints REST `/characters`
│       └── frontend.py    # Blueprint da página web `/`
└── README.md
```

## Pré-requisitos
- Python 3.12+
- Opcional: [Poetry](https://python-poetry.org/) se desejar usar o fluxo com scripts do projeto.

## Executando com `pip`
1. (Opcional, recomendado) Crie e ative um ambiente virtual:
   ```bash
   python -m venv .venv
   source .venv/bin/activate  # Windows: .venv\Scripts\activate
   ```
2. Instale as dependências:
   ```bash
   pip install -r requirements.txt
   ```
3. Inicie o servidor Flask:
   ```bash
   python main.py
   ```
4. Acesse em `http://localhost:5000/` para o front-end, `http://localhost:5000/characters` para a API e `http://localhost:5000/docs` para a documentação.

## Executando com Poetry
1. Instale as dependências declaradas no `pyproject.toml`:
   ```bash
   poetry install
   ```
2. Rode o servidor usando o script configurado:
   ```bash
   poetry run server
   ```
   (Alternativamente: `poetry run python main.py`)

## Endpoints Principais
- `GET /characters` — lista todos os personagens.
- `GET /characters/<id>` — retorna um personagem específico.
- `POST /characters` — cria um novo personagem (JSON: `name`, `class`, `power`, `weakness`).
- `PUT /characters/<id>` — atualiza campos permitidos de um personagem existente.
- `DELETE /characters/<id>` — remove um personagem pelo ID.
- `GET /openapi.json` — especificação OpenAPI 3.0 gerada automaticamente.
- `GET /docs` — Swagger UI embutido.
- `GET /` — página front-end responsiva com busca e cards dos personagens.