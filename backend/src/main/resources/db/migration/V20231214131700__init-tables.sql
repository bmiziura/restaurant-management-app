CREATE TABLE users (
    id bigint PRIMARY KEY,
    email VARCHAR,
    password VARCHAR,
    first_name VARCHAR,
    last_name VARCHAR
);

CREATE TABLE users_role_mapping (
    user_account_id bigint NOT NULL,
    role varchar NOT NULL
);

CREATE SEQUENCE users_id_seq START 1 INCREMENT 1;

ALTER TABLE users_role_mapping ADD CONSTRAINT users_role_constraint UNIQUE (user_account_id, role);
ALTER TABLE users_role_mapping ADD FOREIGN KEY (user_account_id) REFERENCES users (id);