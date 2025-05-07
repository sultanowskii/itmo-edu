# Лабораторная работа №4

Вариант: `31392`

## Задание

Цель работы - ознакомиться с методами и средствами построения отказоустойчивых решений на базе СУБД Postgres; получить практические навыки восстановления работы системы после отказа.

Работа рассчитана на двух человек и выполняется в три этапа: настройка, симуляция и обработка сбоя, восстановление.

### Требования к выполнению работы

- В качестве хостов использовать одинаковые виртуальные машины.
- В первую очередь необходимо обеспечить сетевую связность между ВМ.
- Для подключения к СУБД (например, через psql), использовать отдельную виртуальную или физическую машину.
- Демонстрировать наполнение базы и доступ на запись на примере не менее, чем двух таблиц, столбцов, строк, транзакций и клиентских сессий.

### Этап 1. Конфигурация

Развернуть postgres на двух узлах в режиме балансировки нагрузки. Использовать pgpool-II. Продемонстрировать обработку транзакций обоими серверами.

### Этап 2. Симуляция и обработка сбоя

#### 2.1 Подготовка

- Установить несколько клиентских подключений к СУБД.
- Продемонстрировать состояние данных и работу клиентов в режиме чтение/запись.

#### 2.2 Сбой

Симулировать переполнение дискового пространства на основном узле - заполнить всё свободное пространство раздела с PGDATA “мусорными” файлами.

#### 2.3 Обработка

- Найти и продемонстрировать в логах релевантные сообщения об ошибках.
- Выполнить переключение (failover) на резервный сервер.
- Продемонстрировать состояние данных и работу клиентов в режиме чтение/запись.

### Восстановление

- Восстановить работу основного узла - откатить действие, выполненное с виртуальной машиной на этапе 2.2.
- Актуализировать состояние базы на основном узле - накатить все изменения данных, выполненные на этапе 2.3.
- Восстановить исправную работу узлов в исходной конфигурации (в соответствии с этапом 1).
- Продемонстрировать состояние данных и работу клиентов в режиме чтение/запись.

## Выполнение

### Подготовка

Создадим две виртуальные машины на основе `ubuntu`-образа. Можно использовать `preinstalled`-версию, но у меня на локалке уже был `.img` (qemu) с установленным и настроенным Ubuntu.

Вообще, процесс "от образа убунты" до "запускаемого qemu-образа убунты" выглядит так:

```bash
$ # Создаем qemu-образ (оседает как файл на диске)
$ qemu-img create -f qcow2 ubuntu.img 16G
$ # Запускаем, используя ранее созданный qemu-образ как жесткий диск, 
$ # ISO убунты как CD-ROM
$ # Бут с CD-ROM (значение d)
$ #
$ # Когда запустится, необходимо совершить установку Ubuntu и после
$ # перезагрузить/выключить ВМ. В дальнейшем будет достаточно лишь запускать полученный 
$ # образ - он будет запускаться как простая Ubuntu.
$ qemu-system-x86_64 -hda ubuntu.img -cdrom ubuntu-22.04.2-live-server-amd64.iso -boot d -m 6144
$
```

Создадим сетевой мост (`dsods-br`):

```bash
$ # Разрешаем использование моста
$ sudo echo 'allow dsods-br' >> /etc/qemu/bridge.conf
$ # Разрешаем использовать мост не-админам
$ sudo chmod +s /usr/lib/qemu/qemu-bridge-helper
$
$ # Создаем мост 'dsods-br'
$ sudo brctl addbr dsods-br
$ # Устанавливаем IP-адрес моста
$ sudo ip addr add 192.169.0.1/24 dev dsods-br
$ # "Поднимаем" мост
$ sudo ip link set dsods-br up
$ # Настраиваем iptables для передачи трафика
$ sudo iptables -I FORWARD -m physdev --physdev-is-bridged -j ACCEPT
$
```

Запуск первой машины (первая половина MAC-адреса - `52:54:00` - конвенция для KVM/qemu):

