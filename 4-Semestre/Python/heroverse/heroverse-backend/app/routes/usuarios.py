from flask import Blueprint, jsonify, request
from flask_jwt_extended import create_access_token
from pydantic import ValidationError

from ..auth import hash_password, verify_password
from ..database import get_session
from ..models import Usuario
from ..schemas import UsuarioCreate, UsuarioLogin, UsuarioOut

bp = Blueprint("usuarios", __name__)


@bp.post("/register")
def register():
    """
    Registrar novo usuário
    ---
    tags:
      - Auth
    security: []
    parameters:
      - in: body
        name: payload
        required: true
        schema:
          $ref: '#/definitions/UsuarioCreate'
    responses:
      201:
        description: Usuário criado
        schema:
          type: object
          properties:
            user:
              $ref: '#/definitions/UsuarioOut'
      400:
        description: Erro de validação
    """
    data = request.get_json() or {}
    try:
        payload = UsuarioCreate(**data)
    except ValidationError as exc:
        return jsonify({"errors": exc.errors()}), 400

    with get_session() as session:
        if session.query(Usuario).filter_by(email=payload.email).first():
            return jsonify({"error": "E-mail já cadastrado"}), 400

        user = Usuario(
            nome=payload.nome,
            email=payload.email,
            senha=hash_password(payload.senha),
        )
        session.add(user)
        session.flush()

        return jsonify({"user": UsuarioOut.model_validate(user).model_dump()}), 201


@bp.post("/login")
def login():
    """
    Autenticar usuário
    ---
    tags:
      - Auth
    security: []
    parameters:
      - in: body
        name: payload
        required: true
        schema:
          $ref: '#/definitions/UsuarioLogin'
    responses:
      200:
        description: Token JWT emitido
        schema:
          type: object
          properties:
            access_token:
              type: string
            user:
              $ref: '#/definitions/UsuarioOut'
      401:
        description: Credenciais inválidas
    """
    data = request.get_json() or {}
    try:
        payload = UsuarioLogin(**data)
    except ValidationError as exc:
        return jsonify({"errors": exc.errors()}), 400

    with get_session() as session:
        user = session.query(Usuario).filter_by(email=payload.email).first()
        if not user or not verify_password(payload.senha, user.senha):
            return jsonify({"error": "Credenciais inválidas"}), 401

        token = create_access_token(identity=user.id)

        return jsonify(
            {"access_token": token, "user": UsuarioOut.model_validate(user).model_dump()}
        )
