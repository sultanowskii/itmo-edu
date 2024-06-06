from common import rounded


def solve_sys(extended_matrix: list[list[float]]) -> list[float]:
    # Прямой ход метода Гаусса (для приведения матрицы к треугольному виду)
    n = len(extended_matrix)

    for i in range(n):
        # Поиск максимального элемента в столбце
        max_elem = abs(extended_matrix[i][i])
        max_row = i

        for k in range(i + 1, n):
            if abs(extended_matrix[k][i]) > max_elem:
                max_elem = abs(extended_matrix[k][i])
                max_row = k

        # Обмен строк для улучшения устойчивости
        extended_matrix[i], extended_matrix[max_row] = extended_matrix[max_row], extended_matrix[i]

        # Приведение матрицы к треугольному виду
        for k in range(i + 1, n):
            factor = -extended_matrix[k][i] / extended_matrix[i][i]
            for j in range(i, n + 1):
                if i == j:
                    extended_matrix[k][j] = 0
                else:
                    extended_matrix[k][j] += factor * extended_matrix[i][j]

    # Обратный ход метода Гаусса для нахождения решений
    solutions = [0] * n
    for i in range(n - 1, -1, -1):
        solutions[i] = rounded(extended_matrix[i][n] / extended_matrix[i][i])
        for k in range(i - 1, -1, -1):
            extended_matrix[k][n] -= extended_matrix[k][i] * solutions[i]

    return solutions


if __name__ == '__main__':
    matrix = [
        [2, 1, -1, 8],
        [-3, -1, 2, -11],
        [-2, 1, 2, -3],
    ]

    print('Решение:', solve_sys(matrix))
