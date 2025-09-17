from flask import Blueprint, render_template

from ..storage import characters

bp = Blueprint("frontend", __name__)


@bp.get("/")
def characters_page():
    """Render a responsive gallery with all characters."""
    return render_template("characters.html", characters=characters)
