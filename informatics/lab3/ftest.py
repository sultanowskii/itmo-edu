from ast import Call
from typing import Any, Callable, MutableMapping, MutableSequence


def call_func_with_args_type_detection(func: Callable, args: Any) -> Any:
    if isinstance(args, MutableMapping):
        return func(**args)
    if isinstance(args, MutableSequence):
        return func(*args)
    return func(args)


def ftest(func: Callable, test_cases: 'list[(any,any)]', test_name: str = None) -> None:
    if test_name:
        print(f'=== {test_name} ===')

    for i, (arg, expected) in enumerate(test_cases):
        result = call_func_with_args_type_detection(func, arg)

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
