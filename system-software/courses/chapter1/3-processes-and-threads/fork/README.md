# Fork

## `pid_t fork(void);` (`<unistd.h>`)

Создает копию процесса.

У родителя возвращается pid ребенка. У ребенка - 0. При ошибке -1.

Копируется все, кроме:

- pid (очевидно)
- ppid (родитель - изначальный процесс)
- mlocks (memory locks)
- сигналов для обработки
- и т.д.

## `pid_t wait(int *_Nullable wstatus);`, `pid_t waitpid(pid_t pid, int *_Nullable wstatus, int options);` (`<sys/wait.h>`)

Ожидает изменение статуса дочернего процесса. Блокирующий вызов.

- `wait` ожидает любого из дочерних
- `waitpid` позволяет указать конкретный pid.

A state change is considered to be:

- the child terminated
- the child was stopped by a signal
- the child was resumed by a signal.

Значения аргумента pid:

- `< -1`: Wait for any child process whose process group ID is equal to the absolute value of pid.
- `-1`: Wait for any child process.
- `0`: Wait for any child process whose process group ID is equal to that of the calling process.
- `> 0`: Wait for the child whose process ID is equal to the value of pid.

"Reaps" the zombie process.

Возвращает pid дочернего процесса, у которого изменился статус.

## `int execl(const char *pathname, const char *arg, .../*, (char *) NULL */);`

И в целом семейство `exec` - в основном они отличаются сигнатурой, способом передачи аргументов и, собственно, их подробностью

Суффиксы в названиях:

- `l` - list (varargs)
- `v` - vector (массив аргументов)
- `e` - env (возможность докинуть массив переменных окружения)
- `p` - PATH (использовать PATH, если в начале команды нет `/`)

Заменяют текущий процесс новым процессом.
