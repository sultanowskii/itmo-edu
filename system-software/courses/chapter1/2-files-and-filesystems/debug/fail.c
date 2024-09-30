#include <stdio.h>

int *f() {
    return (int *)12;
}

void print(int *v) {
    printf("value = %d\n", *v);
}

int main() {
    print(f());
    return 0;
}
