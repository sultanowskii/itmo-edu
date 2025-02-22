// Deterministic finite automaton.

#include <stdio.h>
#include <stdbool.h>

enum State {
    STATE_S,
    STATE_R,
    STATE_Q,
};

bool check_string(const char *s) {
    enum State state = STATE_S;

    size_t cursor = 0;

    while (s[cursor] != '\0') {
        char sym = s[cursor];

        switch (state) {
            case STATE_S: {
                switch (sym) {
                    case 'a': {
                        cursor++;
                        state = STATE_R;
                        break;
                    }
                    case 'b':
                    case 'c': {
                        cursor++;
                        state = STATE_S;
                        break;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case STATE_R: {
                switch (sym) {
                    case 'a': {
                        cursor++;
                        state = STATE_R;
                        break;
                    }
                    case 'b': {
                        cursor++;
                        state = STATE_S;
                        break;
                    }
                    case 'c': {
                        cursor++;
                        state = STATE_Q;
                        break;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case STATE_Q: {
                switch (sym) {
                    case 'a': {
                        return false;
                    }
                    case 'b':
                    case 'c': {
                        cursor++;
                        state = STATE_S;
                        break;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
        }
    }

    return true;
}

struct Sample {
    const char *s;
    bool expected;
};

struct Sample samples[] = {
    (struct Sample) { .s = "", .expected = true },
    (struct Sample) { .s = "a", .expected = true },
    (struct Sample) { .s = "b", .expected = true },
    (struct Sample) { .s = "c", .expected = true },
    (struct Sample) { .s = "aaa", .expected = true },
    (struct Sample) { .s = "bacbbbaa", .expected = true },
    (struct Sample) { .s = "aca", .expected = false },
    (struct Sample) { .s = "aacaa", .expected = false },
    (struct Sample) { .s = "cacac", .expected = false },
    (struct Sample) { .s = "bbbbbbaca", .expected = false },
    (struct Sample) { .s = "completely unrelated string", .expected = false },
};

const char *fmt_bool(bool b) {
    return b ? "true" : "false";
}

int main() {
    for (size_t i = 0; i < sizeof(samples) / sizeof(samples[0]); i++) {
        bool actual = check_string(samples[i].s);

        if (actual == samples[i].expected) {
            printf("[OK]  '%s': %s\n", samples[i].s, fmt_bool(actual));
        } else {
            printf("[ERR] '%s': %s, expected: %s\n", samples[i].s, fmt_bool(actual), fmt_bool(samples[i].expected));
        }
    }

    return 0;
}
