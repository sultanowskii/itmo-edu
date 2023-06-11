INSERT INTO destinations (x, y, z, name) VALUES
    (0, 0, 0, 'island1'),
    (0, 0, 1, 'island2'),
    (0, 0, 2, 'island3'),
    (0, 0, 3, 'island4'),
    (0, 0, 4, 'island5'),
    (0, 0, 5, 'island6');

INSERT INTO adventures (duration, description, x, y, z) VALUES
    (30, 'adv1', 0, 0, 0),
    (30, 'adv2', 0, 0, 1),
    (30, 'adv3', 0, 0, 2),
    (30, 'adv4', 0, 0, 3),
    (30, 'adv5', 0, 0, 4),
    (30, 'adv6', 0, 0, 5);

INSERT INTO humans (name) VALUES
    ('peter'),
    ('alex');

INSERT INTO adventures_humans (humans_id, adventures_id) VALUES
    (1, 1),
    (2, 6);