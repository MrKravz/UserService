--liquibase formatted sql

--changeset change:1
ALTER TABLE users ADD COLUMN version BIGINT;