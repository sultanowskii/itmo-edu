# SVN

Пересоздать репу (локальный прикол, не пытайтесь запустить - делал, т.к. пользовался локальной инсталляцией svn-сервера)
```bash
sudo ./reset-svn.sh
```

Склонировать
```bash
svn checkout svn://0.0.0.0:3690/lab2-svn
```

Коммит + push
```bash
svn commit -m "MESSAGE" --username USERNAME --password PASSWORD
```
