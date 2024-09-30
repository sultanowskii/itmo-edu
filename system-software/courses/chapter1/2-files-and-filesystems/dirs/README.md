# Папки

```c
#include <sys/types.h>
#include <dirent.h>
```

## `DIR *opendir(const char *name);`, `DIR *fdopendir(int fd);`

Открывает директорию по имени или по файловому дескриптору. Возвращает указатель на directory stream. При ошибке - NULL.

## `struct dirent *readdir(DIR *dirp);`

Возвращает структуру dirent - некоторую сущность в директории. Работает как проход по односвязному списку, в цикле указывается проверка на NULL. Структурки dirent хранят тип (d_type: DT_DIR, DT_REG, DT_UNKNOWN...), номер inode, имя файла и т.д.

## `int closedir(DIR *dirp);`

Закрывает directory stream.
