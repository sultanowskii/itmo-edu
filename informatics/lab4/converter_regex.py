import re
import regex

from converter_custom import yaml_out

"""
https://github.com/mrabarnett/mrab-regex

capturesdict is a combination of groupdict and captures:

groupdict returns a dict of the named groups and the last capture of those groups.

captures returns a list of all the captures of a group

capturesdict returns a dict of the named groups and lists of all the captures of those groups.
"""

VWS = 'аеёиоуэыюя'
CNS = 'бвгджзйклмнпрстфчйчшщ'
RUS = 'а-яё'

ENG = 'a-z'

LTS = RUS + ENG

PUNCT = r'!"#$%&()*+,-./:;<=>?@[]^_`{|}~' + '\''
NMS = '0-9'

PRINTABLE = LTS + PUNCT + NMS

RE_TAG = regex.compile(
    r'<'
    r'(?P<name>[_a-z]{1}[a-z0-9_]*)'
    r'(?: +(?P<attr_name>[_a-z]{1}[a-z0-9_]*)="(?P<attr_value>[^"]*)")*'
    r'>'
)

RE_TAG_CLOSE = regex.compile(
    r'<'
    r'\/(?P<name>[a-z_]{1}[a-z0-9_]*)'
    r'>'
)

RE_TOKENIZER = re.compile(
    r'('

    r'<'
    r'[a-z_]{1}[a-z0-9_]*'
    r'(?: +[a-z_]{1}[a-z0-9_]*="[^"]*")*'
    r'>'

    r'|'

    r'<\/[a-z_]{1}[a-z0-9_]*>'

    r'|'

    r'(?<=>).+(?=<)'

    r')'
)

class XMLRegexConverterException(Exception):
    """Ошибка парсинга XML."""

    def __init__(self, message: str) -> None:
        super().__init__(message)


def _tokens_to_dict(tokens: 'list[str]', parent_tag_name: str = None, index: int = 0) -> dict:
    data = dict()
    tag_name = ''
    tag_close_name = ''
    tag_value = ''
    i = index

    while i < len(tokens):
        token = tokens[i]

        if match := RE_TAG.match(token):
            tag_name = match.captures('name')[0]
            attr_names = match.captures('attr_name')
            attr_values = match.captures('attr_value')
            if len(attr_names) > len(set(attr_names)):
                raise XMLRegexConverterException(f'Non-unique attr found: {token}')

            value, tag_to_close, i = _tokens_to_dict(tokens, tag_name, i + 1)
            new_elem = dict(
                value=value,
                attrs=dict(
                    zip(
                        attr_names,
                        attr_values,
                    )
                )
            )

            if tag_name in data:
                if not isinstance(data[tag_name], list):
                    elem = data[tag_name]
                    data[tag_name] = [elem]
                data[tag_name].append(new_elem)
            else:
                data[tag_name] = new_elem
            
            if tag_to_close not in (tag_name, parent_tag_name):
                raise XMLRegexConverterException(f'Invalid tag close: {token}')

            if tag_to_close == parent_tag_name:
                return data, parent_tag_name, i

        elif match := RE_TAG_CLOSE.match(token):
            tag_close_name = match.captures('name')[0]
            if not tag_name:
                data = tag_value if tag_value else {}
            return data, tag_close_name, i
        else:
            tag_value = token

            if tag_name in data:
                if isinstance(data[tag_name], list):
                    data[tag_name][-1]['value'] = tag_value
                else:
                    data[tag_name]['value'] = tag_value
        i += 1

    return data, tag_close_name, i


def tokenize(xml_data: str) -> 'list[str]':
    m = RE_TOKENIZER.findall(xml_data, re.IGNORECASE)
    return m


def xml_to_dict(xml_data: str) -> dict:
    tokens = tokenize(xml_data)

    return _tokens_to_dict(tokens)[0]


def xml_in(input_file: str) -> dict:
    with open(input_file, 'r', encoding='utf-8') as f:
        xml_data = f.read()

    return xml_to_dict(xml_data)


def convert(input_file: str, output_file: str) -> None:
    dict_data = xml_in(input_file)
    yaml_out(dict_data, output_file)


if __name__ == '__main__':
    convert('input/input.xml', 'output/output_regex.yaml')
