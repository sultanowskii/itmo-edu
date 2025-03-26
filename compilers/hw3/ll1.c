// LL(1) analyzer

#include <stddef.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <stdio.h>
#include <stdarg.h>

#define ERR_NO_REASON ""
#define ERR_EXPECTED(c) "'" #c "' is expected"
#define ERR_EOF_OR_EXPECTED(c) "end of text or '" #c "' is expected"

#define STACK_SIZE 1024

void panic(const char *message) {
    puts(message);
    exit(1);
}

enum RuleType {
    VALID,
    ERROR,
};

struct Rule {
    // Terminating symbol
    char t;
    // Rule type
    enum RuleType type;
    union {
        // Right path of the rule
        char *right;
        // Error message
        char *error;
    };
};

struct Row {
    // Non-Terminating symbol
    char nt;
    // Rules
    struct Rule *rules[6];
};

struct Row TABLE[] = {
    {
        .nt = 'S',
        .rules = {
            &(struct Rule){'a', VALID, .right = "aF"},
            &(struct Rule){'b', ERROR, .error = ERR_EXPECTED(a)},
            &(struct Rule){'c', ERROR, .error = ERR_EXPECTED(a)},
            &(struct Rule){'d', ERROR, .error = ERR_EXPECTED(a)},
            &(struct Rule){EOF, ERROR, .error = ERR_EXPECTED(a)},
            NULL,
        },
    },
    {
        .nt = 'F',
        .rules = {
            &(struct Rule){'a', VALID, .right = "aC"},
            &(struct Rule){'b', VALID, .right = "bA"},
            &(struct Rule){'c', VALID, .right = "cBA"},
            &(struct Rule){'d', ERROR, .error = ERR_NO_REASON},
            &(struct Rule){EOF, ERROR, .error = ERR_NO_REASON},
            NULL,
        }
    },
    {
        .nt = 'A',
        .rules = {
            &(struct Rule){'a', ERROR, .error = ERR_EXPECTED(d)},
            &(struct Rule){'b', ERROR, .error = ERR_EXPECTED(d)},
            &(struct Rule){'c', ERROR, .error = ERR_EXPECTED(d)},
            &(struct Rule){'d', VALID, .right = "dD"},
            &(struct Rule){EOF, ERROR, .error = ERR_EXPECTED(d)},
            NULL,
        }
    },
    {
        .nt = 'D',
        .rules = {
            &(struct Rule){'a', VALID, .right = "aD"},
            &(struct Rule){'b', VALID, .right = "bD"},
            &(struct Rule){'c', ERROR, .error = ERR_NO_REASON},
            &(struct Rule){'d', VALID, .right = "AaD"},
            &(struct Rule){EOF, VALID, .right = ""},
            NULL,
        }
    },
    {
        .nt = 'B',
        .rules = {
            &(struct Rule){'a', ERROR, .error = ERR_EXPECTED(b)},
            &(struct Rule){'b', VALID, .right = "bG"},
            &(struct Rule){'c', ERROR, .error = ERR_EXPECTED(b)},
            &(struct Rule){'d', ERROR, .error = ERR_EXPECTED(b)},
            &(struct Rule){EOF, ERROR, .error = ERR_EXPECTED(b)},
            NULL,
        }
    },
    {
        .nt = 'G',
        .rules = {
            &(struct Rule){'a', ERROR, .error = ERR_NO_REASON},
            &(struct Rule){'b', VALID, .right = "bH"},
            &(struct Rule){'c', VALID, .right = "cB"},
            &(struct Rule){'d', ERROR, .error = ERR_NO_REASON},
            &(struct Rule){EOF, ERROR, .error = ERR_NO_REASON},
            NULL,
        }
    },
    {
        .nt = 'H',
        .rules = {
            &(struct Rule){'a', ERROR, .error = ERR_EOF_OR_EXPECTED(b)},
            &(struct Rule){'b', VALID, .right = "BB"},
            &(struct Rule){'c', ERROR, .error = ERR_EOF_OR_EXPECTED(b)},
            &(struct Rule){'d', ERROR, .error = ERR_EOF_OR_EXPECTED(b)},
            &(struct Rule){EOF, VALID, .right = ""},
            NULL,
        },
    },
    {
        .nt = 'C',
        .rules = {
            &(struct Rule){'a', ERROR, .error = ERR_EXPECTED(с)},
            &(struct Rule){'b', ERROR, .error = ERR_EXPECTED(с)},
            &(struct Rule){'c', VALID, .right = "cE"},
            &(struct Rule){'d', ERROR, .error = ERR_EXPECTED(d)},
            &(struct Rule){EOF, ERROR, .error = ERR_EXPECTED(d)},
            NULL,
        },
    },
    {
        .nt = 'E',
        .rules = {
            &(struct Rule){'a', ERROR, .error = ERR_EOF_OR_EXPECTED(c)},
            &(struct Rule){'b', ERROR, .error = ERR_EOF_OR_EXPECTED(c)},
            &(struct Rule){'c', VALID, .right = "cE"},
            &(struct Rule){'d', ERROR, .error = ERR_EOF_OR_EXPECTED(c)},
            &(struct Rule){EOF, VALID, .right = ""},
            NULL,
        },
    },
};

