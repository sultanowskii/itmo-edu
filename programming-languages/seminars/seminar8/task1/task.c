/* fork-example-1.c */

#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/wait.h>
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


int main() {
    int *shmem = create_shared_memory(10 * sizeof(int));

    printf("Shared memory at: %p\n" , shmem);

    for (int i = 0; i < 10; i++) {
        shmem[i] = i + 1;
    }

    int pid = fork();

    if (pid == 0) {
        size_t index;
        int new_value;
        scanf(" %zu %d", &index, &new_value);
        shmem[index] = new_value;
    } else {
        wait(NULL);
        for (size_t i = 0; i < 10; i++) {
            printf("%d ", shmem[i]);
        }
    }
}