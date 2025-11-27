CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL 
);

CREATE TABLE universos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE tipos_personagem (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE personagens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(120) NOT NULL,
    idade INT,
    poder_principal VARCHAR(150),
    
    universo_id INT NOT NULL,
    tipo_id INT NOT NULL,
    
    FOREIGN KEY (universo_id) REFERENCES universos(id),
    FOREIGN KEY (tipo_id) REFERENCES tipos_personagem(id)
);