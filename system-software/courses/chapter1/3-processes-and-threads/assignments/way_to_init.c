#include <stdio.h>
// #include <unistd.h>

void traverse_process_hierarchy(int pid) {
    if (pid == 0) {
        return;
    }

    printf("%d\n", pid);

    char proc_stat_filepath[257];
    snprintf(proc_stat_filepath, 256, "/proc/%d/stat", pid);
    FILE *f = fopen(proc_stat_filepath, "r");
    if (f == NULL) {
        perror(proc_stat_filepath);
        return;
    }

    int ppid;
    // https://man7.org/linux/man-pages/man5/proc_pid_stat.5.html
    fscanf(f, "%*d %*s %*c %d", &ppid);

    fclose(f);

    traverse_process_hierarchy(ppid);    
}

int main(int argc, const char *argv[]) {
    if (argc != 2) {
        return 1;
    }

    int pid;
    sscanf(argv[1], "%d", &pid);

    traverse_process_hierarchy(pid);
    return 0;
}
