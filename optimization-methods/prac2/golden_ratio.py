"""
Метод золотого сечения.
"""

from typing import Callable

GOLDEN_RATIO_1 = 0.382
GOLDEN_RATIO_2 = 0.618


def solve(
    f_derivatives: list[Callable[[float], float]],
    _a: float,
    _b: float,
    e: float,
) -> tuple[float, float]:
    f = f_derivatives[0]

    a = _a
    b = _b
    x1 = a + GOLDEN_RATIO_1 * (b - a)
    x2 = a + GOLDEN_RATIO_2 * (b - a)

    while True:
        # Шаг 2
        if f(x1) < f(x2):
            b = x2
            x2 = x1
            x1 = a + GOLDEN_RATIO_1 * (b - a)
        else:
            a = x1
            x1 = x2
            x2 = a + GOLDEN_RATIO_2 * (b - a)

        # Шаг 3
        if (b - a) < e * 2:
            break

    # Шаг 4
    x_m = (a + b) / 2
    y_m = f(x_m)

    return x_m, y_m
