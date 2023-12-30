/* fork-example-1.c */

#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <string.h>
#include <unistd.h>

#define MSG_READY 'R'
#define MSG_END 'E'
#define MSG_DEFAULT ' '

void* create_shared_memory(size_t size) {
    return mmap(
        NULL,
        size,
        PROT_READ | PROT_WRITE,
        MAP_SHARED | MAP_ANONYMOUS,
        -1, 0);
}

int main() {
    int *shmem = create_shared_memory(10 * sizeof(int));

    printf("Shared memory at: %p\n" , shmem);

    for (int i = 0; i < 10; i++) {
        shmem[i] = i + 1;
    }

    int pipe_fd[2];
    pipe(pipe_fd);

    int pid = fork();

    if (pid == 0) {
        char msg = MSG_DEFAULT;
        int index = 0;
        int new_value;

        close(pipe_fd[0]);

        while (index >= 0) {
            scanf(" %d %d", &index, &new_value);

            if (index >= 0) {
                shmem[index] = new_value;
                msg = MSG_READY;
            } else {
                msg = MSG_END;
            }

            write(pipe_fd[1], &msg, 1);
            printf("sent %c\n", msg);
        }

        close(pipe_fd[1]);
    } else {
        char msg = MSG_DEFAULT;

        close(pipe_fd[1]);

        while (msg != MSG_END) {
            read(pipe_fd[0], &msg, 1);
            printf("got %c\n", msg);

            if (msg == MSG_READY) {
                for (size_t i = 0; i < 10; i++) {
                    printf("%d ", shmem[i]);
                }
                puts("");
            }
        }

        close(pipe_fd[0]);
    }
}