from math import factorial, exp

def rounded(n: float) -> float:
    return round(n, 6)

DATA = {
    0: 877,
    1: 63,
    2: 47,
    3: 7,
    4: 4,
    5: 1,
    6: 1,
}

n = value_sum = sum(DATA.values())
print('Сумма значений (n):', value_sum)

tmp = sum([k * v for k, v in DATA.items()])

x_ = rounded(tmp / value_sum)

print('X\u0304 =', x_)
print()

lambda_ = x_

p_i_data = {
    i: lambda_ ** i / factorial(i) * exp(- lambda_)
    for i, v in DATA.items()
}

nstar_i_data = {
    i: round(n * p_i)
    for i, p_i in p_i_data.items()
}

print('Табл1')
print('i'.ljust(3) + 'n_i'.ljust(4) + 'p_i'.ljust(9) + 'n*_i'.ljust(4))

for i in DATA.keys():
    print(f'{i}'.ljust(3), end='')
    print(f'{DATA[i]}'.ljust(4), end='')
    print(f'{rounded(p_i_data[i]):f}'.ljust(9), end='')
    print(f'{nstar_i_data[i]}'.ljust(4), end='')
    print()

print()

print('Сумма n_i:', sum(DATA.values()))
print('Сумма nstar_i:', sum(nstar_i_data.values()))
print()

for i in list(DATA.keys()):
    if nstar_i_data[i] < 5:
        prev_i = sorted(filter(lambda x: x < i, nstar_i_data.keys()))[-1]
        nstar_i_data[prev_i] += nstar_i_data.pop(i)
        DATA[prev_i] += DATA.pop(i)
        p_i_data.pop(i)

print('Табл2')
print('i'.ljust(3) + 'n_i'.ljust(4) + 'n*_i'.ljust(5) + 'im not writing that')

tanki_online_data = {
    i: (DATA[i] - v) ** 2 / v
    for i, v in nstar_i_data.items()
}

for i in DATA.keys():
    print(f'{i}'.ljust(3), end='')
    print(f'{DATA[i]}'.ljust(4), end='')
    print(f'{nstar_i_data[i]}'.ljust(5), end='')
    print(f'{rounded(tanki_online_data[i])}', end='')
    print()

print()

x_2_nabl = sum(tanki_online_data.values())

print('X_набл\u00B2 =', rounded(x_2_nabl))
