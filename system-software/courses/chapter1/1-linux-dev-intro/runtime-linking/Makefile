TARGET := solution

.PHONY: all
all: build-exe

.PHONY:
clean:
	rm *.so $(TARGET)

.PHONY: build-lib
build-lib: shared.c
	gcc -shared -fPIC -o libShared.so $<

.PHONY: build-exe
build-exe: solution.c
	gcc $< -fPIC -ldl -o $(TARGET)
