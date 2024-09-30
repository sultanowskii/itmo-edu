#include <stdio.h>
#include <unistd.h>

int get_ppid_from_procfs(int pid) {
    char proc_stat_filepath[257];
    snprintf(proc_stat_filepath, 256, "/proc/%d/stat", pid);

    int ppid;
    FILE *f = fopen(proc_stat_filepath, "r");
    // https://man7.org/linux/man-pages/man5/proc_pid_stat.5.html
    fscanf(f, "%*d %*s %*c %d", &ppid);
    fclose(f);

    return ppid;
}

int main() {
    int current_pid = getpid();
    int parent_pid = get_ppid_from_procfs(current_pid);
    printf("%d\n", parent_pid);
    return 0;
}
