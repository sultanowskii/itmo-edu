from common import Function
from dots import Dots
from msolve import solve_sys


def get_cubic_approximation_factors(dots: Dots) -> tuple[float, float, float, float]:
    n = dots.get_n()

    sx, sy, sxx, sxy = dots.sx(), dots.sy(), dots.sxx(), dots.sxy()
    sx3, sx4, sx2y = dots.sx3(), dots.sx4(), dots.sx2y()
    sx5, sx6, sx3y = dots.sx5(), dots.sx6(), dots.sx3y()

    a0, a1, a2, a3 = solve_sys(
        [
            [n, sx, sxx, sx3, sy],
            [sx, sxx, sx3, sx4, sxy],
            [sxx, sx3, sx4, sx5, sx2y],
            [sx3, sx4, sx5, sx6, sx3y],
        ],
    )

    return a0, a1, a2, a3


def get_cubic_approximation_function(dots: Dots) -> Function:
    a0, a1, a2, a3 = get_cubic_approximation_factors(dots)

    print(f'{a0=}, {a1=}, {a2=}, {a3=}')
    print(f'φ(x) = {a3} * x^3 + ({a2}) * x^2 + ({a1}) * x + ({a0})')

    return lambda x: a3 * x ** 3 + a2 * x ** 2 + a1 * x + a0
