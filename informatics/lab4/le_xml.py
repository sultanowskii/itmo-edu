ALLOWED_NAME_FIRST_SYMBOLS = (
    {chr(c) for c in range(ord('a'), ord('z') + 1)}
    .union({chr(c) for c in range(ord('A'), ord('Z') + 1)})
    .union({'_'})
)

ALLOWED_NAME_SYMBOLS = (
    ALLOWED_NAME_FIRST_SYMBOLS
    .union({str(c) for c in range(10)})
    .union({'-', '.'})
)


class XMLBaseException(Exception):
    """Базоваая ошибка парсинга XML."""
    SYMBOLS_TO_SHOW = 25

    def __init__(self, context_str: str, err_index: int, detail: str = 'XML parsing base error') -> None:
        if (start := err_index - self.SYMBOLS_TO_SHOW) >= 0:
            payload = '...' + context_str[start:err_index + 1]
        else:
            payload = context_str[:err_index + 1]

        message = f'{detail}. This error occured while handling the last symbol here:\n{payload}'

        super().__init__(message)


class XMLParseSyntaxException(XMLBaseException):
    """Синтаксическая ошибка при парсинге."""

    def __init__(self, context_str: str, err_index: int, detail: str = 'Syntax error') -> None:
        super().__init__(context_str, err_index, detail)


class XMLParseIllegalStateException(XMLBaseException):
    """Ошибка невалидного состояния при парсинге."""

    def __init__(self, context_str: str, err_index: int, detail: str = 'Invalid state') -> None:
        super().__init__(context_str, err_index, detail)


class XMLParseNonUniqueAttrException(XMLBaseException):
    """Ошибка наличия неуникального атрибута у тэга."""

    def __init__(self, context_str: str, err_index: int, detail: str = 'Non-unique attribute') -> None:
        super().__init__(context_str, err_index, detail)


class XMLParseInvalidCloseException(XMLBaseException):
    """Ошибка закрытия тэга."""

    def __init__(self, context_str: str, err_index: int, detail: str = 'Invalid tag close') -> None:
        super().__init__(context_str, err_index, detail)


class XMLParseState:
    """Состояния парсера."""

    TAG_START                    = 1
    TAG_NAME                     = 2
    TAG_SPACE                    = 3
    TAG_ATTR_NAME                = 4
    TAG_ATTR_EQ                  = 5
    TAG_ATTR_VALUE_START_QUOTE   = 6
    TAG_ATTR_VALUE               = 7
    TAG_ATTR_VALUE_END_QUOTE     = 8
    TAG_END                      = 9
    TAG_CLOSE                    = 10

    VALUE                        = 11

    PROCESSING_INSTRUCTION_START = 12
    PROCESSING_INSTRUCTION_END   = 13


def clear_xml(xml_raw_data: str) -> str:
    """Очищает XML-строку от лишних символов."""
    clean_xml_raw_data = xml_raw_data

    while ' \n' in clean_xml_raw_data:
        clean_xml_raw_data = clean_xml_raw_data.replace(' \n', '\n')

    while '\t\n' in clean_xml_raw_data:
        clean_xml_raw_data = clean_xml_raw_data.replace('\t\n', '\n')

    while '\n ' in clean_xml_raw_data:
        clean_xml_raw_data = clean_xml_raw_data.replace('\n ', '\n')

    while '\n\t' in clean_xml_raw_data:
        clean_xml_raw_data = clean_xml_raw_data.replace('\n\t', '\n')

    clean_xml_raw_data = clean_xml_raw_data.replace('\n', '')

    return clean_xml_raw_data


