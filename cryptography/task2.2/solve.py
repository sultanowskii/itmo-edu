from typing import Callable

Data = list[int]
Key = list[int]


def str_to_data(s: str) -> Data:
    return [int(c) for c in s]


def data_to_str(d: Data) -> str:
    return ''.join([str(c) for c in d])


def xor(data1: Data, data2: Data) -> Data:
    result: Data = []
    for a, b in zip(data1, data2):
        result.append(a ^ b)
    return result


def feistel_network(m: Data, keys: list[Key], f: Callable[[Data, Key], Data]) -> Data:
    left = m[:len(m)//2].copy()
    right = m[len(m)//2:].copy()

    for k in keys:
        right_ciphered = f(right, k)
        left_xored = xor(right_ciphered, left)

        left, right = right, left_xored

    return left + right

M = str_to_data('10101101010011100010')
K1 = str_to_data('1010001110')
K2 = str_to_data('1101001001')

result = feistel_network(M, [K1, K2], xor)

print(data_to_str(result))
