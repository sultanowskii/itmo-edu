#!/usr/bin/env bash

set -e

POOL2DIR="/etc/pgpool2"

function setup_conf() {
    local filename="$1"
    local target="$2"

    cp "$POOL2DIR/$target" "$POOL2DIR/$target.backup"
    cp "$filename" "$POOL2DIR/$target"
}

setup_conf pgpool.conf pgpool.conf
setup_conf pool_passwd pool_passwd
