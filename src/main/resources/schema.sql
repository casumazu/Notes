DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS note CASCADE;
DROP TABLE IF EXISTS send_note CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name   varchar(250) NOT NULL,
    email  varchar(254) UNIQUE NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS note (
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name            varchar(250),
    description     varchar(250) NOT NULL,
    created         TIMESTAMP WITHOUT TIME ZONE,
    changed         TIMESTAMP WITHOUT TIME ZONE,
    user_id         BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL,
    CONSTRAINT pk_note PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS send_note (
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    sender_user_id     BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL,
    accepted_user_id   BIGINT REFERENCES users(id) ON DELETE CASCADE NOT NULL,
    note_id            BIGINT REFERENCES note(id) ON DELETE CASCADE NOT NULL,
    accepted           BOOLEAN,
    CONSTRAINT pk_send_note PRIMARY KEY(id)
);