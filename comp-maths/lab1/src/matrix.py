from copy import deepcopy

from error import print_error_and_exit

MIN_N = 1
MAX_N = 20
Matrix1D = list[float]
Matrix2D = list[list[float]]
MatrixSquare = Matrix2D


def create_1_n_matrix(n: int) -> Matrix1D:
    """Создает матрицу 1xN."""
    return [0 for _ in range(n)]


def create_n_n_matrix(n: int) -> MatrixSquare:
    """Создает матрицу NxN."""
    return [create_1_n_matrix(n) for _ in range(n)]


def copy_matrix(orig) -> list:
    """Создает копию матрицы."""
    return deepcopy(orig)
