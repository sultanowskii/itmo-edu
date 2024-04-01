"""
Метод деления отрезка пополам aka метод половинного деления (для одной переменной).
"""

from typing import Callable


def solve(
    f: Callable[[float], float],
    _a: float,
    _b: float,
    e: float,
) -> tuple[float, float]:
    a = _a
    b = _b

    while True:
        # Шаг 1
        x1 = (a + b - e) / 2
        x2 = (a + b + e) / 2

        # Шаг 2
        y1 = f(x1)
        y2 = f(x2)

        # Шаг 3
        if y1 > y2:
            a = x1
        else:
            b = x2
        
        # Шаг 4
        if b - a <= 2 * e:
            break

    # Шаг 5
    x_m = (a + b) / 2
    y_m = f(x_m)

    return x_m, y_m
