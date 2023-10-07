"""
Reads raw BCOMP CLI data and transforms it into CSV file ready to be uploaded to Google Drive.

This script is a convenient way to get rid of anal pain while formatting BCOMP trace logs.

Simply run it, Ctrl+V BCOMP output, hit Enter, type 'END', and hit Enter again!

Made by @sultanowskii to reduce the amount of sweat while doing ОПД labs.
"""

from argparse import ArgumentParser

args = None
HEADER = [
    'Адрес',
    'Код',
    'IP',
    'CR',
    'AR',
    'DR',
    'SP',
    'BR',
    'AC',
    'NZVC',
    'Адрес',
    'Новый код',
]
COLUMN_NUMBER = len(HEADER)


def read_input_data() -> list[str]:
    """Read input data."""
    global args

    if (input_filename := args.input_filename):
        with open(input_filename, 'r', encoding='utf-8') as f:
            data = f.read().split('\n')
    else:
        data = []
        line = ''
        while (line := input()).lower() != 'end':
            data.append(line)

    return list(filter(lambda x: x, data))  # remove empty lines


def list_to_csv_row(tokens: list[str]) -> str:
    """Transform list of string into CSV row."""
    normalized_tokens = tokens.copy()

    if (diff := COLUMN_NUMBER - len(normalized_tokens)) > 0:
        normalized_tokens.extend([''] * diff)

    return ','.join([f'"\'{token}"' for token in tokens])


def convert_to_csv(data: list[str]) -> str:
    """Convert data to CSV."""
    result = []
    result.append(list_to_csv_row(HEADER))
    for line in data:
        tokens = line.split(' ')
        result.append(list_to_csv_row(tokens))
    return '\n'.join(result)


def write_csv_data(csv_data: str) -> None:
    """Write CSV data."""
    global args

    if (output_filename := args.output_filename):
        with open(output_filename, 'w', encoding='utf-8') as f:
            f.write(csv_data)
    else:
        print('Result:')
        print(csv_data)


def main() -> None:
    """Read data, convert to CSV."""
    global args

    parser = ArgumentParser()
    parser.add_argument(
        '-i',
        '--input',
        dest='input_filename',
        help='Read data from file (ignores STDIN)',
        metavar='INPUT_FILE',
        required=True,
    )
    parser.add_argument(
        '-o',
        '--output',
        dest='output_filename',
        help="Read data from file (doesn't write to STDOUT)",
        metavar='OUTPUT_FILE',
        required=True,
    )

    args = parser.parse_args()

    data = read_input_data()

    csv_data = convert_to_csv(data)

    write_csv_data(csv_data)


if __name__ == '__main__':
    main()
