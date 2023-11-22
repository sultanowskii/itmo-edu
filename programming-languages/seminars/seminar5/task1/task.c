#include <stdio.h>

#define print_var(x) printf(#x " is %d", x )

#define coolmacro 789

int main() {
    int coolvar = 123;

    print_var(coolvar);
    print_var(456);
    print_var(coolmacro);
    return 0;
}
