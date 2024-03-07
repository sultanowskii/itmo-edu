"""
Метод Ньютона aka Метод касательных.
"""

from typing import Callable


def solve(
    f_derivatives: list[Callable[[float], float]],
    _a: float,
    _b: float,
    e: float,
) -> tuple[float, float]:
    f = f_derivatives[0]
    f_derivative_1 = f_derivatives[1]
    f_derivative_2 = f_derivatives[2]

    a = _a
    b = _b

    # Шаг 1
    x = (a + b) / 2
    print(f'x0={x}')

    while True:
        # Шаг 2
        fd1x = f_derivative_1(x)
        fd2x = f_derivative_2(x)
        x = x - (fd1x / fd2x)

        # Шаг 3
        if abs(f_derivative_1(x)) <= e:
            return x, f(x)
