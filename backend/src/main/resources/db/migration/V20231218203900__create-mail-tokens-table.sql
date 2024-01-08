CREATE TYPE MailTokenType AS ENUM ('ACCOUNT_CONFIRMATION', 'PASSWORD_CHANGE');

CREATE TABLE mail_tokens
(
    id              bigint PRIMARY KEY,
    token           VARCHAR       NOT NULL,
    type            MailTokenType NOT NULL,
    create_time     timestamp     NOT NULL,
    expire_time     timestamp     NOT NULL,
    user_account_id bigint        NOT NULL
);

CREATE SEQUENCE mail_tokens_id_seq START 1 INCREMENT 1;

ALTER TABLE mail_tokens
    ADD CONSTRAINT mail_token_user_constraint UNIQUE (token, user_account_id);

ALTER TABLE mail_tokens
    ADD FOREIGN KEY (user_account_id) REFERENCES users (id);