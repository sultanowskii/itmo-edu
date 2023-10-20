; hello_mmap.asm
%define O_RDONLY 0 
%define PROT_READ 0x1
%define MAP_PRIVATE 0x2

%define SYS_WRITE 1
%define SYS_OPEN 2
%define SYS_CLOSE 3
%define SYS_FSTAT 5
%define SYS_MMAP 9
%define SYS_MUNMAP 11
%define SYS_EXIT 60

%define FD_STDOUT 1

%define PAGE_SIZE 4096
%define STAT_SIZE 144

%define STAT_ST_SIZE_OFFSET 48

section .data
    ; This is the file name. You are free to change it.
    fname: db 'hello.txt', 0

section .text
global _start

; use exit system call to shut down correctly
exit:
    mov  rax, SYS_EXIT
    xor  rdi, rdi
    syscall

; args:
; rdi: fd
;
; locals:
; rsp+0: stat
;
; returns:
; rax = filesize
get_file_size:
    sub rsp, STAT_SIZE

    ; fstat(fd, &stat)
    lea rsi, [rsp+0]
    mov rax, SYS_FSTAT
    syscall

    ; ret_value = stat.st_size
    lea rdi, [rsp+STAT_ST_SIZE_OFFSET]
    mov rax, [rdi]

    add rsp, STAT_SIZE
    ret 

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

; rsp+0: fd
; rsp+8: mmaped file
_start:
    sub rsp, 16

    ; Вызовите open и откройте fname в режиме read only.
    mov  rax, SYS_OPEN
    mov  rdi, fname
    mov  rsi, O_RDONLY    ; Open file read only
    mov  rdx, 0 	      ; We are not creating a file
                          ; so this argument has no meaning
    syscall
    mov [rsp+0], rax
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
    mov [rsp+8], rax

    mov rdi, [rsp+0]
    call get_file_size
    ; rax = file size

    ; с помощью print_string теперь можно вывести его содержимое
    mov rdi, [rsp+8]
    mov rsi, rax
    call print_substring

    ; теперь можно освободить память с помощью munmap
    mov rdi, [rsp+8]
    mov rax, SYS_MUNMAP
    syscall

    ; закрыть файл используя close
    mov rdi, [rsp+0]
    mov rax, SYS_CLOSE
    syscall

    add rsp, 16
    ; и выйти
    call exit
