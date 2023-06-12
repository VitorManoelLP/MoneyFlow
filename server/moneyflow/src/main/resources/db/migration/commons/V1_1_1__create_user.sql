CREATE SEQUENCE IF NOT EXISTS usuario_id_seq;

CREATE TABLE IF NOT EXISTS Usuario (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    imagem VARCHAR(50),
    CONSTRAINT email_unique UNIQUE (email),
    CONSTRAINT nome_not_null CHECK (nome <> ''),
    CONSTRAINT email_not_null CHECK (email <> '')
);