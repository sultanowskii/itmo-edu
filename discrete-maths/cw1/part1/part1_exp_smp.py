"""
Uhh, how many fucking hours does it take to finish this blyat.

yay.
"""

from typing import Iterable

mode = 1

CUBES_1 = {
    'A': '0X101',
    'B': 'X1011',
    'C': '0110X',
    'D': '110X1',
    'E': '00X1X',
    'F': '0X01X',
    'G': '0XX10',
    'H': 'X01X1',
    'I': 'X011X',
    'J': 'XX110',
    'K': 'X11X0',
    'L': '10X0X',
    'M': '1X00X',
    'N': '1XX00',
    'O': '101XX',
    'P': '1X1X0',
}

CUBES_0 = {
    'A': '111X1',
    'B': '1X010',
    'C': '1001X',
    'D': 'X1111',
    'E': '00X00',
    'F': 'X10X0',
    'G': '0X00X',
    'H': 'X00X1',
}


CUBES_0 = {
    'A': '001X1',
    'B': 'X0101',
    'C': '0011X',
    'D': '0X110',
    'E': 'X0110',
    'F': '011X0',
    'G': 'X1100',
    'H': '1X001',
    'I': '1010X',
    'J': '1X100',
    'K': '10XX0',
    'L': '10X0X',
    'M': '1X0X1',
}

DATA_1 = [
    [
        {'E'},
        {'F'},
        {'G'},
    ],
    [
        {'A'},
        {'H'},
    ],
    [
        {'E'},
        {'G'},
        {'I'},
        {'J'},
    ],
    [
        {'E'},
        {'H'},
        {'I'},
    ],
    [
        {'B'},
        {'F'},
    ],
    [
        {'C'},
        {'K'},
    ],
    [
        {'A'},
        {'C'},
    ],
    [
        {'G'},
        {'J'},
        {'K'},
    ],
    [
        {'L'},
        {'M'},
        {'N'},
    ],
    [
        {'L'},
        {'N'},
        {'O'},
        {'P'},
    ],
    [
        {'H'},
        {'L'},
        {'O'},
    ],
    [
        {'I'},
        {'J'},
        {'O'},
        {'P'},
    ],
    [
        {'H'},
        {'I'},
        {'O'},
    ],
    [
        {'D'},
        {'M'},
    ],
    [
        {'B'},
        {'D'},
    ],
    [
        {'K'},
        {'N'},
        {'P'},
    ],
    [
        {'J'},
        {'K'},
        {'P'},
    ],
]


DATA_0 = [
    [
        {'A'},
        {'B'},
    ],
    [
        {'C'},
        {'D'},
        {'E'},
    ],
    [
        {'A'},
        {'C'},
    ],
    [
        {'F'},
        {'G'},
    ],
    [
        {'D'},
        {'F'},
    ],
    [
        {'I'},
        {'J'},
        {'K'},
        {'L'},
    ],
    [
        {'B'},
        {'I'},
        {'L'},
    ],
    [
        {'E'},
        {'K'},
    ],
    [
        {'H'},
        {'M'},
    ],
    [
        {'G'},
        {'J'},
    ],
]


def remove_duplicates(data: list[set[str]]) -> list[set[str]]:
    """Remove duplicates."""
    res = []
    for elem in data:
        if elem not in res:
            res.append(elem)
    return res


def remove_extended_ones(data: list[set[str]]) -> list[set[str]]:
    """Remove extended terms."""
    res = []

    for i in range(len(data)):
        ch = True
        for j in range(len(data)):
            if i == j:
                continue
            if data[j].issubset(data[i]):
                ch = False
                break
        if ch:
            res.append(data[i].copy())

    return res


def multiply_expressions(data: list[list[set[str]]]) -> list[set]:
    """Multiply expressions."""
    prev = data[0]
    current = []

    for i in range(1, len(data)):
        for elem1 in prev:
            for elem2 in data[i]:
                current.append(elem1.union(elem2))
        prev = current
        prev = remove_duplicates(prev)
        prev = remove_extended_ones(prev)
        current = []

    return prev


def get_entries_by_length(data: Iterable[Iterable]) -> tuple[dict[int, list[Iterable]], int]:
    """Get list of shortest entries."""
    min_length = 100000
    result = dict()

    for entry in data:
        entry_length = len(entry)
        min_length = entry_length if entry_length < min_length else min_length

        if entry_length in result.keys():
            result[entry_length].append(entry.copy())
        else:
            result[entry_length] = [entry.copy()]

    return result, min_length


def set_to_str(s: set) -> str:
    """Transform set to sorted str."""
    result = []
    for el in s:
        result.append(f'{el}')
    return ''.join(sorted(result))


def get_user_mode() -> int:
    """Get user mode (0/1)."""
    mode = int(input('0 or 1?\n'))

    if mode not in {0, 1}:
        print('0 or 1.')
        exit(-1)

    return mode


def get_number_of_INs(data: Iterable[str]) -> int:
    """Basically count 0 and 1s inside list of lists."""
    result = 0
    cubes = globals()[f'CUBES_{mode}']

    for letter in data:
        result += cubes[letter].count('1') + cubes[letter].count('0')

    return result


def print_entries(data: list[Iterable]) -> None:
    """Print entries, yup."""
    cubes = globals()[f'CUBES_{mode}']

    for entry in data:
        str_entry = set_to_str(entry)
        number_of_INs = get_number_of_INs(entry)

        print(f'{str_entry} (INs={number_of_INs})')

        for letter in str_entry:
            print(f'\t{cubes[letter]} ({letter})')


def _show_all(data: list[set[str]]) -> None:
    answer = input(f'Show all ({len(data)})?')
    if answer not in {'yes', 'y', 'Y'}:
        return
    print_entries(data)


def main() -> None:
    """Yay."""
    global mode
    mode = get_user_mode()

    data = globals()[f'DATA_{mode}']

    result = multiply_expressions(data)

    entries_by_length, min_length = get_entries_by_length(result)

    print(f'Min ones (length={min_length}):')
    print_entries(entries_by_length[min_length])

    _show_all(result)


if __name__ == '__main__':
    main()
