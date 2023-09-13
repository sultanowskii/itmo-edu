#!/bin/bash

echo 'any symbol:'
grep '^.$' -- test.txt
echo ''

echo 'containing digits:'
grep '^.*[[:digit:]]\+.*$' -- test.txt
echo ''

echo 'digits or special symbols:'
grep '^[0-9+-\*/]\+$' -- test.txt
echo ''

echo '3 letter-word:'
grep '^[[:alpha:]]\{3\}$' -- test.txt
echo ''

echo 'empty line:'
grep '^$' -- test.txt
echo ''

echo 'inverted results:'
grep -v 'lalala' -- test.txt
echo ''
