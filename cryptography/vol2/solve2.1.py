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


def feistel_network_1_round(m: Data, k: Key, f: Callable[[Data, Key], Data]) -> Data:
    left = m[:len(m)//2].copy()
    right = m[len(m)//2:].copy()

    right_ciphered = f(right, k)
    left_xored = xor(right_ciphered, left)

    left, right = right, left_xored

    return left + right


M = str_to_data('0000111001')
K = str_to_data('00110')

result = feistel_network_1_round(M, K, xor)

print(''.join([str(c) for c in result]))
