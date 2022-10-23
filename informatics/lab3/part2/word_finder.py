"""
Вариант: 5

С помощью регулярного выражения найти в тексте все слова, в которых две гласные
стоят подряд, а после этого слова идёт слово, в котором не больше 3 согласных.
"""

from re import findall


VWS = 'аеёиоуэыяeuoia'
CNS = 'бвгджзйклмнпрстфчйчшщbcdfgjjlmnpqstvxzhrwy'
SPECIAL_CHARACTERS = '.,!?();:\'\"'

# находит слова с 2 гласными подряд
REGEX = r'\b(?i:[а-яa-z])*(?i:[' + VWS + r']{2})(?i:[а-яa-z])*\b'

# выполняет задание
REGEX = (
    r'\b(?i:[а-яa-z])*(?i:[' + VWS + r']{2})(?i:[а-яa-z])*\b'
    r'(?! \b(?i:[а-яa-z])*(?i:[' + VWS + r']*)(?i:[' + CNS + r']){1}(?i:[' + VWS + r']*)(?i:[' + CNS + r']){1}(?i:[' + VWS + r']*)(?i:[' + CNS + r']){1}(?i:[' + VWS + r']*)(?i:[' + CNS + r']){1}(?i:[' + VWS + r']*)(?i:[а-яa-z])*\b)'
    r'(?= \b(?i:[а-яa-z])+\b)'
)


def remove_special_characters(text: str) -> str:
    for special_character in SPECIAL_CHARACTERS:
        text = text.replace(special_character, '')
    return text


def find_all_special_words(text: str) -> list:
    normalized_text = remove_special_characters(text)
    return findall(REGEX, normalized_text)


def main() -> None:
    text = input('Входной текст:\n')

    special_words = find_all_special_words(text)

    for special_word in special_words:
        print(special_word, end=' ')
    print()


if __name__ == '__main__':
    main()
