#include "log.h"

#include <stdarg.h>
#include <stdio.h>

void write_log(const char *filename, const char *fmt, ...) {
    va_list args;

    va_start(args, fmt);
    FILE *file = fopen(filename, "a");
    vfprintf(file, fmt, args);
    fclose(file);
    va_end(args);

    va_start(args, fmt);
    vprintf(fmt, args);
    va_end(args);
}
