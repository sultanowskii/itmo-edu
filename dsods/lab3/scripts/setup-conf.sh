#!/usr/bin/env bash

function setup_conf() {
    local filename="$1"
    local target="$2"

    cp "$PGDATA/$target" "$PGDATA/$target.backup"
    cp "$filename" "$PGDATA/$target"
}

if [[ "$#" -gt 2 ]]; then
    echo "usage: $0 [local]"
    exit 1
fi

MODE=$1

if [[ "$MODE" == "local" ]]; then
    setup_conf pg_hba.conf pg_hba.conf
    setup_conf postgresql-local.conf postgresql.conf
else
    setup_conf pg_hba.conf pg_hba.conf
    setup_conf postgresql.conf postgresql.conf
fi
