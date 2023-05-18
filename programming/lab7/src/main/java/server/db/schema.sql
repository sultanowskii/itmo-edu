BEGIN;

CREATE TYPE color AS ENUM (
    'RED',
    'ORANGE',
    'WHITE',
    'BROWN'
);

CREATE TYPE country AS ENUM (
    'THAILAND',
    'SOUTH_KOREA',
    'NORTH_KOREA'
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    login TEXT UNIQUE NOT NULL,
    hashed_password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS coordinates (
    id SERIAL PRIMARY KEY,
    x FLOAT NOT NULL CONSTRAINT coordinates_x_boundary CHECK (x > -527),
    y INTEGER NOT NULL CONSTRAINT coordinates_y_boundary CHECK (y <= 897),
    owner_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS location (
    id SERIAL PRIMARY KEY,
    x DOUBLE PRECISION NOT NULL,
    y INTEGER NOT NULL,
    name TEXT NOT NULL CONSTRAINT location_not_empty_name CHECK (length(name) > 0),
    owner_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    coordinates_id INT NOT NULL REFERENCES coordinates(id) ON DELETE RESTRICT,
    creation_date TIMESTAMPTZ,
    height BIGINT NOT NULL CONSTRAINT person_positive_height CHECK (height > 0),
    passport_id TEXT NOT NULL UNIQUE CONSTRAINT person_passport_validation CHECK (length(passport_id) > 0 AND length(passport_id) <= 46),
    eye_color color,
    nationality country,
    location_id INT NOT NULL REFERENCES location(id) ON DELETE RESTRICT,
    owner_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

END;