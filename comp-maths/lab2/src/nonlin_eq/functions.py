from dataclasses import dataclass
from typing import Callable
from math import exp, log


@dataclass
class Function:
    f: Callable[[float], float]
    f_der: Callable[[float], float]
    s: str
    roots: list[float]

    def root_count_in_range(self, a: float, b: float) -> int:
        return len(list(filter(lambda x: a <= x <= b, self.roots)))

    def get_root_in_range(self, a: float, b: float) -> float:
        return list(filter(lambda x: a <= x <= b, self.roots))[0]


FUNCTIONS = [
    Function(
        lambda x: x ** 2 + 3 * x - 4,
        lambda x: 2 * x + 3,
        'x^2 + 3x - 4',
        [-4, 1],
    ),
    Function(
        lambda x: 7 * x ** 3 - 0.5 * x ** 2,
        lambda x: 21 * x ** 2 - x,
        '7 * x^3 - 0.5 * x^2',
        [0, 0.0714286],
    ),
    Function(
        lambda x: exp(2 * x) - 8,
        lambda x: 2 * exp(2 * x),
        'e^{2x} - 8',
        [1.039720770839]
    ),
]
