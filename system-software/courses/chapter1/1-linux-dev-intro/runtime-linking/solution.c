#include <dlfcn.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

union library_function {
    void *raw;
    int (*func)(int);
};

char *strdup(const char *s) {
    size_t length = strlen(s);
    char *copy = malloc(length + 1);
    strncpy(copy, s, length);
    return copy;
}

// man.
char *normalize_path(const char *path) {
    if (path[0] == '/') {
        return strdup(path);
    }
    if (path[0] == '.' && path[1] == '/') {
        return strdup(path);
    }

    size_t length = strlen(path);

    char *normalized = malloc(length + 3);
    normalized[0] = '.';
    normalized[1] = '/';
    strncpy(normalized + 2, path, length);

    return normalized;
}

int main(int argc, char *argv[]) {
    if (argc != 4) {
        return 1;
    }

    char *lib_path = normalize_path(argv[1]);
    const char *function_name = argv[2];
    int function_argument = atoi(argv[3]);

    void *lib_handle = dlopen(lib_path, RTLD_LAZY);
    if (lib_handle == NULL) {
        printf("can't load library: %s\n", dlerror());
        return 1;
    }

    union library_function f;
    f.raw = dlsym(lib_handle, function_name);
    if (f.raw == NULL) {
        printf("can't find symbol '%s': %s\n", function_name, dlerror());
        return 1;
    }

    int function_result = f.func(function_argument);
    printf("%d\n", function_result);

    dlclose(lib_handle);
    free(lib_path);

    return 0;
}
