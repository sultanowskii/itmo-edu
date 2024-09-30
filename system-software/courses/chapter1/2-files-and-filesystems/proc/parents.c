#include <stdio.h>
#include <unistd.h>

void traverse_process_hierarchy(int pid) {
    printf("%d", pid);
    char proc_stat_filepath[257];
    if (pid == 0) {
        return;
    }
    printf(" <- ");

    snprintf(proc_stat_filepath, 256, "/proc/%d/stat", pid);

    int ppid;
    FILE *f = fopen(proc_stat_filepath, "r");

    // https://man7.org/linux/man-pages/man5/proc_pid_stat.5.html
    fscanf(f, "%*d %*s %*c %d", &ppid);

    fclose(f);

    traverse_process_hierarchy(ppid);    
}

int main() {
    int current_pid = getpid();

    traverse_process_hierarchy(current_pid);
    puts("");
    return 0;
}
