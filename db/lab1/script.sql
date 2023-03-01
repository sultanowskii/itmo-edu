--- delete existing tables

DROP TABLE IF EXISTS
    adventures_humans,
    analyses_humans,
    risks,
    adventures,
    humans,
    conslusions,
    analyses; 

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

CREATE TABLE adventures (
    id SERIAL PRIMARY KEY,
    known BOOLEAN NOT NULL,
    duration INTEGER NOT NULL CHECK (duration >= 0)
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

--- example input

INSERT INTO analyses (duration, description) VALUES
    (50, 'Hibernation analysis'),
    (1, 'Cool research');

INSERT INTO conslusions (analyses_id, description) VALUES
    (1, 'Success!'),
    (1, 'IDK');

INSERT INTO adventures (known, duration) VALUES
    (FALSE, 100),
    (TRUE, 3);

INSERT INTO risks (adventures_id, description) VALUES
    (1, 'Spaceship could run out of oxygen'),
    (1, 'Crew might be kidnapped by ailens'),
    (2, 'It might be too boring');

INSERT INTO humans (name) VALUES
    ('Peter'),
    ('Alex'),
    ('Gennadiy'),
    ('Felix');

INSERT INTO adventures_humans (adventures_id, humans_id) VALUES
    (1, 2),
    (1, 3),
    (1, 4),
    (2, 1),
    (2, 2);

INSERT INTO analyses_humans (analyses_id, humans_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 4);
