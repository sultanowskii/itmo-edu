#include "msg.h"
#include "ipc.h"

#include <stdarg.h>
#include <stdio.h>
#include <string.h>

Message build_msg(MessageType type, const char *fmt, ...) {
    va_list args;
    va_start(args, fmt);

    Message msg = {
        .s_header = {
            .s_magic = MESSAGE_MAGIC,
            .s_type = type,
        },
    };

    size_t length = vsnprintf(msg.s_payload, MAX_PAYLOAD_LEN, fmt, args);

    msg.s_header.s_payload_len = length;

    va_end(args);
    return msg;
}
