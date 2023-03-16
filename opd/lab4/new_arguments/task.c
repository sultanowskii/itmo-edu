#include <inttypes.h>
#include <stdio.h>

int f(int arg) {
    int16_t A = (int16_t)0xFF11;
    int16_t B = 0x008C;
    int16_t acc;

    acc = arg;

    // arg <= 0 -> ...
    if (acc > 0) {
        return A;
    }

    // A < arg <= 0 -> ...
    if (acc <= A) {
        return A;
    }

    // A < x <= 0   -> (x * 3) + 140 (B)
    // x <= A       -> -239 (A)
    // x > 0        -> -239 (A)

    // equivalent to acc * 3
    acc <<= 1;
    acc += arg;

    acc += B;

    return acc;
}

int main() {
    int16_t z = (int16_t)500;
    int16_t y = (int16_t)-9;
    int16_t x = (int16_t)-9000;
    int16_t r = 0x8D;
    int16_t acc;

    r = 0;
    acc = f(y - 1);
    r = acc + r;
    // f(y - 1)
    // r = f(y - 1)

    acc = f(z);
    r = (acc - 1) - r;
    // f(z) - 1 - r
    // r = f(z) - 1 - f(y - 1)

    acc = f(x);
    r = (acc - 1) + r;
    // f(x) - 1 + r
    // r = f(x) - 1 + f(z) - 1 - f(y - 1)

    // r = f(x) + f(z) - f(y - 1) - 2

    printf("%" PRId16 "\n", r);

    return 0;
}