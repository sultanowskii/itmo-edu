from math import sqrt

def rounded(n: float) -> float:
    return round(n, 6)

DATA = [154, 152, 146, 161, 148, 153, 159, 160, 154, 146, 150, 155, 161]
Y = 0.9
QUANTILE = 1.782

print('Data: ' + ' '.join([str(n) for n in DATA]))
print()

n = len(DATA)

print('n =', n)
print()

avg = rounded(sum(DATA) / n)

print('X\u0304 =', avg)
print()

sum_of_squares = sum([n ** 2 for n in DATA])

print('sum(i=1, n)(x\u00B2_i) =', sum_of_squares)

dx = rounded(sum_of_squares / n - avg ** 2)

print('DX =', dx)
s2 = rounded(n / (n - 1) * dx)
print('s\u00B2 =', s2)
s = rounded(sqrt(s2))
print('s =', s)
print()

_lol = (1 + Y) / 2

print('(1 + y)/2 =', _lol)
print('квантиль нужно найти в таблице Прил.2')
print()

quantile = QUANTILE

delta = rounded(quantile * (s / sqrt(n)))

lower = avg - delta
higher = avg + delta

print(f'Доверительный интервал: {lower} < m < {higher}')
