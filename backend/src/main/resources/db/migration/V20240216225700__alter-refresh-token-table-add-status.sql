CREATE TYPE RefreshTokenStatus AS ENUM ('ACTIVE', 'EXPIRED', 'NOT_ACTIVE');

ALTER TABLE refresh_token ADD COLUMN status RefreshTokenStatus NOT NULL DEFAULT 'ACTIVE';