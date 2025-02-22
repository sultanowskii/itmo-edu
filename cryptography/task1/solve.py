ALPH = 'абвгдеёжзийклмнопрстуфхцчшщъыьэюя'
N = len(ALPH)


def ctoi(c: str) -> int:
    """Char to index."""
    return ALPH.index(c)


def itoc(i: int) -> str:
    """Index to char."""
    index = i
    if index >= N - 1:
        index = index % N
    elif index < 0:
        index = N - abs(index)
    return ALPH[index]


def vigenere_encode(phrase: str, key: str) -> str:
    phrase_length = len(phrase)
    key_length = len(key)

    return ''.join(
        [
            itoc(ctoi(phrase[i]) + ctoi(key[i % key_length]))
            for i in range(phrase_length)
        ]
    )


def vigenere_decode(phrase: str, key: str) -> str:
    phrase_length = len(phrase)
    key_length = len(key)

    return ''.join(
        [
            itoc(ctoi(phrase[i]) - ctoi(key[i % key_length]))
            for i in range(phrase_length)
        ]
    )


print(vigenere_encode('мавритания', 'борона'))
print(vigenere_decode('ноьэюотащн', 'борона'))
