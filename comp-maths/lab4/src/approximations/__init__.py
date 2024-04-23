from approximations.linear import get_linear_approximation_function
from approximations.quadratic import get_quadratic_approximation_function
from approximations.cubic import get_cubic_approximation_function
from approximations.exponential import get_exponential_approximation_function
from approximations.logarithmic import get_logarithmic_approximation_function
from approximations.power import get_power_approximation_function

APPROXIMATIONS = [
    dict(
        name='Линейное приближение',
        func=get_linear_approximation_function,
        is_x_valid=lambda x: True,
    ),
    dict(
        name='Квадратичное приближение',
        func=get_quadratic_approximation_function,
        is_x_valid=lambda x: True,
    ),
    dict(
        name='Кубическое приближение',
        func=get_cubic_approximation_function,
        is_x_valid=lambda x: True,
    ),
    dict(
        name='Экспоненциальное приближение',
        func=get_exponential_approximation_function,
        is_x_valid=lambda x: True,
    ),
    dict(
        name='Логарифмическое приближение',
        func=get_logarithmic_approximation_function,
        is_x_valid=lambda x: x > 0 and x != 1,
    ),
    dict(
        name='Степенное приближение',
        func=get_power_approximation_function,
        is_x_valid=lambda x: True,
    ),
]