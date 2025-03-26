#!/usr/bin/env bash

# cluster directory
mkdir -p "$PGDATA"

# separate custom tablespace for indexes
mkdir -p "$HOME/amh13"

# yes, there is a LANG variable
# but I don't want to use it because it messes with my output
# 
# https://www.postgresql.org/docs/current/locale.html#LOCALE-OVERVIEW

# helios: ru_RU.KOI8-R
# local:  ru_RU.koi8r
initdb --locale="ru_RU.KOI8-R"
