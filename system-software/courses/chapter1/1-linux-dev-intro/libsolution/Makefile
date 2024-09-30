.PHONY: all
all: build-lib

.PHONY:
clean:
	rm *.o *so *elf

.PHONY: build-lib
build-lib: solution.o
	gcc -shared -fPIC -o libsolution.so $<

.PHONY: build-exe
build-exe: main.o
	gcc -L. -lsolution -o solution.elf $< 

%.o: %.c
	gcc -c -fPIC -o $@ $<
