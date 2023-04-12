// READ-ONLY. Don't try to execute.

#include <stdio.h>
#include <inttypes.h>

int32_t X = 0; // 8 bit
int32_t WORKING = 1; // 1 bit
int32_t ACC = 0;

int32_t *get_stack_head() {

}

void print_to_DEV5(char *s) {
    printf(s);
}

void print_number_to_DEV5(uint32_t num) {
    for (int32_t i = 4; i > 0; i--) {
        uint32_t sym = num & 0xf;
        uint32_t offset = 0x30;
        if (sym >= 10)  {
            offset = 0x41;
        }
        putchar(sym + offset);
        num >> 4;
    }
}

struct CMD {
    int32_t code;
    char *name;
    void (*func)();
};

void hlt_func() {
    WORKING = 0;
}

void cla_func() {
    ACC = 0;
}

void ld_func() {
    int32_t tmp = *get_stack_head();
    print_number_to_DEV5(tmp);
    ACC = tmp;
}

void ls_func() {
    uint32_t addr = X;
    uint32_t addr = SXTB(addr);
    uint32_t addr = addr & 0x7ff;

    print_number_to_DEV5(X);
    uint32_t *stack_ptr = X;
    for (int32_t i = 3; i > 0; i--) {
        print_number_to_DEV5(*stack_ptr);
        stack_ptr++;
    }
}

void nop_func() {

}

char HLT_NAME[] = "HLT\n\0";
char CLA_NAME[] = "CLA\n\0";
char LD_NAME[] = "LD\n\0";
char LS_NAME[] = "LS \0";
char NULL_NAME[] = "NULL\n\0";

struct CMD cmds[] = {
    {
        0x10,
        HLT_NAME,
        hlt_func,
    },
    {
        0x20,
        CLA_NAME,
        cla_func,
    },
    {
        0xA0,
        LD_NAME,
        ld_func,
    },
    {
        0xB0,
        LS_NAME,
        ls_func,
    },
    {
        0x00,
        NULL_NAME,
        nop_func,
    },
};

void handle_DEV3() {
    int cmd_code;
    scanf(" %x", &cmd_code);

    print_to_DEV5("> ");

    struct CMD *cmd_ptr = cmds;
    while (cmd_ptr->code != 0x00 || cmd_ptr->code != cmd_code) {
        cmd_ptr++;
    }
    print_to_DEV5(cmd_ptr->name);

    cmd_ptr->func();
}


int main() {
    while (WORKING != 0) {
        X--;
        if (X < -128) {
            X = 127;
        }
    }
    return 0;
}