from math import exp, sqrt
from typing import Callable

X1_0 = 0 # 2
X2_0 = 0 # 3
E = 0.000001

Function = Callable[[float, float], float]
Vec2D = tuple[float, float]


def gradient(x1: float, x2: float) -> tuple[float, float]:
    return f_derivative_x1(x1, x2), f_derivative_x2(x1, x2)


def vec2d_magnitude(v: Vec2D) -> float:
    """Модуль вектора."""
    return sqrt(v[0] ** 2 + v[1] ** 2)


def mul_vec2d_by_number(v: Vec2D, n: float) -> Vec2D:
    return (v[0] * n, v[1] * n)


def div_vec2d_by_number(v: Vec2D, n: float) -> Vec2D:
    return (v[0] / n, v[1] / n)


def add_vec2d(a: Vec2D, b: Vec2D) -> Vec2D:
    return (
        a[0] + b[0],
        a[1] + b[1],
    )


def sub_vec2d(a: Vec2D, b: Vec2D) -> Vec2D:
    return (
        a[0] - b[0],
        a[1] - b[1],
    )


def f(x1: float, x2: float) -> float:
    """Функция f."""
    return exp(-(x1 ** 2) + (x2 ** 2) + 2 * x1 * x2 - x2)
    # return x1 ** 2 + x2 ** 2 + 1.5 * x1 * x2


def generate_f(x1: Callable[[float], float], x2: Callable[[float], float]) -> Callable[[float], float]:
    return lambda h: f(x1(h), x2(h))


def f_derivative_x1(x1: float, x2: float) -> float:
    """Частная производная функции f по x1."""
    return (-(2 * x1) + 2 * x2) * exp(-(x1 ** 2) + (x2 ** 2) + 2 * x1 * x2 - x2)
    # return 2 * x1 + 1.5 * x2


def f_derivative_x2(x1: float, x2: float) -> float:
    """Частная производная функции f по x2."""
    return (2 * x1 + 2 * x2 - 1) * exp(-(x1 ** 2) + (x2 ** 2) + 2 * x1 * x2 - x2)
    # return 1.5 * x1 + 2 * x2
