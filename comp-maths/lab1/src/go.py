import argparse
from error import print_error_and_exit

from matrix import Matrix1D, Matrix2D, find_determinant
from input import read_input_from_file, read_input_from_stdin, read_input_from_stdin_and_gen_random_matrix
from pretty import print_1d_matrix, print_2d_matrix
from solve import solve


def create_cli_arg_parser() -> argparse.ArgumentParser:
    """Создает парсер CLI-аргументов."""
    parser = argparse.ArgumentParser()
    parser.add_argument(
        '-f',
        '--filename',
        type=str,
        help='Файл с данными.',
        required=False,
    )
    parser.add_argument(
        '-r',
        '--random',
        action='store_true',
        help='Сгенерировать случайную матрицу.',
    )

    return parser


def read_input(cli_args: argparse.Namespace) -> tuple[int, Matrix2D, Matrix1D, float]:
    """Читает входные данные."""
    if cli_args.random:
        return read_input_from_stdin_and_gen_random_matrix()

    if filename := cli_args.filename:
        try:
            return read_input_from_file(filename)
        except Exception as e:
            print(e)
            return

    return read_input_from_stdin()


def main() -> None:
    cli_arg_parser = create_cli_arg_parser()
    cli_args = cli_arg_parser.parse_args()

    n, a, b, accuracy = read_input(cli_args)

    print()

    print(f'{n=}')

    print(f'{accuracy=}')
    print()

    print('A:')
    print_2d_matrix(a)
    print()

    print('B:')
    print_1d_matrix(b)
    print()

    print('вектор погрешностей:')
    solution, iterations = solve(n, a, b, accuracy)
    print()

    print(f'число итераций: {iterations}')
    print()

    print('решение:')
    for i in range(n):
        print(f'x{i + 1}: {solution[i]}')


if __name__ == '__main__':
    main()
