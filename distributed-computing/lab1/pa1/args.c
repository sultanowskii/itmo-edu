#include "args.h"

#include <argp.h>
#include <asm-generic/errno-base.h>
#include <errno.h>
#include <stdlib.h>

struct argp_option options[] = {
    {"process-count", 'p', "X", 0, "Number of processes (1-10)", 0 },
    {0},
};

const char  *argp_program_version     = "DC Lab1";
const char  *argp_program_bug_address = "";
char         doc[]                    = "Lab 1";
char         args_doc[]               = "";

struct argp argp = {options, parse_opt, args_doc, doc, 0, 0, 0};

error_t parse_opt(int key, char *arg, struct argp_state *state) {
    struct Args *arguments = state->input;

    switch (key) {
		case 'p': {
            char *end;
            errno = 0;
            long result = strtol(arg, &end, 10);
            if (errno == EINVAL) {
                argp_error(state, "process count is required");
                return EINVAL;
            } else if (errno == ERANGE) {
                argp_error(state, "process count is out of range");
                return ERANGE;
            }
            if (result < 1 || result > 10) {
                argp_error(state, "process count must be in range [1; 10]");
                return ERANGE;
            }
            arguments->process_count = result;
            break;
        }
        case ARGP_KEY_END: {
            if (arguments->process_count == 0) {
                argp_error(state, "process count (-p) is required");
                return ARGP_ERR_UNKNOWN;
            }
        }
		default: return ARGP_ERR_UNKNOWN;
    }   
    return 0;
}
