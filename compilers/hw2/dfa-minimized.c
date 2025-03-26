// Deterministic finite automaton (minimized).

#include <stdio.h>
#include <stdbool.h>

enum State {
    STATE_1,
    STATE_2,
    STATE_3,
    STATE_4,
    STATE_5,
};

bool is_state_accepting(enum State state) {
    return state == STATE_5;
}

bool check_string(const char *s) {
    enum State state = STATE_1;

    size_t cursor = 0;

    while (s[cursor] != '\0') {
        char sym = s[cursor];

        switch (state) {
            case STATE_1: {
                switch (sym) {
                    case 'a': {
                        cursor++;
                        state = STATE_2;
                        break;
                    }
                    case 'b':
                    case 'c': {
                        return false;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case STATE_2: {
                switch (sym) {
                    case 'a': {
                        cursor++;
                        state = STATE_2;
                        break;
                    }
                    case 'b':{
                        cursor++;
                        state = STATE_3;
                        break;
                    }
                    case 'c': {
                        return false;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case STATE_3: {
                switch (sym) {
                    case 'a': {
                        cursor++;
                        state = STATE_2;
                        break;
                    }
                    case 'b':{
                        cursor++;
                        state = STATE_3;
                        break;
                    }
                    case 'c': {
                        cursor++;
                        state = STATE_5;
                        break;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case STATE_4: {
                switch (sym) {
                    case 'a': {
                        return false;
                    }
                    case 'b':{
                        cursor++;
                        state = STATE_4;
                        break;
                    }
                    case 'c': {
                        cursor++;
                        state = STATE_5;
                        break;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case STATE_5: {
                switch (sym) {
                    case 'a': {
                        return false;
                    }
                    case 'b':{
                        cursor++;
                        state = STATE_4;
                        break;
                    }
                    case 'c': {
                        cursor++;
                        state = STATE_5;
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

    return is_state_accepting(state);
}

struct Sample {
    const char *s;
    bool expected;
};

struct Sample samples[] = {
    (struct Sample) { .s = "", .expected = false },
    (struct Sample) { .s = "a", .expected = false },
    (struct Sample) { .s = "b", .expected = false },
    (struct Sample) { .s = "c", .expected = false },
    (struct Sample) { .s = "ab", .expected = false },
    (struct Sample) { .s = "bc", .expected = false },
    (struct Sample) { .s = "ac", .expected = false },
    (struct Sample) { .s = "acbc", .expected = false },
    (struct Sample) { .s = "abcb", .expected = false },
    (struct Sample) { .s = "babc", .expected = false },

    (struct Sample) { .s = "abc", .expected = true },
    (struct Sample) { .s = "aabc", .expected = true },
    (struct Sample) { .s = "abbc", .expected = true },
    (struct Sample) { .s = "abcc", .expected = true },
    (struct Sample) { .s = "aabbcc", .expected = true },
    (struct Sample) { .s = "abababcbcbc", .expected = true },
    (struct Sample) { .s = "aaaaabbbbbccccc", .expected = true },
    (struct Sample) { .s = "abababcbcbcbcbc", .expected = true },
    (struct Sample) { .s = "aaabbbaaabbbcccbbbccc", .expected = true },
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