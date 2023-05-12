CREATE SEQUENCE IF NOT EXISTS usuario_rendimento_id_seq;

CREATE TABLE IF NOT EXISTS usuario_rendimento (
  id BIGSERIAL NOT NULL,
  usuario_id BIGSERIAL NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  id_tipo_rendimento BIGSERIAL NOT NULL,
  competencia INTEGER NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (id_tipo_rendimento) REFERENCES tipo_rendimento(id),
  CONSTRAINT descricao_not_null CHECK (descricao <> '')
);