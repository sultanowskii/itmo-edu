import yaml
from typing import Any

from error import print_error_and_exit
from gen import gen_a, gen_b, gen_valid_a
from matrix import MIN_N, MAX_N, Matrix1D, Matrix2D
from parse import parse_int, parse_float, parse_float_row


def _is_float_or_int(n: Any) -> bool:
    """Проверяет, является ли n float или int."""
    return isinstance(n, int) or isinstance(n, float)


def read_input_from_file(filename: str) -> tuple[int, Matrix2D, Matrix1D, float]:
    """Парсит входные данные из файла."""
    N_KEY = 'N'
    ACCURACY_KEY = 'accuracy'
    A_KEY = 'a'
    B_KEY = 'b'
    REQUIRED_KEYS = (N_KEY, ACCURACY_KEY, A_KEY, B_KEY)

    data = None

    try:
        with open(filename, 'r') as f:
            data: dict = yaml.safe_load(f)
    except OSError as e:
        print_error_and_exit(f'Невозможно отрыть файл. Ошибка:\n{e}')
    except Exception as e:
        print_error_and_exit(f'Файл-данные должен быть валидным YAML. Ошибка:\n{e}')

    for required_key in REQUIRED_KEYS:
        if not data.get(required_key):
            print_error_and_exit(f'Пожалуйста укажите {required_key} в файле с входными данными')

    n = data[N_KEY]
    if not isinstance(n, int):
        print_error_and_exit(f'{N_KEY} должен быть валидным целым положительным числом')
    
    if n > MAX_N or n < MIN_N:
        print_error_and_exit(f'{N_KEY} должен быть в диапазоне [{MIN_N}; {MAX_N}]')

    accuracy = data[ACCURACY_KEY]
    if not _is_float_or_int(accuracy):
        print_error_and_exit(f'{ACCURACY_KEY} должен быть валидным числом')

    a = data[A_KEY]
    if not isinstance(a, list):
        print_error_and_exit(f'{A_KEY} должна быть валидной матрицей')
    
    if len(a) != n:
        print_error_and_exit(f'{A_KEY} должна быть валидной матрицей {n}x{n}')
    
    for row in a:
        if len(row) != n:
            print_error_and_exit(f'{A_KEY} должна быть валидной матрицей {n}x{n}')
        for elem in row:
            if not _is_float_or_int(elem):
                print_error_and_exit(f'{A_KEY} должна быть валидной матрицей {n}x{n} и состоять из чисел')

    b = data[B_KEY]
    if not isinstance(b, list):
        print_error_and_exit(f'{B_KEY} должен быть валидной матрицей')
    
    if len(b) != n:
        print_error_and_exit(f'{B_KEY} должен быть валидной матрицей 1x{n}')

    for elem in b:
        if not _is_float_or_int(elem):
            print_error_and_exit(f'{B_KEY} должен быть валидной матрицей 1x{n} и состоять из чисел')
    
    return n, a, b, accuracy


def read_n_from_stdin() -> int:
    """Читает n из stdin."""
    n = None
    while not n:
        print(f'Пожалуйста введите валидный n [{MIN_N}; {MAX_N}]:')
        n = parse_int(input(), MIN_N, MAX_N)
    
    return n


def read_accuracy_from_stdin() -> int:
    """Читает точность из stdin."""
    accuracy = None
    while not accuracy:
        print(f'Пожалуйста введите валидную точность (>=0):')
        accuracy = parse_float(input(), 0, None)
    
    return accuracy


def read_a_from_stdin(n: int) -> Matrix2D:
    """Читает A из stdin."""
    a = []
    for i in range(n):
        row = None
        while not row:
            print(f'Пожалуйста введите валидную строку матрицы A номер #{i + 1} ({n} элементов, разделены пробелами):')
            row = parse_float_row(input(), n)
        a.append(row)

    return a


def read_b_from_stdin(n: int) -> Matrix1D:
    """Читает B из stdin."""
    b = None
    while not b:
        print(f'Пожалуйста введите валидную матрицу B ({n} элементов, разделены пробелами):')
        b = parse_float_row(input(), n)
    
    return b


def read_input_from_stdin() -> tuple[int, Matrix2D, Matrix1D, float]:
    """Читает данные из stdin."""
    n = read_n_from_stdin()
    accuracy = read_accuracy_from_stdin()
    a = read_a_from_stdin(n)
    b = read_b_from_stdin(n)

    return n, a, b, accuracy


def read_input_from_stdin_and_gen_random_matrix() -> tuple[int, Matrix2D, Matrix1D, float]:
    """Читает параметры из stdin и генерирует случайные A и B."""
    n = read_n_from_stdin()
    accuracy = read_accuracy_from_stdin()
    a = gen_valid_a(n)
    b = gen_b(n)

    return n, a, b, accuracy
