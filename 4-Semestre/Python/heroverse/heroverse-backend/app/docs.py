from typing import Dict, List, Type

from pydantic import BaseModel

from . import schemas


def _collect_models() -> List[Type[BaseModel]]:
    return [
        schemas.UsuarioCreate,
        schemas.UsuarioLogin,
        schemas.UsuarioOut,
        schemas.UniversoCreate,
        schemas.UniversoOut,
        schemas.TipoCreate,
        schemas.TipoOut,
        schemas.PersonagemCreate,
        schemas.PersonagemUpdate,
        schemas.PersonagemOut,
    ]


def _build_definitions() -> Dict[str, Dict]:
    definitions: Dict[str, Dict] = {}
    for model in _collect_models():
        definitions[model.__name__] = model.model_json_schema(
            ref_template="#/definitions/{model}"
        )
    return definitions


swagger_template = {
    "swagger": "2.0",
    "info": {
        "title": "Heroverse API",
        "description": "CRUD 1–N–1 com autenticação JWT",
        "version": "1.0.0",
    },
    "securityDefinitions": {
        "Bearer": {
            "type": "apiKey",
            "name": "Authorization",
            "in": "header",
            "description": "Use o formato: Bearer <token>",
        },
    },
    "security": [{"Bearer": []}],
    "definitions": _build_definitions(),
}


swagger_config = {
    "headers": [],
    "static_url_path": "/flasgger_static",
    "swagger_ui": True,
    "specs": [
        {
            "endpoint": "apispec_1",
            "route": "/apispec_1.json",
            "rule_filter": lambda rule: True,
            "model_filter": lambda tag: True,
        }
    ],
    "specs_route": "/docs/",
}
