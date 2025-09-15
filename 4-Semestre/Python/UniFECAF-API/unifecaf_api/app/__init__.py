from flask import Flask
from flask_cors import CORS

def create_app(testing: bool = False):
    app = Flask(__name__)
    app.config["JSON_AS_ASCII"] = False
    app.config["TESTING"] = testing

    # CORS
    CORS(app)

    # Registra blueprints (resources)
    from .resources.characters import bp as characters_bp
    app.register_blueprint(characters_bp)

    # Healthcheck simples
    @app.get("/health")
    def health():
        return {"status": "ok"}

    return app
