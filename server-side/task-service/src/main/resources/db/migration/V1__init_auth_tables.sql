CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS users (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username      VARCHAR(100) NOT NULL UNIQUE,
    password      VARCHAR(200) NOT NULL,
    enabled       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS roles (
                                     id    BIGSERIAL PRIMARY KEY,
                                     name  VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS user_roles (
    user_id  UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id  BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
    );

INSERT INTO roles(name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$10$7fT3p2Wm6Jj9y0hQe5pZ7u1V7P3v0FQ6h1a3b9R2V0B6zq3o3Yt7u', TRUE)
    ON CONFLICT (username) DO NOTHING;

INSERT INTO user_roles(user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'
    ON CONFLICT DO NOTHING;
