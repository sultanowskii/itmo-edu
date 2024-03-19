from math import exp

A = 0.5
B = 1.5
E = 0.000001


def f(x: float) -> float:
    """Функция f."""
    return (1 / x) + exp(x)


def f_derivative_1(x: float) -> float:
    """Первая производная функции f."""
    return (- 1 / (x ** 2)) + exp(x)


def f_derivative_2(x: float) -> float:
    """Вторая производная функции f."""
    return (2 / (x ** 3)) + exp(x)


F_DERIVATIES = [
    f,
    f_derivative_1,
    f_derivative_2,
]
