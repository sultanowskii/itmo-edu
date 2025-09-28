#ifndef __MSG_H
#define __MSG_H

#include "ipc.h"

Message build_msg(MessageType type, const char *fmt, ...);

#endif // __MSG_H
