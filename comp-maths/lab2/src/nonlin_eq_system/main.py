import matplotlib.pyplot as plt

from systems import SYSTEMS
from methods import METHODS
from input import read_int_from_stdin


def main():
    max_system_index_index = len(SYSTEMS) - 1

    s = f'Выберите систему [0-{max_system_index_index}]:'
    for i, system in enumerate(SYSTEMS):
        s += f'\n{i}. {system.first.s}; {system.second.s}'

    system_index = read_int_from_stdin(s, lambda n: 0 <= n <= max_system_index_index)
    system = SYSTEMS[system_index]

    max_method_index = len(METHODS) - 1

    s = f'Выберите метод [0-{max_method_index}]:'
    for i, method in enumerate(METHODS):
        s += f'\n{i}. {method.name}'

    method_index = read_int_from_stdin(s, lambda n: 0 <= n <= max_method_index)
    method = METHODS[method_index]

    inp = method.read_input()

    print()

    x_res, y_res = method.perform(system, inp)

    print(method.name)
    print(f'x = {x_res:.8f}')
    print(f'y = {y_res:.8f}')

    xs: list = [i / 100 for i in range(-500, 500)]

    for line in (system.first, system.second):
        for proj in line.proj:
            ys = []
            for x in xs:
                try:
                    y = proj(x)
                    ys.append(y)
                except:
                    ys.append(None)
            plt.plot(xs, ys, line.color)

    plt.plot(x_res, y_res, 'yo')

    ax = plt.gca()
    ax.set_xlim([-5, 5])
    ax.set_ylim([-5, 5])
    plt.grid(True)
    plt.show()


if __name__ == '__main__':
    main()
