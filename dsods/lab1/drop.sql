DROP PROCEDURE print_table_triggers;

DROP TRIGGER trigger_owner_check_name_not_empty ON owner;
DROP TRIGGER trigger_building_check_name_not_empty ON building;
DROP TRIGGER trigger_city_check_name_not_empty ON city;
DROP FUNCTION check_name_not_empty;

DROP TRIGGER trigger_building_check_not_too_tall ON building;
DROP FUNCTION check_building_not_too_tall;

DROP TRIGGER trigger_city_check_population ON city;
DROP FUNCTION check_population;

DROP TABLE owner;
DROP TABLE building;
DROP TABLE city;
