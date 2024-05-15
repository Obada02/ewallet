CREATE SEQUENCE IF NOT EXISTS sequence_card START WITH 1 INCREMENT BY 5;

CREATE TABLE card
(
    id          BIGINT      NOT NULL,
    credentials VARCHAR(50) NOT NULL,
    expiry_date TIMESTAMP   NOT NULL,
    user_id     BIGINT      NOT NULL,
    CONSTRAINT pk_card PRIMARY KEY (id)
);

ALTER TABLE card
    ADD CONSTRAINT uc_card_credentials UNIQUE (credentials);

CREATE UNIQUE INDEX card_user_id_credentials_key ON card (user_id, credentials);

ALTER TABLE card
    ADD CONSTRAINT FK_CARD_ON_USER FOREIGN KEY (user_id) REFERENCES public."user" (id);
