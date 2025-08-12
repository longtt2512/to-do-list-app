CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS members (
                                       id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(150) NOT NULL,
    code        VARCHAR(50)  NOT NULL UNIQUE,
    dob         DATE,
    class       VARCHAR(50),
    phone       VARCHAR(30),
    email       VARCHAR(150) UNIQUE,
    company     JSONB NOT NULL DEFAULT '{}'::jsonb,
    avatar     VARCHAR(255),
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
    );
