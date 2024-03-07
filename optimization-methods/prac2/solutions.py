from statements import A, B, E, F_DERIVATIES

from chord import solve as solve_via_chord_method
from golden_ratio import solve as solve_via_golden_ratio
from newton import solve as solve_via_newton_method
from segment_halving import solve as solve_via_segment_halving

METHODS = [
    dict(
        name='Метод половинного деления',
        func=solve_via_segment_halving
    ),
    dict(
        name='Метод золотого сечения',
        func=solve_via_golden_ratio,
    ),
    dict(
        name='Метод хорд',
        func=solve_via_chord_method,
    ),
    dict(
        name='Метод Ньютона',
        func=solve_via_newton_method,
    )
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
