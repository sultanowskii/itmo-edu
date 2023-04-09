HEADER = """
\\documentclass{article}
\\usepackage[utf8]{inputenc}
\\usepackage{mathtools}

\\begin{document}

\\[
\\begin{equation}
\\begin{dcases}
"""

FOOTER = """
\\end{dcases}
\\end{equation}
\\]

\\end{document}
"""

CUBE_SETS = [
    [
        '0',
    ],
    [
        '0X10X',
        'X0100',
        '00110',
        'X1011',
        '1X01X',
        '110X1',
    ],
    [
        '01X01',
        '0001X',
        '00X10',
        '0X010',
        '10X00',
        '1000X',
        '1X000',
        '1X111',
        '11X11',
        '1111X',
    ],
    [
        '01X00',
        '00X01',
        'X00X1',
        'X1111',
        'X10X0',
        '111X1',
        '10110',
    ],
]

LETTER_NAMES = [
    'a_1',
    'a_2',
    'b_1',
    'b_2',
    'b_3',
]


def get_letter_repr(i: int, value: str) -> str:
    letter = LETTER_NAMES[i]
    if value == 'X':
        return ''
    elif value == '0':
        return '\\overline{' + letter + '} '
    elif value == '1':
        return f'{letter} '
    else:
        return ''


print(HEADER)

for set_i, cube_set in enumerate(CUBE_SETS):
    s = f'c_{set_i + 1}='
    cntr = 0
    for cube in cube_set:
        s += '('
        for i, value in enumerate(cube):
            s += get_letter_repr(i, value)
            if value != 'X':
                cntr += 1
        s += ') v '
    s += '\\\\\n'
    print(cntr)
    print(s)

print(FOOTER)
