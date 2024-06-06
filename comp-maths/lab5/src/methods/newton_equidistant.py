from common import EPS, rounded
from dots import Dots
from finite_diff import get_finite_diffs


def _check_equidistancy(dots: Dots) -> bool:
    n = dots.get_n()
    xs = dots.get_xs()

    if len(xs) < 2:
        return True

    h = rounded(xs[1] - xs[0])

    for i in range(1, n):
       if rounded(xs[i] - xs[i - 1]) - h > EPS:
           return False

    return True
        

def newton_equidistant(dots: Dots, X: float) -> float:
    n = dots.get_n()
    xs = dots.get_xs()

    if not _check_equidistancy(dots):
        raise ValueError('Точки не равноудалены')

    h = xs[1] - xs[0]
    t = (X - xs[0]) / h

    finite_diff = get_finite_diffs(dots)

    result = finite_diff[0][0]
    factor = 1

    for i in range(1, n):
        factor *= (t - (i - 1)) / i

        result += finite_diff[0][i] * factor

    return rounded(result)
