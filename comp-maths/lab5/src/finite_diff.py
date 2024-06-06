from types import NoneType
from common import rounded
from dots import Dots


def get_finite_diffs(dots: Dots) -> list[list[float | str]]:
    n = dots.get_n()

    result = [
        [
            ''
            for _ in range(n)
        ]
        for _ in range(n)
    ]

    ys = dots.get_ys()

    for i in range(n):
        result[i][0] = ys[i]
    
    for i in range(1, n):
        for j in range(0, n - i):
            result[j][i] = rounded(result[j + 1][i - 1] - result[j][i - 1])

    return result
