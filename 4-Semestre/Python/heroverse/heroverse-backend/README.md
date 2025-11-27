# Heroverse Backend

API Flask com autenticação JWT, SQLAlchemy e Pydantic para o projeto CRUD 1–N–1.

## Executar

```bash
poetry install --no-root
poetry run python -m app.main
```

- Swagger UI disponível em `http://localhost:5000/docs/`.
- Especificação JSON em `http://localhost:5000/apispec_1.json`.
- Seed automático cria usuário admin se não existir:
  - Email: `admin@heroverse.com`
  - Senha: `admin123`
