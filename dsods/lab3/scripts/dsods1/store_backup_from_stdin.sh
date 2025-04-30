#!/bin/bash

set -e

if [[ $# -ne 1 ]]; then
    echo "usage: $0 DATE"
    exit 1
fi

DATE=$1

BACKUP_DIR="$HOME/backups"
BACKUP_FILENAME="$DATE.bkp.gz"
BACKUP_FILEPATH="$BACKUP_DIR/$BACKUP_FILENAME"

mkdir -p $BACKUP_DIR

cat >$BACKUP_FILEPATH
