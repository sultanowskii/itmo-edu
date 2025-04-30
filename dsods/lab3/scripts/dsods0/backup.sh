#!/bin/bash

set -e

DATE=$(date "+%Y-%m-%d-%H-%M-%S")

echo "[$DATE] Starting backup..."

pg_dumpall -p 9253 -h /tmp -U a7ult -w | gzip -c | ssh -i /home/a7ult/.ssh/id_rsa "$DSODS1_USER"@"$DSODS1_HOST" "bash ~/store_backup_from_stdin.sh $DATE"

echo "[$DATE] Backup created successfully"
