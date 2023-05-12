INSERT INTO tipo_rendimento (id, descricao) VALUES (1, 'Receita') ON CONFLICT DO NOTHING;
INSERT INTO tipo_rendimento (id, descricao) VALUES (2, 'Despesa') ON CONFLICT DO NOTHING;