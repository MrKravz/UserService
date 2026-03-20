--liquibase formatted sql

--changeset change:2
ALTER TABLE payment_cards ADD COLUMN version BIGINT;

CREATE INDEX idx_users_name_surname ON users (name, surname);