#!/bin/sh

find -name "*.java" > sources.txt
javac -d build/classes/ -cp libs/Pokemon.jar @sources.txt
jar -cvfm lab2.jar MANIFEST.MF -C build/classes .
rm sources.txt
