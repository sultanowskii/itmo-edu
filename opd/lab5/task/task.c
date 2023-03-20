#include <string.h>
#include <stdio.h>

int main() {
    short str[100];
    short *str_ptr = str;

    memset(str, 0, 100 * sizeof(short));

    short c = 0;
    short to_write;
    short even_container = 0x0100;
    short even;

    while (c != '\n') {
        c = getchar();

        if (even == 0) {
            to_write = c; // if it's odd character, than write it to the lower byte
        } else {
            to_write = c << 8; // if it's an even characeter, than write it to the higher byte
        }
        *str_ptr = *str_ptr | to_write; // 
        str_ptr += even; // since even is 0 -> 1 -> 0 -> 1, so we can add it to the address, so each 2nd iteration we will move pointer

        even = 1 - even; // there is no XOR in bcomp, but I want to swap 0 to 1 and vice versa
    }

    for (int i = 0; i < 100; i++) {
        printf("%c%c", str[i], str[i] >> 8);
    }
    puts("");

    return 0;
}