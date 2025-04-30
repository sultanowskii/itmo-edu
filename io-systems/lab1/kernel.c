#include "kernel.h"

typedef unsigned char uint8_t;
typedef unsigned int uint32_t;
typedef uint32_t size_t;

extern char __bss[], __bss_end[], __stack_top[];

void *memset(void *buf, char c, size_t n) {
    uint8_t *p = (uint8_t *) buf;
    while (n--)
        *p++ = c;
    return buf;
}

struct sbiret sbi_call(long arg0, long arg1, long arg2, long arg3, long arg4,
                       long arg5, long fid, long eid) {
    register long a0 __asm__("a0") = arg0;
    register long a1 __asm__("a1") = arg1;
    register long a2 __asm__("a2") = arg2;
    register long a3 __asm__("a3") = arg3;
    register long a4 __asm__("a4") = arg4;
    register long a5 __asm__("a5") = arg5;
    register long a6 __asm__("a6") = fid;
    register long a7 __asm__("a7") = eid;

    __asm__ __volatile__("ecall"
                         : "=r"(a0), "=r"(a1)
                         : "r"(a0), "r"(a1), "r"(a2), "r"(a3), "r"(a4), "r"(a5),
                           "r"(a6), "r"(a7)
                         : "memory");
    return (struct sbiret){.error = a0, .value = a1};
}

void putchar(char ch) {
    sbi_call(ch, 0, 0, 0, 0, 0, 0, 1 /* Console Putchar */);
}

long getchar(void) {
    struct sbiret ret = { .error = -1 };
    while (ret.error < 0) {
        ret = sbi_call(0, 0, 0, 0, 0, 0, 0, 2);
    }
    return ret.error;
}

struct SpecVersion {
    long major;
    long minor;
};

struct SpecVersion get_spec_version(void) {
    struct sbiret ret = sbi_call(0, 0, 0, 0, 0, 0, 0, 0x10 /* Base extension */);

    return (struct SpecVersion) {
        .major = (ret.value >> 24) & (0b1111111),
        .minor = (ret.value) & (0b111111111111111111111111),
    };
}

long get_number_of_counters(void) {
    struct sbiret ret = sbi_call(0, 0, 0, 0, 0, 0, 0, 0x504D55 /* PMU */);
    return ret.value;
}

enum CounterType {
    COUNTER_TYPE_HARDWARE = 0,
    COUNTER_TYPE_FIRMWARE = 1,
};

const long SBI_SUCCESS = 0;
const long SBI_ERR_INVALID_PARAM = -3;

struct CounterDetails {
    long status;
    long csr;
    long width;
    enum CounterType type;
};

struct CounterDetails get_details_of_a_counter(unsigned long id) {
    struct sbiret ret = sbi_call(id, 0, 0, 0, 0, 0, 1, 0x504D55 /* PMU */);
    return (struct CounterDetails) {
        .status = ret.error,
        .type = (ret.value >> 31) & 1,
        .width = (ret.value >> 12) & 0b111111,
        .csr = ret.value & 0b111111111111,
    };
}

void system_shutdown(void) {
    sbi_call(0, 0, 0, 0, 0, 0, 0, 0x08 /* System shutdown */);
}

// ----------------------

void print(const char *s) {
    for (int i = 0; s[i] != '\0'; i++) {
        putchar(s[i]);
    }
}

void print_long(long n) {
    unsigned long abs_n = n;

    if (n < 0) {
        putchar('-');
        abs_n = -abs_n;
    }

    unsigned divisor = 1;
    while (abs_n / divisor > 9) {
        divisor *= 10;
    }

    while (divisor > 0) {
        putchar('0' + abs_n / divisor);
        abs_n %= divisor;
        divisor /= 10;
    }
}

long read_unsigned_long() {
    long number = 0;
    while (1) {
        char c = getchar();
        putchar(c);

        if (c < '0' || '9' < c) {
            putchar('\n');
            break;
        }

        long n = c - '0';

        number *= 10;
        number += n;
    }

    return number;
}

int input(char *buf, int n) {
    int length = 0;
    for (int i = 0; i < n; i++) {
        char ch = getchar();
        putchar(ch);

        if (ch == '\r') {
            putchar('\n');
            buf[i] = '\0';
            break;
        } else {
            buf[i] = ch;
            length++;
        }
    }
    return length;
}

void kernel_main(void) {
    const int MAX_BUF_SIZE = 1024;
    char buf[MAX_BUF_SIZE];

    while (1) {
        print("1. Get SBI specification version\n");
        print("2. Get number of counters\n");
        print("3. Get details of a counter\n");
        print("4. System Shutdown\n");
        print("> ");

        char cmd = getchar();
        putchar(cmd);
        putchar('\n');

        switch (cmd) {
        case '1': {
            struct SpecVersion v = get_spec_version();
            print("Major: ");
            print_long(v.major);
            print(", Minor: ");
            print_long(v.minor);
            putchar('\n');
            break;
        }
        case '2': {
            long n = get_number_of_counters();
            print("Number of counters: ");
            print_long(n);
            putchar('\n');
            break;
        }
        case '3': {
            print("Enter ID: ");
            long id = read_unsigned_long();
            struct CounterDetails details = get_details_of_a_counter(id);
            if (details.status == SBI_ERR_INVALID_PARAM) {
                print("Invalid ID provided\n");
                break;
            }

            switch (details.type) {
            case COUNTER_TYPE_HARDWARE: {
                print("Type: hardware, Width: ");
                print_long(details.width);
                print(", CSR: ");
                print_long(details.csr);
                print("\n");
                break;
            }
            case COUNTER_TYPE_FIRMWARE: {
                print("Type: firmware\n");
                break;
            }
            }
            break;
        }
        case '4': {
            print("Shutting down...\n");
            system_shutdown();
            break;
        }
        default: {
            print("Unknown button pressed\n");
            break;
        }
        }
    }

    // WFI: Provides a hint to the implementation that the current hart can be stalled until an interrupt might need servicing.
    for (;;) {
        __asm__ __volatile__("wfi");
    }
}

__attribute__((section(".text.boot")))
__attribute__((naked))
void boot(void) {
    __asm__ __volatile__(
        "mv sp, %[stack_top]\n" // Устанавливаем указатель стека
        "j kernel_main\n"       // Переходим к функции main ядра
        :
        : [stack_top] "r" (__stack_top) // Передаём верхний адрес стека в виде %[stack_top]
    );
}
