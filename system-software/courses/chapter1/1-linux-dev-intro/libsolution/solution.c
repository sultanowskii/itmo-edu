#include <stddef.h>
#include <string.h>

int stringStat(const char *string, size_t multiplier, int *count) {
    (*count)++;

    int length = strlen(string);
    return length * multiplier;
}
