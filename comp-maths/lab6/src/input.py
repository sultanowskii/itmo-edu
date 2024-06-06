from typing import Any, Callable
from common import EPS

from parse import parse_int, parse_float


def _is_float_or_int(n: Any) -> bool:
    """Проверяет, является ли n float или int."""
    return isinstance(n, int) or isinstance(n, float)


def read_float_from_stdin(message: str, checker: Callable[[float], bool] = lambda x: True) -> float:
    """Читает float из stdin."""
    f = None
    while not f:
        print(f'{message}')
        f = parse_float(input())

        if f is not None and checker(f):
            break
        else:
            f = None

    return f


def read_int_from_stdin(message: str, checker: Callable[[int], bool] = lambda x: True) -> int:
    """Читает int из stdin."""
    f = None
    while not f:
        print(f'{message}')
        f = parse_int(input())

        if f is not None and checker(f):
            break
        else:
            f = None

    return f


def read_choice_from_stdin(message: str, choices: list[Any]) -> int:
    """Выбор из списка. Возвращает выбранный индекс."""
    choice_count = len(choices)

    s = f'{message} [1-{choice_count}]:'

    for i, choice in enumerate(choices):
        s += f'\n{i + 1}. {choice}'

    index = read_int_from_stdin(s, lambda n: 1 <= n <= choice_count)
    index -= 1

    return index

