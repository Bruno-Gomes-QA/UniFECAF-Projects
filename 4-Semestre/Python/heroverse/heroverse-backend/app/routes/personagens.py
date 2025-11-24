from flask import Blueprint, jsonify, request
from flask_jwt_extended import jwt_required
from pydantic import ValidationError
from sqlalchemy.orm import joinedload

from ..database import get_session
from ..models import Personagem, TipoPersonagem, Universo
from ..schemas import PersonagemCreate, PersonagemOut, PersonagemUpdate

bp = Blueprint("personagens", __name__)


def _serialize_personagem(personagem: Personagem) -> dict:
    data = PersonagemOut.model_validate(personagem).model_dump()
    if personagem.universo:
        data["universo"] = {
            "id": personagem.universo.id,
            "nome": personagem.universo.nome,
        }
    if personagem.tipo:
        data["tipo"] = {
            "id": personagem.tipo.id,
            "nome": personagem.tipo.nome,
        }
    return data


@bp.get("/")
@jwt_required()
def listar_personagens():
    """
    Listar personagens
    ---
    tags:
      - Personagens
    responses:
      200:
        description: Lista de personagens
        schema:
          type: array
          items:
            $ref: '#/definitions/PersonagemOut'
    """
    with get_session() as session:
        personagens = (
            session.query(Personagem)
            .options(joinedload(Personagem.universo), joinedload(Personagem.tipo))
            .all()
        )
        data = [_serialize_personagem(p) for p in personagens]
    return jsonify(data)


@bp.get("/<int:personagem_id>")
@jwt_required()
def detalhar_personagem(personagem_id: int):
    """
    Detalhar personagem
    ---
    tags:
      - Personagens
    parameters:
      - in: path
        name: personagem_id
        schema:
          type: integer
        required: true
    responses:
      200:
        description: Personagem encontrado
        schema:
          $ref: '#/definitions/PersonagemOut'
      404:
        description: Personagem não encontrado
    """
    with get_session() as session:
        personagem = (
            session.query(Personagem)
            .options(joinedload(Personagem.universo), joinedload(Personagem.tipo))
            .filter(Personagem.id == personagem_id)
            .first()
        )
        if not personagem:
            return jsonify({"error": "Personagem não encontrado"}), 404
        data = _serialize_personagem(personagem)
    return jsonify(data)


@bp.post("/")
@jwt_required()
def criar_personagem():
    """
    Criar personagem
    ---
    tags:
      - Personagens
    parameters:
      - in: body
        name: payload
        required: true
        schema:
          $ref: '#/definitions/PersonagemCreate'
    responses:
      201:
        description: Personagem criado
        schema:
          $ref: '#/definitions/PersonagemOut'
      404:
        description: Universo ou tipo inexistente
    """
    data = request.get_json() or {}
    try:
        payload = PersonagemCreate(**data)
    except ValidationError as exc:
        return jsonify({"errors": exc.errors()}), 400

    with get_session() as session:
        if not session.get(Universo, payload.universo_id):
            return jsonify({"error": "Universo informado não existe"}), 404
        if not session.get(TipoPersonagem, payload.tipo_id):
            return jsonify({"error": "Tipo informado não existe"}), 404

        personagem = Personagem(
            nome=payload.nome,
            idade=payload.idade,
            poder_principal=payload.poder_principal,
            imagem_url=payload.imagem_url,
            universo_id=payload.universo_id,
            tipo_id=payload.tipo_id,
        )
        session.add(personagem)
        session.flush()
        session.refresh(personagem)
        _ = personagem.universo
        _ = personagem.tipo
        data = _serialize_personagem(personagem)
        return jsonify(data), 201


@bp.put("/<int:personagem_id>")
@jwt_required()
def atualizar_personagem(personagem_id: int):
    """
    Atualizar personagem
    ---
    tags:
      - Personagens
    parameters:
      - in: path
        name: personagem_id
        schema:
          type: integer
        required: true
      - in: body
        name: payload
        required: true
        schema:
          $ref: '#/definitions/PersonagemUpdate'
    responses:
      200:
        description: Personagem atualizado
        schema:
          $ref: '#/definitions/PersonagemOut'
      404:
        description: Personagem/Universo/Tipo não encontrado
    """
    data = request.get_json() or {}
    try:
        payload = PersonagemUpdate(**data)
    except ValidationError as exc:
        return jsonify({"errors": exc.errors()}), 400

    with get_session() as session:
        personagem = session.get(Personagem, personagem_id)
        if not personagem:
            return jsonify({"error": "Personagem não encontrado"}), 404

        if payload.universo_id:
            if not session.get(Universo, payload.universo_id):
                return jsonify({"error": "Universo informado não existe"}), 404
            personagem.universo_id = payload.universo_id
        if payload.tipo_id:
            if not session.get(TipoPersonagem, payload.tipo_id):
                return jsonify({"error": "Tipo informado não existe"}), 404
            personagem.tipo_id = payload.tipo_id

        for field in ["nome", "idade", "poder_principal", "imagem_url"]:
            value = getattr(payload, field)
            if value is not None:
                setattr(personagem, field, value)

        session.add(personagem)
        session.flush()
        session.refresh(personagem)
        _ = personagem.universo
        _ = personagem.tipo
        return jsonify(_serialize_personagem(personagem))


@bp.delete("/<int:personagem_id>")
@jwt_required()
def remover_personagem(personagem_id: int):
    """
    Remover personagem
    ---
    tags:
      - Personagens
    parameters:
      - in: path
        name: personagem_id
        schema:
          type: integer
        required: true
    responses:
      204:
        description: Personagem removido
      404:
        description: Personagem não encontrado
    """
    with get_session() as session:
        personagem = session.get(Personagem, personagem_id)
        if not personagem:
            return jsonify({"error": "Personagem não encontrado"}), 404
        session.delete(personagem)
        return "", 204
