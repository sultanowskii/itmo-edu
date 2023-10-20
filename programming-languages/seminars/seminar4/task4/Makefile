ASM=nasm
LD=ld

BIN=task.elf

.PHONY: all
all: build

%.o: %.asm
	$(ASM) -felf64 -o $@ $<

%.o: %.c
	$(CC) -o $@ -c $<

.PHONY: build
build: lib.o hello.o
	$(CC) -o $(BIN) $^

.PHONY: clean
clean:
	rm -f *.o $(BIN)
