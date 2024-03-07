"""
Метод хорд.
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

    a = _a
    b = _b
    fd1a = f_derivative_1(a)
    fd1b = f_derivative_1(b)

    while True:
        # Шаг 1
        x = a - (fd1a / (fd1a - fd1b)) * (a - b)

        fd1x = f_derivative_1(x)

        # Шаг 2
        if abs(fd1x) <= e:
            return x, f(x)

        # Шаг 3
        if fd1x > 0:
            b = x
            fd1b = f_derivative_1(b)
        else:
            a = x
            fd1a = f_derivative_1(a)
