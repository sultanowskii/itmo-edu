all: build

lib.o: lib.asm
	nasm -felf64 -o lib.o lib.asm

main.o: main.asm
	nasm -felf64 -o main.o main.asm

build: lib.o main.o
	ld -o task.elf lib.o main.o

clean:
	rm -f *.o task.elf
