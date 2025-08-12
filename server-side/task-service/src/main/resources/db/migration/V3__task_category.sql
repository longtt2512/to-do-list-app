-- Parent: categories
CREATE TABLE IF NOT EXISTS task_categories (
                                               id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    category_name  VARCHAR(150) NOT NULL UNIQUE,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW()
    );

-- Child: values (one row per value)
CREATE TABLE IF NOT EXISTS task_category_values (
                                                    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    category_id  UUID NOT NULL REFERENCES task_categories(id) ON DELETE CASCADE,
    value        VARCHAR(150) NOT NULL,
    sort_order   INT NOT NULL,  -- position for UI
    created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT NOW()
    );
