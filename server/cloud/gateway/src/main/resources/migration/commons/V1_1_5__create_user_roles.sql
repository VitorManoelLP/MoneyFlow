CREATE TABLE IF NOT EXISTS user_role (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES Usuario (id),
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES Role (id)
);