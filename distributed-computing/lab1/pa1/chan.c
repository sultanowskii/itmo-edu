#include "chan.h"

#include <stdio.h>

static inline void print_chan_fd(int chan_fd) {
    if (chan_fd == CHAN_FD_CLOSED) {
        printf("no");
    } else {
        printf("%d", chan_fd);
    }
}

void chan_print(Chan chan) {
    printf("{ r=");
    print_chan_fd(chan.fds[CHAN_IDX_READ]);
    printf(" w=");
    print_chan_fd(chan.fds[CHAN_IDX_WRITE]);
    printf(" }");
}
