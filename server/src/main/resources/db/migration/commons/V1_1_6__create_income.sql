CREATE SEQUENCE IF NOT EXISTS rendimento_id_seq;

CREATE TABLE IF NOT EXISTS rendimento (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    descricao VARCHAR(50) NOT NULL,
    valor NUMERIC(10,2) NOT NULL,
    data DATE NOT NULL,
    usuario_rendimento_id BIGSERIAL NOT NULL,
    CONSTRAINT descricao_not_null_rendimento CHECK (descricao <> ''),
    FOREIGN KEY (usuario_rendimento_id) REFERENCES usuario_rendimento(id)
);