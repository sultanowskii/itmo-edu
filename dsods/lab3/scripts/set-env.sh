#!/usr/bin/env bash

# run: source set-env.sh

if [[ "$#" -gt 2 ]]; then
    echo "usage: source $0 [local]"
    exit 1
fi

MODE=$1

set -a # automatically export all variables

if [[ "$MODE" == "local" ]]; then
    source .env.local
else
    source .env
fi

set +a
