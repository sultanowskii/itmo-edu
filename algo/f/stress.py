from os import system

INPUT_FILENAME = 'input.txt'

DUMB_SOLUTION_FILENAME = 'dumb.py'
DUMB_SOLUTION_OUTPUT = 'dumb_output.txt'
DUMB_SOLUTION_ERROR = 'dumb_error.txt'

SMART_SOLUTION_FILENAME = 'solution.cpp'
SMART_SOLUTION_OUTPUT = 'smart_output.txt'
SMART_SOLUTION_ERROR = 'smart_error.txt'


def write_input_to_file(
    l,
    filename: str = INPUT_FILENAME,
):
    s = ''
    for elem in l:
        if isinstance(elem, list):
            for sub_elem in elem:
                s += f'{sub_elem} '
        else:
            s += f'{elem} '
        s += '\n'

    with open(filename, 'w') as f:
        f.write(s)


def run_dumb_solution(
    dumb_solution_filename: str = DUMB_SOLUTION_FILENAME,
    input_filename: str = INPUT_FILENAME,
    output_filename: str = DUMB_SOLUTION_OUTPUT,
    error_filename: str = DUMB_SOLUTION_ERROR,
):
    system(f'python {dumb_solution_filename} <{input_filename} 2>{error_filename} >{output_filename}')


def run_smart_solution(
    smart_solution_filename: str = SMART_SOLUTION_FILENAME,
    input_filename: str = INPUT_FILENAME,
    output_filename: str = SMART_SOLUTION_OUTPUT,
    error_filename: str = SMART_SOLUTION_ERROR,
):
    bin_filename = 'smart.elf'

    assert bin_filename != smart_solution_filename

    system(f'g++ {smart_solution_filename} -o {bin_filename}')

    system(
        f'./{bin_filename} <{input_filename} 2>{error_filename} >{output_filename}'
    )

    system(f'rm {bin_filename}')


def print_results(
    input_filename: str = INPUT_FILENAME,
    dumb_output_filename: str = DUMB_SOLUTION_OUTPUT,
    dumb_error_filename: str = DUMB_SOLUTION_ERROR,
    smart_output_filename: str = SMART_SOLUTION_OUTPUT,
    smart_error_filename: str = SMART_SOLUTION_ERROR,
) -> bool:
    expected = ''
    with open(f'{dumb_output_filename}') as f:
        expected = f.read().strip()

    actual = ''
    with open(f'{smart_output_filename}') as f:
        actual = f.read().strip()

    if expected == actual:
        print('ok')
        return True
    
    print('Wrong answer!')

    print('Input:')
    with open(f'{input_filename}', 'r+') as f:
        print(f.read())
    print()
    
    print('Expected output:')
    with open(f'{dumb_output_filename}', 'r+') as f:
        print(f.read())
    print()
    
    print('Actual output:')
    with open(f'{smart_output_filename}', 'r+') as f:
        print(f.read())
    print()

    print('stderr:')
    with open(f'{smart_error_filename}', 'r+') as f:
        print(f.read())
    print()

    return False


def cleanup(
    input_filename: str = INPUT_FILENAME,
    dumb_output_filename: str = DUMB_SOLUTION_OUTPUT,
    dumb_error_filename: str = DUMB_SOLUTION_ERROR,
    smart_output_filename: str = SMART_SOLUTION_OUTPUT,
    smart_error_filename: str = SMART_SOLUTION_ERROR,
):
    system(f'rm {input_filename} {dumb_output_filename} {dumb_error_filename} {smart_output_filename} {smart_error_filename}')


def generate_input() -> list:
    from random import randint

    return [
        ''.join([str(randint(0, 9)) for i in range(randint(1, 100))])
        for _ in range(randint(1, 10))
    ]


def main():
    while True:
        inp = generate_input()
        write_input_to_file(inp)

        run_dumb_solution()
        run_smart_solution()

        matched = print_results()

        cleanup()

        if not matched:
            break


if __name__ == '__main__':
    main()
