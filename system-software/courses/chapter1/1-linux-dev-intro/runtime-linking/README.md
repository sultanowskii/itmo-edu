# Runtime-линковка

Для нее используется `libdl`.

Для запуска примера решения задания:

```bash
❯ make build-lib
gcc -c -fPIC -o shared.o shared.c
gcc -shared -fPIC -o libShared.so shared.o
❯ make
gcc -c -fPIC -o solution.o solution.c
gcc -ldl -o solution solution.o
❯ ./solution libShared.so some_function 3
4
```

## `void *dlopen(const char *filename, int flags);`

Загружает динамическую библиотеку. Флаги: RTLD_LAZY, RTLD_NOW. Возвращает не-NULL хэндл, который можно использовать дальше как объект для работы с загруженной библиотекой.

## `int dlclose(void *handle);`

Закрывает хэндл (действует как деструктор shared ptr).

## `void *dlsym(void *restrict handle, const char *restrict symbol);`

Принимает хэндл и название символа. Если найден - возвращает его, дальше можно кастовать (например, к указателю на функцию). Если не найдено - возвращает NULL.

## `char *dlerror(void);`

Возвращает человекочитаемую строку описания случившейся в работе с libdl ошибки, если таковая была.
