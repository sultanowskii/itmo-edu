from common import Function, Xs, Ys


def improved_euler(f: Function, x0: float, y0: float, xn: float, h: float) -> tuple[Xs, Ys]:
    """Модифицированный метод Эйлера."""
    n = int((xn - x0) / h)

    xs = [x0 + h * i for i in range(n + 1)]
    ys = [0 for i in range(n + 1)]

    ys[0] = y0

    for i in range(n):
        x_i, y_i = xs[i], ys[i]
        x_i1 = xs[i + 1]
        ys[i + 1] = y_i + (h / 2) * (f(x_i, y_i) + f(x_i1, y_i + h * f(x_i, y_i)))

    return xs, ys


def get_improved_euler_precision(
    f: Function,
    y0: float,
    x0: float,
    xn: float,
    h1: float,
    h2: float,
) -> float:
    """Оценка погрешности правилом Рунге."""
    P = 2

    _xs1, ys1 = improved_euler(f, y0, x0, xn, h1)
    _xs2, ys2 = improved_euler(f, y0, x0, xn, h2)

    return abs(ys1[-1] - ys2[-1]) / (2 ** P - 1)
