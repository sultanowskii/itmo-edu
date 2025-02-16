CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    population INTEGER
);

CREATE TABLE building (
    id SERIAL PRIMARY KEY,
    city_id INTEGER REFERENCES city(id),
    name TEXT NOT NULL,
    height INTEGER,
    area INTEGER
);

CREATE TABLE owner (
    id SERIAL PRIMARY KEY,
    building_id INTEGER REFERENCES building(id),
    name TEXT NOT NULL
);

-- one table, one column
CREATE OR REPLACE FUNCTION check_population()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.population < 0 THEN
        RAISE EXCEPTION 'Population cannot be negative';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trigger_city_check_population
BEFORE INSERT OR UPDATE OF population ON city
FOR EACH ROW EXECUTE FUNCTION check_population();


-- multiple tables, 'same' (not really) column
CREATE OR REPLACE FUNCTION check_name_not_empty() 
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.name = '' THEN
        RAISE EXCEPTION 'Name cannot be empty';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trigger_city_check_name_not_empty
BEFORE INSERT OR UPDATE OF name ON city
FOR EACH ROW EXECUTE FUNCTION check_name_not_empty();

CREATE OR REPLACE TRIGGER trigger_building_check_name_not_empty
BEFORE INSERT OR UPDATE OF name ON building
FOR EACH ROW EXECUTE FUNCTION check_name_not_empty();

CREATE OR REPLACE TRIGGER trigger_owner_check_name_not_empty
BEFORE INSERT OR UPDATE OF name ON owner
FOR EACH ROW EXECUTE FUNCTION check_name_not_empty();


-- same table, multiple columns
CREATE OR REPLACE FUNCTION check_building_not_too_tall() 
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.height > SQRT(NEW.area) THEN
        RAISE EXCEPTION 'Building is too tall';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trigger_building_check_not_too_tall
BEFORE INSERT OR UPDATE OF height, area ON building
FOR EACH ROW EXECUTE FUNCTION check_building_not_too_tall();


-- The procedure itself
CREATE OR REPLACE PROCEDURE print_table_triggers(table_name TEXT)
AS $$
DECLARE
    results CURSOR FOR (
        SELECT
            parent_table_column.attname column_name,
            trigger.tgname trigger_name
        FROM pg_trigger trigger
            INNER JOIN pg_class parent_table
                ON parent_table.oid = trigger.tgrelid
                AND parent_table.relname = table_name
            INNER JOIN pg_attribute parent_table_column
                ON parent_table_column.attrelid = trigger.tgrelid
                AND parent_table_column.attnum = ANY(trigger.tgattr::smallint[])
    );
    column_name_length INTEGER;
    max_column_name_length INTEGER;
    trigger_name_length INTEGER;
    max_trigger_name_length INTEGER;
    max_line_length INTEGER;
    COLUMN_NAME CONSTANT TEXT := 'COLUMN NAME';
    TRIGGER_NAME CONSTANT TEXT := 'TRIGGER NAME';
    GAP_LENGTH CONSTANT INTEGER := 4;
BEGIN
    max_column_name_length := LENGTH(COLUMN_NAME);
    max_trigger_name_length := LENGTH(TRIGGER_NAME);

    FOR e IN results
    LOOP
        column_name_length := LENGTH(e.column_name);
        IF column_name_length > max_column_name_length THEN
            max_column_name_length := column_name_length;
        END IF;

        trigger_name_length := LENGTH(e.trigger_name);
        IF trigger_name_length > max_trigger_name_length THEN
            max_trigger_name_length := trigger_name_length;
        END IF;
    END LOOP;

    max_line_length := max_column_name_length + GAP_LENGTH + max_trigger_name_length;

    RAISE NOTICE '%', CONCAT(
        RPAD(COLUMN_NAME, max_column_name_length, ' '),
        REPEAT(' ', GAP_LENGTH),
        RPAD(TRIGGER_NAME, max_trigger_name_length, ' ')
    );
    RAISE NOTICE '%', REPEAT('-', max_line_length);

    FOR e IN results
    LOOP
        RAISE NOTICE '%', CONCAT(
            RPAD(e.column_name, max_column_name_length, ' '),
            REPEAT(' ', GAP_LENGTH),
            RPAD(e.trigger_name, max_trigger_name_length, ' ')
        );
    END LOOP;
END;
$$ LANGUAGE plpgsql;
