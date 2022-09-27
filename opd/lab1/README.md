# Лабораторная работа №1

**Вариант**: 1316

## Ход выполнения

### 1. Иерархия

Создал иерархию директорий и файлов, заполнил файлы содержимым согласно пунктам 1-2 задания. Все используемые для выполенения этого шага команды записал в скрипт ([setup_lab0.sh](setup_lab0.sh)). Команды с комментариями к ним:

```bash
# --== Пункт 1 ==--

# Создание иерархии директорий и файлов

mkdir lab0
cd lab0

mkdir magmortar6 nuzleaf5 octillery5
touch golurk3 ninjask7 poliwag3

mkdir magmortar6/forretress magmortar6/cubone
touch magmortar6/rattata magmortar6/jumpluff

mkdir nuzleaf5/wurmple nuzleaf5/ariados
touch nuzleaf5/ludicolo

mkdir octillery5/primeape octillery5/clamperl
touch octillery5/spearow

# Заполнение файлов содержимым

echo 'Живет  Desert Mountain' > golurk3

echo 'Возможности Overland=8' > magmortar6/rattata
echo 'Surface=6 Jump=2 Power=1 Intelligence=3 Stealth=0' >> magmortar6/rattata

echo 'weigth=6.6' > magmortar6/jumpluff
echo 'height=31.0 atk=6 def=7' >> magmortar6/jumpluff

echo 'Тип покемона  BUG' > ninjask7
echo 'FLYING' >> ninjask7

echo 'Возможности  Overland=6 Surface=10 Underwater=6' > nuzleaf5/ludicolo
echo 'Jump4=0 Power=4 Intelligence=4 Fountain=0' >> nuzleaf5/ludicolo

echo 'Развитые' > octillery5/spearow
echo 'способности  Sniper' >> octillery5/spearow

echo 'Возможности  Overland=2 Surface=8' > poliwag3
echo 'Underwater=5 Jump=2 Power=1 Intelligence=3 Fountain=0 Gilled=0' >> poliwag3

# --== Пункт 2 ==--

# golurk3:
# владелец должен читать и записывать файл;
# группа-владелец должна не иметь никаких прав;
# остальные пользователи должны читать файл
chmod u=rw,g=,o=r golurk3

# magmortar6:
# права 551
chmod 551 magmortar6

# forretress:
# владелец должен читать директорию и переходить в нее;
# группа-владелец должна читать, записывать директорию и переходить в нее;
# остальные пользователи должны читать, записывать директорию и переходить в нее
chmod u=rx,g=rwx,o=rwx magmortar6/forretress

# rattata:
# rw--w----
chmod u=rw,g=w,o= magmortar6/rattata

# jumpluff:
# r--------
chmod u=r,g=,o= magmortar6/jumpluff

# cubone:
# права 770
chmod 770 magmortar6/cubone

# ninjask7:
# права 046
chmod 046 ninjask7

# nuzleaf5:
# права 750
chmod 750 nuzleaf5

# wurmple:
# владелец должен читать директорию и переходить в нее;
# группа-владелец должна только переходить в директорию;
# остальные пользователи должны записывать директорию
chmod u=rx,g=x,o=w nuzleaf5/wurmple

# ariados:
# -wxrw---x
chmod u=wx,g=rw,o=x nuzleaf5/ariados

# ludicolo:
# r--r-----
chmod u=r,g=r,o= nuzleaf5/ludicolo

# octillery5:
# r-x-wxrwx
chmod u=rx,g=wx,o=rwx octillery5

# primeape:
# -wxrw--wx
chmod u=wx,g=rw,o=wx octillery5/primeape

# spearow:
# владелец должен читать и записывать файл;
# группа-владелец должна не иметь никаких прав;
# остальные пользователи должны не иметь никаких прав
chmod u=rw,g=,o= octillery5/spearow

# clamperl:
# rwxr-x-w-
chmod u=rwx,g=rx,o=w octillery5/clamperl

# poliwag3:
# владелец должен не иметь никаких прав;
# группа-владелец должна читать и записывать файл;
# остальные пользователи должны не иметь никаких прав
chmod u=,g=rw,o= poliwag3
```

