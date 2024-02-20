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


def tr(m: MatrixSquare):
    a = copy_matrix(m)
    n = len(a)
    print(a)

    for i in range(n):
        if a[i][i] == 0:
            print_error_and_exit('0 на главной диагонали!')

        for j in range(i + 1, n):
            ratio = a[j][i] / a[i][i]
            
            for k in range(n):
                a[j][k] = a[j][k] - ratio * a[i][k]
 
    print(a)
    return a


def find_determinant(m: MatrixSquare) -> float:
    """Находит определитель квадратной матрицы."""
    n = len(m)

    tmp = tr(m)
    d = 1
    for i in range(n):
        d *= tmp[i][i]
    print(f'd::: {d}')

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
