KEY = [3,6,7,9,4,1,2,5,8,0]
L = len(KEY)
N = 10

def generate_s() -> list[int]:
    s = [i for i in range(N)]

    j = 0
    for i in range(N):
        j = (j + s[i] + KEY[i % L]) % N
        s[i], s[j] = s[j], s[i]

    return s


def gen_ciphertext(_s: list[int], text: list[int]) -> list[int]:
    s = _s.copy()
    ciphered = []

    i = 0
    j = 0

    for k in range(len(text)):
        i = (i + 1) % N
        j = (j + s[i]) % N
        s[i], s[j] = s[j], s[i]
        t = (s[i] + s[j]) % N

        ciphered.append(s[t])

    return ciphered


s = generate_s()
print(s)

ciphertext = gen_ciphertext(s, [0, 0, 0])
print(list(reversed(ciphertext)))
