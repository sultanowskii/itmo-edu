from statements import A, B, E, F_DERIVATIES

from quadratic_approximation_with_prints import solve as solve_via_quadratic_approximation

METHODS = [
    dict(
        name='Метод квадратичной апроксимации',
        func=solve_via_quadratic_approximation,
    ),
]


def main():
    for method in METHODS:
        print(f'{method["name"]}:')

        solve = method['func']
        x_m, y_m = solve(F_DERIVATIES, A, B, E)

        print(f'x_m = {x_m}')
        print(f'y_m = f(x_m) = {y_m}')

        print()


if __name__ == '__main__':
    main()
