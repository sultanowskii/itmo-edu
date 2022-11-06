from timeit import timeit

import converter_custom
import converter_lib
import converter_regex


EXEC_NUMBER = 100

data_custom = None
data_lib = None


def test_custom_xml_in():
    global data_custom
    data_custom = converter_custom.xml_in('input/large.xml')


def test_lib_xml_in():
    global data_lib
    data_lib = converter_lib.xml_in('input/large.xml')


def test_regex_xml_in():
    global data_custom
    data_custom = converter_regex.xml_in('input/large.xml')


def test_custom_yaml_out():
    converter_custom.yaml_out(data_custom, 'output/large_custom.yaml')


def test_lib_yaml_out():
   converter_lib.yaml_out(data_lib, 'output/large_lib.yaml')


print(f'IN (Самописный):         {timeit(test_custom_xml_in, number=EXEC_NUMBER)} сек.')
print(f'IN (Библиотечный):       {timeit(test_lib_xml_in, number=EXEC_NUMBER)} сек.')
print(f'IN (Самописный с regex): {timeit(test_regex_xml_in, number=EXEC_NUMBER)} сек.')
print(f'OUT (Самописный):        {timeit(test_custom_yaml_out, number=EXEC_NUMBER)} сек.')
print(f'OUT (Библиотечный):      {timeit(test_lib_yaml_out, number=EXEC_NUMBER)} сек.')
