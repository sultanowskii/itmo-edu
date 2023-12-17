intersections = [
    0b111110000000000,
    0b110001000000000,
    0b101001111011101,
    0b100101111010101,
    0b100010001000101,
    0b011101001000101,
    0b001100101000101,
    0b001100011000101,
    0b001111111100000,
    0b000000001111101,
    0b001100000110101,
    0b001000000101101,
    0b001111110111110,
    0b000000000000111,
    0b001111110111011,
]

paths = []
path_set = set()


def gen(n: int, path: list):
    if n == 0b111111111111111:
        to_check = ' '.join([str(q) for q in sorted(path)])
        if to_check not in paths:
            path_set.add(to_check)
            paths.append(' '.join([str(q) for q in path]))
        return

    n_s = bin(n)[2:].rjust(15, '0')
    zero_indexes = [i for i in range(15) if n_s[i] == '0']

    for index in zero_indexes:
        tmp = n | intersections[index]
        path_copy = path.copy()
        path_copy.append(index + 1)
        gen(tmp, path_copy.copy())

    return


for index, number in enumerate(intersections):
    gen(number, [index + 1])

for path in paths:
    print(path)
