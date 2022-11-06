from xmltodict import parse as xml_to_dict
from yaml import dump as dict_to_yaml


def xml_in(input_file: str) -> dict:
    with open(input_file, 'r', encoding='utf-8') as f:
        xml_data = f.read()

    return xml_to_dict(xml_data)


def yaml_out(dict_data: dict, output_file: str) -> None:
    yaml_data = dict_to_yaml(dict_data, default_flow_style=False, allow_unicode=True)

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(yaml_data)


def convert(input_file: str, output_file: str) -> None:
    dict_data = xml_in(input_file)
    yaml_out(dict_data, output_file)


if __name__ == '__main__':
    convert('input/input.xml', 'output/output_lib.yaml')
