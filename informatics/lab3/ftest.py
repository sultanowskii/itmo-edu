from typing import Any, Callable, MutableMapping, MutableSequence


def call_func_with_args_type_detection(func: Callable, arg: Any) -> Any:
    """Вызов функции в зависимости от заданных аргументов."""
    if isinstance(arg, MutableMapping):
        return func(**arg)
    if isinstance(arg, MutableSequence):
        return func(*arg)
    return func(arg)


def _exec_test_cases(func: Callable, test_cases: 'list[(Any,Any)]') -> None:
    """Запуск тестируемой функции на тестовых примерах."""
    for i, (arg, expected) in enumerate(test_cases):
        try:
            result = call_func_with_args_type_detection(func, arg)
        except Exception as e:
            print(
                (
                    f'[!] Test case #{i + 1}: Exception is thrown:\n'
                    f'{e}'
                )
            )
            continue

        if result != expected:
            print(
                (
                    f'[!] Test case #{i + 1}: Error. Args: {arg}\n'
                    f'  expected: {expected}\n'
                    f'  got:      {result}'
                )
            )
        else:
            print(f'[.] Test case #{i + 1}: OK.')


def ftest(func: Callable, test_cases: 'list[(any,any)]', test_name: str = None) -> None:
    """
    Запуск ftest.
    
    @func: Тестируемая функция. Функции с `*args`, `**kwargs` не поддерживаются.
    @test_cases: `list` тестовых случаев. Каждый тестовый случай должен представлять из себя `tuple` формата:

        (arg, expected)
        @arg: Аргумент/-ы функции.
        @expected: Ожидаемое возвращаемое значение функции.

    @test_name: Название теста.
    """
    if test_name:
        print(f'=== {test_name} ===')

    _exec_test_cases(func, test_cases)
