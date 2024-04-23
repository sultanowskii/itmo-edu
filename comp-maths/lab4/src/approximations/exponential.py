from math import exp
from approximations.linear import get_linear_approximation_factors
from common import Function
from dots import Dots


def get_exponential_approximation_factors(dots: Dots) -> tuple[float, float]:
    A, B = get_linear_approximation_factors(dots)

    a = exp(A)
    b = B

    return a, b


def get_exponential_approximation_function(dots: Dots) -> Function:
    if not dots.all_ys_exp_safe():
        raise ValueError('Есть невалидные Y')

    a, b = get_exponential_approximation_factors(dots)

    print(f'{a=}, {b=}')
    print(f'φ(x) = {a} * e^({b} * x)')

    return lambda x: a * exp(b * x)
