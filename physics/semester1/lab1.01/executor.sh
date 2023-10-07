#!/bin/bash

if [[ -f result.txt ]]
then
    echo 'results.txt already exists.'
    exit 1
fi

echo "Compiling..."
gcc task.c -o task.elf
echo "Compiled"

N=50

for ((i=1; i<=N; i++))
do
    echo "Execution #$i"
    ./timer.sh 2>>result.txt
    echo "Execution #$i finished"
done

echo "Removing tmp binary..."
rm task.elf
echo "Removed tmp binary"

sed -i 's/\./,/g' result.txt

echo "See result.txt for results"
