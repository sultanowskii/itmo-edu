"""Table generator."""

HEADER = [
    'a1',
    'a2',
    'b1',
    'b2',
    'b3',
    'c0',
    'c1',
    'c2',
    'c3',
]
COLUMN_NUMBER = len(HEADER)


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

    with open('cw1_part2_table.csv', 'w', encoding='utf-8') as f:
        f.write(csv_data)


def f(x1: str, x2: str, x3: str, x4: str, x5: str) -> int:
    """Operation."""
    a = int(f'{x1}{x2}', 2)
    b = int(f'{x3}{x4}{x5}', 2)

    return (a + b) % 7


def f1(a1: str, a2: str, b1: str, b2: str, b3: str) -> int:
    """Operation."""
    a1, a2, b1, b2, b3 = [int(c) for c in (a1, a2, b1, b2, b3)]
    q = a2 and b3
    c1 = 0
    c2 = int(
        (
            (not a1) and b1 and (not b2)
        ) or (
            (not a2) and b1 and (not b2) and (not b3)
        ) or (
            not a1 and not a2 and b1 and b2 and not b3
        ) or (
            q and not b1 and b2
        ) or (
            a1 and not b1 and b2
        ) or (
            a1 and q and not b1
        ),
    )
    c3 = int(
        (
            not a1 and q and not b2
        ) or (
            not a1 and not a2 and not b1 and b2
        ) or (
            not a1 and not a2 and b2 and not b3
        ) or (
            not a1 and not b1 and b2 and not b3
        ) or (
            a1 and not a2 and not b2 and not b3
        ) or (
            a1 and not a2 and not b1 and not b2
        ) or (
            a1 and not b1 and not b2 and not b3
        ) or (
            a1 and b1 and b2 and b3
        ) or (
            a1 and q and b2
        ) or (
            a1 and a2 and b1 and b2
        ),
    )
    c4 = int(
        (
            not a1 and a2 and not b2 and not b3
        ) or (
            not a1 and not a2 and not b2 and b3
        ) or (
            not a2 and not b1 and b3
        ) or (
            q and b1 and b2
        ) or (
            a2 and not b1 and not b3
        ) or (
            a1 and q and b1
        ) or (
            a1 and not a2 and b1 and b2 and not b3
        ),
    )
    return int(f'{c1}{c2}{c3}{c4}', 2)


def f2(a1: str, a2: str, b1: str, b2: str, b3: str) -> int:
    """Operation."""
    a1, a2, b1, b2, b3 = [int(c) for c in (a1, a2, b1, b2, b3)]
    q = a2 and b3
    c1 = 0
    c2 = int(
        (
            (b1 and not b2) and (not a1 or (not a2 and not b3))
        ) or (
            b2 and ((not a1 and not a2 and b1 and not b3) or (a1 and not b1))
        ) or (
            a2 and not b1 and b3 and (b2 or a1)
        ),
    )
    c3 = int(
        (
            (a2 and b3) and ((a1 and b2) or (not a1 and not b2))
        ) or (
            not a2 and ((a1 and not b2 and not b3) or (not a1 and not b1 and b2))
        ) or (
            (not a1 and b2 and not b3) and (not a2 or not b1)
        ) or (
            (a1 and not b1 and not b2) and (not a2 or not b3)
        ) or (
            (a1 and b1 and b2) and (b3 or a2)
        ),
    )
    c4 = int(
        (
            (not a2 and b3) and ((not a1 and not b2) or not b1)
        ) or (
            a1 and not a2 and b1 and b2 and not b3
        ) or (
            (a2 and not b3) and (not b1 or (not a1 and not b2))
        ) or (
            (a2 and b1 and b3) and (b2 or a1)
        ),
    )
    return int(f'{c1}{c2}{c3}{c4}', 2)


def f3(a1: str, a2: str, b1: str, b2: str, b3: str) -> int:
    """Operation."""
    a1, a2, b1, b2, b3 = [int(c) for c in (a1, a2, b1, b2, b3)]
    z1 = not a2 and not b3
    z2 = a1 and b2
    z3 = not a1 and not b2

    c1 = 0
    c2 = int(
        (
            (b1 and not b2) and (not a1 or z1)
        ) or (
            b2 and ((not a1 and b1 and z1) or (a1 and not b1))
        ) or (
            a2 and not b1 and b3 and not z3
        ),
    )
    c3 = int(
        (
            (a2 and b3) and (z2 or z3)
        ) or (
            not a2 and ((a1 and not b2 and not b3) or (not a1 and not b1 and b2))
        ) or (
            (not a1 and b2 and not b3) and (not a2 or not b1)
        ) or (
            (a1 and not b1 and not b2) and (not a2 or not b3)
        ) or (
            z2 and b1 and not z1
        ),
    )
    c4 = int(
        (
            (not a2 and b3) and (z3 or not b1)
        ) or (
            z1 and z2 and b1
        ) or (
            (a2 and not b3) and (not b1 or z3)
        ) or (
            (a2 and b1 and b3 and not z3)
        ),
    )
    return int(f'{c1}{c2}{c3}{c4}', 2)


def f4(a1: str, a2: str, b1: str, b2: str, b3: str) -> int:
    """Operation."""
    a1, a2, b1, b2, b3 = [int(c) for c in (a1, a2, b1, b2, b3)]
    z1 = not a2 and not b3
    z2 = a1 and b2
    z3 = not a1 and not b2
    z4 = a2 and b3

    c1 = 0
    c2 = int(
        (
            (b1 and not b2) and (not a1 or z1)
        ) or (
            b2 and ((not a1 and b1 and z1) or (a1 and not b1))
        ) or (
            not b1 and not z3 and z4
        ),
    )
    c3 = int(
        (
            z4 and (z2 or z3)
        ) or (
            not a2 and ((a1 and not b2 and not b3) or (not a1 and not b1 and b2))
        ) or (
            (not a1 and b2 and not b3) and (not a2 or not b1)
        ) or (
            (a1 and not b1 and not b2 and not z4)
        ) or (
            z2 and b1 and not z1
        ),
    )
    c4 = int(
        (
            (not z1 and not z4) and (z3 or not b1)
        ) or (
            z1 and z2 and b1
        ) or (
            (b1 and not z3 and z4)
        ),
    )
    return int(f'{c1}{c2}{c3}{c4}', 2)


def main() -> None:
    """Yay."""
    data = []
    for n in range(32):
        x1, x2, x3, x4, x5 = [c for c in bin(n)[2:].rjust(5, '0')]
        res = bin(f(x1, x2, x3, x4, x5))[2:].rjust(4, '0')
        res1 = bin(f1(x1, x2, x3, x4, x5))[2:].rjust(4, '0')
        res2 = bin(f2(x1, x2, x3, x4, x5))[2:].rjust(4, '0')
        res3 = bin(f3(x1, x2, x3, x4, x5))[2:].rjust(4, '0')
        res4 = bin(f4(x1, x2, x3, x4, x5))[2:].rjust(4, '0')
        if not (res == res1 == res2 == res3 == res4):
            print(f'{x1} {x2} {x3} {x4} {x5} |', res, res1, res2, res3, res4)
        data.append(
            [
                x1,
                x2,
                x3,
                x4,
                x5,
                res[0],
                res[1],
                res[2],
                res[3],
            ],
        )
    csv_data = convert_to_csv(data)
    write_csv_data(csv_data)


if __name__ == '__main__':
    main()