bool is_non_terminal(char c) {
    return 'A' <= c && c <= 'Z';
}

bool is_terminal(char c) {
    return !is_non_terminal(c);
}

char stack[STACK_SIZE];
size_t stack_index = 0;

void stack_push(char c) {
    if (stack_index == STACK_SIZE) {
        panic("LL(1) stack upper limit reached");
    }
    stack[stack_index] = c;
    stack_index++;
}

void stack_push_str(const char *s) {
    for (size_t i = strlen(s); i > 0; i--) {
        stack_push(s[i - 1]);
    }
}

char stack_pop() {
    if (stack_index == 0) {
        panic("LL(1) stack lower limit reached");
    }
    char c = stack[stack_index];
    stack_index--;
    return c;
}

void stack_clear() {
    stack_index = 0;
}

char stack_top() {
    return stack[stack_index - 1];
}

bool stack_is_empty() {
    return stack_index == 0;
}

void stack_print() {
    printf("stack (size=%zu)\n", stack_index);
    printf("|");
    for (size_t i = 0; i < stack_index; i++) {
        printf("%c|", stack[i]);
    }
    puts("");
}

struct Rule *find_rule_in_table(char non_terminal, char terminal) {
    if (terminal == '\0') {
        terminal = EOF;
    }

    for (size_t i = 0; i < sizeof(TABLE) / sizeof(TABLE[0]); i++) {
        struct Row row = TABLE[i];
        if (row.nt != non_terminal) {
            continue;
        }

        size_t j = 0;
        while (row.rules[j] != NULL) {
            if (row.rules[j]->t == terminal) {
                return row.rules[j];
            }
            j++;
        }
    }
    return NULL;
}

char *fmt(const char *format, ...) {
    va_list args;
    va_start(args, format);
    size_t size = vsnprintf(NULL, 0, format, args);
    va_end(args);

    va_start(args, format);
    char *s = malloc(size + 1);
    vsprintf(s, format, args);
    va_end(args);

    return s;
}

char *get_separator(const char *message) {
    if (strlen(message) == 0) {
        return "";
    }
    return ": ";
}

char *fmt_err_unexpected(size_t pos, char c, const char *message) {
    const char *separator = get_separator(message);
    if (c == '\0') {
        return fmt("at %zu: unexpected end of text%s%s", pos, separator, message);
    }
    return fmt("at %zu: unexpected symbol '%c'%s%s", pos, c, separator, message);
}

char *fmt_err_c_is_expected(size_t pos, char expected, char actual) {
    char *sub_message = fmt("'%c' is expected");
    char *result = fmt_err_unexpected(pos, expected, sub_message);
    free(sub_message);
    return result;
}

