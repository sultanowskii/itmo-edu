from math import cos, sin, exp

from common import Function

FUNCTIONS: list[dict[str, str | Function]] = [
    dict(
        name='y + cos(3x)',
        f=lambda x, y: y + cos(3 * x),
        compute_constant=lambda x, y: (y - (3 / 10) * sin(3 * x) + (1 / 10) * cos(3 * x)) / exp(x),
        integral=lambda x, c: c * exp(x) + (3 / 10) * sin(3 * x) - (1 / 10) * cos(3 * x),
    ),
    dict(
        name='4x + 5y',
        f=lambda x, y: 4 * x + 5 * y,
        compute_constant=lambda x, y: (y + 4 * x / 5 + 4 / 25) / exp(5 * x),
        integral=lambda x, c: c * exp(5 * x) - (4 * x / 5) - (4 / 25),
    ),
    dict(
        name='2x^3 + y',
        f=lambda x, y: x ** 3 + y,
        compute_constant=lambda x, y: (y + 2 * x ** 3 + 6 * x ** 2 + 12 * x + 12) / exp(x),
        integral=lambda x, c: c * exp(x) - 2 * x ** 3 - 6 * x ** 2 - 12 * x - 12,
    ),
]
