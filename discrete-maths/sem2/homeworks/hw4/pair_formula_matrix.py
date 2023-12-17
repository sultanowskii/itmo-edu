sets = [
    {1, 6, 7, 8, 10, 14},
    {1, 6, 7, 8, 11, 12, 14},
    {1, 9, 11, 12, 14},
    {1, 9, 13, 15},
    {2, 3, 4, 5, 10, 14},
    {2, 4, 5, 12, 14},
    {2, 5, 7, 8, 10, 14},
    {2, 5, 7, 8, 11, 12, 14},
    {2, 9, 11, 12, 14},
    {2, 9, 13, 15},
    {5, 6, 7, 8, 10, 14},
    {5, 6, 7, 8, 11, 12, 14},
]

for i, a in enumerate(sets):
    for j, b in enumerate(sets):
        if i == j:
            print('"0"', end=',')
            continue

        f = len(a) + len(b) - len(a.intersection(b))
        print(f'"{f}"', end=',')

    print()
