from timeit import timeit

import converter_custom
import converter_lib
import converter_regex


EXEC_NUMBER = 100


def test_convert_custom():
    converter_custom.convert('input/large.xml', 'output/large_custom.yaml')


def test_convert_lib():
    converter_lib.convert('input/large.xml', 'output/large_lib.yaml')


def test_convert_regex():
    converter_regex.convert('input/large.xml', 'output/large_regex.yaml')


print(f'Convert (Самописный):         {timeit(test_convert_custom, number=EXEC_NUMBER)} сек.')
print(f'Convert (Библиотечный):       {timeit(test_convert_lib, number=EXEC_NUMBER)} сек.')
print(f'Convert (Самописный с regex): {timeit(test_convert_regex, number=EXEC_NUMBER)} сек.')
