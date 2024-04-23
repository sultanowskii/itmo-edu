from common import Function
from dots import Dots
from msolve import solve_sys


def get_linear_approximation_factors(dots: Dots) -> tuple[float, float]:
    n = dots.get_n()

    sx, sy, sxx, sxy = dots.sx(), dots.sy(), dots.sxx(), dots.sxy()

    a, b = solve_sys(
        [
            [sxx, sx, sxy],
            [sx, n, sy],
        ],
    )

    return a, b


def get_linear_approximation_function(dots: Dots) -> Function:
    a, b = get_linear_approximation_factors(dots)

    print(f'{a=}, {b=}')
    print(f'φ(x) = {a} * x + ({b})')

    return lambda x: a * x + b
