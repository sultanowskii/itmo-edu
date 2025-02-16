# Лаба 1

## Задание

Вариант: `367370`

Используя сведения из системных каталогов, получить информацию обо всех триггерах, назначенных на указанную таблицу схемы.

```text
COLUMN NAME		TRIGGER NAME
----------------------- ------------------------
COLUMN1			TRIGGER1
COLUMN2			TRIGGER2
		...
```

Программу оформить в виде процедуры.

## Использование

### Инициализация

```sql
\i schema.sql
```

### Запуск

```sql
\i procedure.sql
```

### Удаление

```sql
\i drop.sql
```

## Пример запуска

```sql
dsods1=# \i procedure.sql 
Target table name: city
SET
psql:procedure.sql:4: NOTICE:  COLUMN NAME    TRIGGER NAME                     
psql:procedure.sql:4: NOTICE:  ------------------------------------------------
psql:procedure.sql:4: NOTICE:  population     trigger_city_check_population    
psql:procedure.sql:4: NOTICE:  name           trigger_city_check_name_not_empty
CALL
RESET
dsods1=# \i procedure.sql 
Target table name: building
SET
psql:procedure.sql:4: NOTICE:  COLUMN NAME    TRIGGER NAME                         
psql:procedure.sql:4: NOTICE:  ----------------------------------------------------
psql:procedure.sql:4: NOTICE:  name           trigger_building_check_name_not_empty
psql:procedure.sql:4: NOTICE:  height         trigger_building_check_not_too_tall  
psql:procedure.sql:4: NOTICE:  area           trigger_building_check_not_too_tall  
CALL
RESET
dsods1=# \i procedure.sql 
Target table name: owner
SET
psql:procedure.sql:4: NOTICE:  COLUMN NAME    TRIGGER NAME                      
psql:procedure.sql:4: NOTICE:  -------------------------------------------------
psql:procedure.sql:4: NOTICE:  name           trigger_owner_check_name_not_empty
CALL
RESET
dsods1=# 
```
