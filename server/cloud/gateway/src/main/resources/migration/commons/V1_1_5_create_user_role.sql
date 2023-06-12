CREATE TABLE IF NOT EXISTS user_role (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGSERIAL NOT NULL,
    role_id BIGSERIAL NOT NULL,
    CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES usuario (id),
    CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES role (id)
);