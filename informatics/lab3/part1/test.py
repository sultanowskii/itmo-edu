import sys
sys.path.append('..')

from part1.script import count_smiles
from ftest import ftest


SMILE = '[<\\'

TEST_CASES = [
    (
        '[<\\',
        1,
    ),
    (
        '[<\\' * 4,
        4,
    ),
    (
        '',
        0,
    ),
    (
        ']</[>/[</',
        0,
    ),
    (
        '[<\\Lorem ipsum dolor [<\\ sit[<\\ amet.[<\\',
        4,
    ),
    (
        '[ <\\ [< \\ [< \\ [ < \\',
        0,
    ),
]


def test() -> None:
    ftest(count_smiles, TEST_CASES, test_name='Lab 3. Smile counter.')


if __name__ == '__main__':
    test()
