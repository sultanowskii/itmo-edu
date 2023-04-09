"""Get cooler cubes."""


def get_cooler_cubes(cubes: list[str]) -> tuple[list[str], list[str]]:
    """Get cooler cubes from current ones."""
    res = set()
    having_pairs = set()
    for cube in cubes:
        for i in range(len(cube)):
            if cube[i] == 'X':
                continue
            cube_c = [c for c in cube]
            cube_c[i] = '1' if cube_c[i] == '0' else '0'
            tmp = ''.join(cube_c)
            if tmp in cubes:
                having_pairs.add(tmp)
                having_pairs.add(cube)
                cube_c[i] = 'X'
                res.add(''.join(cube_c))

    return list(res), list(set(cubes) - having_pairs)


def main() -> None:
    """Yay."""
    prev_cubes = []
    depth = 0

    with open('original_cubes_0.txt', 'r') as f:
        cubes = f.read().replace(' ', '').split('\n')

    while set(prev_cubes) != set(cubes):
        print(f'K{depth}, {len(cubes)} total')
        for cube in cubes:
            print(''.join(cube))

        prev_cubes = cubes.copy()
        cubes, single_ones = get_cooler_cubes(cubes)

        if set(prev_cubes) == set(cubes):
            break

        print(f'Single ones ({len(single_ones)} total):')
        for single_one in single_ones:
            print(single_one)
        print()

        depth += 1


if __name__ == '__main__':
    main()
