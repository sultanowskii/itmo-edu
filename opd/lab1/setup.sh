#!/usr/bin/bash

# --== Пункт 1 ==--

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

# --== Пункт 3 ==--
