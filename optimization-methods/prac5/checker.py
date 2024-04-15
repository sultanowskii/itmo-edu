import numpy as np

A = np.array([
    [2, -1, 1, 1, 0],
    [2, 3, -1, 0, 1],
])

B = np.array([1/3, 2/3])

C = np.array([5, 1, 2, 0, 0]).transpose()

for i in range(0, 5):
    for j in range(i+1, 5):
        if i == j:
            continue
        Ab = A[:, [i, j]]
        Ac = A[:, [n for n in range(0, 5) if n not in (i, j)]]
        Cb = C[[i, j]]
        Cc = C[[n for n in range(0, 5) if n not in (i, j)]]

        res = Cb @ np.linalg.inv(Ab)
        res = res @ Ac
        res = Cc - res
        if all(map(lambda x: x <= 0, res)):
            print('!!!!!!')
        print(f'basis:  {i + 1}, {j + 1}:')
        print(f'delta:')
        print(f'{res}')
        print()