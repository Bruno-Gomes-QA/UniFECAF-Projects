# In-memory "database" (max 5 columns: id, name, class, power, weakness)
characters = [
    {"id": 1, "name": "Maria",  "class": "Archer",   "power": "Precision",       "weakness": "Melee"},
    {"id": 2, "name": "Arthus", "class": "Warrior",  "power": "Brute Force",     "weakness": "Magic"},
    {"id": 3, "name": "Lyra",   "class": "Mage",     "power": "Elemental Magic", "weakness": "Close Combat"},
]

def next_id():
    return (max((c["id"] for c in characters), default=0) + 1)

def find_character(char_id: int):
    return next((c for c in characters if c["id"] == char_id), None)
