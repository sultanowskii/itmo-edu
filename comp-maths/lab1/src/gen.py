from random import uniform

from matrix import Matrix1D, Matrix2D


def gen_1D_matrix(n: int, _min: float, _max: float) -> Matrix1D:
    """Генерирует матрицу 1xN."""
    return [
        round(number, 2)
        for _ in range(n)
        if (number := uniform(_min, _max)) != 0
    ]


def gen_square_matrix(n: int, _min: float, _max: float) -> Matrix2D:
    """Генерирует матрицу NxN."""
    return [
        gen_1D_matrix(n, _min, _max)
        for _ in range(n)
    ]


def gen_a(n: int) -> Matrix2D:
    """Генерирует A."""
    return gen_square_matrix(n, -10, 10)


def gen_b(n: int) -> Matrix1D:
    """Генерирует B."""
    return gen_1D_matrix(n, -10, 10)


def gen_valid_a(n: int) -> Matrix1D:
    """Генерирует валидную A."""
    m = gen_a(n)

    for i in range(n):
        tmp = sum([abs(c) for c in m[i]])
        m[i][i] = tmp

    return m
