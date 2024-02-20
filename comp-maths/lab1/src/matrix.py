from copy import deepcopy

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


def find_determinant(m: MatrixSquare) -> float:
    """Находит определитель квадратной матрицы."""
    n = len(m)

    if n == 1:
        return m[0][0]

    if n == 2:
        return m[0][0] * m[1][1] - m[0][1] * m[1][0]

    det = 0
    # для всех столбцов
    for j in range(n):
        sign = (-1) ** j
        submatrix = []

        # для строчек 1-n
        for i in range(1, n):
            row = m[i][0:j] + m[i][j+1:]
            submatrix.append(row)

        det += sign * m[0][j] * find_determinant(submatrix)

    return det
