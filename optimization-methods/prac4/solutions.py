from statements import X1_0, X2_0, E

from gradient_descent import solve as solve_via_gradient_descent
from fastest_descent import solve as solve_via_fastest_descent

METHODS = [
    dict(
        name='Метод градиентного спуска',
        func=solve_via_gradient_descent,
    ),
    dict(
        name='Метод наискорейшего спуска',
        func=solve_via_fastest_descent,
    ),
]


def main():
    for method in METHODS:
        print(f'{method["name"]}:')

        solve = method['func']
        x, value = solve(X1_0, X2_0, E)

        print(f'x = ({x[0]:.8f}, {x[1]:.8f})')
        print(f'f(x) = {value:.8f}')

        print()


if __name__ == '__main__':
    main()
