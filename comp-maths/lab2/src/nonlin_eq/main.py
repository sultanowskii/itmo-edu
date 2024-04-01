import matplotlib.pyplot as plt  # type: ignore

from functions import FUNCTIONS
from methods import METHODS
from input import read_int_from_stdin


def main():
    max_function_index = len(FUNCTIONS) - 1

    s = f'Выберите уравнение [0-{max_function_index}]:'
    for i, func in enumerate(FUNCTIONS):
        s += f'\n{i}. {func.s} = 0'

    function_index = read_int_from_stdin(s, lambda n: 0 <= n <= max_function_index)
    func = FUNCTIONS[function_index]

    max_method_index = len(METHODS) - 1

    s = f'Выберите метод [0-{max_method_index}]:'
    for i, method in enumerate(METHODS):
        s += f'\n{i}. {method.name}'

    method_index = read_int_from_stdin(s, lambda n: 0 <= n <= max_method_index)
    method = METHODS[method_index]

    input_valid = False
    while not input_valid:
        inp = method.read_input()
        root_count = method.get_root_count_in_range(func, inp)
        if root_count == 1:
            input_valid = True
            break
        print(f'На отрезке [a; b] {root_count} корней. Укажите отрезок, на котором есть 1 корень.')
    
    print()

    _, a, b = inp

    x, f = method.perform(func, inp)

    print(method.name)
    print(f'{func.s} = 0')
    print(f'x =   {x:.8f}')
    print(f'f(x)= {f:.8f}')

    xs = [
        i / 100
        for i in range(
            (int(min(a, min(func.roots))) - 1) * 100,
            (int(max(b, max(func.roots))) + 1) * 100,
        )
    ]
    ys = list(map(func.f, xs))
    plt.plot(xs, ys)

    current_root = func.get_root_in_range(a, b)
    for root in func.roots:
        plt.plot(root, 0, 'go')
    plt.plot(current_root, 0, 'ro')
    plt.plot(x, f, 'yo')

    plt.grid(True)
    plt.show()


if __name__ == '__main__':
    main()
