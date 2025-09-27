#ifndef __ARGS_H
#define __ARGS_H

#include <argp.h>

extern struct argp argp;
error_t parse_opt(int key, char *arg, struct argp_state *state);

struct Args {
    int process_count;
};

#endif // __ARGS_H
