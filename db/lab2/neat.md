# interesno da

https://towardsdatascience.com/how-we-optimized-postgresql-queries-100x-ff52555eabe

---

Забавно:

```sql
WITH "УЧЕНИКИ_СО_СР_ОЦЕНКАМИ" AS (
    SELECT
        "УЧЕНИКИ_ГРУПП"."ИД",
        "ГРУППА",
        "ФАМИЛИЯ",
        "ИМЯ",
        "ОТЧЕСТВО",
        AVG(
            CASE
                WHEN "Н_ВЕДОМОСТИ"."ОЦЕНКА" IN ('2', '3', '4', '5') THEN CAST("Н_ВЕДОМОСТИ"."ОЦЕНКА" AS INTEGER)
                ELSE 2
            END
        ) AS "СР_ОЦЕНКА"
    FROM
        "Н_УЧЕНИКИ" "УЧЕНИКИ_ГРУПП"
        INNER JOIN "Н_ЛЮДИ" ON "Н_ЛЮДИ"."ИД" = "УЧЕНИКИ_ГРУПП"."ЧЛВК_ИД"
        INNER JOIN "Н_ВЕДОМОСТИ" ON "Н_ВЕДОМОСТИ"."ЧЛВК_ИД" = "УЧЕНИКИ_ГРУПП"."ЧЛВК_ИД"
    GROUP BY
        "УЧЕНИКИ_ГРУПП"."ИД",
        "ГРУППА",
        "ФАМИЛИЯ",
        "ИМЯ",
        "ОТЧЕСТВО"
)
SELECT
    *
FROM
    "УЧЕНИКИ_СО_СР_ОЦЕНКАМИ" "УЧЕНИКИ_4100"
WHERE
    "УЧЕНИКИ_4100"."СР_ОЦЕНКА" < (
        SELECT
            MAX("СР_ОЦЕНКА")
        FROM
            "УЧЕНИКИ_СО_СР_ОЦЕНКАМИ" "УЧЕНИКИ_3100"
        WHERE
            "УЧЕНИКИ_3100"."ГРУППА" = '3100'
    )
    AND "УЧЕНИКИ_4100"."ГРУППА" = '4100';
```

Анализ:

