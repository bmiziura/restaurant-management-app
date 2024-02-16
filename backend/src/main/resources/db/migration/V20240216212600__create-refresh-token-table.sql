CREATE TABLE refresh_token (
    id bigint PRIMARY KEY,
    token VARCHAR NOT NULL UNIQUE,
    create_time timestamp NOT NULL,
    expire_time timestamp NOT NULL,
    user_account_id bigint NOT NULL,

    FOREIGN KEY (user_account_id) REFERENCES users (id)
);

CREATE SEQUENCE refresh_token_id_seq START 1 INCREMENT 1;