cubes = [
    '110X1',
    '001X0',
    'X1011',
    'X0100',
    '1X01X',
    '0X10X',

    '1111X',
    '0X010',
    '0001X',
    '1X111',
    '00X10',
    '11X11',
    '10X00',
    '1000X',
    '1X000',
    '01X01',

    '10110',
    '111X1',
    '00X01',
    '01X00',
    'X1111',
    'X10X0',
    'X00X1',
]


def get_val_from_idx(i: int) -> str:
    if i == 2:
        return '.'
    return str(i)


def get_price_win(a: list[str]) -> int:
    q_cost = a.count('1') + a.count('0')
    if q_cost < 2:
        return 0
    win = - q_cost

    for cube in cubes:
        ch = True
        for i in range(len(cube)):
            if a[i] == '.':
                continue
            if a[i] != cube[i]:
                ch = False
                break
        if ch:
            win += q_cost - 1
    return win


for a1 in range(3):
    for a2 in range(3):
        for a3 in range(3):
            for a4 in range(3):
                for a5 in range(3):
                    a = [a1, a2, a3, a4, a5]
                    a = [get_val_from_idx(i) for i in a]
                    win = get_price_win(a)
                    print(''.join(a), win)
