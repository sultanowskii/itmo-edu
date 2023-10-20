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
build: string.o hello.o
	$(CC) -o $(BIN) -no-pie $^

.PHONY: build
build-pie: string_pie.o hello.o
	$(CC) -o $(BIN) $^

.PHONY: clean
clean:
	rm -f *.o $(BIN)
