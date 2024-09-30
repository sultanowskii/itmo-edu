# Динамическая библиотека

Компиляция динамической библиотеки:

```bash
gcc -o libHello.so -shared -fPIC hello.c
```

Компиляция программы с динамической библиотекой:

```bash
gcc main.c -L. -lHello -o hello.elf
```

Запуск (переменная нужна, т.к. наша либа лежит в случайной папке, а не, например, `/usr/lib`):

```bash
LD_LIBRARY_PATH=. ./hello.elf
```

Указание `extern "C"` нужно в том случае, когда мы рассматриваем какую-то функциию как сишную в контексте C++.

```c
#pragma once

#ifdef __cplusplus
extern "C" void say_hello(const char *name);
#else
void say_hello(const char *name);
#endif
```

Без этого, при компиляции cpp-реализации, функция будет переименована (mangle) и при попытке использования динамической библиотеки мы столкнемся с тем, что в ней нет символа `say_hello`:

```bash
❯ gcc -o libHello.so -shared -fPIC hello.cpp
❯ nm libHello.so
                 w __cxa_finalize@GLIBC_2.2.5
0000000000004008 d __dso_handle
0000000000003e08 d _DYNAMIC
0000000000001134 t _fini
0000000000003fe8 d _GLOBAL_OFFSET_TABLE_
                 w __gmon_start__
000000000000200c r __GNU_EH_FRAME_HDR
0000000000001000 t _init
                 w _ITM_deregisterTMCloneTable
                 w _ITM_registerTMCloneTable
                 U printf@GLIBC_2.2.5
0000000000004010 d __TMC_END__
0000000000001109 T _Z9say_helloPKc # как видно, без extern "C" произошло переименование.
```

Ну и более яркий пример из задачки в курсе:

| было                      | стало              |
|---------------------------|--------------------|
| `getValue(int)`           | `_Z8getValuei`     |
| `getValue(void (*)(int))` | `_Z8getValuePFviE` |
| `getValue(void*, int)`    | `_Z8getValuePvi`   |
| `getValue()`              | `_Z8getValuev`     |
