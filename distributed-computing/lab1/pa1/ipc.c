#include "ipc.h"

#include <stdio.h>
#include <unistd.h>

#include "chan.h"
#include "chan_matrix.h"
#include "worker.h"

static inline size_t message_length(const Message *msg) {
    return sizeof(MessageHeader) + msg->s_header.s_payload_len;
}

static inline int _send(ChanMatrix *cm, local_id from, local_id to, const Message *msg) {
    Chan chan = chan_matrix_get_at(cm, from, to);
    int fd = chan.fds[CHAN_IDX_WRITE];

    size_t message_len = message_length(msg);

    ssize_t written = write(fd, msg, message_len);
    if (written == -1) {
        // TODO: return error
        return -1;
    }

    return 0;
}

int send(void *self, local_id dst, const Message *msg) {
    Worker *worker = (Worker *)self;
    return _send(worker->chan_matrix, worker->id, dst, msg);
}

int send_multicast(void *self, const Message *msg) {
    Worker *worker = (Worker *)self;
    ChanMatrix *cm = worker->chan_matrix;
    local_id self_id = worker->id;

    int res = 0;
    for (local_id dst = 0; dst < cm->process_count; dst++) {
        if (dst == self_id) {
            continue;
        }

        res = _send(cm, self_id, dst, msg);
        if (res != 0) {
            perror("uh-oh");
            break;
        }
    }
    return res;
}

static inline int _receive(ChanMatrix *cm, local_id from, local_id to, Message *msg) {
    Chan chan = chan_matrix_get_at(cm, from, to);
    int fd = chan.fds[CHAN_IDX_READ];

    ssize_t read_header_res = read(fd, &msg->s_header, sizeof(MessageHeader));
    if (read_header_res == -1) {
        return -1;
    }

    // TODO: read atomically with the read above?
    ssize_t read_payload_res = read(fd, &msg->s_payload, msg->s_header.s_payload_len);
    if (read_payload_res == -1) {
        return -1;
    }

    return 0;
}

int receive(void *self, local_id from, Message *msg) {
    Worker *worker = (Worker *)self;
    return _receive(worker->chan_matrix, from, worker->id, msg);
}

int receive_any(void *self, Message *msg) {
    Worker *worker = (Worker *)self;
    local_id from = 0;

    // TODO: try all and exit with error or loop until success?
    while (1) {
        from = (from + 1) % worker->chan_matrix->process_count;
        if (from == worker->id) {
            continue;
        }

        int res = _receive(self, from, worker->id, msg);
        if (res == 0) {
            break;
        }
    }

    return 0;
}
