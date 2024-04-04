from functions import FUNCTIONS
from methods import METHODS
from input import read_choice_from_stdin


def main():
    function_index = read_choice_from_stdin(
        'Выберите уравнение',
        [f.name for f in FUNCTIONS],
    )
    func = FUNCTIONS[function_index]

    method_index = read_choice_from_stdin(
        'Выберите уравнение',
        [m.NAME for m in METHODS],
    )
    method = METHODS[method_index]

    method.read_input()

    area = method.perform(func)

    print(method.NAME)
    print(f'S =   {area:.8f}')


if __name__ == '__main__':
    main()
