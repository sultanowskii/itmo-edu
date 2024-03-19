"""
Метод квадратичной апроксимации.
"""

from typing import Callable, Optional


def quadratic_polynom_min(
    x1: float,
    x2: float,
    x3: float,
    f_x1: float,
    f_x2: float,
    f_x3: float,
) -> Optional[float]:
    down = (x2 - x3) * f_x1 + (x3 - x1) * f_x2 + (x1 - x2) * f_x3

    if down == 0:
        return None
    
    up = (x2 ** 2 - x3 ** 2) * f_x1 + (x3 ** 2 - x1 ** 2) * f_x2 + (x1 ** 2 - x2 ** 2) * f_x3

    return 0.5 * (up / down)


def solve(
    f_derivatives: list[Callable[[float], float]],
    _a: float,
    _b: float,
    e: float,
) -> tuple[float, float]:
    f = f_derivatives[0]

    x_result = 0
    f_result = 0

    # Шаг 1
    DELTA_X = (_b - _a) / 2
    EPS_1 = e
    EPS_2 = e
    x1 = (_a + _b) / 2
    x2 = 0
    x3 = 0

    skip_to_step_6 = False

    while True:
        if not skip_to_step_6:
            # Шаг 2
            x2 = x1 + DELTA_X

            # Шаг 3
            f_x1 = f(x1)
            f_x2 = f(x2)

            # Шаг 4
            if f_x1 > f_x2:
                x3 = x1 + 2 * DELTA_X
            else:
                x3 = x1 - DELTA_X
            
            # Шаг 5
            f_x3 = f(x3)

        skip_to_step_6 = False

        # Шаг 6
        x_min, f_min = min(
            (x1, f_x1),
            (x2, f_x2),
            (x3, f_x3),
            key=lambda pair: pair[1],
        )

        # Шаг 7
        x__ = quadratic_polynom_min(x1, x2, x3, f_x1, f_x2, f_x3)
        if x__ is None:
            x1 = x_min
            # Переход к шагу 2
            continue
        f_x__ = f(x__)

        # Шаг 8
        criteria1 = abs((f_min - f_x__) / f_x__)
        cond1 = criteria1 < EPS_1
        criteria2 = abs((x_min - x__) / x__)
        cond2 = criteria2 < EPS_2

        if cond1 and cond2:
            # Завершение поиска
            x_result = x__
            f_result = f_x__
            break

        # Одно из условий не выполняется и x__ в [x1, x3]
        if min(x1, x3) <= x__ <= max(x1, x3):
            new_x2, _ = min(
                (x_min, f(x_min)),
                (x__, f_x__),
                key=lambda pair: pair[1],
            )
            other, _ = max(
                (x_min, f(x_min)),
                (x__, f_x__),
                key=lambda pair: pair[1],
            )
            new_x1 = max(x for x in (x1, x2, x3, other) if x < new_x2)
            new_x3 = min(x for x in (x1, x2, x3, other) if x > new_x2)
            x1, x2, x3 = new_x1, new_x2, new_x3
            f_x1, f_x2, f_x3 = f(x1), f(x2), f(x3)
            # Переход к шагу 6
            skip_to_step_6 = True
            continue
        # Одно из условий не выполняется и x__ не в [x1, x3]
        else:
            x1 = x__
            # Переход к шагу 2
            continue

    return x_result, f_result
