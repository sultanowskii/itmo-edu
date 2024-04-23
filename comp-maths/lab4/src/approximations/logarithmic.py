from math import log
from approximations.linear import get_linear_approximation_factors
from common import Function
from dots import Dots


def get_logarithmic_approximation_factors(dots: Dots) -> tuple[float, float]:
    A, B = get_linear_approximation_factors(dots)

    a = A
    b = B

    return a, b


def get_logarithmic_approximation_function(dots: Dots) -> Function:
    if not dots.all_xs_exp_safe():
        raise ValueError('Есть невалидные X')

    a, b = get_logarithmic_approximation_factors(dots)

    print(f'{a=}, {b=}')
    print(f'φ(x) = {a} * ln(x) + ({b})')

    return lambda x: a * log(x) + b
