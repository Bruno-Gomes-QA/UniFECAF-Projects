from flask import Blueprint, jsonify, request
from flask_jwt_extended import jwt_required
from pydantic import ValidationError

from ..database import get_session
from ..models import TipoPersonagem
from ..schemas import TipoCreate, TipoOut

bp = Blueprint("tipos", __name__)


@bp.get("/")
@jwt_required()
def listar_tipos():
    """
    Listar tipos de personagem
    ---
    tags:
      - Tipos
    responses:
      200:
        description: Lista de tipos
        schema:
          type: array
          items:
            $ref: '#/definitions/TipoOut'
    """
    with get_session() as session:
        tipos = session.query(TipoPersonagem).all()
        data = [TipoOut.model_validate(tp).model_dump() for tp in tipos]
    return jsonify(data)


@bp.get("/<int:tipo_id>")
@jwt_required()
def detalhar_tipo(tipo_id: int):
    """
    Detalhar tipo de personagem
    ---
    tags:
      - Tipos
    parameters:
      - in: path
        name: tipo_id
        schema:
          type: integer
        required: true
    responses:
      200:
        description: Tipo encontrado
        schema:
          $ref: '#/definitions/TipoOut'
      404:
        description: Tipo não encontrado
    """
    with get_session() as session:
        tipo = session.get(TipoPersonagem, tipo_id)
        if not tipo:
            return jsonify({"error": "Tipo não encontrado"}), 404
        data = TipoOut.model_validate(tipo).model_dump()
    return jsonify(data)


@bp.post("/")
@jwt_required()
def criar_tipo():
    """
    Criar tipo de personagem
    ---
    tags:
      - Tipos
    parameters:
      - in: body
        name: payload
        required: true
        schema:
          $ref: '#/definitions/TipoCreate'
    responses:
      201:
        description: Tipo criado
        schema:
          $ref: '#/definitions/TipoOut'
      400:
        description: Erro de validação
    """
    data = request.get_json() or {}
    try:
        payload = TipoCreate(**data)
    except ValidationError as exc:
        return jsonify({"errors": exc.errors()}), 400

    with get_session() as session:
        tipo = TipoPersonagem(nome=payload.nome, descricao=payload.descricao)
        session.add(tipo)
        session.flush()
        return jsonify(TipoOut.model_validate(tipo).model_dump()), 201


@bp.put("/<int:tipo_id>")
@jwt_required()
def atualizar_tipo(tipo_id: int):
    """
    Atualizar tipo de personagem
    ---
    tags:
      - Tipos
    parameters:
      - in: path
        name: tipo_id
        schema:
          type: integer
        required: true
      - in: body
        name: payload
        required: true
        schema:
          $ref: '#/definitions/TipoCreate'
    responses:
      200:
        description: Tipo atualizado
        schema:
          $ref: '#/definitions/TipoOut'
      404:
        description: Tipo não encontrado
    """
    data = request.get_json() or {}
    try:
        payload = TipoCreate(**data)
    except ValidationError as exc:
        return jsonify({"errors": exc.errors()}), 400

    with get_session() as session:
        tipo = session.get(TipoPersonagem, tipo_id)
        if not tipo:
            return jsonify({"error": "Tipo não encontrado"}), 404

        tipo.nome = payload.nome
        tipo.descricao = payload.descricao
        session.add(tipo)
        return jsonify(TipoOut.model_validate(tipo).model_dump())


@bp.delete("/<int:tipo_id>")
@jwt_required()
def remover_tipo(tipo_id: int):
    """
    Remover tipo de personagem
    ---
    tags:
      - Tipos
    parameters:
      - in: path
        name: tipo_id
        schema:
          type: integer
        required: true
    responses:
      204:
        description: Tipo removido
      404:
        description: Tipo não encontrado
    """
    with get_session() as session:
        tipo = session.get(TipoPersonagem, tipo_id)
        if not tipo:
            return jsonify({"error": "Tipo não encontrado"}), 404
        session.delete(tipo)
        return "", 204
