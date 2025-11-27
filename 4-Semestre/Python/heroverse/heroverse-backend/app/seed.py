import json
from pathlib import Path

from .auth import hash_password
from .database import get_session
from .models import Personagem, TipoPersonagem, Universo, Usuario

DEFAULT_ADMIN = {
    "nome": "admin",
    "email": "admin@heroverse.com",
    "senha": "admin123",
}

ROOT_DIR = Path(__file__).resolve().parent.parent.parent
SEED_FILE = ROOT_DIR / "seed.json"


def _load_seed_file() -> dict:
    """Tenta ler seed.json (JSON válido ou script com variáveis)."""
    if not SEED_FILE.exists():
        return {}

    raw = SEED_FILE.read_text(encoding="utf-8")

    # Primeiro tenta JSON padrão
    try:
        data = json.loads(raw)
        return data if isinstance(data, dict) else {}
    except json.JSONDecodeError:
        pass

    # Fallback para o formato existente (variáveis Python no arquivo)
    local_vars: dict = {}
    try:
        exec(raw, {}, local_vars)
    except Exception:
        return {}

    return {
        "universos": local_vars.get("universos", []),
        "tipos": local_vars.get("tipos", []),
        "personagens": local_vars.get("personagens", []),
    }


def _seed_admin(session):
    existing = session.query(Usuario).filter_by(email=DEFAULT_ADMIN["email"]).first()
    if existing:
        return

    admin = Usuario(
        nome=DEFAULT_ADMIN["nome"],
        email=DEFAULT_ADMIN["email"],
        senha=hash_password(DEFAULT_ADMIN["senha"]),
    )
    session.add(admin)


def _seed_universos(session, universos):
    for item in universos:
        if not item.get("nome"):
            continue
        existing = session.get(Universo, item.get("id")) or session.query(Universo).filter_by(nome=item["nome"]).first()
        if existing:
            continue
        session.add(
            Universo(
                id=item.get("id"),
                nome=item["nome"],
                descricao=item.get("descricao"),
            )
        )


def _seed_tipos(session, tipos):
    for item in tipos:
        if not item.get("nome"):
            continue
        existing = session.get(TipoPersonagem, item.get("id")) or session.query(TipoPersonagem).filter_by(
            nome=item["nome"]
        ).first()
        if existing:
            continue
        session.add(
            TipoPersonagem(
                id=item.get("id"),
                nome=item["nome"],
                descricao=item.get("descricao"),
            )
        )


def _seed_personagens(session, personagens):
    for item in personagens:
        if not item.get("nome") or not item.get("universo_id") or not item.get("tipo_id"):
            continue

        existing = session.get(Personagem, item.get("id")) or session.query(Personagem).filter_by(
            nome=item["nome"]
        ).first()
        if existing:
            continue

        # Garante que universo/tipo existem antes de criar
        if not session.get(Universo, item["universo_id"]) or not session.get(TipoPersonagem, item["tipo_id"]):
            continue

        session.add(
            Personagem(
                id=item.get("id"),
                nome=item["nome"],
                idade=item.get("idade"),
                poder_principal=item.get("poder_principal"),
                imagem_url=item.get("imagem_url"),
                universo_id=item["universo_id"],
                tipo_id=item["tipo_id"],
            )
        )


def seed_initial_data():
    seed_data = _load_seed_file()

    with get_session() as session:
        _seed_admin(session)
        if seed_data:
            _seed_universos(session, seed_data.get("universos", []))
            _seed_tipos(session, seed_data.get("tipos", []))
            _seed_personagens(session, seed_data.get("personagens", []))
