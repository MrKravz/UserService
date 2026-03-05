CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255),
                       surname VARCHAR(255),
                       birth_date DATE,
                       email VARCHAR(255) UNIQUE,
                       active VARCHAR(50),
                       created_at DATE,
                       updated_at DATE
);

CREATE TABLE payment_cards (
                               id BIGSERIAL PRIMARY KEY,
                               number BIGINT,
                               holder VARCHAR(255),
                               expiration_date DATE,
                               active VARCHAR(50),
                               created_at DATE,
                               updated_at DATE,
                               user_id BIGINT,
                               CONSTRAINT fk_payment_card_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(id)
                                       ON DELETE CASCADE
);