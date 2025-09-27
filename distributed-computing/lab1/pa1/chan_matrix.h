#ifndef __CHAN_MATRIX_H
#define __CHAN_MATRIX_H

#include "chan.h"
#include "ipc.h"

typedef struct {
    Chan *data;
    size_t process_count;
} ChanMatrix;

ChanMatrix *chan_matrix_create(int process_count);
void chan_matrix_destroy(ChanMatrix *chan_matrix);

void chan_matrix_set_at(ChanMatrix *chan_matrix, local_id from, local_id to, Chan chan);
Chan chan_matrix_get_at(ChanMatrix *chan_matrix, local_id from, local_id to);

void chan_matrix_print(ChanMatrix *chan_matrix);

#endif // __CHAN_MATRIX_H