```bash
qemu-system-x86_64 \
  -hda dsods0.img \
  -m 4096 \
  -nic user,hostfwd=tcp::30022-:22 \
  -net nic,model=virtio,macaddr=52:54:00:00:00:01 \
  -net bridge,br=dsods-br
```

Запуск второй машины:

```bash
qemu-system-x86_64 \
  -hda dsods1.img \
  -m 4096 \
  -nic user,hostfwd=tcp::31022-:22 \
  -net nic,model=virtio,macaddr=52:54:00:00:00:02 \
  -net bridge,br=dsods-br
```

На первой машине:

```bash
$ sudo ip addr add 192.169.0.10/24 dev ens4
$ sudo ip link set ens4 up
$
```

На второй машине:

```bash
$ sudo ip addr add 192.169.0.11/24 dev ens4
$ sudo ip link set ens4 up
$
```

Проверим (на первой машине):

```bash
$ ping 192.169.0.1
...
$ ping 192.169.0.11
...
$
```

На основном хосте также создадим отдельный раздел, куда поместим все, связанное с БД - это будет полезно для выполнения задания (по варианту нужно будет переполнить диск). Снаружи:

```bash
qemu-img create -f qcow2 dbdisk.qcow2 300M
```

И добавим этот диск при запуске первой ВМ:

```bash
-drive file=dbdisk.qcow2,if=virtio,format=qcow2
```

После запуска, внутри ВМ находим нужную нам партицию:

```bash
$ sudo fdisk -l
...

Disk /dev/vda: 1 GiB, 1073741824 bytes, 2097152 sectors
Units: sectors of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
```

Отформатируем ее как `ext4`:

```bash
$ sudo mkfs.ext4 /dev/vda
mke2fs 1.46.5 (30-Dec-2021)
Discarding device blocks: done                            
Creating filesystem with 262144 4k blocks and 65536 inodes
Filesystem UUID: 9ac3c749-abe5-48c7-b3ac-399af1aa6691
Superblock backups stored on blocks: 
        32768, 98304, 163840, 229376

Allocating group tables: done                            
Writing inode tables: done                            
Creating journal (8192 blocks): done
Writing superblocks and filesystem accounting information: done
```

В `/etc/fstab` добавляем запись:

```sda
/dev/vda /home/a7ult/db ext4 rw,user,exec 0 1
```

И монтируем все вхождения в fstab:

```bash
$ sudo mount -a
$ df -h | grep db
/dev/vda                           974M   24K  907M   1% /home/a7ult/db
```

Поправим владельца, удалим предыдущие файлы (`lost+found/`):

```bash
$ sudo chown a7ult:a7ult db
$ rm -rf db/*
$
```

Готово!

### Этап 1. Конфигурация

#### Конфигурация основного хоста

Для начала, в `postgresql.conf`:

```conf
listen_addresses = '*' # use '*' for all

wal_level = replica
hot_standby = on # allows performing read-only operations on the standby server
max_wal_senders = 3

log_destination = 'stderr'
logging_collector = on
log_directory = 'log'
log_filename = 'postgresql-%Y-%m-%d_%H%M%S.log'
```

В `pg_hba.conf`:

```conf
...
# Allow standby instance to connect
host    replication     replicator      192.169.0.11/32         scram-sha-256
# Allow pg_pool to connect
host    all             replicator      192.169.0.12/32         scram-sha-256
# Allow 'regular' user to connect 
host    all             regular         192.169.0.0/24          scram-sha-256
```

Также не забудем создать пользователя для репликации, выдать ему права (`init.sql`):

```sql
CREATE USER replicator WITH REPLICATION PASSWORD '...';
GRANT pg_monitor to replicator;
```

Осталось лишь запустить!

Все это обернуто в `init.sh`. Использование:

```bash
$ source set-env.sh
$ ./init.sh
$ psql -d postgres -U postgres
psql (16.8 (Ubuntu 16.8-1.pgdg22.04+1))
Type "help" for help.
...
$
```

