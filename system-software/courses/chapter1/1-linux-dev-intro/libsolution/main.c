#include <stdio.h>
#include <stddef.h>

extern int stringStat(const char *string, size_t multiplier, int *count);

int main() {
    char *s = "hey";
    int cntr = 5;

    int result = stringStat(s, 8, &cntr);

    printf("result: %d, cntr: %d\n", result, cntr);
}
