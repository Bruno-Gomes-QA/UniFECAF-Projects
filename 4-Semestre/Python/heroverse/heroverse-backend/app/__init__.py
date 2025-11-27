from flask import Flask, jsonify
from flask_jwt_extended import JWTManager
from flasgger import Swagger
from flask_cors import CORS
from dotenv import load_dotenv

from .config import settings
from .database import Base, engine
from .docs import swagger_config, swagger_template
from .seed import seed_initial_data

jwt = JWTManager()


def create_app() -> Flask:
    load_dotenv()

    app = Flask(__name__)
    app.config["JWT_SECRET_KEY"] = settings.SECRET_KEY

    jwt.init_app(app)
    CORS(
        app,
        resources={r"/*": {"origins": "*"}},
        allow_headers=["Content-Type", "Authorization"],
        expose_headers=["Authorization"],
    )
    Swagger(app, config=swagger_config, template=swagger_template)

    Base.metadata.create_all(bind=engine)
    seed_initial_data()

    from .routes.usuarios import bp as usuarios_bp
    from .routes.universos import bp as universos_bp
    from .routes.tipos import bp as tipos_bp
    from .routes.personagens import bp as personagens_bp

    app.register_blueprint(usuarios_bp, url_prefix="/auth")
    app.register_blueprint(universos_bp, url_prefix="/universos")
    app.register_blueprint(tipos_bp, url_prefix="/tipos")
    app.register_blueprint(personagens_bp, url_prefix="/personagens")

    @app.get("/health")
    def healthcheck():
        return jsonify({"status": "ok"})

    return app
