INDENT_SPACE_COUNT = 2
INDENT = ' ' * INDENT_SPACE_COUNT


def yaml_normalize(yaml_str: str) -> str:
    """Нормализация YAML-строки."""
    normalized_yaml_str = yaml_str

    while '\n\n' in normalized_yaml_str:
        normalized_yaml_str = normalized_yaml_str.replace('\n\n', '\n')

    return normalized_yaml_str.rstrip()


def _rec_yaml_format(dict_data: dict, depth: int, list_element: bool = False) -> str:
    result = ''

    for key, element in dict_data.items():
        dive = 1

        if isinstance(element, dict):
            element_data = element['value']
        else:
            element_data = element

        if isinstance(element_data, list):
            dive -= 1
        else:
            if list_element:
                result += f'{INDENT * depth}- {key}:\n'
                dive += 1  # Дополнительный сдвиг, так как мы заняли этот уровень табуляции символом элемента массива (-)
            else:
                result += f'{INDENT * depth}{key}:\n'

            # Печатаем атрибуты, если они вообще есть
            if 'attrs' in element and len(element['attrs'].keys()) > 0:
                result += f'{INDENT * (depth + dive)}attrs:'

                for attr_name, attr_value in element['attrs'].items():
                    result += f'\n{INDENT * (depth + dive + 1)}{attr_name}: {attr_value}'

                result += '\n'

            # Предзнаменуем, что сейчас будет значение тэга
            result += f'{INDENT * (depth + dive)}value:'

        if isinstance(element_data, dict) and element_data:
            # Если значение тэга - тэг, погружаемся
            result += '\n' + _rec_yaml_format(element_data, depth + dive + 1)
        elif isinstance(element_data, list) and element_data:
            # Если значение тэга - массив, для каждого его элемента погружаемся
            for inner_element in element_data:
                result += '\n' + _rec_yaml_format({key: inner_element}, depth + dive, True)
        else:
            # Если значение тэга примитивное, печатаем его на той же строке (учитывая пустое значение)
            result += f' {element_data if element_data else "null"}'
        result += '\n'

    return result if result else 'null'


def dict_to_yaml(dict_data: dict) -> str:
    """Перевести словарь в YAML-строку."""
    return yaml_normalize(_rec_yaml_format(dict_data, 0))
