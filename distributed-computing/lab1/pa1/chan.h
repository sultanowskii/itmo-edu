#ifndef __CHAN_H
#define __CHAN_H

#include "ipc.h"

#define CHAN_IDX_READ 0
#define CHAN_IDX_WRITE 1

typedef struct {
    int fds[2];
} Chan;

#endif // __CHAN_H
