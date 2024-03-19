from dataclasses import dataclass
from typing import Callable
from math import sqrt


@dataclass
class System:
    first: 'Line'
    second: 'Line'


@dataclass
class Line:
    f: Callable[[float, float], float]
    f_ders: list[Callable[[float, float], float]]
    s: str
    proj: list[Callable[[float], float]]
    color: str

    def root_count_in_range(self, ax: float, bx: float, ay: float, by: float) -> int:
        return len(
            list(
                filter(
                    lambda x, y: ax <= x <= bx and ay <= y <= by,
                    self.roots,
                ),
            ),
        )

    def get_root_in_range(self, ax: float, bx: float, ay: float, by: float) -> float:
        return list(
            filter(
                lambda x, y: ax <= x <= bx and ay <= y <= by,
                self.roots,
            ),
        )[0]


SYSTEMS = [
    System(
        Line(
            lambda x, y: x ** 2 + y ** 2 - 4,
            [
                lambda x, y: 2 * x,
                lambda x, y: 2 * y,
            ],
            'x^2 + y^2 - 4',
            [
                lambda x: sqrt(4 - x ** 2),
                lambda x: -sqrt(4 - x ** 2),
            ],
            'g',
        ),
        Line(
            lambda x, y: y - 3 * x ** 2,
            [
                lambda x, y: - 6 * x,
                lambda x, y: 1,
            ],
            'y - 3 * x^2',
            [
                lambda x: 3 * x ** 2,
            ],
            'b',
        ),
    )
]
