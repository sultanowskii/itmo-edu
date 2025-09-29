#ifndef __WORKER_H
#define __WORKER_H

#include <sys/types.h>
#include <unistd.h>

#include "chan_matrix.h"
#include "ipc.h"

typedef struct {
    ChanMatrix *chan_matrix;
    local_id id;

    pid_t pid;
    pid_t parent_pid;

    int process_count;
    int subprocess_count;
} Worker;

#endif // __WORKER_H
