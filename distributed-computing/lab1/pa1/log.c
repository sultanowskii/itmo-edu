#include "log.h"

#include <stdarg.h>
#include <stdio.h>
#include <unistd.h>

void logger_open(Logger *logger) {
    logger->events = fopen(LOG_FILES[LOG_FILE_EVENTS], "a");
    logger->pipes = fopen(LOG_FILES[LOG_FILE_EVENTS], "a");
}

void logger_close(Logger *logger) {
    fclose(logger->events);
    fclose(logger->pipes);
}

static inline void _logger_log_to_file(FILE *file, const char *fmt, va_list args) {
    vfprintf(file, fmt, args);
    fflush(file);
}

void logger_log_event(const Logger *logger, const char *fmt, ...) {
    va_list args;

    va_start(args, fmt);
    _logger_log_to_file(logger->events, fmt, args);
    va_end(args);

    va_start(args, fmt);
    _logger_log_to_file(stdout, fmt, args);
    va_end(args);
}

void logger_log_pipe(const Logger *logger, const char *fmt, ...) {
    va_list args;

    va_start(args, fmt);
    _logger_log_to_file(logger->pipes, fmt, args);
    va_end(args);

    va_start(args, fmt);
    _logger_log_to_file(stdout, fmt, args);
    va_end(args);
}
