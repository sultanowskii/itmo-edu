%macro cool 1-*
    %assign i 0

    %rep %0
        %if i != 0
            db ", ", %1
        %else 
            db %1
        %endif

        %rotate 1

        %assign i i + 1
    %endrep

    db 0
%endmacro

global _start

section .data

s:
    cool "hello", "another", "world"

section .text

_start:
    mov rdi, 1
    mov rsi, s
    mov rdx, 24
    mov rax, 1
    syscall
    ret