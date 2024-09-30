#include <ctype.h>
#include <dirent.h>
#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>

bool isnumber(const char *s) {
    const char *ptr = s;
    while (*ptr != '\0') {
        if (!isdigit(*ptr)) {
            return false;
        }
        ptr++;
    }
    return true;
}

void get_process_name(int pid, char *name) {
    char path[512];
    snprintf(path, 511, "/proc/%d/comm", pid);
    FILE *f = fopen(path, "r");

    // https://man7.org/linux/man-pages/man5/proc_pid_stat.5.html
    fscanf(f, "%256s", name);
    fclose(f);

    size_t length = strlen(name);
    if (name[length - 1] == '\n') {
        name[length - 1] = '\0';
    }
}

int main() {
    const char *target_name = "genenv";
    char name[256];

    int cntr = 0;

    DIR *procdir = opendir("/proc");

    struct dirent *dir_entry = readdir(procdir);

    while (dir_entry != NULL) {
        char *entry_name = dir_entry->d_name;

        if (isnumber(entry_name)) {
            int pid;
            sscanf(entry_name, "%d", &pid);
            get_process_name(pid, name);

            if (strcmp(name, target_name) == 0) {
                // printf("%d\n", pid);
                cntr++;
            }
        }

        dir_entry = readdir(procdir);
    }

    printf("%d\n", cntr);

    return 0;
}