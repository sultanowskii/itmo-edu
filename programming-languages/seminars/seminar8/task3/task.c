/* fork-example-1.c */

#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <semaphore.h>
#include <string.h>
#include <unistd.h>

void* create_shared_memory(size_t size) {
    return mmap(
        NULL,
        size,
        PROT_READ | PROT_WRITE,
        MAP_SHARED | MAP_ANONYMOUS,
        -1, 0);
}

sem_t sem_updated, sem_exit;

int main() {
    int *shmem = create_shared_memory(10 * sizeof(int));

    if (sem_init(&sem_updated, 1, 0) != 0) {
        perror("oh no");
    }
    if (sem_init(&sem_exit, 1, 0) != 0) {
        perror("no");
    }

    printf("Shared memory at: %p\n" , shmem);

    for (int i = 0; i < 10; i++) {
        shmem[i] = i + 1;
    }

    int pid = fork();

    if (pid == 0) {
        int index = 0;
        int new_value;

        while (index >= 0) {
            scanf(" %d %d", &index, &new_value);

            if (index >= 0) {
                shmem[index] = new_value;
                sem_post(&sem_updated);
            } else {
                sem_post(&sem_exit);
            }
        }

    } else {
        while (1) {
            if (sem_trywait(&sem_updated) == 0) {
                for (size_t i = 0; i < 10; i++) {
                    printf("%d ", shmem[i]);
                }
                puts("");
            }
            if (sem_trywait(&sem_exit) == 0) {
                break;
            }
        }

    }
}