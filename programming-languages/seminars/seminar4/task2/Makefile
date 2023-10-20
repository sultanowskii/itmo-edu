ASM=nasm
LD=ld

BIN=task.elf

.PHONY: all
all: build

%.o: %.asm
	$(ASM) -felf64 -o $@ $<

.PHONY: build
build: task.o
	$(LD) -o $(BIN) $^

.PHONY: clean
clean:
	rm -f *.o $(BIN)
