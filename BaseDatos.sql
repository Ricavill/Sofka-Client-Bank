
\c sofkadb

CREATE SCHEMA ms1 AUTHORIZATION postgres;


CREATE TABLE ms1.person
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(250) NOT NULL,
    gender         INT          NOT NULL,
    age            INT          NOT NULL,
    identification VARCHAR(10)  NOT NULL,
    address        VARCHAR(250) NOT NULL,
    telephone      VARCHAR(15)  NOT NULL,
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP,
    deleted_at     TIMESTAMP
);

CREATE TABLE ms1.client
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(60)         NOT NULL,
    status        SMALLINT            NOT NULL,
    CONSTRAINT fk_client_person FOREIGN KEY (id)
        REFERENCES ms1.person (id)
        ON DELETE CASCADE
);

CREATE SCHEMA ms2 AUTHORIZATION postgres;


CREATE SEQUENCE account_number_seq
    START WITH 100000000000 -- número inicial (ej: 12 dígitos)
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE ms2.account
(
    id              SERIAL PRIMARY KEY,
    account_number  BIGINT UNIQUE NOT NULL DEFAULT nextval('account_number_seq'),
    account_type    INT           NOT NULL,
    initial_balance NUMERIC       NOT NULL,
    status          SMALLINT      NOT NULL,
    client_id       int           NOT NULL,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP,
    deleted_at      TIMESTAMP
);

CREATE TABLE ms2.transaction
(
    id              SERIAL PRIMARY KEY,
    account_id      INT     NOT NULL,
    transaction_type INT     NOT NULL,
    amount           NUMERIC NOT NULL,
    balance         NUMERIC NOT NULL,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP,
    deleted_at      TIMESTAMP,

    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id)
        REFERENCES ms2.account (id)
        ON DELETE CASCADE
)

