# Лабораторная работа #1

Вариант: 367553

## Для выполнения лабораторной работы №1 необходимо:

    На основе предложенной предметной области (текста) составить ее описание. Из полученного описания выделить сущности, их атрибуты и связи.
    Составить инфологическую модель.
    Составить даталогическую модель. При описании типов данных для атрибутов должны использоваться типы из СУБД PostgreSQL.
    Реализовать даталогическую модель в PostgreSQL. При описании и реализации даталогической модели должны учитываться ограничения целостности, которые характерны для полученной предметной области.
    Заполнить созданные таблицы тестовыми данными.

Для создания объектов базы данных у каждого студента есть своя схема. Название схемы соответствует имени пользователя в базе studs (sXXXXXX). Команда для подключения к базе studs:

```bash
psql -h pg -d studs
```

Каждый студент должен использовать свою схему при работе над лабораторной работой №1 (а также в рамках выполнения 2, 3 и 4 этапа курсовой работы).

## Отчёт по лабораторной работе должен содержать:

1. Текст задания.
2. Описание предметной области.
3. Список сущностей и их классификацию (стержневая, ассоциация, характеристика).
4. Инфологическая модель (ER-диаграмма в расширенном виде - с атрибутами, ключами...).
5. Даталогическая модель (должна содержать типы атрибутов, вспомогательные таблицы для отображения связей "многие-ко-многим").
6. Реализация даталогической модели на SQL.
7. Выводы по работе.

Темы для подготовки к защите лабораторной работы:

- Архитектура ANSI-SPARC
- Модель "Сущность-Связь". Классификация сущностей. Виды связей. Ограничения целостности.
- DDL
- DML

## Описание предметной области, по которой должна быть построена доменная модель:

    Здесь риск входит в расчет, как и во всех путешествиях в неизведанное. Правда, полувековая проверка показала, что искусственно вызываемая спячка совершенно безвредна для людей и открывает новые возможности для космических путешествий. Однако до этого полета к усыплению людей на такой продолжительный срок ни разу не прибегали. 