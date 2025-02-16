\prompt 'Target table name: ' target_table_name
SET lab1.target_table_name = :'target_table_name';

CALL print_table_triggers(current_setting('lab1.target_table_name'));

RESET lab1.target_table_name;
