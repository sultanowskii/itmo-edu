#include <stdlib.h>
#include <stdio.h>
#include <string.h>

extern void print_string(const char *s);
extern void print_file(const char *filename);

int main() {
    char filename[260] = {0};

    print_string("Enter file name: ");

    fgets(filename, 256, stdin);

    size_t read = strlen(filename);
    if (filename[read - 1] == '\n') {
        filename[read - 1] = '\0';
    }

    print_file(filename);

    return 0;
}
