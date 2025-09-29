#ifndef __LOG_H
#define __LOG_H

#include <stdio.h>

typedef enum {
    LOG_FILE_PIPES,
    LOG_FILE_EVENTS,
} LogFile;

static const char *LOG_FILES[] = {
    [LOG_FILE_PIPES] = "pipes.log",
    [LOG_FILE_EVENTS] = "events.log",
};

typedef struct {
    FILE *pipes;
    FILE *events;
} Logger;

void logger_open(Logger *logger);
void logger_close(Logger *logger);
void logger_log_event(const Logger *logger, const char *fmt, ...);
void logger_log_pipe(const Logger *logger, const char *fmt, ...);

#endif // __LOG_H
