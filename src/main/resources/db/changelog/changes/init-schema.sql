--liquibase formatted sql

--changeset init:1
CREATE TABLE users
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255),
    surname    VARCHAR(255),
    birth_date DATE,
    email      VARCHAR(255) UNIQUE,
    active     VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE payment_cards
(
    id              BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    number          VARCHAR UNIQUE,
    holder          VARCHAR(255),
    expiration_date DATE,
    active          VARCHAR(50),
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP,
    user_id         BIGINT,
    CONSTRAINT fk_payment_card_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_cards_user_id ON payment_cards (user_id);