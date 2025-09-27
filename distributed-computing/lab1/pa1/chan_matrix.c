#include "chan_matrix.h"

#include <malloc.h>
#include <stdio.h>

#include "chan.h"
#include "ipc.h"

ChanMatrix *chan_matrix_create(int process_count) {
    ChanMatrix *cm = malloc(sizeof(ChanMatrix));
    cm->data = calloc(process_count * process_count, sizeof(Chan));
    cm->process_count = process_count;
    return cm;
}

void chan_matrix_destroy(ChanMatrix *chan_matrix) {
    free(chan_matrix->data);
    free(chan_matrix);
}

static inline size_t get_index(ChanMatrix *cm, local_id from, local_id to) {
    return from * cm->process_count + to;
}

void chan_matrix_set_at(ChanMatrix *chan_matrix, local_id from, local_id to, Chan chan) {
    chan_matrix->data[get_index(chan_matrix, from, to)] = chan;
}

Chan chan_matrix_get_at(ChanMatrix *chan_matrix, local_id from, local_id to) {
    return chan_matrix->data[get_index(chan_matrix, from, to)];
}

void chan_matrix_print(ChanMatrix *chan_matrix) {
    for (local_id from = 0; from < chan_matrix->process_count; from++) {
        for (local_id to = 0; to < chan_matrix->process_count; to++) {
            if (from == to) {
                printf("{ itself } ");
                continue;
            }
            Chan chan = chan_matrix_get_at(chan_matrix, from, to); 
            printf("{ r=%d w=%d } ", chan.fds[CHAN_IDX_READ], chan.fds[CHAN_IDX_WRITE]);
        }
        printf("\n");
    }
}
