#!/usr/bin/env bash

set -e

function setup_conf() {
    local filename="$1"
    local target="$2"

    cp "$PGDATA/$target" "$PGDATA/$target.backup"
    cp "$filename" "$PGDATA/$target"
}


setup_conf pg_hba.conf pg_hba.conf
setup_conf postgresql.conf postgresql.conf

