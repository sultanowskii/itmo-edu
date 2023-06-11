--- delete existing tables

DROP TABLE IF EXISTS
    adventures_humans,
    analyses_humans,
    risks,
    adventures,
    humans,
    conslusions,
    analyses,
    destinations; 

--- create tables

CREATE TABLE analyses (
    id SERIAL PRIMARY KEY,
    duration INTEGER NOT NULL CHECK (duration >= 0),
    description TEXT NOT NULL
);

CREATE TABLE conslusions (
    id SERIAL PRIMARY KEY,
    description TEXT,
    analyses_id INTEGER NOT NULL REFERENCES analyses(id) ON DELETE CASCADE
);

CREATE TABLE destinations (
    x DOUBLE PRECISION NOT NULL,
    y DOUBLE PRECISION NOT NULL,
    z DOUBLE PRECISION NOT NULL,
    name TEXT NOT NULL,
    PRIMARY KEY (x, y, z)
);

CREATE TABLE adventures (
    id SERIAL PRIMARY KEY,
    duration INTEGER NOT NULL CHECK (duration >= 0),
    description TEXT NOT NULL,
    x DOUBLE PRECISION NOT NULL,
    y DOUBLE PRECISION NOT NULL,
    z DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (x, y, z) REFERENCES destinations (x, y, z)
);

CREATE TABLE risks (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    adventures_id INTEGER NOT NULL REFERENCES adventures(id) ON DELETE CASCADE
);

CREATE TABLE humans (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE adventures_humans (
    adventures_id INTEGER NOT NULL REFERENCES adventures(id) ON DELETE CASCADE,
    humans_id INTEGER NOT NULL REFERENCES humans(id) ON DELETE CASCADE,
    UNIQUE (adventures_id, humans_id)
);

CREATE TABLE analyses_humans (
    analyses_id INTEGER NOT NULL REFERENCES analyses(id) ON DELETE CASCADE,
    humans_id INTEGER NOT NULL REFERENCES humans(id) ON DELETE CASCADE,
    UNIQUE (analyses_id, humans_id)
);
