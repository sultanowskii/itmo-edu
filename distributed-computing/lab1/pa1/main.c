#define _GNU_SOURCE

#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>

#include "args.h"
#include "chan_matrix.h"
#include "ipc.h"

int main(int argc, char **argv) {
    struct Args args = {0};

    argp_parse(&argp, argc, argv, 0, NULL, &args);

    int subprocess_count = args.process_count;
    int process_count = subprocess_count + 1;

    ChanMatrix *cm = chan_matrix_create(process_count);

    for (local_id from = 0; from < process_count; from++) {
        for (local_id to = 0; to < process_count; to++) {
            if (from == to) {
                continue;
            }

            Chan chan = { .fds = {-1, -1}, };
            pipe2(chan.fds, 0);

            chan_matrix_set_at(cm, from, to, chan);
        }
    }
    puts("lets go");
    chan_matrix_print(cm);

    puts("hey");

    char buf[65];

    int pipefds[2];
    pipe2(pipefds, 0);

    pid_t pid = fork();
    if (pid == 0) {
        puts("child: hi!");
        close(pipefds[1]);

        while (1) {
            puts("child: now waiting for parent");
            int bytes_read = read(pipefds[0], buf, 64);
            if (bytes_read == -1) {
                perror("child: read() error");
                continue;
            } else if (bytes_read == 0) {
                puts("child: parent closed the channel, bye!");
                close(pipefds[0]);
                break;
            }
            buf[bytes_read] = '\0';
            printf("child: parent said '%s'\n", buf);
        }
    } else {
        puts("parent: hi!");
        close(pipefds[0]);

        while (1) {
            puts("parent: say something! (say 'bye' if you want to finish)");
            int res = scanf("%64s", buf);
            if (res == EOF || ((res == 1) && (strcmp(buf, "bye") == 0))) {
                puts("parent: ok, bye!");
                close(pipefds[1]);
                break;
            }
            puts("parent: ok, now sending it to the child...");
            write(pipefds[1], buf, strlen(buf));
            puts("parent: sent!");
        }
    }

    chan_matrix_destroy(cm);

    return 0;
}
