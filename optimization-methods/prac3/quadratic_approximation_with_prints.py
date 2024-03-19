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

    print(
        f'1. Задать начальную точку $x_1 = {x1}$, длина шага $\Delta x = {DELTA_X}$, относительная точность изменения функции '
        f'$\\varepsilon_1 = {EPS_1}$, относительная точность изменения координаты $\\varepsilon_2 = {EPS_2}$.'
    )

    skip_to_step_6 = False

    while True:
        if not skip_to_step_6:
            # Шаг 2
            x2 = x1 + DELTA_X
            print(
                f'2. Вычислить значение второй точки $x_2 = x_1 + \Delta x = {x2:.3f}$.'
            )

            # Шаг 3
            f_x1 = f(x1)
            f_x2 = f(x2)
            print(
                f'3. Вычислить значение функции в точках $x_1$ и $x_2$: $f(x_1) = {f_x1:.3f}, f(x_2) = {f_x2:.3f}$'
            )

            # Шаг 4
            print(
                f'4. Сранвнить значения функции в точках $x_1$ и $x_2$: ',
                end=''
            )
            if f_x1 > f_x2:
                x3 = x1 + 2 * DELTA_X
                print(
                    f'$f(x_1) > f(x_2)$, и рассчитать третью точку: $x_3 = x_1 + 2 \Delta x = {x1:.3f} + 2 * {DELTA_X:.3f} = {x3:.3f}$'
                )
            else:
                x3 = x1 - DELTA_X
                print(
                    f'$f(x_1) \leq f(x_2)$, и рассчитать третью точку: $x_3 = x_1 - \Delta x = {x1:.3f} - {DELTA_X:.3f} = {x3:.3f}$'
                )
            
            # Шаг 5
            f_x3 = f(x3)
            print(
                f'5. Вычислить значение функции в точке $x_3$: $f(x_3) = {f_x3:.3f}$'
            )

        skip_to_step_6 = False

        # Шаг 6
        x_min, f_min = min(
            (x1, f_x1),
            (x2, f_x2),
            (x3, f_x3),
            key=lambda pair: pair[1],
        )
        print(
            f'6. Найти $F_{{min}} = min (f_1, f_2, f_3)$ и $x_min = x_i: F_min$:\n'
            f'$F_min = min({f_x1:.3f}, {f_x2:.3f}, {f_x3:.3f}) = {f_min:.3f}, x_min = {x_min:.3f}$'
        )

        # Шаг 7
        x__ = quadratic_polynom_min(x1, x2, x3, f_x1, f_x2, f_x3)
        if x__ is None:
            x1 = x_min
            # Переход к шагу 2
            continue
        f_x__ = f(x__)
        print(
            f'7. Вычислить точку минимума $\\overline{{x}}$ квадратичного интерполяционного полинома.\n'
            f'$$\n'
            f'\\overline{{x}} = \\dfrac{{1}}{{2}}\\dfrac{{(x_2^2 - x_3^2)f_1 + (x_3^2 - x_1^2)f_2 + (x_1^2 - x_2^2)f_3}}{{(x_2 -x_3)f_1 + (x_3 -x_1)f_2 + (x_1 -x_2)f_3}} = '
            f'\\dfrac{{1}}{{2}}\\dfrac{{({x2 ** 2:.3f} - {x3 ** 2:.3f}) {f_x1:.3f} + ({x3 ** 2:.3f} - {x1 ** 2:.3f}) {f_x2:.3f} + ({x1 ** 2:.3f} - {x2 ** 2:.3f}) {f_x3:.3f}}}{{({x2:.3f} - {x3:.3f}) {f_x1:.3f} + ({x3:.3f} - {x1:.3f}) {f_x2:.3f} + ({x1:.3f} - {x2:.3f}) {f_x3:.3f}}} = {x__:.3f}\n'
            f'$$\n'
        )

        # Шаг 8
        criteria1 = abs((f_min - f_x__) / f_x__)
        cond1 = criteria1 < EPS_1
        criteria2 = abs((x_min - x__) / x__)
        cond2 = criteria2 < EPS_2
        print(
            f'8. Проверим условие окончания\n'
            f'$$\n'
            f'\\left| \\dfrac{{F_{{min}} - f(\\overline{{x}})}}{{f(\\overline{{x}})}} \\right| = {criteria1:.6f}\n'
            f'$$\n'
            f'$$\n'
            f'\\left| \\dfrac{{x_{{min}} - \\overline{{x}}}}{{\\overline{{x}}}} \\right| = {criteria2:.6f}\n'
            f'$$\n'
        )

        if cond1 and cond2:
            print(
                f'\\begin{{align*}}\n'
                f'&{criteria1:.6f} < \\varepsilon_1 (\\varepsilon_1 = {EPS_1:.6f};\n \\'
                f'&{criteria2:.6f} < \\varepsilon_2 (\\varepsilon_2 = {EPS_2:.6f};\n'
                f'\\end{{align*}}\n'
                f'Условие окончания расчета выполняется. Расчет завершен.\n'
            )
            # Завершение поиска
            x_result = x__
            f_result = f_x__
            break

        # Одно из условий не выполняется и x__ в [x1, x3]
        print(
            f'\\begin{{align*}}\n'
            f'&{criteria1:.6f}, \\varepsilon_1 = {EPS_1:.6f};\n \\'
            f'&{criteria2:.6f}, \\varepsilon_2 = {EPS_2:.6f};\n'
            f'\\end{{align*}}\n'
            f'Условие окончания расчета не выполнено.\n'
        )
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
            print(
                f'Определить и расположить новые расчетные точки в порядке возрастания.\n'
                f'Получаем: $x_1 = {x1:.3f}, x_2 = {x2:.3f}, x_3 = {x3:.3f}$.\n'
                f'Значения функций в этих точках равны: $f(x_1) = {f_x1:.3f}, f(x_2) = {f_x2:.3f}, f(x_3) = {f_x3:.3f}$\n'
                f'Продолжить расчет. Перейти к шагу 6.'
            )
            print('---')
            # Переход к шагу 6
            skip_to_step_6 = True
            continue
        # Одно из условий не выполняется и x__ не в [x1, x3]
        else:
            x1 = x__
            # Переход к шагу 2
            continue

    return x_result, f_result
