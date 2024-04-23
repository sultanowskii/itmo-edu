import matplotlib.pyplot as plt  # type: ignore

from actions import ACTIONS
from approximations import APPROXIMATIONS
from common import Checker, Function
from dots import Dots
from input import read_choice_from_stdin


def draw_plot(dots: Dots, short_results: list[tuple[str, Function, Checker]]):
    for x, y in dots.get_paired():
        plt.plot(x, y, 'ko')

    min_x = min(dots.get_xs())
    max_x = max(dots.get_xs())
    diff_x = max_x - min_x
    min_y = 1e100
    max_y = -1e100

    for name, phi, is_x_valid in short_results:
        PLOT_XS = [
            i / 100
            for i in range(int((min_x - diff_x) * 100), int((max_x + diff_x) * 100))
            if is_x_valid(i)
        ]

        min_y = min(min_y, min(dots.get_ys()))
        max_y = max(max_y, max(dots.get_ys()))

        ys = list(map(phi, PLOT_XS))
        plt.plot(PLOT_XS, ys, label=name)

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

    results = dict()
    min_deviation = 10000000
    min_approximation_name = ''

    short_results = []

    for approximation in APPROXIMATIONS:
        name = approximation['name']
        print(name)

        try:
            phi = approximation['func'](dots)
        except ValueError as e:
            print(f'Невозможно применить {name}: {e}')
            print()
            continue

        ys = dots.get_ys()
        print(f'yi: {", ".join([str(y) for y in ys])}')

        phis = dots.get_phis(phi)
        print(f'φi: {", ".join([str(phi_i) for phi_i in phis])}')

        epsilons = dots.get_epsilons(phi)
        print(f'εi: {", ".join([str(epsilon) for epsilon in epsilons])}')

        deviation = dots.get_deviation(phi)
        print(f'σ = {deviation}')

        if deviation < min_deviation:
            min_deviation = deviation
            min_approximation_name = approximation['name']

        results[approximation['name']] = dict(
            phis=phis,
            epsilons=epsilons,
            deviation=deviation,
        )

        short_results.append((name, phi, approximation['is_x_valid']))

        print()

    print(f'Лучшее приближение: {min_approximation_name} ({min_deviation})')

    draw_plot(dots, short_results)


if __name__ == '__main__':
    main()
