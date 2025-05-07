#!/usr/bin/env bash

set -e

# cluster directory
mkdir -p "$PGDATA"

# yes, there is a LANG variable
# but I don't want to use it because it messes with my output
# 
# https://www.postgresql.org/docs/current/locale.html#LOCALE-OVERVIEW

initdb -U postgres

./setup-conf.sh

pg_ctl start

psql -U postgres -d postgres -f init.sql
