"""
Вариант: 5-1-4

Подсчет кол-ва смайликов в подаваемой строке.

Смайлик по варианту:
[<\\
"""

from re import findall

SMILE_REGEX = r'\[\<\\'


def count_smiles(text: str) -> int:
    smiles = findall(SMILE_REGEX, text)
    return len(smiles)

def main() -> None:
    text = input('Входной текст:\n')

    smile_count = count_smiles(text)

    print(f'Количество смайликов: {smile_count}')


if __name__ == '__main__':
    main()