// analyze_string returns NULL if s is a valid text.
// Returns an error message otherwise. You shall free() it.
char *analyze_string(const char *s) {
    stack_clear();
    stack_push('S');

    size_t length = strlen(s);
    size_t head = 0;

    while (head <= length && !stack_is_empty()) {
        char in = s[head];
        char c = stack_top();

        if (is_terminal(c)) {
            if (c == in) {
                stack_pop();
                head++;
            } else {
                return fmt_err_c_is_expected(head, c, in);
            }
        } else {
            struct Rule *rule = find_rule_in_table(c, in);
            if (rule == NULL) {
                return fmt_err_unexpected(head, in, "");
            }
            switch (rule->type) {
                case VALID: {
                    stack_pop();
                    stack_push_str(rule->right);
                    break;
                }
                case ERROR: {
                    struct Rule *end_rule = find_rule_in_table(c, EOF);
                    if (end_rule == NULL) {
                        return fmt_err_unexpected(head, in, "");
                    }
                    if (end_rule->type == VALID) {
                        stack_pop();
                        break;
                    }
                    return fmt_err_unexpected(head, in, rule->error);
                }
            }
        }
    }

    if (stack_is_empty()) {
        return NULL;
    }

    char c = stack_top();
    if (is_terminal(c)) {
        return fmt("at %zu: unexpected end of text: '%c' is expected", head, c);
    } else {
        return fmt("at %zu: unexpected end of text", head);
    }
}

struct Sample {
    const char *s;
    struct {
        enum RuleType type;
        char *error;
    } expected;
};

struct Sample samples[] = {
    (struct Sample) { .s = "", .expected = { .type = ERROR, .error = "at 0: unexpected end of text: 'a' is expected" } },
    (struct Sample) { .s = "qqq", .expected = { .type = ERROR, .error = "at 0: unexpected symbol 'q'" } },
    (struct Sample) { .s = "a", .expected = { .type = ERROR, .error = "at 1: unexpected end of text" } },
    (struct Sample) { .s = "b", .expected = { .type = ERROR, .error = "at 0: unexpected symbol 'b': 'a' is expected" } },
    (struct Sample) { .s = "c", .expected = { .type = ERROR, .error = "at 0: unexpected symbol 'c': 'a' is expected" } },
    (struct Sample) { .s = "d", .expected = { .type = ERROR, .error = "at 0: unexpected symbol 'd': 'a' is expected" } },
    (struct Sample) { .s = "abda", .expected = { .type = VALID } },
    (struct Sample) { .s = "acbbd", .expected = { .type = VALID } },
    (struct Sample) { .s = "aacccccccccc", .expected = { .type = VALID } },
};

void test() {
    for (size_t i = 0; i < sizeof(samples) / sizeof(samples[0]); i++) {
        char *err = analyze_string(samples[i].s);

        switch (samples[i].expected.type) {
            case VALID: {
                if (err != NULL) {
                    printf("[ERR] '%s': expected success, got error ('%s')\n", samples[i].s, err);
                } else {
                    printf("[OK]  '%s': success\n", samples[i].s, samples[i].expected.error);
                }
                break;
            };
            case ERROR: {
                if (err == NULL) {
                    printf("[ERR] '%s': expected error('%s'), got success\n", samples[i].s, samples[i].expected.error);
                } else if (strcmp(err, samples[i].expected.error) != 0) {
                    printf("[ERR] '%s': expected error('%s'), got error('%s')\n", samples[i].s, samples[i].expected.error, err);
                } else {
                    printf("[OK]  '%s': got expected error('%s')\n", samples[i].s, samples[i].expected.error);
                }
                break;
            };
        }

        free(err);
    }
}

void showcase() {
    char str[4096];
    scanf("%4096s", str);


    char *err = analyze_string(str);
    if (err == NULL) {
        puts("OK");
    } else {
        printf("ERR: %s\n", err);
    }
    free(err);
}

int main() {
    test();
    // showcase();

    return 0;
}