### Конфигурация резервного хоста

```bash
$ # -P show progress information
$ # -R write configuration for replication (creates standby.signal + adds info)
$ # -X include required WAL files with specified method
$ # -c et fast or spread checkpointing
$ # -W specify password
$ pg_basebackup -P -R -X stream -c fast -h 192.169.0.10 -U replicator -D ./db -W
Password: 
30837/30837 kB (100%), 1/1 tablespace
$ ls db
backup_label      log                 pg_ident.conf  pg_serial     pg_tblspc    postgresql.auto.conf
backup_manifest   pg_commit_ts        pg_logical     pg_snapshots  pg_twophase  postgresql.conf
base              pg_dynshmem         pg_multixact   pg_stat       PG_VERSION   postgresql.conf.backup
current_logfiles  pg_hba.conf         pg_notify      pg_stat_tmp   pg_wal       standby.signal
global            pg_hba.conf.backup  pg_replslot    pg_subtrans   pg_xact
$ pg_ctl start
$ psql -d postgres -U postgres
psql (16.8 (Ubuntu 16.8-1.pgdg22.04+1))
Type "help" for help.
...
$
```

### Проверка

На основном хосте:

```sql
postgres=# SELECT client_addr, state FROM pg_stat_replication;
 client_addr  |   state   
--------------+-----------
 192.169.0.11 | streaming
(1 row)
```

На резервном:

```sql
postgres=# SELECT sender_host, status FROM pg_stat_wal_receiver;
 sender_host  |  status   
--------------+-----------
 192.169.0.10 | streaming
(1 row)
```

Вставим какие-нибудь значения в таблицу на основном хосте:

```bash
$ psql -d longblueroad -U regular 
psql (16.8 (Ubuntu 16.8-1.pgdg22.04+1))
Type "help" for help.

longblueroad=> INSERT INTO road (name, length) VALUES
('E95', 3770),
('М-7', 858),
('US 6', 492);
INSERT 0 3
```

Проверим на резервном:

```bash
$ psql -d longblueroad -U regular
psql (16.8 (Ubuntu 16.8-1.pgdg22.04+1))
Type "help" for help.

longblueroad=> \dt
        List of relations
 Schema | Name | Type  |  Owner   
--------+------+-------+----------
 public | cafe | table | postgres
 public | road | table | postgres
(2 rows)

longblueroad=> select * from road;
 id | name | length 
----+------+--------
  4 | E95  |   3770
  5 | М-7  |    858
  6 | US 6 |    492
(3 rows)
```

Теперь сделаем важную проверку - что напрямую данные на резервный хост вставить нельзя:

```bash
$ psql -U regular -h 192.169.0.11 -d longblueroad -W
Password: 
psql (17.4, server 16.8 (Ubuntu 16.8-1.pgdg22.04+1))
Type "help" for help.

longblueroad=> INSERT INTO road (name, length) VALUES ('test road', 1337);
ERROR:  cannot execute INSERT in a read-only transaction
```

Отлично, репликация работает, standby-нода работает в read-only.

Время настроить `pgpool-II`. Для этого добавим еще одну виртуалку, установим ей IP `192.169.0.12`.

```bash
$ sudo ip addr add 192.169.0.12/24 dev ens4
$ sudo ip link set ens4 up
$
```

