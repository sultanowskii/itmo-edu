# Лабораторная работа 4

## Подготовка

```bash
git submodule add git@github.com:antlr/antlr4.git antlr4
```

Не забываем скачать CLI (`antlr.jar`): [https://www.antlr.org/download.html](https://www.antlr.org/download.html)

Далее идем в [antlr4/runtime/Cpp/README.md](antlr4/runtime/Cpp/README.md) и делаем все по гайду в зависимости от ОС:

```bash
$ cd antlr4/runtime/Cpp
$ mkdir build && mkdir run && cd build
$ cmake ..
$ make
$
```
