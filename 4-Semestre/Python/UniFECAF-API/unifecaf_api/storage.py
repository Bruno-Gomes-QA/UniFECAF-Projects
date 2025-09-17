# In-memory "database" (max 5 columns: id, name, class, power, weakness)
characters = [
    {"id": 1, "name": "Link", "class": "Arqueira", "power": "Precisão", "weakness": "Combate corpo a corpo"},
    {"id": 2, "name": "Mario", "class": "Encanador", "power": "nenhum", "weakness": "Inimigos (ex. Goomba, Koopa, etc.)"},
    {"id": 3, "name": "Pikachu", "class": "Elétrico", "power": "Choque do trovão", "weakness": "Terra"},
    {"id": 4, "name": "Luigi", "class": "Encanador", "power": "nenhum", "weakness": "Inimigos (ex. Goomba, Koopa, etc.)"},
    {"id": 5, "name": "Zelda", "class": "Maga", "power": "Magia", "weakness": "Combate corpo a corpo"},
    {"id": 6, "name": "Bowser", "class": "Rei dos Koopas", "power": "Força bruta", "weakness": "Velocidade"},
    {"id": 7, "name": "Charizard", "class": "Fogo/Voador", "power": "Lança-chamas", "weakness": "Água"},
    {"id": 8, "name": "Peach", "class": "Princesa", "power": "Coração puro", "weakness": "Inimigos (ex. Bowser)"},
    {"id": 9, "name": "Yoshi", "class": "Dinossauro", "power": "Língua longa", "weakness": "Velocidade"},
    {"id": 10, "name": "Ganondorf", "class": "Rei dos Gerudo", "power": "Magia negra", "weakness": "Luz"},
    {"id": 11, "name": "Donkey Kong", "class": "Gorila", "power": "Força bruta", "weakness": "Velocidade"},
    {"id": 12, "name": "Wario", "class": "Aventureiro", "power": "Força bruta", "weakness": "Ganância"},
    {"id": 13, "name": "Mewtwo", "class": "Psíquico", "power": "Telecinese", "weakness": "Inimigos (ex. Darkrai)"},
    {"id": 14, "name": "Waluigi", "class": "Aventureiro", "power": "Agilidade", "weakness": "Ganância"},
]


def next_id():
    return (max((c["id"] for c in characters), default=0) + 1)


def find_character(char_id: int):
    return next((c for c in characters if c["id"] == char_id), None)
