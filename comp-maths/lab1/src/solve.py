from error import print_error_and_exit
from matrix import (
    Matrix1D,
    MatrixSquare,
    create_1_n_matrix,
    create_n_n_matrix,
)


def find_predominant_element_index(m: Matrix1D) -> int | None:
    """Находит индекс преобладающего элемента в строке."""
    abs_m = [abs(e) for e in m]
    abs_m_sum = sum(abs_m)

    for i in range(len(m)):
        elem = abs_m[i]
        rest = abs_m_sum - elem
        if elem > rest:
            return i

    return None


def sort_by_diagonal_element_predominance(a: MatrixSquare, b: Matrix1D) -> tuple[MatrixSquare, Matrix1D]:
    """Сортирует строки (и A, и B) так, чтобы получилась матрица с диагональным преобладанием."""
    lines = [(a[i].copy(), b[i]) for i in range(len(a))]

    predominant_indexes = [find_predominant_element_index(row) for row in a]

    if any(map(lambda index: index is None, predominant_indexes)):
        print_error_and_exit('Диагональное преобладание не может быть достигнуто для матрицы A.')

    if len(set(predominant_indexes)) != len(predominant_indexes):
        print_error_and_exit('Диагональное преобладание не может быть достигнуто для матрицы A.')

    lines.sort(key=lambda line: find_predominant_element_index(line[0]))

    return list(map(lambda line: line[0], lines)), list(map(lambda line: line[1], lines))


def solve(n: int, a: MatrixSquare, b: Matrix1D, accuracy: float) -> tuple[Matrix1D, int]:
    """
    Решает СЛАУ (a, b) с заданной точностю.
    
    Помимо решения, возвращает число итераций.
    """
    c = create_n_n_matrix(n)
    d = create_1_n_matrix(n)

    a, b = sort_by_diagonal_element_predominance(a, b)

    # (5) -> (6)
    for i in range(n):
        for j in range(n):
            if i != j:
                c[i][j] = - (a[i][j] / a[i][i])
    
    for i in range(n):
        d[i] = b[i] / a[i][i]

    return _solve(n, c, d, accuracy)


def _solve(n: int, c: MatrixSquare, d: Matrix1D, accuracy: float, prev_x: Matrix1D = None) -> tuple[Matrix1D, int]:
    """Итерация решения."""
    x = create_1_n_matrix(n)

    if not prev_x:
        prev_x = create_1_n_matrix(n)

    for i in range(n):
        x[i] = d[i]
        for j in range(n):
            x[i] += c[i][j] * prev_x[j]
    
    abs_deviations = create_1_n_matrix(n)

    for i in range(n):
        abs_deviations[i] = abs(x[i] - prev_x[i])
    
    criteria = max(abs_deviations)

    print(criteria)

    if criteria < accuracy:
        return x, 1

    solution, iterations = _solve(n, c, d, accuracy, x)
    return solution, iterations + 1
