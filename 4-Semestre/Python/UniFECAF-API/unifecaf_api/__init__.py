from flask import Flask
from flask_cors import CORS

def create_app(testing: bool = False):
    app = Flask(__name__)
    app.config["JSON_AS_ASCII"] = False
    app.config["TESTING"] = testing
    app.config.setdefault("SWAGGER_TITLE", "Characters API")
    app.config.setdefault("SWAGGER_VERSION", "1.0.0")

    # CORS
    CORS(app)

    # Registra blueprints (resources)
    from .resources.frontend import bp as frontend_bp
    from .resources.characters import bp as characters_bp
    from .openapi import bp as docs_bp
    app.register_blueprint(frontend_bp)
    app.register_blueprint(characters_bp)
    app.register_blueprint(docs_bp)

    # Healthcheck simples
    @app.get("/health")
    def health():
        return {"status": "ok"}

    return app
