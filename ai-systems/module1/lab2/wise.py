import re
from pyswip import Prolog  # type: ignore


def prolog_string(s: str) -> str:
    return f"'{s}'"


# Унифицированные переменные для запросов к базе знаний.
VAR_MONITOR = 'Monitor'
VAR_MONITOR_SIZE = 'Size'
VAR_MONITOR_RESOLUTION = 'Res'
VAR_MONITOR_REFRESH_RATE = 'RefreshRate'

# Regex-значения для названия групп, соответствующим характеристикам мониторов и маппинг их значений.
# Используются для удобного перевода валидного пользовательского ввода в prolog-запрос.
RE_PROMPT_SIZE_GROUP = 'size'
RE_PROMPT_SIZE_PROLOG_VALUES = {
    'небольшой': '21',
    'средний': '24',
    'большой': '27',
    'любых размеров': VAR_MONITOR_SIZE,
}
RE_PROMPT_RESOLUTION_GROUP = 'resolution'
RE_PROMPT_RESOLUTION_PROLOG_VALUES = {
    'нормальным': prolog_string('FullHD'),
    'хорошим': prolog_string('2K'),
    'отличным': prolog_string('4K'),
    'любым': VAR_MONITOR_RESOLUTION,
}
RE_PROMPT_REFRESH_RATE_GROUP = 'refresh_rate'
RE_PROMPT_REFRESH_RATE_PROLOG_VALUES = {
    'не играю': '60',
    'играю': '144',
    'кибератлет': '240',
    'герцовка любая': VAR_MONITOR_REFRESH_RATE,
}

# Основной Regex для парсинга валидного пользовательского ввода.
RE_PROMPT = re.compile(r'хочу (?P<size>небольшой|средний|большой|любых размеров) монитор с (?P<resolution>нормальным|хорошим|отличным|любым) разрешением, (?P<refresh_rate>не играю|играю|кибератлет|герцовка любая)')


def print_prompt_sym() -> None:
    """Печатает символ-промпт для ожидания ввода пользователя."""
    print('> ', end='')


def prompt_yes_no(message: str) -> bool:
    """Спрашивает у пользователя да/нет."""
    valid = False

    while not valid:
        print(message)
        print('да/нет:')
        print_prompt_sym()

        s = input().strip().lower()
        if s in ('д', 'да'):
            return True
        elif s in ('н', 'нет'):
            return False
        else:
            valid = False
        print('Неверный ввод, попробуйте снова.')

    return False


def prompt_choices(message: str, choices: list[str]) -> str:
    """Спрашивает у пользователя выбор."""
    valid = False

    while not valid:
        print(message)
        print('Подсказки для выбора:')
        for c in choices:
            print(' - ' + c)
        print_prompt_sym()

        s = input().strip()
        if s in choices:
            return s
        else:
            valid = False
        print('Неверный ввод, попробуйте снова.')

    return ''


def run_and_print_query(
    prolog: Prolog,
    message: str,
    query: str,
) -> None:
    """Исполняет prolog-запрос и печатает результат."""
    query_results = list(
        prolog.query(
            f'{query}, '
            f'monitor_size({VAR_MONITOR}, {VAR_MONITOR_SIZE}), '
            f'monitor_resolution({VAR_MONITOR}, {VAR_MONITOR_RESOLUTION}), '
            f'monitor_refresh_rate({VAR_MONITOR}, {VAR_MONITOR_REFRESH_RATE})'
        ),
    )

    if len(query_results) == 0:
        print('Ой! Ничего не нашлось. Попробуйте поменять запрос!')
        return

    print(message + ':')

    for result in query_results:
        print(f'- {result[VAR_MONITOR]} ({result[VAR_MONITOR_SIZE]}", {result[VAR_MONITOR_RESOLUTION]}, {result[VAR_MONITOR_REFRESH_RATE]}hz)')


def process_simplified(prolog: Prolog) -> None:
    """Проводит упрощенный диалог с пользователем."""
    choice_query_bases = {
        'работа': 'monitor_for_work',
        'рисование': 'monitor_for_art',
        'игры': 'monitor_for_pc_gaming',
        'консольные игры': 'monitor_for_console_gaming',
    }
    choice = prompt_choices('Чем вы планируете заниматься?', list(choice_query_bases.keys()))

    query_base = choice_query_bases[choice]

    query = f'{query_base}({VAR_MONITOR})'
    run_and_print_query(prolog, f'Я нашел вот такие мониторы ({choice})', query)


def process(prolog: Prolog) -> None:
    """Проводит стандартный диалог с пользователем."""
    result = None

    while result is None:
        print('Введите ваш запрос (подробнее можно посмотреть в примере - examples.txt)')
        print_prompt_sym()
        s = input().strip().lower()

        result = RE_PROMPT.search(s)

        if result is None:
            print('Неверный ввод, попробуйте снова.')

    size_value = RE_PROMPT_SIZE_PROLOG_VALUES[result.group(RE_PROMPT_SIZE_GROUP)]
    resolution_value = RE_PROMPT_RESOLUTION_PROLOG_VALUES[result.group(RE_PROMPT_RESOLUTION_GROUP)]
    refresh_rate_value = RE_PROMPT_REFRESH_RATE_PROLOG_VALUES[result.group(RE_PROMPT_REFRESH_RATE_GROUP)]

    query = (
        f'monitor({VAR_MONITOR}), '
        f'monitor_size({VAR_MONITOR}, {size_value}), '
        f'monitor_resolution({VAR_MONITOR}, {resolution_value}), '
        f'monitor_refresh_rate({VAR_MONITOR}, {refresh_rate_value})'
    )
    run_and_print_query(prolog, 'Я нашел вот такие мониторы', query)


def main():
    """Основное тело программы."""
    prolog = Prolog()
    prolog.consult('db.pl')

    run = True

    while run:    
        simplified = prompt_yes_no('Перейти в упрощенную версию?')

        if simplified:
            process_simplified(prolog)
        else:
            process(prolog)

        run = prompt_yes_no('Желаете продолжить?')


if __name__ == '__main__':
    main()
