# Файлы

- Файл – это именованная последовательность байт на жестком диске
- Все файлы связаны иерархической системой имен, начинающейся с символа /
- Файлу сопоставлен набор атрибутов, таких как имя, время доступа к файлу, размер...

## `<fcntl.h>`

### `int open(const char *pathname, int flags, ... /* mode_t mode */ );`

Открывает файл, возращает файловый дескриптор.

### `int creat(const char *pathname, mode_t mode);`

Вызов  `creat()`  эквивалентен вызову open()  с флагами `O_CREAT|O_WRONLY|O_TRUNC`.

## `<unistd.h>`

### `ssize_t read(int fd, void buf[.count], size_t count);`

Пытается прочитать из файлового дескриптора count байтов в buf.

При ошибке возвращает -1.

В случае успеха возвращает кол-во прочитанных байт.

### `ssize_t write(int fd, const void buf[.count], size_t count);`

Пытается записать в файловый дескриптор count байтов из buf.

При ошибке возвращает -1.

В случае успеха возвращает кол-во записанных байт.

### `off_t lseek(int fd, off_t offset, int whence);`

Перемещает "курсор" на offset байтов относительно whence.

- `SEEK_SET` - относительно начала файла
- `SEEK_CUR` - относительно текущего положения
- `SEEK_END` - относительно конца файла (filesize + offset)

### `int close(int fd);`

Закрывает файловый дескриптор. 0 при успехе, -1 при ошибке.

## Примеры флагов открытия файла

`O_CREAT` - Файл будет создан при открытии, если не существует, при наличии соответствующих прав
`O_APPEND` - Режим добавления. Перед каждым вызовом write указатель на текущую позицию будет перемещен в конец файла
`O_RDONLY` - Файл в режиме только для чтения
`O_TRUNC` - Если файл существует, то при открытии его содержимое будет удалено
`O_RDWR` - Файл открывается для чтения и записи