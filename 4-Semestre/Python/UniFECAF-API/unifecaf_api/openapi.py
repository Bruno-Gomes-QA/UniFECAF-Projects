import json
import re
from typing import Dict, Any

from flask import Blueprint, Response, current_app


def _rule_to_openapi_path(rule: str) -> str:
    """Convert Flask/werkzeug path params (<int:id>) to OpenAPI style ({id})."""
    return re.sub(r"<(?:[^:<>]+:)?([^<>]+)>", r"{\1}", rule)


def _converter_to_schema(converter) -> Dict[str, Any]:
    """Best-effort mapping from werkzeug converters to OpenAPI schema types."""
    name = converter.__class__.__name__
    mapping = {
        "IntegerConverter": ("integer", "int32"),
        "FloatConverter": ("number", "float"),
        "UUIDConverter": ("string", "uuid"),
    }
    type_, fmt = mapping.get(name, ("string", None))
    schema: Dict[str, Any] = {"type": type_}
    if fmt:
        schema["format"] = fmt
    return schema


def generate_openapi_spec() -> Dict[str, Any]:
    app = current_app
    spec: Dict[str, Any] = {
        "openapi": "3.0.3",
        "info": {
            "title": app.config.get("SWAGGER_TITLE", "UniFECAF API"),
            "version": app.config.get("SWAGGER_VERSION", "1.0.0"),
        },
        "paths": {},
    }

    tags_seen = {}

    for rule in sorted(app.url_map.iter_rules(), key=lambda r: r.rule):
        if rule.endpoint == "static":
            continue

        methods = sorted(
            m for m in rule.methods
            if m not in {"HEAD", "OPTIONS"}
        )
        if not methods:
            continue

        path = _rule_to_openapi_path(rule.rule)
        view_func = app.view_functions[rule.endpoint]
        docstring = (view_func.__doc__ or "").strip()
        summary = docstring.splitlines()[0] if docstring else view_func.__name__.replace("_", " ").title()
        description = "\n".join(docstring.splitlines()[1:]).strip() if docstring.count("\n") else (docstring if docstring else "")

        tag = rule.endpoint.split(".")[0]
        tags_seen[tag] = True

        path_item = spec["paths"].setdefault(path, {})
        parameters = []
        for name in rule.arguments:
            converter = rule._converters.get(name)
            schema = _converter_to_schema(converter) if converter else {"type": "string"}
            parameters.append({
                "name": name,
                "in": "path",
                "required": True,
                "schema": schema,
            })

        for method in methods:
            method_lower = method.lower()
            operation: Dict[str, Any] = {
                "summary": summary,
                "tags": [tag],
                "responses": {
                    "200": {"description": "Successful response"},
                },
            }
            if description:
                operation["description"] = description
            if parameters:
                operation["parameters"] = parameters
            if method_lower in {"post", "put", "patch"}:
                operation["requestBody"] = {
                    "description": "JSON payload",
                    "required": True,
                    "content": {
                        "application/json": {
                            "schema": {"type": "object"}
                        }
                    }
                }
            if method == "POST":
                operation["responses"].update({"201": {"description": "Resource created"}})
            path_item[method_lower] = operation

    if tags_seen:
        spec["tags"] = [{"name": name} for name in sorted(tags_seen)]

    servers = app.config.get("SWAGGER_SERVERS")
    if servers:
        spec["servers"] = servers

    return spec


bp = Blueprint("docs", __name__)


@bp.get("/openapi.json")
def openapi_json() -> Response:
    spec = generate_openapi_spec()
    payload = json.dumps(spec, ensure_ascii=False, indent=2)
    return Response(payload, mimetype="application/json; charset=utf-8")


SWAGGER_UI_TEMPLATE = """<!DOCTYPE html>
<html lang=\"pt-BR\">
<head>
  <meta charset=\"utf-8\" />
  <title>{{ title }}</title>
  <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/swagger-ui-dist@5/swagger-ui.css\" />
  <style>body { margin: 0; } #swagger-ui { width: 100vw; height: 100vh; }</style>
</head>
<body>
  <div id=\"swagger-ui\"></div>
  <script src=\"https://cdn.jsdelivr.net/npm/swagger-ui-dist@5/swagger-ui-bundle.js\"></script>
  <script>
    window.onload = () => {
      SwaggerUIBundle({
        url: '{{ spec_url }}',
        dom_id: '#swagger-ui'
      });
    };
  </script>
</body>
</html>"""


@bp.get("/docs")
def swagger_ui() -> Response:
    html = SWAGGER_UI_TEMPLATE.replace("{{ title }}", current_app.config.get("SWAGGER_TITLE", "UniFECAF API Docs"))
    html = html.replace("{{ spec_url }}", current_app.config.get("SWAGGER_SPEC_URL", "/openapi.json"))
    return Response(html, mimetype="text/html; charset=utf-8")
