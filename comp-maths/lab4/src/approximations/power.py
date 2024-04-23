from math import exp, log
from approximations.linear import get_linear_approximation_factors
from common import Function
from dots import Dots


def get_power_approximation_factors(dots: Dots) -> tuple[float, float]:
    A, B = get_linear_approximation_factors(dots)

    a = exp(A)
    b = B

    return a, b


def get_power_approximation_function(dots: Dots) -> Function:
    if not dots.all_xs_exp_safe() or not dots.all_ys_exp_safe():
        raise ValueError('Есть невалидные X или Y')

    transformed_xs = []
    transformed_ys = []

    for x, y in dots.get_paired():
        transformed_xs.append(log(x))
        transformed_ys.append(log(y))
    
    transformed_dots = Dots(transformed_xs, transformed_ys)

    a, b = get_power_approximation_factors(transformed_dots)

    print(f'{a=}, {b=}')
    print(f'φ(x) = {a} * x^({b})')

    return lambda x: a * (x ** b)
