from matrix import Matrix1D, Matrix2D


def print_1d_matrix(m: Matrix1D) -> None:
    """Выводит 1-мерную матрицу."""
    for elem in m:
        print(elem)


def print_2d_matrix(m: Matrix2D) -> None:
    """Выводит 2-мерную матрицу."""
    for row in m:
        for elem in row:
            print(elem, end=' ')
        print()
