# Отладка на примере

У меня на локалке все равно не сработало - core dump сохраняется в другое место + компрессируется, подробнее можно почитать [здесь](https://unix.stackexchange.com/questions/688000/core-dump-not-written-on-segmentation-fault). Забил :)

Компиляция:

```bash
gcc -ggdb fail.c -o fail.elf
```

Включение генерации core dump:

```bash
ulimit -c unlimited
```

Запуск:

```bash
./fail.elf
```

Отладка с core dump:

```bash
gdb ./fail.elf -c core
```

## GDB cheatsheet (случайный из гугла)

[ссылка](https://github.com/reveng007/GDB-Cheat-Sheet)
