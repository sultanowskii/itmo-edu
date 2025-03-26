# Отчет

Выбранная версия - `16.4` (аналогична `helios`)

## Подготовка локального окружения

Создаем виртуалку на ubuntu.

Настраиваем локали (не забываем добавить по варианту):

```bash
sudo dpkg-reconfigure locales
```

Сперва идем по [оф.доке](https://www.postgresql.org/download/linux/ubuntu/):

```bash
# Import the repository signing key:
sudo apt install curl ca-certificates
sudo install -d /usr/share/postgresql-common/pgdg
sudo curl -o /usr/share/postgresql-common/pgdg/apt.postgresql.org.asc --fail https://www.postgresql.org/media/keys/ACCC4CF8.asc

# Create the repository configuration file:
sudo sh -c 'echo "deb [signed-by=/usr/share/postgresql-common/pgdg/apt.postgresql.org.asc] https://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'

# Update the package lists:
sudo apt update
```

По [оф.доке](https://www.postgresql.org/download/linux/ubuntu/) и [ответу на askubuntu](https://askubuntu.com/questions/659663/dont-create-default-cluster-in-postgresql), для начала поставим `postgresql-commons` - пакет, который позволяет иметь на одной машине несколько версий postgres:

```bash
sudo apt install -y postgresql-common
```

Далее, в `/etc/postgresql-common/createcluster.conf` раскомментируем строку с параметром `create_main_cluster` и выставим его в `false`:

```conf
# Create a "main" cluster when a new postgresql-x.y server package is installed
create_main_cluster = false
```

И наконец установим сам postgres:

```bash
# Install the postgres
sudo apt install postgresql-16
```

## Инициализация кластера

Создадим директорию кластера:

```bash
cd $HOME
mkdir uwo39
```

На локалке дополнительно добавим `/usr/lib/postgresql/16/bin/` в `PATH`:

```bash
export PATH="$PATH:/usr/lib/postgresql/16/bin/"
```

Инициализируем кластер (есть скрипт `init.sh`):

```bash
export PGDATA="$HOME/uwo39"
export PGCLIENTENCODING="KOI8R"

mkdir -p "$PGDATA"

initdb --locale="ru_RU.KOI8-R"
```

### В виде скрипта

Для удобства завернуто в скрипт. Использование:

```bash
source set-env.sh
./init.sh
```

## Конфигурация и запуск сервера БД

### Способы подключения

- `type`: Тип записи
- `address`: Указывает на разрешенный IP-адрес(а) клиента. Может содержать имя хоста, IP-диапазон или кейворд (например, `all`)

#### Unix-domain сокет в режиме peer

Из [документации](https://www.postgresql.org/docs/current/auth-pg-hba-conf.html):

>TYPE: `local` - This record matches connection attempts using Unix-domain sockets. Without a record of this type, Unix-domain socket connections are disallowed.

>(AUTH) METHOD: `peer` - Obtain the client's operating system user name from the operating system and check if it matches the requested database user name. This is only available for local connections

Допишем строку:

```conf
local   all             all                                     peer
```

#### Сокет TCP/IP, принимать подключения к любому IP-адресу узла

Способ аутентификации TCP/IP клиентов: по паролю SHA-256.

>TYPE: `host` - This record matches connection attempts made using TCP/IP. host records match SSL or non-SSL connection attempts as well as GSSAPI encrypted or non-GSSAPI encrypted connection attempts.

>ADDRESS: `0.0.0.0/0` - represents all IPv4 addresses.

>(AUTH) METHOD - Perform SCRAM-SHA-256 authentication to verify the user's password.

```conf
host    all             all             0.0.0.0/0               scram-sha-256
```

#### Остальные способы подключений запретить

```conf
host    all             all             all                     reject
```

Итого получаем (файл `pg_hba.conf`):

```conf
 PostgreSQL Client Authentication Configuration File

# TYPE  DATABASE        USER            ADDRESS                 METHOD

# Unix-domain socket in peer mode.
local   all             all                                     peer
# TCP/IP socket, accept all connections. Use SHA-256
host    all             all             0.0.0.0/0               scram-sha-256
# Reject all other connections
host    all             all             0.0.0.0/0               reject
```

### Номер порта: `9253`

```conf
port = 9253
```

### Способ аутентификации TCP/IP клиентов: по паролю SHA-256

```conf
password_encryption = scram-sha-256
```

### Директория WAL файлов: `$PGDATA/pg_wal`

```conf
archive_mode = on
archive_command = 'cp %p $HOME/uwo39/pg_wal'
```

Указанная команда используется для архивации (завершенной) части WAL.

### Формат лог-файлов: `.log`

```conf
log_destination = 'stderr'
logging_collector = on
log_directory = 'log'
log_filename = 'postgresql-%Y-%m-%d_%H%M%S.log'
```

Этой частью конфига включим сборщик логов, устанавливаем назначение, директорию для файлов логов и шаблон названия файлов.

### Уровень сообщений лога: `WARNING`

```conf
log_min_messages = warning
```

Устанавливаем минимальный уровень логирования. Любой уровень также логирует все уровни выше него по значимости (например, выставив INFO, сообщения уровня FATAL тоже будут логироваться).

### Дополнительно логировать: завершение сессий и продолжительность выполнения команд

```conf
log_disconnections = on
log_duration = on
```

Дополнительно включим логирование завершений сессий и продолжительности выполнения команд.

### Дополнительная настройка

Т.к. пользователей 50 и каждый будет иметь 2 сессии, получаем `50 * 2 = 100` сессий. Для запаса (на случай, когда нам нужно внести правки в конфигурацию) добавим еще `3` резервных соединения и `3` для суперпользователя. Добавим в конфиг:

```conf
max_connections = 100
reserved_connections = 3
superuser_reserved_connections = 3
```

Далее, установим значение `shared_buffers` - это объем памяти для буферов в разделяемой памяти. Согласно документации, рекомендуемым базовым значением является 25% от общей доступной оперативной памяти. В качестве условного среднего возьмем 8ГБ - тогда дадим на разделяемые буферы 2ГБ.

```conf
shared_buffers = 2GB
```

Соответственно поднимем и рекомендованный макс.размер WAL (мин. оставим по умолчанию):

```conf
max_wal_size = 2GB
min_wal_size = 80MB
```

Для `temp_buffers` оставим значение по умолчанию - 8МБ. Это сессионно-локальные буферы для временных таблиц (те, что сбрасываются по окончанию сессии).

```conf
temp_buffers = 8MB
```

Каждая сессия инициирует до 9 транзакций. Можно было бы установить `max_prepared_transactions`, но не сказано, используется ли `PREPARE TRANSACTION`, так что пропустим.

Каждая запись размером 8КБ, до 9 транзакций на сессию, итого получается `8КБ * 9 * 50 * 2 = 7200КБ`. Для удобства округлим в большую сторону, получим 8МБ.

```conf
work_mem = 8MB
```

Время между контрольными точками оставим в стандартном значении - если взять больше, то есть риск потери большего кол-ва данных, если взять меньше - оно может повлиять на производительность.

```conf
checkpoint_timeout = 5min
```

После установим ориентировачное значение размера кэша - его использует планировщик. Оно должно быть более или равным `shared_buffers`, возьмем равным ему (по минимуму). 2ГБ совпадает со значением по умолчанию.

```conf
effective_cache_size = 2GB
```

Параметр `fsync` отвечает за синхронизацию буфера с файлом на диске (с помощью системного вызова `fsync` или других методов). Отключение этого параметра может привести к потере/повреждению данных.

```conf
fsync = on
```

`commit_delay` - это задержка между записью коммита в WAL-буфер и записью буфера на диск, в микросекундах. Идея в том, что если устанавливается ненулевое значение, что в один `fsync` могут успеть зайти несколько коммитов. Однако, есть шанс и проиграть - ожидание произойдет вне зависимости от кол-ва "попавших" коммитов. Здесь оставим значение по умолчанию - ноль, чтобы WAL сразу писался на диск.

```conf
commit_delay = 0
```

### Запуск сервера

Не забываем про переменную окружения `PGDATA`, запускаем кластер:

```bash
pg_ctl start
```

И подключимся к БД:

```bash
psql -p 9253 -d postgres
```

(В локальном окружении также нужен флаг, чтобы сокет искался в правильной директории, установленной параметром `unix_socket_directories`):

```bash
psql -h /tmp -p 9253 -d postgres
```

Сработало, двигаемся далее.

### Скрипт

```bash
source set-env.sh
./setup-conf.sh
pg_ctl start
```

## Дополнительные табличные пространства и наполнение базы

### Создать новое табличное пространство для индексов: `$HOME/amh13`

Сначала я попробовал создать так:

```sql
CREATE TABLESPACE ispace LOCATION '$HOME/amh13';
```

На что получил:

```text
ОШИБКА:  путь к табличному пространству должен быть абсолютным
```

---

Тогда, (локально):

```sql
CREATE TABLESPACE ispace LOCATION '/home/a7ult/amh13';
```

На сервере:

```sql
CREATE TABLESPACE ispace LOCATION '/var/db/postgres0/amh13';
```

### На основе `template1` создать новую базу: `longblueroad`

Воспользуемся утилитой `createdb` (не забыв указать порт, а на локалке - флаг `-h /tmp`)

```bash
createdb -p 9253 --template=template1 longblueroad
```

### Создать новую роль, предоставить необходимые права, разрешить подключение к базе

Помимо указанного, также предварительно создадим тестовую таблицу.

```bash
$ psql -p 9253 -d longblueroad
longblueroad=# CREATE TABLE road (id SERIAL PRIMARY KEY, name TEXT NOT NULL UNIQUE, length INT NOT NULL);
CREATE TABLE
longblueroad=# CREATE INDEX ON road(name) TABLESPACE ispace;
CREATE INDEX
longblueroad=# CREATE ROLE driver LOGIN PASSWORD 'AZCVoi172e9A&CV981';
CREATE ROLE
longblueroad=# GRANT CONNECT ON DATABASE longblueroad TO driver;
GRANT
longblueroad=# GRANT INSERT ON TABLE road TO driver;
GRANT       
longblueroad=# -- USAGE: For sequences, allows use of the currval and nextval functions.
longblueroad=# GRANT USAGE, SELECT ON SEQUENCE road_id_seq TO driver;
GRANT
```

### От имени новой роли (не администратора) произвести наполнение ВСЕХ созданных баз тестовыми наборами данных. ВСЕ табличные пространства должны использоваться по назначению

```bash
$ psql -p 9253 -d longblueroad -U driver -W
Password: 
psql: error: connection to server on socket "/tmp/.s.PGSQL.9253" failed: ВАЖНО:  пользователь "driver" не прошёл проверку подлинности (Peer)
```

Не вышло. С паролем стоит подключиться по сети (из-за настроек `pg_hba`):

```bash
$ psql -h 0.0.0.0 -p 9253 -d longblueroad -U driver -W
Password:
longblueroad=> 
```

Сработало! Для начала ради интереса проверим разрешения:

```sql
longblueroad=> SELECT * FROM road;
ОШИБКА:  нет доступа к таблице road
```

И теперь заполним тестовыми данными:

```sql
longblueroad=> \i test-data.sql
```

### Вывести список всех табличных пространств кластера и содержащиеся в них объекты

```bash
$ psql -p 9253 -d longblueroad
longblueroad=# SELECT oid, spcname, pg_tablespace_location(oid) FROM pg_tablespace;
  oid  |  spcname   | pg_tablespace_location  
-------+------------+-------------------------
  1663 | pg_default | 
  1664 | pg_global  | 
 16388 | ispace     | /var/db/postgres0/amh13
(3 строки)
longblueroad=# SELECT relname, spcname FROM pg_tablespace INNER JOIN pg_class ON pg_class.reltablespace = pg_tablespace.oid;
                 relname                 |  spcname  
-----------------------------------------+-----------
 road_name_idx                           | ispace
 pg_toast_1262                           | pg_global
 pg_toast_1262_index                     | pg_global
 pg_toast_2964                           | pg_global
 pg_toast_2964_index                     | pg_global
 pg_toast_1213                           | pg_global
 pg_toast_1213_index                     | pg_global
 pg_toast_1260                           | pg_global
 pg_toast_1260_index                     | pg_global
 pg_toast_2396                           | pg_global
 pg_toast_2396_index                     | pg_global
 pg_toast_6000                           | pg_global
 pg_toast_6000_index                     | pg_global
 pg_toast_3592                           | pg_global
 pg_toast_3592_index                     | pg_global
 pg_toast_6243                           | pg_global
 pg_toast_6243_index                     | pg_global
 pg_toast_6100                           | pg_global
 pg_toast_6100_index                     | pg_global
 pg_database_datname_index               | pg_global
 pg_database_oid_index                   | pg_global
 pg_db_role_setting_databaseid_rol_index | pg_global
 pg_tablespace_oid_index                 | pg_global
 pg_tablespace_spcname_index             | pg_global
 pg_authid_rolname_index                 | pg_global
 pg_authid_oid_index                     | pg_global
 pg_auth_members_oid_index               | pg_global
 pg_auth_members_role_member_index       | pg_global
 pg_auth_members_member_role_index       | pg_global
 pg_auth_members_grantor_index           | pg_global
 pg_shdepend_depender_index              | pg_global
 pg_shdepend_reference_index             | pg_global
 pg_shdescription_o_c_index              | pg_global
 pg_replication_origin_roiident_index    | pg_global
 pg_replication_origin_roname_index      | pg_global
 pg_shseclabel_object_index              | pg_global
 pg_parameter_acl_parname_index          | pg_global
 pg_parameter_acl_oid_index              | pg_global
 pg_subscription_oid_index               | pg_global
 pg_subscription_subname_index           | pg_global
 pg_authid                               | pg_global
 pg_subscription                         | pg_global
 pg_database                             | pg_global
 pg_db_role_setting                      | pg_global
 pg_tablespace                           | pg_global
 pg_auth_members                         | pg_global
 pg_shdepend                             | pg_global
 pg_shdescription                        | pg_global
 pg_replication_origin                   | pg_global
 pg_shseclabel                           | pg_global
 pg_parameter_acl                        | pg_global
(51 строка)
```

Можно заметить, что таблицы `road` и `pg_default` здесь нет. Из документации:

>`reltablespace`: If zero, the database's default tablespace is implied.

В `pg_class` у объектов с дефолтным tablespace в поле `reltablespace` стоит 0, поэтому в вывод выше таблица не попала.

Также важно отметить:

>Not meaningful if the relation has no on-disk file, except for partitioned tables, where this is the tablespace in which partitions will be created when one is not specified in the creation command.