def _rec_xml_parse(clean_xml_raw_data: str, index: int = 0, parent_tag_name: str = '') -> 'tuple[dict, int, str]':
    """Рекурсивный парсинг XML-строки."""
    state = XMLParseState.TAG_START

    data = dict()

    tag_name = ''
    tag_name_completed = False
    tag_value = None

    tag_attr_name = ''
    tag_attr_value = ''
    tag_attrs = dict()
    tag_is_closed = False

    processing_instruction = False
    closing_tag = False
    i = index

    def init_new_tag() -> None:
        """Инициализация нового тэга (или добавление оного в массив)."""
        nonlocal data
        nonlocal tag_name, tag_name_completed, closing_tag

        if not tag_name_completed and not closing_tag:
            tag_name_completed = True
            if tag_name in data:
                if not isinstance(data[tag_name], list):
                    el = data[tag_name]
                    data[tag_name] = [el]
                data[tag_name].append(
                    {
                        'value': None,
                        'attrs': dict(),
                    }
                )
            else:
                data[tag_name] = {
                    'value': None,
                    'attrs': dict(),
                }

    def cleanup() -> None:
        """Очистка переменных, возврат к изначальному состоянию (после успешной полной обработки конкретного тэга)."""
        nonlocal tag_name, tag_value, tag_attr_name, tag_attr_value, tag_attrs
        nonlocal tag_name_completed, tag_is_closed, processing_instruction, closing_tag

        tag_name = ''
        tag_name_completed = False
        tag_value = None

        tag_attr_name = ''
        tag_attr_value = ''
        tag_attrs = dict()
        tag_is_closed = False

        processing_instruction = False
        closing_tag = False

    def assign_tag_value_and_attrs() -> None:
        """Присвоить текущему тэгу значение и атрибуты."""
        nonlocal data
        nonlocal tag_name, tag_value, tag_attrs

        if isinstance(data[tag_name], list):
            data[tag_name][-1] = {
                'value': tag_value,
                'attrs': tag_attrs,
            }
        else:
            data[tag_name] = {
                'value': tag_value,
                'attrs': tag_attrs,
            }

    while i < len(clean_xml_raw_data):
        c = clean_xml_raw_data[i]
        match state:
            case XMLParseState.TAG_START:
                match c:
                    case '<' | '>' | '=' | ' ' | '"':
                        raise XMLParseSyntaxException(clean_xml_raw_data, i)
                    case '/':
                        closing_tag = True
                        state = XMLParseState.TAG_CLOSE
                    case '?':
                        state = XMLParseState.PROCESSING_INSTRUCTION_START
                        processing_instruction = True
                    case _:
                        if c not in ALLOWED_NAME_FIRST_SYMBOLS:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        state = XMLParseState.TAG_NAME
                        tag_name = c
            case XMLParseState.TAG_NAME:
                match c:
                    case '<' | '/' | '=' | '"':
                        raise XMLParseSyntaxException(clean_xml_raw_data, i)
                    case '>':
                        state = XMLParseState.TAG_END
                        if processing_instruction:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        init_new_tag()
                    case ' ':
                        state = XMLParseState.TAG_SPACE
                        init_new_tag()
                    case '?':
                        if not processing_instruction:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        state = XMLParseState.PROCESSING_INSTRUCTION_END
                    case _:
                        if c not in ALLOWED_NAME_SYMBOLS:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        tag_name += c
            case XMLParseState.TAG_SPACE:
                match c:
                    case '<' | '/' | '=' | ' ' | '"':
                        raise XMLParseSyntaxException(clean_xml_raw_data, i)
                    case '>':
                        if processing_instruction:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        state = XMLParseState.TAG_END
                    case '?':
                        if not processing_instruction:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        state = XMLParseState.PROCESSING_INSTRUCTION_END
                    case _:
                        if closing_tag:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        if c not in ALLOWED_NAME_FIRST_SYMBOLS:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        state = XMLParseState.TAG_ATTR_NAME
                        tag_attr_name = c
            case XMLParseState.TAG_ATTR_NAME:
                match c:
                    case '<' | '>' | '/' | '"' | '?':
                        raise XMLParseSyntaxException(clean_xml_raw_data, i)
                    case '=':
                        state = XMLParseState.TAG_ATTR_EQ
                        if tag_attr_name in tag_attrs:
                            raise XMLParseNonUniqueAttrException(clean_xml_raw_data, i)
                    case ' ':
                        pass
                    case _:
                        if c not in ALLOWED_NAME_SYMBOLS:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        tag_attr_name += c
            case XMLParseState.TAG_ATTR_EQ:
                match c:
                    case ' ':
                        pass
                    case '"':
                        state = XMLParseState.TAG_ATTR_VALUE_START_QUOTE
                    case _:
                        raise XMLParseSyntaxException(clean_xml_raw_data, i)
            case XMLParseState.TAG_ATTR_VALUE_START_QUOTE:
                match c:
                    case '"':
                        state = XMLParseState.TAG_ATTR_VALUE_END_QUOTE
                        tag_attrs[tag_attr_name] = tag_attr_value
                    case _:
                        state = XMLParseState.TAG_ATTR_VALUE
                        tag_attr_value = c
            case XMLParseState.TAG_ATTR_VALUE:
                match c:
                    case '"':
                        state = XMLParseState.TAG_ATTR_VALUE_END_QUOTE
                        tag_attrs[tag_attr_name] = tag_attr_value
                    case _:
                        tag_attr_value += c
            case XMLParseState.TAG_ATTR_VALUE_END_QUOTE:
                match c:
                    case '>':
                        if processing_instruction:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        state = XMLParseState.TAG_END
                    case ' ':
                        state = XMLParseState.TAG_SPACE
                    case '?':
                        if not processing_instruction:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        state = XMLParseState.PROCESSING_INSTRUCTION_END
                    case _:
                        raise XMLParseSyntaxException(clean_xml_raw_data, i)
            case XMLParseState.TAG_END:
                # Текущий тэг - закрывающийся. Пора возвращаться к родительскому
                if closing_tag:
                    return {
                        'single_closing_tag': True,
                        'data': data,
                        'index': i,
                        'tag_name': tag_name,
                    }
                match c:
                    case '<':
                        if not processing_instruction:
                            inner_data = _rec_xml_parse(clean_xml_raw_data, i + 1, tag_name)

                            tag_value = inner_data['data']

                        assign_tag_value_and_attrs()

                        if not processing_instruction:
                            i = inner_data['index']

                            if index == 1:
                                return {
                                    'data': data,
                                    'index': i,
                                    'tag_name': tag_name,
                                }

                            # Закрылся непонятный тэг (ни текущий, ни родительский)
                            if inner_data['tag_name'] not in (tag_name, parent_tag_name):
                                raise XMLParseInvalidCloseException(clean_xml_raw_data, i)

                            if tag_is_closed:
                                if parent_tag_name != inner_data['tag_name']:
                                    raise XMLParseInvalidCloseException(clean_xml_raw_data, i)
                                return {
                                    'data': data,
                                    'index': i,
                                    'tag_name': tag_name,
                                }

                            if tag_name != inner_data['tag_name']:
                                raise XMLParseInvalidCloseException(clean_xml_raw_data, i)
                            tag_is_closed = True

                        cleanup()
                        i += 1

                        state = XMLParseState.TAG_START
                        continue
                    case _:
                        state = XMLParseState.VALUE
                        tag_value = c
            case XMLParseState.TAG_CLOSE:
                match c:
                    case '<' | '>' | '/' | '=' | '"' | '?' | ' ':
                        raise XMLParseSyntaxException(clean_xml_raw_data, i)
                    case _:
                        if c not in ALLOWED_NAME_FIRST_SYMBOLS:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        state = XMLParseState.TAG_NAME
                        tag_name = c
            case XMLParseState.VALUE:
                match c:
                    case '<':
                        inner_data = _rec_xml_parse(clean_xml_raw_data, i + 1, tag_name)

                        assign_tag_value_and_attrs()

                        # Обработка частного случая
                        if index == 1:
                            return {
                                'data': data,
                                'index': inner_data['index'],
                                'tag_name': tag_name,
                            }

                        # После значения не может идти открывающийся тэг
                        if 'single_closing_tag' not in inner_data or not inner_data['single_closing_tag']:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)

                        # Закрылся непонятный тэг (ни текущий, ни родительский)
                        if inner_data['tag_name'] not in (tag_name, parent_tag_name):
                            raise XMLParseInvalidCloseException(clean_xml_raw_data, inner_data['index'])

                        if tag_is_closed:
                            if parent_tag_name != inner_data['tag_name']:
                                raise XMLParseInvalidCloseException(clean_xml_raw_data, i)
                            return {
                                'data': data,
                                'index': inner_data['index'],
                                'tag_name': tag_name,
                            }

                        if tag_name != inner_data['tag_name']:
                            raise XMLParseInvalidCloseException(clean_xml_raw_data, i)
                        tag_is_closed = True

                        cleanup()
                        i = inner_data['index'] + 1

                        state = XMLParseState.TAG_START
                        continue
                    case _:
                        tag_value += c
            case XMLParseState.PROCESSING_INSTRUCTION_START:
                match c:
                    case '<' | '?' | '"' | ' ' | '=' | '/' | '>':
                        raise XMLParseSyntaxException(clean_xml_raw_data, i)
                    case _:
                        if c not in ALLOWED_NAME_SYMBOLS:
                            raise XMLParseSyntaxException(clean_xml_raw_data, i)
                        state = XMLParseState.TAG_NAME
                        tag_name = c
            case XMLParseState.PROCESSING_INSTRUCTION_END:
                match c:
                    case '>':
                        state = XMLParseState.TAG_END
                    case _:
                        raise XMLParseSyntaxException(clean_xml_raw_data, i)
            case _:
                raise XMLParseIllegalStateException(clean_xml_raw_data, i)

        i += 1

    return {
        'data': data,
        'index': i,
        'tag_name': tag_name,
    }


def xml_to_dict(xml_raw_data: str) -> dict:
    """Перевод XML в словарь."""
    clean_xml_raw_data = clear_xml(xml_raw_data)

    if clean_xml_raw_data[0] != '<':
        raise XMLParseSyntaxException(xml_raw_data[0])

    xml_data = _rec_xml_parse(clean_xml_raw_data, 1)['data']

    if 'xml' in xml_data:
        xml_data.pop('xml')

    return xml_data
