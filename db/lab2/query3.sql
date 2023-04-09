SELECT
    EXISTS(
        SELECT
            1
        FROM
            "Н_УЧЕНИКИ"
            INNER JOIN "Н_ЛЮДИ" ON "Н_УЧЕНИКИ"."ЧЛВК_ИД" = "Н_ЛЮДИ"."ИД"
        WHERE
            "Н_УЧЕНИКИ"."ГРУППА" = '3102'
            AND "Н_ЛЮДИ"."ОТЧЕСТВО" IS NULL
    );