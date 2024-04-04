from dataclasses import dataclass
from typing import Callable
from math import exp, log


@dataclass
class Function:
    f: Callable[[float], float]
    name: str
    nuh_uhs: list[tuple[float, float]]
    start: float
    end: float

    def safe_f(self, x: float, e: float) -> float:
        try:
            return self.f(x)
        except Exception:
            try:
                return self.f(x + e)
            except Exception:
                return 0

    def is_convergent_in_x(self, x: float) -> bool:
        y = 0
        try:
            y = self.f(x)
        except Exception:
            return False

        if isinstance(y, complex):
            return False

        return True

    def is_x_ok(self, x: float) -> bool:
        for nuh_uh in self.nuh_uhs:
            a, b = nuh_uh
            if a <= x <= b:
                return False
        return True


FUNCTIONS = [
    Function(
        lambda x: x ** 2 + 3 * x - 4,
        'x^2 + 3x - 4',
        list(),
        None,
        None,
    ),
    Function(
        lambda x: 3 * x,
        '3x',
        list(),
        None,
        None,
    ),
    Function(
        lambda x: exp(x / 2),
        'e^{x / 2}',
        list(),
        None,
        None,
    ),
    Function(
        lambda x: 1 / x,
        '1 / x',
        [(0, 0)],
        None,
        None,
    ),
    Function(
        lambda x: log(3, x),
        'log_x(3)',
        [(-1e1000, 0), (1, 1)],
        0,
        None,
    ),
]
