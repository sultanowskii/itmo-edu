from le_yaml import dict_to_yaml
from le_xml import xml_to_dict


def xml_in(input_file: str) -> dict:
    with open(input_file, 'r', encoding='utf-8') as f:
        xml_data = f.read()
    return xml_to_dict(xml_data)


def yaml_out(dict_data: dict, output_file: str) -> None:
    yaml_data = dict_to_yaml(dict_data)

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(yaml_data)


def convert(input_file: str, output_file: str) -> None:
    dict_data = xml_in(input_file)
    yaml_out(dict_data, output_file)


if __name__ == '__main__':
    convert('input/input.xml', 'output/output_custom.yaml')
