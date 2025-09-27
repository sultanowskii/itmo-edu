#include "chan_matrix.h"

#include <malloc.h>

#include "chan.h"

ChanMatrix *chan_matrix_create(int process_count) {
    ChanMatrix *cm = malloc(sizeof(ChanMatrix));
    cm->data = malloc(process_count * process_count * sizeof(Chan));
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
