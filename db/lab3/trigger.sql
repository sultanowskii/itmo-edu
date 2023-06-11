CREATE OR REPLACE FUNCTION delete_random_adventures_humans() RETURNS TRIGGER AS
$$
BEGIN

DELETE FROM
    adventures_humans ah1
WHERE
    ah1.adventures_id = NEW.adventures_id
    AND ah1.humans_id = (
        SELECT
            ah2.humans_id hid
        FROM
            adventures
            INNER JOIN adventures_humans ah2 ON ah2.adventures_id = adventures.id
        WHERE
            adventures.id = NEW.adventures_id
        ORDER BY
            random()
        LIMIT
            1
    );
    RETURN NEW;

END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER risk_trigger
AFTER INSERT ON risks FOR EACH ROW EXECUTE PROCEDURE delete_random_adventures_humans();
