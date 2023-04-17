"""
Convect microcode to the binary representation.

Reads line of microoperations and prints a binary&hex repr:

> RDCR WRDR
0000 0000 0000 0001 0000 0000 0000 0000 0000 0010
0001000002
"""

OPERATIONS = dict(
    RDDR=0,
    RDCR=1,
    RDIP=2,
    RDSP=3,
    RDAC=4,
    RDBR=5,
    RDPS=6,
    RDIR=7,

    COMR=8,
    COML=9,
    PLS1=10,
    SORA=11,

    LTOL=12,
    LTOH=13,
    HTOL=14,
    HTOH=15,
    SEXT=16,
    SHLT=17,
    SHL0=18,
    SHRT=19,
    SHRF=20,
    SETC=21,
    SETV=22,
    STNZ=23,

    WRDR=24,
    WRCR=25,
    WRIP=26,
    WRSP=27,
    WRAC=28,
    WRBR=29,
    WRPS=30,
    WRAR=31,

    LOAD=32,
    STOR=33,

    IO=34,
    INTS=35,

    HALT=38,

    TYPE=39,
)


def print_10_nibles(n: int) -> None:
    """Print 10 nibbles of number."""
    mask = 0b1111
    for i in range(36, -1, -4):
        tmp = n >> i
        print(bin(tmp & mask)[2:].rjust(4, '0'), end=' ')
    print()


def print_10_hexes(n: int) -> None:
    """Print 10 nibbles of number."""
    mask = 0b1111
    for i in range(36, -1, -4):
        tmp = n >> i
        print(hex(tmp & mask)[2:].upper(), end='')
    print()


def main() -> None:
    """Ye."""
    line = ' '

    while line != '':
        line = input('> ')
        microop_names = line.split(' ')
        num = 0

        for microop_name in microop_names:
            shift = OPERATIONS.get(microop_name, -1)
            if shift == -1:
                continue
            num |= 1 << shift

        print_10_nibles(num)
        print_10_hexes(num)


if __name__ == '__main__':
    main()
