section .text
 
; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
    push rdi

    mov  rax, rdi
  .counter:
    cmp  byte [rdi], 0
    je   .end
    inc  rdi
    jmp  .counter
  .end:
    sub  rdi, rax
    mov  rax, rdi

    pop rdi
    ret

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    call string_length
    mov  rdx, rax

    mov  rsi, rdi
    mov  rax, 1
    mov  rdi, 1
    syscall
    ret


; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
; rsp+0: num
; rsp+8: i-digit
parse_uint:
    sub rsp, 16

    xor rcx, rcx

    .A:
      ; sym = s[i]
      mov al, [rdi + rcx]

      cmp al, '0'
      jb .B
      cmp al, '9'
      ja .B

      ; digit = sym - '0'
      sub al, '0'
      mov [rsp+8], al

      ; num = num * 10 + digit
      mov rax, [rsp+0]
      imul rax, 10
      add rax, [rsp+8]
      mov [rsp+0], rax

      ; i++
      inc rcx
      jmp .A

    .B:
    mov rax, [rsp+0]
    mov rdx, rcx

    .end:
    add rsp, 16
    ret
