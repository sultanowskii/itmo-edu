from common import Function, Xs, Ys
from methods import euler


def milne(f: Function, x0: float, y0: float, xn: float, h: float) -> tuple[Xs, Ys]:
    """Метод Милна."""
    n = int((xn - x0) / h)

    xs, ys = euler(f, x0, y0, xn, h)

    for i in range(3, n + 1):
        ys[i] = ys[i - 4] + (4 * h / 3) * (2 * f(xs[i - 3], ys[i - 3]) - f(xs[i - 2], ys[i - 2]) + 2 * f(xs[i - 1], ys[i - 1]))
        ys[i] = ys[i - 2] + (h / 3) * (f(xs[i - 2], ys[i - 2]) + 4 * f(xs[i - 1], ys[i - 1]) + f(xs[i], ys[i]))

    return xs, ys
