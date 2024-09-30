#include <stdio.h>

// Ответ - 4, т.к. помимо 3 явных вызовов, так же читается сам ELF.

int main() {
    char name[100];
    printf("What is your name? __");
    gets(name);
    return printf("Hello %s\n",name);
}
