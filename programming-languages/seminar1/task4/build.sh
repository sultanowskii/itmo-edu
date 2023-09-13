#!/bin/bash

nasm -g task.asm -felf64 -o task.o

ld -o task task.o