```
                                                                                     QUERY PLAN                                                                                     
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 CTE Scan on "УЧЕНИКИ_СО_СР_ОЦЕНКАМИ" "УЧЕНИКИ_4100"  (cost=160879.09..186207.81 rows=1689 width=230) (actual time=2652.775..2656.889 rows=200 loops=1)
   Filter: (("СР_ОЦЕНКА" < $3) AND (("ГРУППА")::text = '4100'::text))
   Rows Removed by Filter: 20666
   CTE УЧЕНИКИ_СО_СР_ОЦЕНКАМИ
     ->  GroupAggregate  (cost=4.56..138070.56 rows=1013149 width=89) (actual time=0.209..2636.683 rows=20866 loops=1)
           Group Key: "УЧЕНИКИ_ГРУПП"."ИД", "УЧЕНИКИ_ГРУПП"."ГРУППА", "Н_ЛЮДИ"."ФАМИЛИЯ", "Н_ЛЮДИ"."ИМЯ", "Н_ЛЮДИ"."ОТЧЕСТВО"
           ->  Incremental Sort  (cost=4.56..100077.47 rows=1013149 width=63) (actual time=0.182..1822.501 rows=1611135 loops=1)
                 Sort Key: "УЧЕНИКИ_ГРУПП"."ИД", "УЧЕНИКИ_ГРУПП"."ГРУППА", "Н_ЛЮДИ"."ФАМИЛИЯ", "Н_ЛЮДИ"."ИМЯ", "Н_ЛЮДИ"."ОТЧЕСТВО"
                 Presorted Key: "УЧЕНИКИ_ГРУПП"."ИД"
                 Full-sort Groups: 17868  Sort Method: quicksort  Average Memory: 34kB  Peak Memory: 34kB
                 Pre-sorted Groups: 15296  Sort Method: quicksort  Average Memory: 39kB  Peak Memory: 40kB
                 ->  Nested Loop  (cost=0.89..39886.21 rows=1013149 width=63) (actual time=0.040..665.105 rows=1611135 loops=1)
                       Join Filter: ("Н_ЛЮДИ"."ИД" = "Н_ВЕДОМОСТИ"."ЧЛВК_ИД")
                       ->  Nested Loop  (cost=0.58..4832.76 rows=23311 width=65) (actual time=0.030..60.232 rows=23311 loops=1)
                             ->  Index Scan using "УЧЕН_PK" on "Н_УЧЕНИКИ" "УЧЕНИКИ_ГРУПП"  (cost=0.29..2664.40 rows=23311 width=12) (actual time=0.013..18.952 rows=23311 loops=1)
                             ->  Memoize  (cost=0.29..0.33 rows=1 width=53) (actual time=0.001..0.001 rows=1 loops=23311)
                                   Cache Key: "УЧЕНИКИ_ГРУПП"."ЧЛВК_ИД"
                                   Cache Mode: logical
                                   Hits: 18355  Misses: 4956  Evictions: 0  Overflows: 0  Memory Usage: 754kB
                                   ->  Index Scan using "ЧЛВК_PK" on "Н_ЛЮДИ"  (cost=0.28..0.32 rows=1 width=53) (actual time=0.002..0.002 rows=1 loops=4956)
                                         Index Cond: ("ИД" = "УЧЕНИКИ_ГРУПП"."ЧЛВК_ИД")
                       ->  Memoize  (cost=0.30..2.23 rows=68 width=10) (actual time=0.001..0.014 rows=69 loops=23311)
                             Cache Key: "УЧЕНИКИ_ГРУПП"."ЧЛВК_ИД"
                             Cache Mode: logical
                             Hits: 18355  Misses: 4956  Evictions: 0  Overflows: 0  Memory Usage: 9867kB
                             ->  Index Scan using "ВЕД_ЧЛВК_FK_IFK" on "Н_ВЕДОМОСТИ"  (cost=0.29..2.22 rows=68 width=10) (actual time=0.002..0.031 rows=45 loops=4956)
                                   Index Cond: ("ЧЛВК_ИД" = "УЧЕНИКИ_ГРУПП"."ЧЛВК_ИД")
   InitPlan 2 (returns $3)
     ->  Aggregate  (cost=22808.52..22808.53 rows=1 width=32) (actual time=2650.480..2650.481 rows=1 loops=1)
           ->  CTE Scan on "УЧЕНИКИ_СО_СР_ОЦЕНКАМИ" "УЧЕНИКИ_3100"  (cost=0.00..22795.85 rows=5066 width=32) (actual time=786.107..2650.355 rows=221 loops=1)
                 Filter: (("ГРУППА")::text = '3100'::text)
                 Rows Removed by Filter: 20645
 Planning Time: 0.832 ms
 Execution Time: 2658.413 ms
 ```

 И теперь добавим ограничение, отфильтровав подзапрос в CTE (`WHERE "Н_УЧЕНИКИ"."ГРУППА" IN('3100', '4100')`):

 ```sql
 WITH "УЧЕНИКИ_СО_СР_ОЦЕНКАМИ" AS (
    SELECT
        "УЧЕНИКИ_ГРУПП"."ИД",
        "ГРУППА",
        "ФАМИЛИЯ",
        "ИМЯ",
        "ОТЧЕСТВО",
        AVG(
            CASE
                WHEN "Н_ВЕДОМОСТИ"."ОЦЕНКА" IN ('2', '3', '4', '5') THEN CAST("Н_ВЕДОМОСТИ"."ОЦЕНКА" AS INTEGER)
                ELSE 2
            END
        ) AS "СР_ОЦЕНКА"
    FROM
        (
            SELECT
                "ИД",
                "ЧЛВК_ИД",
                "ГРУППА"
            FROM
                "Н_УЧЕНИКИ"
            WHERE
                "Н_УЧЕНИКИ"."ГРУППА" IN ('3100', '4100')
        ) as "УЧЕНИКИ_ГРУПП"
        INNER JOIN "Н_ЛЮДИ" ON "Н_ЛЮДИ"."ИД" = "УЧЕНИКИ_ГРУПП"."ЧЛВК_ИД"
        INNER JOIN "Н_ВЕДОМОСТИ" ON "Н_ВЕДОМОСТИ"."ЧЛВК_ИД" = "УЧЕНИКИ_ГРУПП"."ЧЛВК_ИД"
    GROUP BY
        "УЧЕНИКИ_ГРУПП"."ИД",
        "ГРУППА",
        "ФАМИЛИЯ",
        "ИМЯ",
        "ОТЧЕСТВО"
)
SELECT
    *
FROM
    "УЧЕНИКИ_СО_СР_ОЦЕНКАМИ" "УЧЕНИКИ_4100"
WHERE
    "УЧЕНИКИ_4100"."СР_ОЦЕНКА" < (
        SELECT
            MAX("СР_ОЦЕНКА")
        FROM
            "УЧЕНИКИ_СО_СР_ОЦЕНКАМИ" "УЧЕНИКИ_3100"
        WHERE
            "УЧЕНИКИ_3100"."ГРУППА" = '3100'
    )
    AND "УЧЕНИКИ_4100"."ГРУППА" = '4100';
 ```

 Результат:

 ```
                                                                         QUERY PLAN                                                                        
----------------------------------------------------------------------------------------------------------------------------------------------------------
 CTE Scan on "УЧЕНИКИ_СО_СР_ОЦЕНКАМИ" "УЧЕНИКИ_4100"  (cost=4177.30..4634.75 rows=30 width=230) (actual time=57.631..57.738 rows=200 loops=1)
   Filter: (("СР_ОЦЕНКА" < $2) AND (("ГРУППА")::text = '4100'::text))
   Rows Removed by Filter: 221
   CTE УЧЕНИКИ_СО_СР_ОЦЕНКАМИ
     ->  HashAggregate  (cost=3536.64..3765.36 rows=18298 width=89) (actual time=57.073..57.381 rows=421 loops=1)
           Group Key: "Н_УЧЕНИКИ"."ИД", "Н_УЧЕНИКИ"."ГРУППА", "Н_ЛЮДИ"."ФАМИЛИЯ", "Н_ЛЮДИ"."ИМЯ", "Н_ЛЮДИ"."ОТЧЕСТВО"
           Batches: 1  Memory Usage: 921kB
           ->  Nested Loop  (cost=227.28..3079.19 rows=18298 width=63) (actual time=2.100..32.294 rows=39733 loops=1)
                 ->  Hash Join  (cost=226.99..765.70 rows=421 width=65) (actual time=2.089..2.608 rows=421 loops=1)
                       Hash Cond: ("Н_УЧЕНИКИ"."ЧЛВК_ИД" = "Н_ЛЮДИ"."ИД")
                       ->  Bitmap Heap Scan on "Н_УЧЕНИКИ"  (cost=11.83..549.44 rows=421 width=12) (actual time=0.053..0.330 rows=421 loops=1)
                             Recheck Cond: (("ГРУППА")::text = ANY ('{3100,4100}'::text[]))
                             Heap Blocks: exact=107
                             ->  Bitmap Index Scan on "УЧЕН_ГП_FK_I"  (cost=0.00..11.72 rows=421 width=0) (actual time=0.034..0.035 rows=421 loops=1)
                                   Index Cond: (("ГРУППА")::text = ANY ('{3100,4100}'::text[]))
                       ->  Hash  (cost=151.18..151.18 rows=5118 width=53) (actual time=2.014..2.017 rows=5118 loops=1)
                             Buckets: 8192  Batches: 1  Memory Usage: 503kB
                             ->  Seq Scan on "Н_ЛЮДИ"  (cost=0.00..151.18 rows=5118 width=53) (actual time=0.009..0.974 rows=5118 loops=1)
                 ->  Index Scan using "ВЕД_ЧЛВК_FK_IFK" on "Н_ВЕДОМОСТИ"  (cost=0.29..4.82 rows=68 width=10) (actual time=0.002..0.054 rows=94 loops=421)
                       Index Cond: ("ЧЛВК_ИД" = "Н_ЛЮДИ"."ИД")
   InitPlan 2 (returns $2)
     ->  Aggregate  (cost=411.93..411.94 rows=1 width=32) (actual time=0.551..0.551 rows=1 loops=1)
           ->  CTE Scan on "УЧЕНИКИ_СО_СР_ОЦЕНКАМИ" "УЧЕНИКИ_3100"  (cost=0.00..411.70 rows=91 width=32) (actual time=0.001..0.499 rows=221 loops=1)
                 Filter: (("ГРУППА")::text = '3100'::text)
                 Rows Removed by Filter: 200
 Planning Time: 0.886 ms
 Execution Time: 57.982 ms
 ```

Очень нелохое ускорение. В статейке выше написано
 