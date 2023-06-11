CREATE
OR REPLACE FUNCTION distance(
    x1 adventures.x % TYPE,
    y1 adventures.y % TYPE,
    z1 adventures.z % TYPE,
    x2 adventures.x % TYPE,
    y2 adventures.y % TYPE,
    z2 adventures.z % TYPE
) returns DOUBLE PRECISION LANGUAGE plpgsql as
$$
BEGIN
RETURN ABS(
    SQRT(POWER(x1, 2) + POWER(y1, 2) + POWER(z1, 2)) - SQRT(POWER(x2, 2) + POWER(y2, 2) + POWER(z2, 2))
);
END;
$$;

CREATE OR REPLACE FUNCTION min_distance_between_human_adventure_destinations(human1 humans.id % TYPE, human2 humans.id % TYPE) returns SETOF DOUBLE PRECISION LANGUAGE plpgsql as
$func$
BEGIN
RETURN QUERY
WITH adventure_fectch1 AS (
    SELECT
        adventures.x x1,
        adventures.y y1,
        adventures.z z1
    FROM
        humans
        INNER JOIN adventures_humans ON adventures_humans.humans_id = humans.id
        INNER JOIN adventures ON adventures.id = adventures_humans.adventures_id
    WHERE
        humans.id = human1
), adventure_fectch2 AS (
    SELECT
        adventures.x x2,
        adventures.y y2,
        adventures.z z2
    FROM
        humans
        INNER JOIN adventures_humans ON adventures_humans.humans_id = humans.id
        INNER JOIN adventures ON adventures.id = adventures_humans.adventures_id
    WHERE
        humans.id = human2
), distances AS (
    SELECT
        distance(x1, y1, z1, x2, y2, z2) distance
    FROM
        adventure_fectch1
        CROSS JOIN adventure_fectch2
) SELECT MIN(distance) FROM distances;
END;
$func$;