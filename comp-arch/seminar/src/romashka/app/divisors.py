from math import sqrt, ceil


def find_divisors(n: int) -> list[int]:
    if n <= 0:
        return []

    result = {1, n}

    for i in range(2, int(ceil(sqrt(n)) + 1)):
        if n % i == 0:
            result.add(i)
            result.add(n // i)

    return sorted(list(result))
