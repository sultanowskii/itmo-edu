; hello_mmap.asm
%define O_RDONLY 0 
%define PROT_READ 0x1
%define MAP_PRIVATE 0x2

%define SYS_WRITE  1
%define SYS_OPEN   2
%define SYS_CLOSE  3
%define SYS_MMAP   9
%define SYS_MUNMAP 11
%define SYS_EXIT   60

%define FD_STDOUT 1

%define PAGE_SIZE     4096

global print_file
global print_string

section .text

; use exit system call to shut down correctly
exit:
    mov  rax, SYS_EXIT
    xor  rdi, rdi
    syscall

; These functions are used to print a null terminated string
; rdi holds a string pointer
print_string:
    push rdi
    call string_length
    pop  rsi
    mov  rdx, rax 
    mov  rax, SYS_WRITE
    mov  rdi, FD_STDOUT
    syscall
    ret

string_length:
    xor  rax, rax
.loop:
    cmp  byte [rdi+rax], 0
    je   .end 
    inc  rax
    jmp .loop 
.end:
    ret

; This function is used to print a substring with given length
; rdi holds a string pointer
; rsi holds a substring length
print_substring:
    mov  rdx, rsi 
    mov  rsi, rdi
    mov  rax, SYS_WRITE
    mov  rdi, FD_STDOUT
    syscall
    ret

; args:
; rdi = filename
;
; locals:
; rsp+0: filename
; rsp+8: fd
; rsp+16: mmaped file
print_file:
    sub rsp, 24
    mov [rsp+0], rdi

    ; Вызовите open и откройте fname в режиме read only.
    mov  rax, SYS_OPEN
    mov  rdi, [rsp+0]
    mov  rsi, O_RDONLY    ; Open file read only
    mov  rdx, 0 	      ; We are not creating a file
                          ; so this argument has no meaning
    syscall
    mov [rsp+8], rax
    ; rax holds the opened file descriptor now

    ; Вызовите mmap c правильными аргументами
    ; Дайте операционной системе самой выбрать, куда отобразить файл
    ; Размер области возьмите в размер страницы 
    ; Область не должна быть общей для нескольких процессов 
    ; и должна выделяться только для чтения.
    xor edi, edi
    mov rsi, PAGE_SIZE
    mov rdx, PROT_READ
    mov r10, MAP_PRIVATE
    mov r8, rax
    mov r9, 0
    mov rax, SYS_MMAP
    syscall
    mov [rsp+16], rax

    ; с помощью print_string теперь можно вывести его содержимое
    mov rdi, [rsp+16]
    call print_string

    ; теперь можно освободить память с помощью munmap
    mov rdi, [rsp+16]
    mov rax, SYS_MUNMAP
    syscall

    ; закрыть файл используя close
    mov rdi, [rsp+8]
    mov rax, SYS_CLOSE
    syscall

    add rsp, 24
    ret
