MESSAGE_LENGTH = 7
BIT_NAMES = {
    1: 'r1',
    2: 'r2',
    3: 'i1',
    4: 'r3',
    5: 'i2',
    6: 'i3',
    7: 'i4',
}


def message_characters_are_valid(m: str) -> bool:
    return all(map(lambda x: x in '01', m))


def message_length_is_valid(m: str) -> bool:
    return len(m) == MESSAGE_LENGTH


def get_error_position(m: str) -> int:
    m_numbers = [int(c) for c in m]

    checksum1 = m_numbers[0] ^ m_numbers[2] ^ m_numbers[4] ^ m_numbers[6]
    checksum2 = m_numbers[1] ^ m_numbers[2] ^ m_numbers[5] ^ m_numbers[6]
    checksum3 = m_numbers[3] ^ m_numbers[4] ^ m_numbers[5] ^ m_numbers[6]

    error_syndrome = int(f'{checksum3}{checksum2}{checksum1}', 2)

    return error_syndrome


def get_information_bits(m: str) -> str:
    return f'{m[2]}{m[4]}{m[5]}{m[6]}'


def swap_message_bit(m: str, pos: int) -> str:
    if pos < 1 or pos > MESSAGE_LENGTH:
        return m
    new_bit_value = '0' if m[pos - 1] == '1' else '1'
    return m[:pos - 1] + new_bit_value + m[pos:]


def main() -> None:
    print('Enter the sequence of 7 bits (0/1s only):')
    message = input()

    if not message_characters_are_valid(message):
        print('Only 0s or 1s!')
        return

    if not message_length_is_valid(message):
        print('The message has to be of length 7!')
        return

    error_position = get_error_position(message)

    fixed_message = swap_message_bit(message, error_position)

    information_bits = get_information_bits(fixed_message)

    print('Information:')
    print(information_bits)

    if error_position == 0:
        print('No errors detected')
    else:
        bit_name = BIT_NAMES[error_position]
        print(f'Error in {bit_name} (position {error_position})')


if __name__ == '__main__':
    main()
