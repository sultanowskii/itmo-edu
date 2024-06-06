import matplotlib.pyplot as plt  # type: ignore
import pandas as pd

from actions import ACTIONS
from finite_diff import get_finite_diffs
from methods import METHODS
from dots import Dots
from input import read_choice_from_stdin, read_float_from_stdin


def draw_plot(dots: Dots, short_results: list[dict]):
    min_y = 1e100
    max_y = -1e100

    min_x = min(dots.get_xs())
    max_x = max(dots.get_xs())
    diff_x = (max_x - min_x) / 3

    for x, y in dots.get_paired():
        plt.plot(x, y, 'ko')
        min_y = min(min_y, y)
        max_y = max(max_y, y)

    for short_result in short_results:
        x, y = short_result['x'], short_result['y']
        plt.plot(x, y, 'o', label=short_result['name'])
        min_y = min(min_y, y)
        max_y = max(max_y, y)

    PLOT_XS = [
        i / 100
        for i in range(int((min_x - diff_x) * 100), int((max_x + diff_x) * 100))
    ]

    for short_result in short_results:
        if short_result['func'] is None:
            continue
        ys = [short_result['func'](dots, x) for x in PLOT_XS]
        plt.plot(PLOT_XS, ys, label=short_result['name'])
        min_y = min(min_y, min(ys))
        max_y = max(max_y, max(ys))

    ax = plt.gca()
    diff_y = max_y - min_y
    ax.set_ylim([min_y - (diff_y * 2), max_y + (diff_y * 2)])

    plt.legend(loc="upper left")
    plt.grid(True)
    plt.show()


def main():
    action_index = read_choice_from_stdin(
        'Выберите действие',
        [action['name'] for action in ACTIONS],
    )
    action = ACTIONS[action_index]['func']

    dots = action()
    print()

    finite_diffs = get_finite_diffs(dots)
    print('Таблица конечных разностей:')
    print(pd.DataFrame(finite_diffs))

    X = read_float_from_stdin(
        'Введите точку поиска: ',
        lambda x: min(dots.get_xs()) <= x and x <= max(dots.get_xs()),
    )

    short_results = []

    for method in METHODS:
        name = method['name']
        print(name)

        try:
            Y = method['func'](dots, X)
        except ValueError as e:
            print(f'Невозможно применить {name}: {e}')
            print()
            continue

        print(f'Y = {Y}')

        short_results.append(dict(
            name=f'{name} (точка в ({X}; {Y}))',
            x=X,
            y=Y,
            func=method['func'] if method['draw'] else None,
        ))

        print()

    draw_plot(dots, short_results)


if __name__ == '__main__':
    main()
