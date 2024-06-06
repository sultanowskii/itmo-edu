from typing import Callable

Xs = list[float]
Ys = list[float]
Function = Callable[[float, float], float]

PRECISION = 6
EPS = 0.000001


def rounded(n: float) -> float:
    return round(n, PRECISION)
