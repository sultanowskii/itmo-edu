/* hello.c */

extern void print_string(const char *arg);
extern void world(void);

int main() {
    print_string("hello");
    world();
    return 0;
}
