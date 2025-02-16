-- select all triggers and their according tables
SELECT
    parent_table.relname "TABLE NAME",
    parent_table_column.attname "COLUMN NAME",
    trigger.tgname "TRIGGER NAME"
FROM pg_trigger trigger
    INNER JOIN pg_class parent_table
        ON parent_table.oid = trigger.tgrelid
    INNER JOIN pg_attribute parent_table_column
        ON parent_table_column.attrelid = trigger.tgrelid
        AND parent_table_column.attnum = ANY(trigger.tgattr::smallint[])
;
