# Основы сетевых технологий

## OVA to qcow2 (`qemu`)

```bash
$ mv ~/Downloads/CentOS_7_64_2207.ova .
$ tar -xvf CentOS_7_64_2207.ova
CentOS_7_64_2207.ovf
CentOS_7_64_2207-disk001.vmdk
CentOS_7_64_2207.mf
$ qemu-img convert -f vmdk -O qcow2 CentOS_7_64_2207-disk001.vmdk centos7.qcow2
$ ls -lah centos7.qcow2
-rw-r--r-- 1 sultanowskii sultanowskii 1.7G May  9 16:34 centos7.qcow2
```
