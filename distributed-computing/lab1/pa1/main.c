#include <errno.h>
#include <fcntl.h>
#include <malloc.h>
#include <sys/types.h>
#include <unistd.h>

#include "args.h"
#include "chan.h"
#include "chan_matrix.h"
#include "ipc.h"
#include "log.h"
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

/** Waits for all messages of specific type from other subprocesses.
 *
 * @param self  Worker (self)
 * @param type  MessageType to expect
 *
 * @return 0 on success, -1 on receive error (see errno), -2 on type mismatch
 */
int wait_for_message_from_all_subprocesses(Worker *self, MessageType type) {
    const int subprocess_count = self->subprocess_count;
    int subprocess_to_wait_count = subprocess_count;
    if (self->id != PARENT_ID) {
        subprocess_to_wait_count--;
    }

    int res = 0;

    uint8_t *done = calloc(subprocess_count, sizeof(uint8_t));
    int done_count = 0;

    local_id id = 0;
    while (done_count < subprocess_to_wait_count) {
        id = (id % subprocess_count) + 1;
        if (id == self->id || done[id]) {
            continue;
        }

        Message message;
        int receive_res = receive(self, id, &message);
        if (receive_res == -1) {
            if (errno == EAGAIN) {
                continue;
            }
            res = -1;
            break;
        }
        if (message.s_header.s_type != type) {
            res = -2;
            break;
        }

        done[id] = 1;
        done_count++;
    }

    free(done);

    return res;
}

void handle_child(Worker self) {
    logger_log_event(&self.logger,  log_started_fmt, self.id, self.pid, self.parent_pid);
    // chan_matrix_print(self.chan_matrix);

    Message started_msg = build_msg(STARTED, log_started_fmt, self.id, self.pid, self.parent_pid);
    int start_multicast_res = send_multicast(&self, &started_msg);
    if (start_multicast_res != 0) {
        // TODO: handle error
    }

    int started_wait_res = wait_for_message_from_all_subprocesses(&self, STARTED);
    if (started_wait_res != 0) {
        // TODO: handle error
    }
    logger_log_event(&self.logger, log_received_all_started_fmt, self.id);

    /* some job here (empty for lab1) */

    logger_log_event(&self.logger, log_done_fmt, self.id);

    Message done_msg = build_msg(DONE, log_done_fmt, self.id);
    int done_multicast_res = send_multicast(&self, &done_msg);
    if (done_multicast_res != 0) {
        // TODO: handle error
    }

    int done_wait_res = wait_for_message_from_all_subprocesses(&self, DONE);
    if (done_wait_res != 0) {
        // TODO: handle error
    }
    logger_log_event(&self.logger,log_received_all_done_fmt, self.id);
}

void handle_parent(Worker self) {
    // chan_matrix_print(self.chan_matrix);

    int started_wait_res = wait_for_message_from_all_subprocesses(&self, STARTED);
    if (started_wait_res != 0) {
        // TODO: handle error
        puts("oh no...");
    }
    logger_log_event(&self.logger,log_received_all_started_fmt, self.id);

    int done_wait_res = wait_for_message_from_all_subprocesses(&self, DONE);
    if (done_wait_res != 0) {
        // TODO: handle error
        puts("oh no 2...");
    }
    logger_log_event(&self.logger,log_received_all_done_fmt, self.id);
}

ChanMatrix *create_and_open_chan_matrix(int process_count) {
    ChanMatrix *cm = chan_matrix_create(process_count);
    for (local_id from = 0; from < process_count; from++) {
        for (local_id to = 0; to < process_count; to++) {
            if (from == to) {
                continue;
            }

            Chan chan = {.fds = {CHAN_FD_CLOSED, CHAN_FD_CLOSED}};
            pipe(chan.fds);

            int read_fd_flags = fcntl(chan.fds[CHAN_IDX_READ], F_GETFL);
            read_fd_flags |= O_NONBLOCK;
            fcntl(chan.fds[CHAN_IDX_READ], F_SETFL, read_fd_flags);

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
    Logger logger;
    logger_open(&logger);

    pid_t parent_pid = getpid();

    int is_parent = 1;
    for (local_id id = 1; id <= subprocess_count; id++) {
        pid_t pid = fork();
        if (pid == 0) {
            is_parent = 0;
            close_unrelated_chans(cm, id);
            Worker worker = (Worker){
                .chan_matrix = cm,
                .logger = logger,
                .id = id,
                .parent_pid = parent_pid,
                .pid = getpid(),
                .process_count = process_count,
                .subprocess_count = subprocess_count,
            };
            handle_child(worker);
            break;
        }
    }
    if (is_parent) {
        close_unrelated_chans(cm, PARENT_ID);
        Worker worker = (Worker){
            .chan_matrix = cm,
            .logger = logger,
            .id = PARENT_ID,
            .pid = parent_pid,
            .process_count = process_count,
            .subprocess_count = subprocess_count,
        };
        handle_parent(worker);
    }

    logger_close(&logger);
    chan_matrix_destroy(cm);

    return 0;
}
