from math import sqrt

def rounded(n: float) -> float:
    return round(n, 6)

N = 150
SUM = 1623
SUM_OF_SQUARES = 17814.36
Y = 0.99
QUANTILE = 2.575

avg = rounded(SUM / N)

print('X\u0304 =', avg)

sigma2 = rounded(SUM_OF_SQUARES / N - avg ** 2)

print('\u03C3\u00B2 =', sigma2)

sigma = rounded(sqrt(sigma2))

print('\u03C3 =', sigma)

phi = (1 + Y) / 2

print('\u03D5 =', phi)
print('квантиль нужно найти по таблице Прил.1')
print()

delta = rounded(QUANTILE * sigma / sqrt(N))

lower = avg - delta
higher = avg + delta

print(f'Доверительный интервал: {lower} < m < {higher}')
