SELECT
    "Н_ЛЮДИ"."ИД",
    "Н_ЛЮДИ"."ФАМИЛИЯ",
    "Н_ЛЮДИ"."ИМЯ",
    "Н_ЛЮДИ"."ОТЧЕСТВО"
FROM
    "Н_ЛЮДИ"
    LEFT JOIN "Н_УЧЕНИКИ" ON "Н_ЛЮДИ"."ИД" = "Н_УЧЕНИКИ"."ЧЛВК_ИД"
WHERE
    "Н_УЧЕНИКИ"."ИД" IS NULL;