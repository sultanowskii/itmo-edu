
#include "log.h"
#define _GNU_SOURCE

#include <fcntl.h>
#include <unistd.h>

#include "args.h"
#include "chan.h"
#include "chan_matrix.h"
#include "ipc.h"
#include "msg.h"
#include "pa1.h"
#include "worker.h"

void close_unrelated_chans(ChanMatrix *cm, local_id self) {
    for (local_id from = 0; from < cm->process_count; from++) {
        for (local_id to = 0; to < cm->process_count; to++) {
            if (from == to) {
                continue;
            }

            Chan chan = chan_matrix_get_at(cm, from, to);
            if (from != self) {
                close(chan.fds[CHAN_IDX_WRITE]);
                chan.fds[CHAN_IDX_WRITE] = CHAN_FD_CLOSED;
            }
            if (to != self) {
                close(chan.fds[CHAN_IDX_READ]);
                chan.fds[CHAN_IDX_READ] = CHAN_FD_CLOSED;
            }
            chan_matrix_set_at(cm, from, to, chan);
        }
    }
}

void handle_child(Worker self, int subprocess_count) {
    write_log(LOG_FILES[LOG_FILE_EVENTS], log_started_fmt, self.id, self.pid, self.parent_pid);

    chan_matrix_print(self.chan_matrix);

    Message start_msg = build_msg(STARTED, log_started_fmt, self.id, self.pid, self.parent_pid);
    int start_multicast_res = send_multicast(&self, &start_msg);
    if (start_multicast_res != 0) {
        // TODO: handle
    }

    // TODO: extract into a function
    // TODO: check type, check magic&message (?)
    for (local_id id = 1; id <= subprocess_count; id++) {
        if (id == self.id) {
            continue;
        }
        Message start_message_from;
        int res = receive(&self, id, &start_message_from);
        if (res != 0) {
            // TODO: handle
        }
    }
    write_log(LOG_FILES[LOG_FILE_EVENTS], log_received_all_started_fmt, self.id);

    /* some job here (empty for lab1) */

    Message done_msg = build_msg(STARTED, log_done_fmt, self.id);
    int done_multicast_res = send_multicast(&self, &done_msg);
    if (done_multicast_res != 0) {
        // TODO: handle
    }

    write_log(LOG_FILES[LOG_FILE_EVENTS], log_done_fmt, self.id);

    // TODO: extract into a function
    // TODO: check type, check magic&message (?)
    for (local_id id = 1; id <= subprocess_count; id++) {
        if (id == self.id) {
            continue;
        }
        Message done_message_from;
        int res = receive(&self, id, &done_message_from);
        if (res != 0) {
            // TODO: handle
        }
    }

    write_log(LOG_FILES[LOG_FILE_EVENTS], log_received_all_done_fmt, self.id);
}

void handle_parent(Worker self, int subprocess_count) {
    chan_matrix_print(self.chan_matrix);

    // TODO: extract into a function
    // TODO: check type, check magic&message (?)
    for (local_id id = 1; id <= subprocess_count; id++) {
        Message start_message_from;
        int res = receive(&self, id, &start_message_from);
        if (res != 0) {
            // TODO: handle
        }
    }
    write_log(LOG_FILES[LOG_FILE_EVENTS], log_received_all_started_fmt, self.id);

    // TODO: extract into a function
    // TODO: check type, check magic&message (?)
    for (local_id id = 1; id <= subprocess_count; id++) {
        if (id == self.id) {
            continue;
        }
        Message done_message_from;
        int res = receive(&self, id, &done_message_from);
        if (res != 0) {
            // TODO: handle
        }
    }
    write_log(LOG_FILES[LOG_FILE_EVENTS], log_received_all_done_fmt, self.id);
}

ChanMatrix *create_and_open_chan_matrix(int process_count) {
    ChanMatrix *cm = chan_matrix_create(process_count);
    for (local_id from = 0; from < process_count; from++) {
        for (local_id to = 0; to < process_count; to++) {
            if (from == to) {
                continue;
            }

            Chan chan = {.fds = {CHAN_FD_CLOSED, CHAN_FD_CLOSED}};
            pipe2(chan.fds, 0);

            chan_matrix_set_at(cm, from, to, chan);
        }
    }
    return cm;
}

int main(int argc, char **argv) {
    struct Args args = {0};
    argp_parse(&argp, argc, argv, 0, NULL, &args);

    int subprocess_count = args.process_count;
    int process_count = subprocess_count + 1;

    ChanMatrix *cm = create_and_open_chan_matrix(process_count);

    pid_t parent_pid = getpid();

    int is_parent = 1;
    for (local_id id = 1; id <= subprocess_count; id++) {
        pid_t pid = fork();
        if (pid == 0) {
            is_parent = 0;
            close_unrelated_chans(cm, id);
            Worker worker = (Worker){.chan_matrix = cm, .id = id, .parent_pid = parent_pid, .pid = getpid()};
            handle_child(worker, subprocess_count);
            break;
        }
    }
    if (is_parent) {
        close_unrelated_chans(cm, PARENT_ID);
        Worker worker = (Worker){.chan_matrix = cm, .id = PARENT_ID, .pid = parent_pid};
        handle_parent(worker, subprocess_count);
    }

    chan_matrix_destroy(cm);

    return 0;
}
