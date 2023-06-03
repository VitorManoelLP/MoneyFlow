CREATE SEQUENCE IF NOT EXISTS usuario_id_seq;

CREATE TABLE IF NOT EXISTS Usuario (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    imagem bytea,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT email_unique UNIQUE (email),
    CONSTRAINT password_not_null CHECK (password <> ''),
    CONSTRAINT nome_not_null CHECK (nome <> ''),
    CONSTRAINT email_not_null CHECK (email <> '')
);