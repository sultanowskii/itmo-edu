from common import Function, Xs, Ys


def euler(f: Function, x0: float, y0: float, xn: float, h: float) -> tuple[Xs, Ys]:
    """Метод Эйлера."""
    n = int((xn - x0) / h)

    xs = [x0 + h * i for i in range(n + 1)]
    ys = [0 for i in range(n + 1)]

    ys[0] = y0

    for i in range(n):
        ys[i + 1] = ys[i] + h * f(xs[i], ys[i])

    return xs, ys


def get_euler_precision(
    f: Function,
    y0: float,
    x0: float,
    xn: float,
    h1: float,
    h2: float,
) -> float:
    """Оценка погрешности правилом Рунге."""
    P = 1

    _xs1, ys1 = euler(f, y0, x0, xn, h1)
    _xs2, ys2 = euler(f, y0, x0, xn, h2)

    return abs(ys1[-1] - ys2[-1]) / (2 ** P - 1)
