from common import rounded
from dots import Dots


def get_factors(dots: Dots):
    n = dots.get_n()

    result = [
        [
            ''
            for _ in range(n)
        ]
        for _ in range(n)
    ]

    xs = dots.get_xs()
    ys = dots.get_ys()

    for i in range(n):
        result[i][0] = ys[i]
    
    for i in range(1, n):
        for j in range(0, n - i):
            result[j][i] = rounded((result[j + 1][i - 1] - result[j][i - 1]) / (xs[j + i] - xs[j]))

    return result


def newton_div(dots: Dots, X: float) -> float:
    factors = get_factors(dots)

    n = dots.get_n()

    xs = dots.get_xs()
    ys = dots.get_ys()
    result = ys[0]

    for i in range(1, n):
        q = factors[0][i]

        for j in range(0, i):
            q *= (X - xs[j])

        result += q
    
    return rounded(result)
