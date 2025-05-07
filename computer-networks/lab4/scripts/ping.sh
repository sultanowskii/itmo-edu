#!/bin/sh

ping_github() {
    ping -c 5 -s $1 github.com
}

ping_github 100
ping_github 128
ping_github 256
ping_github 512
ping_github 1024
ping_github 2048
ping_github 4096
ping_github 10000

