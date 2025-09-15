from flask import Blueprint, Response, request
from ..storage import characters, next_id, find_character
import json

bp = Blueprint("characters", __name__, url_prefix="/characters")

def json_response(payload, status=200):
    return Response(
        json.dumps(payload, ensure_ascii=False),
        status=status,
        mimetype="application/json; charset=utf-8"
    )

# GET /characters - list all
@bp.get("/")
def list_characters():
    return json_response(characters)

# GET /characters/<id> - get one by id
@bp.get("/<int:character_id>")
def get_character(character_id):
    character = find_character(character_id)
    if character:
        return json_response(character)
    return json_response({"error": "Character not found"}, status=404)

# POST /characters - create new
# Expected JSON: { "name": str, "class": str, "power": str, "weakness": str }
@bp.post("/")
def create_character():
    data = request.get_json(silent=True) or {}
    required = ["name", "class", "power", "weakness"]
    missing = [k for k in required if k not in data or not str(data[k]).strip()]
    if missing:
        return json_response({"error": "Missing required fields", "missing": missing}, status=400)

    character = {
        "id": next_id(),
        "name": str(data["name"]).strip(),
        "class": str(data["class"]).strip(),
        "power": str(data["power"]).strip(),
        "weakness": str(data["weakness"]).strip(),
    }
    characters.append(character)
    return json_response(character, status=201)

# PUT /characters/<id> - update existing (partial update allowed)
@bp.put("/<int:character_id>")
def update_character(character_id):
    character = find_character(character_id)
    if not character:
        return json_response({"error": "Character not found"}, status=404)

    data = request.get_json(silent=True) or {}
    allowed = {"name", "class", "power", "weakness"}
    updates = {k: v for k, v in data.items() if k in allowed}

    if not updates:
        return json_response({"error": "No valid fields to update", "allowed": list(allowed)}, status=400)

    for k, v in updates.items():
        character[k] = str(v).strip()

    return json_response(character)

# DELETE /characters/<id> - remove by id
@bp.delete("/<int:character_id>")
def delete_character(character_id):
    global characters
    before = len(characters)
    # rebind da lista para remover o item
    characters[:] = [c for c in characters if c["id"] != character_id]
    if len(characters) == before:
        return json_response({"error": "Character not found"}, status=404)
    return json_response({"msg": f"Character {character_id} removed"})