### 2. Запуск на сервере
Полученный скрипт скопировал на **helios**, запустил (предварительно прописав `chmod u+x setup_lab0.sh`). Скрипт отработал без ошибок.

### 3. Создание копий и ссылок
Далее, начал выполнять действия по 3 пункту задания:
1. _cоздать символическую ссылку для файла golurk3 с именем lab0/magmortar6/rattatagolurk_

    Для создания ссылки воспользовался командой `ln`, с указанным `-s`, означающим, что ссылка - символическая:
    ```bash
    $ ln -s ~/lab0/golurk3 lab0/magmortar6/rattatagolurk
    ln: lab0/magmortar6/rattatagolurk: Permission denied
    ```

    Не вышло, так как у **lab0/magmortar6/** у владельца не стоит разрешение на запись (w). Исправил, попробовал еще раз:

    ```bash
    $ chmod u+w lab0/magmortar6
    $ ln -s ~/lab0/golurk3 lab0/magmortar6/rattatagolurk
    ```

    На этот раз вышло. На всякий случай проверил ссылку и вернул разрешения на изначальные:

    ```bash
    $ ls -l lab0/magmortar6/rattatagolurk
    lrwxr-xr-x  1 s367553  studs  12 13 сент. 21:15 lab0/magmortar6/rattatagolurk -> lab0/golurk
    $ chmod u-w lab0/magmortar6
    ```

2. _cоздать жесткую ссылку для файла ninjask7 с именем lab0/magmortar6/jumpluffninjask_

    На этот раз воспользовался тем же `ln`, но без флагов (в таком случае он создает жесткую ссылку):
    ```bash
    $ ln ~/lab0/ninjask7 lab0/magmortar6/jumpluffninjask
    ln: lab0/magmortar6/jumpluffninjask: Permission denied
    ```

    Исправил права, запустил, вернул права:
    ```bash
    $ chmod u+w lab0/magmortar6
    $ ln ~/lab0/ninjask7 lab0/magmortar6/jumpluffninjask
    $ chmod u-w lab0/magmortar6
    ```

3. _объеденить содержимое файлов lab0/nuzleaf5/ludicolo, lab0/nuzleaf5/ludicolo, в новый файл lab0/poliwag3\_97_

    ```bash
    $ cat lab0/nuzleaf5/ludicolo lab0/nuzleaf5/ludicolo > lab0/poliwag3_97
    ```

4. _создать символическую ссылку c именем Copy\_62 на директорию magmortar6 в каталоге lab0_

    ```bash
    $ ln -s ~/lab0/magmortar6 lab0/Copy_62
    ```

5. _скопировать файл ninjask7 в директорию lab0/magmortar6/cubone_

    ```bash
    $ cp lab0/ninjask7 lab0/magmortar6/cubone
    cp: lab0/ninjask7: Permission denied
    ```

    Причиной ошибки является факт, что владелец (в данном случае я) не имеют разрешения на чтение lab0/ninjask7. Исправив ошибку, я успешно скопировал файл и вернул права на изначальные:

    ```bash
    $ chmod u+r lab0/ninjask7
    $ cp lab0/ninjask7 lab0/magmortar6/cubone
    $ chmod u-r lab0/ninjask7
    ```

6. _скопировать рекурсивно директорию magmortar6 в директорию lab0/nuzleaf5/wurmple_

    Для рекурсивного копирования директории я воспользовался флагом `-r`

    ```bash
    $ cp -r lab0/magmortar6 lab0/nuzleaf5/wurmple
    cp: lab0/nuzleaf5/wurmple/magmortar6: Permission denied
    ```

    Не получилось скопировать, так как для директории **lab0/nuzleaf5/wurmple/** у владельца не стоит разрешение на запись. Исправил:

    ```bash
    $ chmod u+w lab0/nuzleaf5/wurmple
    $ cp -r lab0/magmortar6 lab0/nuzleaf5/wurmple
    cp: lab0/magmortar6/jumpluffninjask: Permission denied
    ```

    Добавил разрешение на чтение файла и запустил еще раз:

    ```bash
    $ chmod u+r lab0/magmortar6/jumpluffninjask
    $ cp -r lab0/magmortar6 lab0/nuzleaf5/wurmple
    cp: lab0/nuzleaf5/wurmple/magmortar6/jumpluffninjask: Permission denied
    cp: lab0/nuzleaf5/wurmple/magmortar6/cubone/ninjask7: Permission denied
    cp: lab0/nuzleaf5/wurmple/magmortar6/jumpluff: Permission denied
    ```

    Добавил разрешение на запись директории **lab0/nuzleaf5/wurmple/magmortar6**, запустил:

    ```bash
    $ chmod u+w lab0/nuzleaf5/wurmple/magmortar6
    $ cp -r lab0/magmortar6 lab0/nuzleaf5/wurmple
    cp: lab0/nuzleaf5/wurmple/magmortar6/cubone/ninjask7: Permission denied
    cp: lab0/nuzleaf5/wurmple/magmortar6/jumpluff: Permission denied
    ```

    Добавил разрешение на запись файлов, запустил:
    ```bash
    $ chmod u+w lab0/nuzleaf5/wurmple/magmortar6/jumpluffninjask
    $ chmod u+w lab0/nuzleaf5/wurmple/magmortar6/jumpluff
    $ chmod u+w lab0/nuzleaf5/wurmple/magmortar6/cubone/ninjask7
    $ cp -r lab0/magmortar6 lab0/nuzleaf5/wurmple
    ```

    Вернул разрешения к прежнему состоянию:

    ```bash
    $ chmod u-w lab0/nuzleaf5/wurmple
    $ chmod u-r lab0/magmortar6/jumpluffninjask
    $ chmod u-w lab0/nuzleaf5/wurmple/magmortar6
    $ chmod u-rw lab0/nuzleaf5/wurmple/magmortar6/jumpluffninjask
    $ chmod u-w lab0/nuzleaf5/wurmple/magmortar6/jumpluff
    $ chmod u-w lab0/nuzleaf5/wurmple/magmortar6/cubone/ninjask7
    ```

7. _скопировать содержимое файла ninjask7 в новый файл lab0/octillery5/spearowninjask_:

    ```bash
    $ cp lab0/ninjask7 lab0/octillery5/spearowninjask
    cp: lab0/ninjask7: Permission denied
    ```

    Добавил разрешение на чтение **lab0/ninjask7** и запустил:

    ```bash
    $ chmod u+r lab0/ninjask7
    $ cp lab0/ninjask7 lab0/octillery5/spearowninjask
    cp: lab0/octillery5/spearowninjask: Permission denied
    ```

    Не получилось скопировать, так как для директории **lab0/nuzleaf5/** у владельца не стоит разрешение на запись и он (я) не может создавать что-либо внутри. Исправив, получилось успешно скопировать файл. После вернул разрешения к изначальному виду:

    ```bash
    $ chmod u+w lab0/octillery5
    $ cp lab0/ninjask7 lab0/octillery5/spearowninjask
    $ chmod u-r lab0/ninjask7
    $ chmod u-w lab0/octillery5
    $ chmod u-r lab0/octillery5/spearowninjask
    ```

Получившееся иерархия файлов и каталогов:

```bash
$ ls -lR
total 28
lrwxr-xr-x  1 s367553  studs   35 19 сент. 10:42 Copy_62 -> /home/studs/s367553/lab0/magmortar6
-rw----r--  1 s367553  studs   28 19 сент. 10:40 golurk3
dr-xr-x--x  4 s367553  studs    8 19 сент. 10:41 magmortar6
----r--rw-  2 s367553  studs   36 19 сент. 10:40 ninjask7
drwxr-x---  4 s367553  studs    5 19 сент. 10:40 nuzleaf5
dr-x-wxrwx  4 s367553  studs    6 19 сент. 10:49 octillery5
----rw----  1 s367553  studs  108 19 сент. 10:40 poliwag3
-rw-r--r--  1 s367553  studs  202 19 сент. 10:41 poliwag3_97

./magmortar6:
total 3
drwxrwx---  2 s367553  studs   3 19 сент. 10:42 cubone
dr-xrwxrwx  2 s367553  studs   2 19 сент. 10:40 forretress
-r--------  1 s367553  studs  35 19 сент. 10:40 jumpluff
----r--rw-  2 s367553  studs  36 19 сент. 10:40 jumpluffninjask
-rw--w----  1 s367553  studs  84 19 сент. 10:40 rattata
lrwxr-xr-x  1 s367553  studs  32 19 сент. 10:41 rattatagolurk -> /home/studs/s367553/lab0/golurk3

./magmortar6/cubone:
total 1
-r--r--r--  1 s367553  studs  36 19 сент. 10:42 ninjask7

./magmortar6/forretress:
total 0

./nuzleaf5:
total 6
d-wxrw---x  2 s367553  studs    2 19 сент. 10:40 ariados
-r--r-----  1 s367553  studs  101 19 сент. 10:40 ludicolo
dr-x--x-w-  3 s367553  studs    3 19 сент. 10:43 wurmple

./nuzleaf5/ariados:
total 0
ls: ./nuzleaf5/ariados: Permission denied

./nuzleaf5/wurmple:
total 9
dr-xr-x--x  4 s367553  studs  8 19 сент. 10:41 magmortar6

./nuzleaf5/wurmple/magmortar6:
total 3
drwxrwx---  2 s367553  studs   3 19 сент. 10:42 cubone
dr-xrwxrwx  2 s367553  studs   2 19 сент. 10:40 forretress
-r--------  1 s367553  studs  35 19 сент. 10:40 jumpluff
----r--rw-  1 s367553  studs  36 19 сент. 10:40 jumpluffninjask
-rw--w----  1 s367553  studs  84 19 сент. 10:40 rattata
-rw----r--  1 s367553  studs  28 19 сент. 10:40 rattatagolurk

./nuzleaf5/wurmple/magmortar6/cubone:
total 1
-r--r--r--  1 s367553  studs  36 19 сент. 10:42 ninjask7

./nuzleaf5/wurmple/magmortar6/forretress:
total 0

./octillery5:
total 2
drwxr-x-w-  2 s367553  studs   2 19 сент. 10:40 clamperl
d-wxrw--wx  2 s367553  studs   2 19 сент. 10:40 primeape
-rw-------  1 s367553  studs  48 19 сент. 10:40 spearow
----r--r--  1 s367553  studs  36 19 сент. 10:49 spearowninjask

./octillery5/clamperl:
total 0

./octillery5/primeape:
total 0
ls: ./octillery5/primeape: Permission denied
```

### 4. Фильтрация файлов и данных
1. _Подсчитать количество символов содержимого файлов в директории octillery5, отсортировать вывод по увеличению количества, ошибки доступа перенаправить в файл в директории /tmp_

    ```bash
    $ cat lab0/octillery5/* 2>/tmp/lab0_errors | wc -m | sort -k1n
      29
    ```

2. _Вывести рекурсивно список имен и атрибутов файлов в директории lab0, содержащих строку "lu", список отсортировать по возрастанию даты доступа к файлу, добавить вывод ошибок доступа в стандартный поток вывода_

    Для удобства сортировки по дате указал флаг `-D` у `ls`, отвечающий за формат даты.

    ```bash
    $ ls -lahd -D ' %Y %m %d ' lab0/* lab0/*/* lab0/*/*/* lab0/*/*/*/* lab0/*/*/*/*/* 2>&1 | grep -v '^d' | grep 'lu\|^ls:' | sort -k1 -k6n -k7n -k8n
    ----r--r--  1 s367553  studs    36B  2022 09 19  lab0/nuzleaf5/wurmple/magmortar6/jumpluffninjask
    ----r--rw-  2 s367553  studs    36B  2022 09 19  lab0/Copy_62/jumpluffninjask
    ----r--rw-  2 s367553  studs    36B  2022 09 19  lab0/magmortar6/jumpluffninjask
    -r--------  1 s367553  studs    35B  2022 09 19  lab0/Copy_62/jumpluff
    -r--------  1 s367553  studs    35B  2022 09 19  lab0/magmortar6/jumpluff
    -r--------  1 s367553  studs    35B  2022 09 19  lab0/nuzleaf5/wurmple/magmortar6/jumpluff
    -r--r-----  1 s367553  studs   101B  2022 09 19  lab0/nuzleaf5/ludicolo
    -rw----r--  1 s367553  studs    28B  2022 09 19  lab0/golurk3
    -rw----r--  1 s367553  studs    28B  2022 09 19  lab0/nuzleaf5/wurmple/magmortar6/rattatagolurk
    lrwxr-xr-x  1 s367553  studs    32B  2022 09 19  lab0/Copy_62/rattatagolurk -> /home/studs/s367553/lab0/golurk3
    lrwxr-xr-x  1 s367553  studs    32B  2022 09 19  lab0/magmortar6/rattatagolurk -> /home/studs/s367553/lab0/golurk3
    ```

3. _Вывести содержимое файла ninjask7 с номерами строк, оставить только строки, заканчивающиеся на 'G', ошибки доступа не подавлять и не перенаправлять_

    ```bash
    $ cat -n lab0/ninjask7 | grep -E 'G$'
    cat: lab0/ninjask7: Permission denied
    $ chmod u+r lab0/ninjask7
    $ cat -n lab0/ninjask7 | grep -E 'G$'
     1	Тип покемона  BUG
     2	FLYING
    $ chmod u-r lab0/ninjask7
    ```

4. _Вывести рекурсивно список имен и атрибутов файлов в директории lab0, содержащих строку "lu", список отсортировать по имени z->a, подавить вывод ошибок доступа_

    ```bash
    $ ls -lahd -D ' %Y %m %d ' lab0/* lab0/*/* lab0/*/*/* lab0/*/*/*/* lab0/*/*/*/*/* 2>>/dev/null | grep -v '^d' | grep 'lu' | sort -r -k9
    -rw----r--  1 s367553  studs    28B  2022 09 19  lab0/nuzleaf5/wurmple/magmortar6/rattatagolurk
    ----r--r--  1 s367553  studs    36B  2022 09 19  lab0/nuzleaf5/wurmple/magmortar6/jumpluffninjask
    -r--------  1 s367553  studs    35B  2022 09 19  lab0/nuzleaf5/wurmple/magmortar6/jumpluff
    -r--r-----  1 s367553  studs   101B  2022 09 19  lab0/nuzleaf5/ludicolo
    lrwxr-xr-x  1 s367553  studs    32B  2022 09 19  lab0/magmortar6/rattatagolurk -> /home/studs/s367553/lab0/golurk3
    ----r--rw-  2 s367553  studs    36B  2022 09 19  lab0/magmortar6/jumpluffninjask
    -r--------  1 s367553  studs    35B  2022 09 19  lab0/magmortar6/jumpluff
    -rw----r--  1 s367553  studs    28B  2022 09 19  lab0/golurk3
    lrwxr-xr-x  1 s367553  studs    32B  2022 09 19  lab0/Copy_62/rattatagolurk -> /home/studs/s367553/lab0/golurk3
    ----r--rw-  2 s367553  studs    36B  2022 09 19  lab0/Copy_62/jumpluffninjask
    -r--------  1 s367553  studs    35B  2022 09 19  lab0/Copy_62/jumpluff
    ```

5. _Подсчитать количество строк содержимого файлов в директории magmortar6, отсортировать вывод по уменьшению количества, ошибки доступа не подавлять и не перенаправлять_

    ```bash
    $ wc -l $(ls -dp lab0/magmortar6/* | grep -v '/$') | grep -v 'total$' | sort -k1rn
    wc: lab0/magmortar6/cubone: read: Is a directory
    wc: lab0/magmortar6/forretress: read: Is a directory
    wc: lab0/magmortar6/jumpluffninjask: open: Permission denied
        2 lab0/magmortar6/rattata
        2 lab0/magmortar6/jumpluff
        1 lab0/magmortar6/rattatagolurk
    ```

6. _Вывести три первых элемента рекурсивного списка имен и атрибутов файлов в директории lab0, содержащих строку "lu", список отсортировать по убыванию даты модификации файла, ошибки доступа перенаправить в файл в директории /tmp_

    ```bash
    $ ls -lahd -D ' %Y %m %d ' lab0/* lab0/*/* lab0/*/*/* lab0/*/*/*/* lab0/*/*/*/*/* 2>>/tmp/lab0_err | grep -v '^d' | grep 'lu' | sort -k6rn -k7rn -k8rn | head -n 3
    ----r--r--  1 s367553  studs    36B  2022 09 19  lab0/nuzleaf5/wurmple/magmortar6/jumpluffninjask
    ----r--rw-  2 s367553  studs    36B  2022 09 19  lab0/Copy_62/jumpluffninjask
    ----r--rw-  2 s367553  studs    36B  2022 09 19  lab0/magmortar6/jumpluffninjask
    ```

### 5. Удаление

1. _Удалить файл ninjask7_

    `rm` через диалог спросил, стоит ли ему изменить права на файл, чтобы его можно было удалить (можно согласиться, нажав "y", затем "Enter"). Для наглядности я отказался и поменял права вручную:

    ```bash
    $ rm lab0/ninjask7 
    override ---r--rw- s367553/studs uarch for lab0/ninjask7? n
    $ chmod u+w lab0/ninjask7
    $ rm lab0/ninjask7 
    $ ls lab0/ninjask7
    ls: lab0/ninjask7: No such file or directory
    ```

2. _Удалить файл lab0/nuzleaf5/ludicolo_

    ```bash
    $ lab0/nuzleaf5/ludicolo
    override r--r----- s367553/studs uarch for lab0/nuzleaf5/ludicolo? n
    $ chmod u+w lab0/nuzleaf5/ludicolo
    $ rm lab0/nuzleaf5/ludicolo
    $ ls lab0/nuzleaf5/ludicolo
    ls: lab0/nuzleaf5/ludicolo: No such file or directory
    ```

3. _удалить символические ссылки Copy\_*_

    Есть только одна символическая ссылка Copy_ - **lab0/Copy_62**

    ```bash
    $ rm lab0/Copy_62
    ```


4. _удалить жесткие ссылки lab0/magmortar6/jumpluffninja*_

    ```bash
    $ rm lab0/magmortar6/jumpluffninja*
    rm: lab0/magmortar6/jumpluffninjask: Permission denied
    $ ls -lah lab0/magmortar6/jumpluffninja*
    --w-r--rw-  1 s367553  studs    36B 27 сент. 15:40 lab0/magmortar6/jumpluffninjask
    $ ls -lah lab0 | grep 'magmortar6'
    dr-xr-x--x  4 s367553  studs     8B 27 сент. 15:41 magmortar6
    $ chmod u+w lab0/magmortar6
    $ rm lab0/magmortar6/jumpluffninjask 
    $ chmod u-w lab0/magmortar6
    ```

5. _Удалить директорию nuzleaf5_

    Для начала я решил убедиться, что директория пустая, и если так, удалить ее. Однако, в ней были файлы, поэтому я воспользовался флагом `-r` у `rm`:

    ```bash
    $ rmdir lab0/nuzleaf5
    rmdir: lab0/nuzleaf5: Directory not empty
    $ rm -r lab0/nuzleaf5
    override r-x--x-w- s367553/studs uarch for lab0/nuzleaf5/wurmple? n
    ```

    Получив диалоговое окно, я решил поменять разрешения для файлов в директории самостоятельно. После выставления прав на запись и чтение всех файлов и директорий в **lab0/nuzleaf5**, мне удалось удалить ее.

    ```bash
    $ chmod u+rw lab0/nuzleaf5/*
    $ chmod u+rw lab0/nuzleaf5/wurmple/magmortar6
    $ chmod u+rw lab0/nuzleaf5/wurmple/magmortar6/*
    $ rm -r lab0/nuzleaf5
    ```

6. _Удалить директорию lab0/magmortar6/forretress_

    ```bash
    $ rmdir lab0/magmortar6/forretress
    rmdir: lab0/magmortar6/forretress: Permission denied
    $ chmod u+w lab0/magmortar6/forretress
    $ rmdir lab0/magmortar6/forretress
    rmdir: lab0/magmortar6/forretress: Permission denied
    $ chmod u+w lab0/magmortar6
    $ rmdir lab0/magmortar6/forretress
    $ chmod u-w lab0/magmortar6
    ```
