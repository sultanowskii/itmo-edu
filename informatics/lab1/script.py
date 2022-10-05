import sys

BASIC_ALPHABET = [str(i) for i in range(10)] + [chr(i) for i in range(ord('a'), ord('z') + 1)]
MAX_SYSTEM = len(BASIC_ALPHABET)
FIBONACCI_SYSTEM_MARK = -1


def fibonacci(n):
    if n == 1 or n == 2:
        return 1
    return fibonacci(n - 1) + fibonacci(n - 2)


def check_fibbonaci_number(n) -> bool:
    for c in n:
        if c not in ['0', '1']:
            return False

    if '11' in n:
        return False

    return True


def check_number(n: str, system: int) -> bool:
    if system == FIBONACCI_SYSTEM_MARK:
        return check_fibbonaci_number(n)

    for c in n:
        if c not in BASIC_ALPHABET:
            return False
    return True


def check_system(system: int) -> bool:
    if 2 <= system <= MAX_SYSTEM:
        return True
    if system == 1:
        return True
    if system == FIBONACCI_SYSTEM_MARK:
        return True
    return False


def transform_system_1_to_10(n: str) -> int:
    return n.count('1')


def transform_number_system_to_10(n: str, system: int) -> int:
    result = 0
    reversed_n = n[::-1]
    for i in range(len(reversed_n)):
        result += BASIC_ALPHABET.index(reversed_n[i]) * (system ** i)
    return result


def transform_system_fibonacci_to_10(n: str) -> int:
    reversed_n = n[::-1]
    result = 0
    for i, c in enumerate(reversed_n):
        if c == '1':
            result += fibonacci(i + 2)
    return result


def transform_number_system_from_10(n: int, new_system: int) -> str:
    result = ''
    while n > 0:
        result = BASIC_ALPHABET[n % new_system] + result
        n //= new_system
    return result


def transform_system_1_from_10(n: int) -> str:
    return '1' * n


def _transform_system_fibonacci_from_10(n: int, guess: str) -> list:
    guess_10_base = transform_system_fibonacci_to_10(guess)
    if guess_10_base > n or transform_system_fibonacci_to_10('1' + '0' * (len(guess) - 1)) > n:
        return []
    if guess_10_base == n:
        return [guess]

    q = []

    for symbol in ['0', '1']:
        q.extend(_transform_system_fibonacci_from_10(n, symbol + guess))

    return q


def transform_system_fibonacci_from_10(n: int) -> str:
    if n == 0:
        return 0

    for result in _transform_system_fibonacci_from_10(n, ''):
        if check_fibbonaci_number(result):
            return result


def transform_number_system(n: str, system: int, new_system: int) -> str:
    if system == 1:
        tmp = transform_system_1_to_10(n)
    elif system == FIBONACCI_SYSTEM_MARK:
        tmp = transform_system_fibonacci_to_10(n)
    else:
        tmp = transform_number_system_to_10(n, system)
    
    if new_system == 1:
        return transform_system_1_from_10(tmp)
    elif new_system == FIBONACCI_SYSTEM_MARK:
        return transform_system_fibonacci_from_10(tmp)

    return transform_number_system_from_10(tmp, new_system)


def main() -> None:
    sys.setrecursionlimit(1500)
    system = int(input(f'Number system [1-{MAX_SYSTEM}] (enter {FIBONACCI_SYSTEM_MARK} for fibonacci): '))
    if not check_system(system):
        print('Invalid system given')
        return

    new_system = int(input(f'New number system [1-{MAX_SYSTEM}] (enter {FIBONACCI_SYSTEM_MARK} for fibonacci): '))
    if not check_system(new_system):
        print('Invalid new system given')
        return

    num = input('Number: ').lower()
    if not check_number(num, system):
        print('Invalid number given.')
        return

    result = transform_number_system(num, system, new_system)
    print(f'Result: {result}')


if __name__ == '__main__':
    main()
