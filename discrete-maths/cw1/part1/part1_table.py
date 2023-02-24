"""Table generator."""

COLUMN_NUMBER = 7
HEADER = [
    'N',
    'x1 x2 x3 x4 x5',
    'x5 x4',
    '(x5 x4)10',
    'x3 x2 x1',
    '(x3 x2 x1)10',
    '+',
    'f',
]


def list_to_csv_row(tokens: list[str]) -> str:
    """Transform list of strings into CSV row."""
    normalized_tokens = tokens.copy()

    if (diff := COLUMN_NUMBER - len(normalized_tokens)) > 0:
        normalized_tokens.extend([''] * diff)

    return ','.join([f'"\'{token}"' for token in tokens])


def convert_to_csv(data: list[str]) -> str:
    """Convert data to CSV."""
    result = []
    result.append(list_to_csv_row(HEADER))
    for line in data:
        result.append(list_to_csv_row(line))
    return '\n'.join(result)


def write_csv_data(csv_data: str) -> None:
    """Write CSV data."""
    global args

    with open('cw1_part1_table.csv', 'w', encoding='utf-8') as f:
        f.write(csv_data)


def op(x1: str, x2: str, x3: str, x4: str, x5: str) -> int:
    """Operation."""
    a = int(f'{x5}{x4}', 2)
    b = int(f'{x3}{x2}{x1}', 2)

    return a + b


def f(x1: str, x2: str, x3: str, x4: str, x5: str) -> int:
    """F."""
    res = op(x1, x2, x3, x4, x5)

    if res in [1, 5, 6, 7, 8]:
        return '1'
    elif res in [3]:
        return 'D'
    return '0'


def f1(x1: str, x2: str, x3: str, x4: str, x5: str) -> int:
    """2."""
    x1, x2, x3, x4, x5 = list(map(lambda x: int(x), [x1, x2, x3, x4, x5]))

    return int(
        ((not x1) or (not x2) or (not x3) or (not x5)) and (
            (not x1) or x2 or x3 or (not x4)
        ) and (
            (not x2) or (not x3) or (not x4) or (not x5)
        ) and (
            x1 or x2 or x4 or x5
        ) and (
            (not x2) or x3 or x5
        ) and (
            x1 or x3 or x4
        ),
    )


def f21(x1: str, x2: str, x3: str, x4: str, x5: str) -> int:
    """2-1."""
    x1, x2, x3, x4, x5 = list(map(lambda x: int(x), [x1, x2, x3, x4, x5]))

    return int(
        ((not x2) or (not x3) or (not x5) or ((not x1) and (not x4))) and (
            (x3) or (
                (x1 or x4) and (
                    (not x1) or x2 or (not x4)
                ) and (
                    (not x2) or x5
                )
            )
        ) and (
            x1 or x2 or x4 or x5
        ),
    )


def f22(x1: str, x2: str, x3: str, x4: str, x5: str) -> int:
    """2-2."""
    x1, x2, x3, x4, x5 = list(map(lambda x: int(x), [x1, x2, x3, x4, x5]))

    return int(
        (x1 and (not x2) and x3) or (
            (not x1) and x3 and (not x4) and x5
        ) or (
            (not x1) and (not x2) and x4
        ) or (
            x1 and (not x3) and (not x4)
        ) or (
            x2 and x3 and (not x5)
        ) or (
            x2 and (not x3) and x4 and x5
        ),
    )


def f3(x1: str, x2: str, x3: str, x4: str, x5: str) -> int:
    """3."""

    def NOT_AND(a: int, *args: list[int]) -> int:
        return not (a == 1 and all(args))

    x1, x2, x3, x4, x5 = list(map(lambda x: int(x), [x1, x2, x3, x4, x5]))

    return int(
        NOT_AND(
            NOT_AND(x1, not x2, x3),
            NOT_AND(not x1, x3, not x4, x5),
            NOT_AND(not x1, not x2, x4),
            NOT_AND(x1, not x3, not x4),
            NOT_AND(x2, x3, not x5),
            NOT_AND(x2, not x3, x4, x5),
        ),
    )


def f4(x1: str, x2: str, x3: str, x4: str, x5: str) -> int:
    """4."""

    def NOT_AND(a: int, *args: list[int]) -> int:
        return not (a == 1 and all(args))

    x1, x2, x3, x4, x5 = list(map(lambda x: int(x), [x1, x2, x3, x4, x5]))
    q = NOT_AND(not x1, not x4)

    return int(
        not (
            NOT_AND(
                NOT_AND(x2, x3, x5, q),
                NOT_AND(
                    not x3,
                    NOT_AND(
                        q,
                        NOT_AND(x1, not x2, x4),
                        NOT_AND(x2, not x5),
                    ),
                ),
                NOT_AND(not q, not x2, not x5),
            )
        ),
    )


def main() -> None:
    """Yay."""
    data = []
    for n in range(32):
        x1, x2, x3, x4, x5 = [c for c in bin(n)[2:].rjust(5, '0')]
        print(
            f'[{x1} {x2} {x3} {x4} {x5}]',
            f(x1, x2, x3, x4, x5),
            f21(x1, x2, x3, x4, x5),
            f22(x1, x2, x3, x4, x5),
            f3(x1, x2, x3, x4, x5),
            f4(x1, x2, x3, x4, x5),
        )
        data.append(
            [
                n,
                f'{x1} {x2} {x3} {x4} {x5}',
                f'{x5} {x4}',
                int(x5 + x4, 2),
                f'{x3} {x2} {x1}',
                int(x3 + x2 + x1, 2),
                op(x1, x2, x3, x4, x5),
                f(x1, x2, x3, x4, x5),
            ],
        )
    csv_data = convert_to_csv(data)
    write_csv_data(csv_data)


if __name__ == '__main__':
    main()
