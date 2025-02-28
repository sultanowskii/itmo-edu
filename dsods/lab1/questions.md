# Вопросы

## reltype

`-> OID pg_type`

0 для индексов, последовательностей (их нет в `pg_type`)

`pg_type` - каталог, хранящий информацию о типах данных.

Тип `_T` - есть `[]T`. `[]` нельзя пихать в имя идентификатора, поэтому `_` ([источник 1](https://dba.stackexchange.com/questions/307344/what-is-the-difference-between-postgres-text-and-text-array-types), [источник 2](https://stackoverflow.com/questions/71332976/text-postgres-data-type)).

## pg_namespace

The catalog pg_namespace stores namespaces. A namespace is the structure underlying SQL schemas: each namespace can have a separate collection of relations, types, etc. without name conflicts.

Есть id, name, owner, привилегии доступа

[Почему не `pg_schema`?](https://postgrespro.ru/list/thread-id/1623569)

## Хранение функций, процедур и триггеров

`pg_proc`. Различаются по `prokind` (`f`, `p`, ...)

Триггеры - в `pg_trigger`, ссылаются на `pg_proc` через `tgfoid` (`the function to be called`)
