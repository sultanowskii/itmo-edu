#!/bin/bash

# Reset the SVN repo

SVN_PATH="/home/sultanowskii/Projects/itmo/svn"
REPO="lab2-svn"
TMP="tmp"

mkdir -p $TMP

cp $SVN_PATH/$REPO/conf/* $TMP/

rm -rf $SVN_PATH/$REPO/

docker exec -it my-svn-server svnadmin create $REPO

cp $TMP/* $SVN_PATH/$REPO/conf/

rm -rf $TMP

rm -rf "$REPO"
