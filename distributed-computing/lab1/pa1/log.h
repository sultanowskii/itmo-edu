#ifndef __LOG_H
#define __LOG_H

typedef enum {
    LOG_FILE_PIPES,
    LOG_FILE_EVENTS,
} LogFile;

static const char *LOG_FILES[] = {
    [LOG_FILE_PIPES] = "pipes.log",
    [LOG_FILE_EVENTS] = "events.log",
};

void write_log(const char *filepath, const char *fmt, ...);

#endif // __LOG_H