Установим по инструкции с [оф.сайта](https://www.pgpool.net/mediawiki/index.php/Apt_Repository).

```bash
$ sudo file /etc/pgpool2/pgpool.conf 
/etc/pgpool2/pgpool.conf: Unicode text, UTF-8 text
```

Укажем параметры:

```conf
backend_hostname0 = '192.169.0.10'
backend_port0 = 5432
backend_weight0 = 1
backend_data_directory0 = '/tmp/data0'

backend_hostname1 = '192.169.0.11'
backend_port1 = 5432
backend_weight1 = 1
backend_data_directory1 = '/tmp/data1'

listen_addresses = '*'
port = 5432

sr_check_user = 'replicator'

# DEBUG-PURPOSE ONLY
logging_collector = on
log_statement = on
log_per_node_statement = on
log_destination = 'stderr'
log_directory = '/home/a7ult/pgpool_logs'
log_filename = 'pgpool-%Y-%m-%d_%H%M%S.log'

health_check_period = 10         # Health check period
health_check_user = 'replicator' # Health check user
```

В `/etc/pgpool2/pool_passwd` добавим запись (для наглядности оставим plaintext-пароль, в реальных условиях можно указать хэш):

```txt
replicator:<password>
```

```bash
$ sudo ./setup-conf.sh
$ sudo systemctl restart pgpool2
$
```

Отправим запросы с двух разных клиентов:

```bash
$ psql -U regular -h 192.169.0.12 -d longblueroad -W
Password: 
psql (17.4, server 16.8 (Ubuntu 16.8-1.pgdg22.04+1))
Type "help" for help.

longblueroad=> select * from road;
 id | name | length 
----+------+--------
  1 | E95  |   3770
  2 | М-7  |    858
  3 | US 6 |    492
(3 rows)
```

И посмотрим на логи:

```bash
$ sudo su
> cd /tmp/pgpool_logs/
> tail -f pgpool-2025-05-04_212553.log
...
2025-05-04 21:27:49.474: psql pid 6093: LOG:  statement: select * from road;
2025-05-04 21:27:49.476: psql pid 6093: LOG:  DB node id: 0 backend pid: 4110 statement: select * from road;
2025-05-04 21:27:51.559: psql pid 6076: LOG:  statement: select * from road;
2025-05-04 21:27:51.560: psql pid 6076: LOG:  DB node id: 1 backend pid: 4211 statement: select * from road;
>
```

Действительно, сработало - запросы распределяются на разные инстансы.

### Этап 2. Симуляция и обработка сбоя

#### 2.1 Подготовка

Сделаем несколько подключений. В одном из них добавим новые данные:

```psql
longblueroad=> INSERT INTO road (name, length) VALUES
    ('test1', 1000),
    ('test2', 2000),
    ('test3', 3000);
INSERT 0 3
longblueroad=> INSERT INTO cafe (name, road_id) VALUES
    ('cafe1', 4),
    ('cafe2', 5),
    ('cafe3', 6);
INSERT 0 3
longblueroad=> SELECT * FROM road;
 id | name  | length 
----+-------+--------
  1 | E95   |   3770
  2 | М-7   |    858
  3 | US 6  |    492
  4 | test1 |   1000
  5 | test2 |   2000
  6 | test3 |   3000
(6 rows)

longblueroad=> SELECT * FROM cafe;
 id | name  | road_id 
----+-------+---------
  1 | cafe1 |       4
  2 | cafe2 |       5
  3 | cafe3 |       6
(3 rows)

longblueroad=> 
```

В логах pgpool:

```log
...
2025-05-05 09:01:25.235: psql pid 1149: LOG:  statement: INSERT INTO road (name, length) VALUES
            ('test1', 1000),
            ('test2', 2000),
            ('test3', 3000);
2025-05-05 09:01:25.263: psql pid 1149: LOG:  DB node id: 0 backend pid: 1025 statement: SELECT pg_catalog.version()
2025-05-05 09:01:25.289: psql pid 1149: LOG:  DB node id: 0 backend pid: 1025 statement: INSERT INTO road (name, length) VALUES
            ('test1', 1000),
            ('test2', 2000),
            ('test3', 3000);
2025-05-05 09:01:37.568: psql pid 1149: LOG:  statement: INSERT INTO cafe (name, road_id) VALUES
            ('cafe1', 4),
            ('cafe2', 5),
            ('cafe3', 6);
2025-05-05 09:01:37.569: psql pid 1149: LOG:  DB node id: 0 backend pid: 1025 statement: INSERT INTO cafe (name, road_id) VALUES
            ('cafe1', 4),
            ('cafe2', 5),
            ('cafe3', 6);
2025-05-05 09:01:49.272: psql pid 1149: LOG:  statement: SELECT * FROM road;
...
2025-05-05 09:01:49.380: psql pid 1149: LOG:  DB node id: 0 backend pid: 1025 statement: SELECT * FROM road;
...
```

Теперь сделаем select от других клиентов (2):

```psql
longblueroad=> SELECT * FROM road;
 id | name  | length 
----+-------+--------
  1 | E95   |   3770
  2 | М-7   |    858
  3 | US 6  |    492
  4 | test1 |   1000
  5 | test2 |   2000
  6 | test3 |   3000
(6 rows)

longblueroad=> SELECT * FROM cafe;
 id | name  | road_id 
----+-------+---------
  1 | cafe1 |       4
  2 | cafe2 |       5
  3 | cafe3 |       6
(3 rows)

longblueroad=> 
```

И посмотрим на логи:

```log
2025-05-05 09:10:40.123: psql pid 1155: LOG:  statement: SELECT * FROM road;
2025-05-05 09:10:40.123: psql pid 1155: LOG:  DB node id: 0 backend pid: 1030 statement: SELECT * FROM road;
2025-05-05 09:10:41.142: psql pid 1155: LOG:  statement: SELECT * FROM cafe;
2025-05-05 09:10:41.144: psql pid 1155: LOG:  DB node id: 0 backend pid: 1030 statement: SELECT * FROM cafe;
2025-05-05 09:10:43.784: psql pid 1165: LOG:  statement: SELECT * FROM road;
2025-05-05 09:10:43.784: psql pid 1165: LOG:  DB node id: 1 backend pid: 1199 statement: SELECT * FROM road;
2025-05-05 09:10:44.631: psql pid 1165: LOG:  statement: SELECT * FROM cafe;
2025-05-05 09:10:44.633: psql pid 1165: LOG:  DB node id: 1 backend pid: 1199 statement: SELECT * FROM cafe;
```

И действительно, запросы распределяются на разные узлы.

#### 2.2 Сбой

Я заранее позаботился об этой части - поэтому на основном узле PGDATA располагается на отдельной партиции размером чуть меньше 300Мб:

```bash
$ df -h db
Filesystem      Size  Used Avail Use% Mounted on
/dev/vda        265M   63M  182M  26% /home/a7ult/db
```

Заполним ее с помощью dd:

```bash
$ dd if=/dev/zero of=db/big-file bs=1M count=181M
dd: error writing 'db/big-file': No space left on device
182+0 records in
181+0 records out
190730240 bytes (191 MB, 182 MiB) copied, 2.09214 s, 91.2 MB/s
$ ls -lah db/big-file 
-rw-rw-r-- 1 a7ult a7ult 182M May  5 09:19 db/big-file
$ df -h db
Filesystem      Size  Used Avail Use% Mounted on
/dev/vda        265M  244M     0 100% /home/a7ult/db
```

Теперь попробуем подключиться:

```bash
$ psql -U regular -h 192.169.0.10 -d longblueroad -W
Password: 
psql: error: connection to server at "192.169.0.10", port 5432 failed: FATAL:  could not write init file
```

#### 2.3 Обработка

В логах основного узла:

```log
2025-05-05 09:41:10.698 UTC [1146] FATAL:  could not write init file
2025-05-05 09:41:45.403 UTC [1154] FATAL:  could not write init file
```

Со стороны standby ничего не видно.

Займемся переключением.

На резервном:

```bash
$ pg_ctl promote
$ tail -f db/log/postgresql-2025-05-05_093425.log
2025-05-05 09:55:34.862 UTC [887] LOG:  received promote request
2025-05-05 09:55:34.864 UTC [889] FATAL:  terminating walreceiver process due to administrator command
2025-05-05 09:55:34.870 UTC [887] LOG:  invalid record length at 0/301A350: expected at least 24, got 0
2025-05-05 09:55:34.871 UTC [887] LOG:  redo done at 0/301A318 system usage: CPU: user: 0.01 s, system: 0.02 s, elapsed: 1268.86 s
2025-05-05 09:55:34.918 UTC [887] LOG:  selected new timeline ID: 2
2025-05-05 09:55:35.114 UTC [887] LOG:  archive recovery complete
2025-05-05 09:55:35.146 UTC [885] LOG:  checkpoint starting: force
2025-05-05 09:55:35.171 UTC [872] LOG:  database system is ready to accept connections
2025-05-05 09:55:35.208 UTC [885] LOG:  checkpoint complete: wrote 2 buffers (0.0%); 0 WAL file(s) added, 0 removed, 0 recycled; write=0.019 s, sync=0.009 s, total=0.063 s; sync files=2, longest=0.005 s, average=0.005 s; distance=0 kB, estimate=0 kB; lsn=0/301A3B8, redo lsn=0/301A380
```

Отключим на основном:

```bash
pg_ctl stop
```

Попробуем подключиться в pgpool, сделать вставку:

```bash
❯ psql -U regular -h 192.169.0.12 -d longblueroad -W
Password: 
psql (17.4, server 16.8 (Ubuntu 16.8-1.pgdg22.04+1))
Type "help" for help.

longblueroad=> INSERT INTO road (name, length) VALUES ('test road5', 1337);
INSERT 0 1
```

Посмотрим на логи:

```log
2025-05-05 10:00:35.637: main pid 1509: LOG:  node status[0]: 0
2025-05-05 10:00:35.640: main pid 1509: LOG:  node status[1]: 1
2025-05-05 10:00:59.592: psql pid 1535: LOG:  statement: INSERT INTO road (name, length) VALUES ('test road5', 1337);
2025-05-05 10:00:59.611: psql pid 1535: LOG:  DB node id: 1 backend pid: 1366 statement: SELECT pg_catalog.version()
2025-05-05 10:00:59.628: psql pid 1535: LOG:  DB node id: 1 backend pid: 1366 statement: INSERT INTO road (name, length) VALUES ('test road5', 1337);
```

Сработало! Как видно, pgpool спокойно отправляет INSERT-запросы на резервный узел, и он их выполняет.

### Восстановление

Удалим проблемный файл и всю PGDATA на основном хосте:

```bash
rm db/big-file
rm -rf db/*
```

Далее, скопируем все данные с резервного хоста:

```bash
$ scp -r a7ult@192.169.0.11:~/db/* db/
...
$ ls db/
ackup_label.old  global        pg_hba.conf         pg_multixact  pg_snapshots  pg_tblspc    pg_xact                 postmaster.opts
backup_manifest   log           pg_hba.conf.backup  pg_notify     pg_stat       pg_twophase  postgresql.auto.conf    postmaster.pid
base              pg_commit_ts  pg_ident.conf       pg_replslot   pg_stat_tmp   PG_VERSION   postgresql.conf
current_logfiles  pg_dynshmem   pg_logical          pg_serial     pg_subtrans   pg_wal       postgresql.conf.backup
```

На резервном узле создаем `standby.signal` и перезапускаем кластер:

```bash
$ touch db/standby.signal
a7ult@dsods1:~$ pg_ctl restart
waiting for server to shut down.... done
server stopped
waiting for server to start....2025-05-05 10:08:47.414 UTC [1559] LOG:  redirecting log output to logging collector process
2025-05-05 10:08:47.414 UTC [1559] HINT:  Future log output will appear in directory "log".
 done
server started
```

Не забываем запустить кластер и на основном хосте:

```bash
$ pg_ctl start
pg_ctl: another server might be running; trying to start server anyway
waiting for server to start....2025-05-05 10:09:12.458 UTC [1523] LOG:  redirecting log output to logging collector process
2025-05-05 10:09:12.458 UTC [1523] HINT:  Future log output will appear in directory "log".
 done
server started
```

Перезапустим pgpool:

```bash
sudo systemctl restart pgpool2
```

Логи:

```log
2025-05-05 10:17:09.281: main pid 1697: LOG:  create socket files[0]: /tmp/.s.PGSQL.9898
2025-05-05 10:17:09.282: main pid 1697: LOG:  listen address[0]: localhost
2025-05-05 10:17:09.283: main pid 1697: LOG:  Setting up socket for 127.0.0.1:9898
2025-05-05 10:17:09.292: pcp_main pid 1733: LOG:  PCP process: 1733 started
2025-05-05 10:17:09.293: sr_check_worker pid 1734: LOG:  process started
2025-05-05 10:17:09.298: health_check pid 1735: LOG:  process started
2025-05-05 10:17:09.301: health_check pid 1736: LOG:  process started
2025-05-05 10:17:09.313: main pid 1697: LOG:  pgpool-II successfully started. version 4.6.0 (chirikoboshi)
2025-05-05 10:17:09.313: main pid 1697: LOG:  node status[0]: 1
2025-05-05 10:17:09.313: main pid 1697: LOG:  node status[1]: 2
```

Проверим, что все работает, как задумано:

```bash
$ psql -U regular -h 192.169.0.11 -d longblueroad -W
Password: 
psql (17.4, server 16.8 (Ubuntu 16.8-1.pgdg22.04+1))
Type "help" for help.

longblueroad=> INSERT INTO road (name, length) VALUES ('test road789', 1337);
ERROR:  cannot execute INSERT in a read-only transaction
```

Попытка вставить данные в резервный хост обрывается.

```bash
❯ psql -U regular -h 192.169.0.12 -d longblueroad -W
Password: 
psql (17.4, server 16.8 (Ubuntu 16.8-1.pgdg22.04+1))
Type "help" for help.

longblueroad=> INSERT INTO road (name, length) VALUES ('test road789', 1337);
INSERT 0 1
longblueroad=> SELECT * FROM road;
 id |     name     | length 
----+--------------+--------
  1 | E95          |   3770
  2 | М-7          |    858
  3 | US 6         |    492
  4 | test1        |   1000
  5 | test2        |   2000
  6 | test3        |   3000
  7 | test road    |   1337
  9 | test road3   |   1337
 40 | test road4   |   1337
 41 | test road5   |   1337
 42 | test road789 |   1337
(11 rows)

longblueroad=>
\q
❯ psql -U regular -h 192.169.0.12 -d longblueroad -W
Password: 
psql (17.4, server 16.8 (Ubuntu 16.8-1.pgdg22.04+1))
Type "help" for help.

longblueroad=> SELECT * FROM road;
 id |     name     | length 
----+--------------+--------
  1 | E95          |   3770
  2 | М-7          |    858
  3 | US 6         |    492
  4 | test1        |   1000
  5 | test2        |   2000
  6 | test3        |   3000
  7 | test road    |   1337
  9 | test road3   |   1337
 40 | test road4   |   1337
 41 | test road5   |   1337
 42 | test road789 |   1337
(11 rows)

longblueroad=>
```

Логи:

```log
2025-05-05 10:20:10.235: psql pid 1723: LOG:  statement: INSERT INTO road (name, length) VALUES ('test road789', 1337);
2025-05-05 10:20:10.252: psql pid 1723: LOG:  DB node id: 0 backend pid: 1590 statement: INSERT INTO road (name, length) VALUES ('test road789', 1337);
2025-05-05 10:20:14.010: psql pid 1723: LOG:  statement: SELECT * FROM road;
2025-05-05 10:20:14.108: psql pid 1723: LOG:  DB node id: 1 backend pid: 1879 statement: SELECT * FROM road;
2025-05-05 10:26:17.675: psql pid 1709: LOG:  statement: SELECT * FROM road;
2025-05-05 10:26:17.675: psql pid 1709: LOG:  DB node id: 0 backend pid: 1599 statement: SELECT * FROM road;
```

Все вернулось на прежнее место - INSERT выполняется на основном хосте, SELECT - на всех.
