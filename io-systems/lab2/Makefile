MOD         = lab2
obj-m       += lab2.o
lab2-objs   += src/module.o

PWD         := $(CURDIR)
KERNEL_DIR  := /usr/lib/modules/$(shell uname -r)/build

all:
	make -C $(KERNEL_DIR) M=$(PWD) modules

clean:
	make -C $(KERNEL_DIR) M=$(PWD) clean

load:
	sudo insmod $(MOD).ko;

unload:
	sudo rmmod $(MOD).ko;
