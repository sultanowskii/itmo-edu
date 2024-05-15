from copy import deepcopy
from random import shuffle, random, randint, sample
from typing import Any

def get_random_unique_shuffles(base: list[Any], m: int) -> list[list[Any]]:
    """Возвращает `m` случайных уникальных перестановок `base`."""
    result = []
    for _ in range(m):
        tmp = deepcopy(base)
        # перемешиваем до тех пор, пока не добьемся уникальности
        shuffle(tmp)
        while tmp in result:
            shuffle(tmp)
        result.append(tmp)
    return result

def get_weighted_random_index(weights: list[float]) -> int:
    """Возвращает случайный индекс элемента, основываясь на весах."""
    c = random()
    total: float = 0
    for i in range(len(weights)):
        total += weights[i]
        if c <= total:
            return i
    return 10 ** 100

def get_weighted_random_unique_index_pairs(weights: list[float], m: int) -> list[tuple[int, int]]:
    """
    Возвращает взвешенно (`weights`) случайный набор из `m` уникальных пар индексов.
    """
    index_pairs = []
    for _ in range(m):
        pair: tuple[int, int] = (0, 0)
        pair_already_exists = True
        while pair_already_exists:
            first_index = get_weighted_random_index(weights)
            second_index = first_index
            while first_index == second_index:
                second_index = get_weighted_random_index(weights)
            tmp = {first_index, second_index}
            pair = (tmp.pop(), tmp.pop())
            pair_already_exists = pair in index_pairs
        index_pairs.append(pair)
    return index_pairs

def get_random_pair_in_range(start: int, end: int) -> tuple[int, int]:
    """Возвращает случайную пару неодинаковых чисел в диапазоне."""
    first, second = sorted(sample(range(start, end + 1), 2))
    return (first, second)
