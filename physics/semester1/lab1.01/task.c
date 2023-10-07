#include <stdio.h>

#define ull unsigned long long

ull divisor_cntr(ull n) {
    if (n == 1) {
        return 1; 
    }

    ull cntr = 2;

    for (ull i = 2; i * i <= n + 1; i++) {
        if (n % i == 0) {
            if (n / i == i) {
                cntr++;
            } else {
                cntr += 2;
            }
        }
    }

    return cntr;
}

int main() {
    for (ull i = 1; i < 3e6; i++) {
        int divisors = divisor_cntr(i);
        printf("%llu %llu\n", i, divisors);
    }

    return 0;
}
