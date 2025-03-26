def print_state(state: list[list[int]]) -> None:
    for row in state:
        for value in row:
            print(hex(value)[2:].rjust(2, '0'), end=' ')
        print()


# yeah, no, I'm not getting into all that
# 
# https://stackoverflow.com/questions/66115739/aes-mixcolumns-with-python
def mix_columns(a, b, c, d):
    return [
        (gmul(a, 2) ^ gmul(b, 3) ^ gmul(c, 1) ^ gmul(d, 1)),
        (gmul(a, 1) ^ gmul(b, 2) ^ gmul(c, 3) ^ gmul(d, 1)),
        (gmul(a, 1) ^ gmul(b, 1) ^ gmul(c, 2) ^ gmul(d, 3)),
        (gmul(a, 3) ^ gmul(b, 1) ^ gmul(c, 1) ^ gmul(d, 2)),
    ]


def gmul(a, b):
    if b == 1:
        return a
    tmp = (a << 1) & 0xff
    if b == 2:
        return tmp if a < 128 else tmp ^ 0x1b
    if b == 3:
        return gmul(a, 2) ^ a


N = 4

TEXT = [
    [0x1a, 0x94, 0xe8, 0x67],
    [0x96, 0x38, 0x4a, 0x5b],
    [0x12, 0xf9, 0xe2, 0x31],
    [0xa0, 0xdd, 0x8f, 0x88],
]

ROUND_KEY_1 = [
    [0x07, 0x14, 0x67, 0xd0],
    [0x09, 0x05, 0xa8, 0xd7],
    [0x6f, 0x57, 0xd6, 0xec],
    [0x2e, 0xbb, 0x0d, 0x5c],
]

# Round 1

# Stage 1. SubBytes (done by hand)
state = [
    [0xa2, 0x22, 0x9b, 0x85],
    [0x90, 0x07, 0xd6, 0x39],
    [0xc9, 0x99, 0x98, 0xc7],
    [0xe0, 0xc1, 0x73, 0xc4],
]

print('state after SubTypes')
print_state(state)
print()

# Stage 2. ShiftRows
for i in range(N):
    state[i] = state[i][i:] + state[i][:i]

print('state after ShiftRows')
print_state(state)
print()

# Stage 3. MixColumns
new_columns = []
for i in range(N):
    new_columns.append(mix_columns(state[0][i], state[1][i], state[2][i], state[3][i]))

for i in range(N):
    for j in range(N):
        state[i][j] = new_columns[j][i] & 0xff

print('state after MixColumns')
print_state(state)
print()

# Stage 4. AddRoundKey
for i in range(N):
    for j in range(N):
        state[i][j] = state[i][j] ^ ROUND_KEY_1[i][j]

print('state after AddRoundKey')
print_state(state)
print()