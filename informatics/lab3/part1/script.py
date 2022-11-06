"""
Вариант: 5-1-4

Подсчет кол-ва смайликов в подаваемой строке.

Смайлик по варианту:
[<\\
"""

from re import findall

smile_regex = r'\[<\\'

SPECIAL_CHARACTERS = '[]().^$?+/\\*'


def count_smiles(text: str) -> int:
    smiles = findall(smile_regex, text)
    return len(smiles)


def main() -> None:
    global smile_regex
    text = input('Входной текст:\n')
    smile = input('Введите смайл (Enter для стандартного):\n')

    if smile:
        smile_regex = ''.join(
            [
                f'\\{c}' if c in SPECIAL_CHARACTERS else c
                for c in smile
            ]
        )

    smile_count = count_smiles(text)

    print(f'Количество смайликов: {smile_count}')


if __name__ == '__main__':
    main()
