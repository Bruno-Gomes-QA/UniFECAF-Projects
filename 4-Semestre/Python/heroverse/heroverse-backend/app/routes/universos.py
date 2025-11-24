from flask import Blueprint, jsonify, request
from flask_jwt_extended import jwt_required
from pydantic import ValidationError

from ..database import get_session
from ..models import Universo
from ..schemas import UniversoCreate, UniversoOut

bp = Blueprint("universos", __name__)


@bp.get("/")
@jwt_required()
def listar_universos():
    """
    Listar universos
    ---
    tags:
      - Universos
    responses:
      200:
        description: Lista de universos
        schema:
          type: array
          items:
            $ref: '#/definitions/UniversoOut'
    """
    with get_session() as session:
        universos = session.query(Universo).all()
        data = [UniversoOut.model_validate(u).model_dump() for u in universos]
    return jsonify(data)


@bp.get("/<int:universo_id>")
@jwt_required()
def detalhar_universo(universo_id: int):
    """
    Detalhar universo
    ---
    tags:
      - Universos
    parameters:
      - in: path
        name: universo_id
        schema:
          type: integer
        required: true
        description: ID do universo
    responses:
      200:
        description: Universo encontrado
        schema:
          $ref: '#/definitions/UniversoOut'
      404:
        description: Universo não encontrado
    """
    with get_session() as session:
        universo = session.get(Universo, universo_id)
        if not universo:
            return jsonify({"error": "Universo não encontrado"}), 404
        data = UniversoOut.model_validate(universo).model_dump()
    return jsonify(data)


@bp.post("/")
@jwt_required()
def criar_universo():
    """
    Criar universo
    ---
    tags:
      - Universos
    parameters:
      - in: body
        name: payload
        required: true
        schema:
          $ref: '#/definitions/UniversoCreate'
    responses:
      201:
        description: Universo criado
        schema:
          $ref: '#/definitions/UniversoOut'
      400:
        description: Erro de validação
    """
    data = request.get_json() or {}
    try:
        payload = UniversoCreate(**data)
    except ValidationError as exc:
        return jsonify({"errors": exc.errors()}), 400

    with get_session() as session:
        universo = Universo(nome=payload.nome, descricao=payload.descricao)
        session.add(universo)
        session.flush()
        return (
            jsonify(UniversoOut.model_validate(universo).model_dump()),
            201,
        )


@bp.put("/<int:universo_id>")
@jwt_required()
def atualizar_universo(universo_id: int):
    """
    Atualizar universo
    ---
    tags:
      - Universos
    parameters:
      - in: path
        name: universo_id
        schema:
          type: integer
        required: true
      - in: body
        name: payload
        required: true
        schema:
          $ref: '#/definitions/UniversoCreate'
    responses:
      200:
        description: Universo atualizado
        schema:
          $ref: '#/definitions/UniversoOut'
      404:
        description: Universo não encontrado
    """
    data = request.get_json() or {}
    try:
        payload = UniversoCreate(**data)
    except ValidationError as exc:
        return jsonify({"errors": exc.errors()}), 400

    with get_session() as session:
        universo = session.get(Universo, universo_id)
        if not universo:
            return jsonify({"error": "Universo não encontrado"}), 404

        universo.nome = payload.nome
        universo.descricao = payload.descricao
        session.add(universo)
        return jsonify(UniversoOut.model_validate(universo).model_dump())


@bp.delete("/<int:universo_id>")
@jwt_required()
def remover_universo(universo_id: int):
    """
    Remover universo
    ---
    tags:
      - Universos
    parameters:
      - in: path
        name: universo_id
        schema:
          type: integer
        required: true
    responses:
      204:
        description: Universo removido
      404:
        description: Universo não encontrado
    """
    with get_session() as session:
        universo = session.get(Universo, universo_id)
        if not universo:
            return jsonify({"error": "Universo não encontrado"}), 404
        session.delete(universo)
        return "", 204
