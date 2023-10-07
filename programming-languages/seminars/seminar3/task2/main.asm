%include "lib.inc"

global _start

section .text

_start:
    mov rdi, 0x1234
    call print_hex

    xor rdi, rdi
    call exit
