"""
Вариант: 5

С помощью регулярного выражения найти в тексте все слова, в которых две гласные
стоят подряд, а после этого слова идёт слово, в котором не больше 3 согласных.
"""

from re import IGNORECASE, findall


VWS = 'аеёиоуэыя'
CNS = 'бвгджзйклмнпрстфчйчшщ'
SPECIAL_CHARACTERS = '.,!?();:\'\"'

# находит слова с 2 гласными подряд
REGEX = r'\b(?i:[а-я])*(?i:[' + VWS + r']{2})(?i:[а-я])*\b'

# выполняет задание
REGEX = (
    r'\b[а-яё]*'                # есть слово (1), начинающееся на произвольное кол-во любых букв
    r'[' + VWS + r']{2}'        #   2 подряд идущие гласные
    r'[а-яё]*\b'                # заканчивается на произвольное кол-во любых букв
    
    r'(?!'                      # и после этого (1) слова нет такого
    r'\s\b[а-яё]*'              #   слова, что начинается на произвольное кол-во любых букв
    r'[' + CNS + r']{1}'        #     идет 1 согласная
    r'[а-яё]*'                  #     идет произвольная буква
    r'[' + CNS + r']{1}'        #     идет 1 согласная
    r'[а-я]*'                   #     идет произвольная буква
    r'[' + CNS + r']{1}'        #     идет 1 согласная
    r'[а-яё]*'                  #     идет произвольная буква
    r'[' + CNS + r']{1}'        #     идет 1 согласная
    r'[а-яё]*\b)'               #   и заканчивается на произвольное кол-во любых букв

    r'(?'                       # и после этого (1) слова,
    r'=\s\b[а-яё]+)\b'          # вообще есть слово
)


def remove_special_characters(text: str) -> str:
    for special_character in SPECIAL_CHARACTERS:
        text = text.replace(special_character, '')
    return text


def find_all_special_words(text: str) -> list:
    normalized_text = remove_special_characters(text)
    return findall(REGEX, normalized_text, IGNORECASE)


def main() -> None:
    text = input('Входной текст:\n')

    special_words = find_all_special_words(text)

    for special_word in special_words:
        print(special_word, end=' ')
    print()


if __name__ == '__main__':
    main()
