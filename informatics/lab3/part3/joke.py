"""
Вариант: 3

Вывесили списки стипендиатов текущего семестра, которые представляют из себя
список людей ФИО и номер группы этого человека. Вы решили подшутить над
некоторыми из своих одногруппников и удалить их из списка.

С помощью регулярного выражения найдите всех студентов своей группы, у которых
инициалы начинаются на одну и туже букву и исключите их из списка.

Могут существовать двойные фамилии, которые тоже нужно учитывать (студенты с
такими фамилиями тоже должны иметь право быть удаленными из списка
стипендиатов текущего семестра).
"""

import re


REGEX = (
    r'^'                   # строка
    r'([А-Я]{1})'          #   заглавная буква (группа 1)
    r'(?=[а-я\- .]*)'      #     после которой идет произвольное количество НЕ ЗАГЛАВНЫХ БУКВ
    r'(?:'                 #     не захватывающая группа
    r'[а-я\- .]*'          #       произвольное количество НЕ ЗАГЛАВНЫХ БУКВ
    r'(\1)'                #       захваченная ранее заглавная буква (группа 1)
    r')+'                  #     1 и более раз
    r'[а-я\- .]*'          #   произвольное количество НЕ ЗАГЛАВНЫХ БУКВ
    r' %s'                #   номер нашей группы (аргумент)
    r'$'                   # конец строки
)

def get_student_list_after_a_joke(lines: str, group: str) -> 'list[str]':
    same_capitals_our_group = [i.group() for i in re.finditer(REGEX % (group,), '\n'.join(lines), re.MULTILINE)]

    result = []

    for line in lines:
        if not line in same_capitals_our_group:
            result.append(line)

    return result


def main() -> None:
    raw_line_count = input('Число строк: ')
    if not raw_line_count.isdecimal():
        print('Введите число!')
        return
    
    line_count = int(raw_line_count)

    lines = []

    print(f'Введите {line_count} строк:')

    for _ in range(line_count):
        lines.append(input())

    group = input('Введите группу:\n')

    print('\n'.join(get_student_list_after_a_joke(lines, group)))


if __name__ == '__main__':
    main()
