import matplotlib.pyplot as plt  # type: ignore
import pandas as pd
from functions import FUNCTIONS

from methods import METHODS
from input import read_choice_from_stdin, read_float_from_stdin


def draw_plot(short_results: list[dict]):
    for short_result in short_results:
        xs, ys = short_result['xs'], short_result['ys']
        plt.plot(xs, ys, label=short_result['name'])

    plt.legend(loc='upper left')
    plt.grid(True)
    plt.show()


def main():
    function_index = read_choice_from_stdin(
        'Выберите метод',
        [function['name'] for function in FUNCTIONS],
    )
    function = FUNCTIONS[function_index]
    f = function['f']
    compute_constant = function['compute_constant']
    integral = function['integral']

    x0 = read_float_from_stdin('Введите x0')
    y0 = read_float_from_stdin('Введите y0')
    xn = read_float_from_stdin('Введите xn')
    h = read_float_from_stdin('Введите h')
    e = read_float_from_stdin('Введите точность')

    short_results = []

    for method in METHODS:
        name = method['name']
        action = method['action']
        print(name)
        h_ = h

        xs, ys = [], []

        if method['one_step']:
            get_precision = method['get_precision']
            epsilon = get_precision(f, y0, x0, xn, h_, h_ / 2)
            while epsilon > e:
                h_ = h_ / 2
                epsilon = get_precision(f, y0, x0, xn, h_, h_ / 2)
            print(f'Точность {name} по правилу Рунге: {epsilon:.8f}')
            xs, ys = action(f, x0, y0, xn, h_)
        else:
            const = compute_constant(x0, y0)
            print(f'{const=}')
            diff = 1e10
            
            while diff > e:
                _diff = -1e10
                h_ /= 2
                xs, ys = action(f, x0, y0, xn, h_)
                n = int((xn - x0) / h)
                for i in range(n + 1):
                    _diff = max(abs(ys[i] - integral(xs[i], const)), _diff)
                diff = _diff
            print(f'Точность {name}: {e:.8f}')
        
        print(' x | y ')
        for x, y in zip(xs, ys):
            print(f'{x} | {y}')

        short_results.append(dict(
            name=name,
            xs=xs,
            ys=ys,
        ))

        print()

    draw_plot(short_results)


if __name__ == '__main__':
    main()
