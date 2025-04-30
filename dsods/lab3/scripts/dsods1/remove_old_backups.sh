#!/bin/bash

BACKUP_DIR=/home/a7ult/backups

if [[ ! -d "$BACKUP_DIR" ]]; then
    echo "Directory $DIRECTORY doesn't exist."
    exit 1
fi

find "$BACKUP_DIR" -type f -mtime +28 -exec rm {} \;

echo "Removed backups older than 4 weeks in $DIRECTORY."
