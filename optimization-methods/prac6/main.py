from copy import deepcopy
from random import random

from random_extra import (
    get_random_unique_shuffles,
    get_weighted_random_unique_index_pairs,
    get_random_pair_in_range,
)


DISTANCES_BETWEEN_CITIES = {
    1: {
        2: 1,
        3: 7,
        4: 2,
        5: 8,
    },
    2: {
        1: 2,
        3: 10,
        4: 3,
        5: 1,
    },
    3: {
        1: 7,
        2: 10,
        4: 2,
        5: 6,
    },
    4: {
        1: 2,
        2: 3,
        3: 2,
        5: 4,
    },
    5: {
        1: 8,
        2: 1,
        3: 6,
        4: 4,
    }
}
CITIES = 5
ITERATIONS = 100
N = POPULATION_SIZE = 4
MUTATION_CHANCE = 0.01

Route = list[int]
Population = list[Route]


def c(r: Route) -> str:
    """Возвращает строковое представление пути."""
    return ''.join(str(c) for c in r)


def print_route(route: Route) -> None:
    """Выводит путь."""
    print(f'{c(route)} (f={calculate_route_total_distance(route)})')


def print_route_list(population: Population, offset: int = 0) -> None:
    """Выводит список путей."""
    for i, route in enumerate(population):
        print(f'{offset + i + 1}:', end=' ')
        print_route(route)


def calculate_route_total_distance(route: Route) -> int:
    """
    Считает суммарное расстояние маршрута.

    f.
    """
    city_count = len(route)

    total_distance = 0
    for i in range(city_count - 1):
        total_distance += DISTANCES_BETWEEN_CITIES[route[i]][route[i + 1]]
    
    # прибавляем расстояние от последнего до первого, т.к. в конце мы возвращаемся в изначальный город.
    total_distance += DISTANCES_BETWEEN_CITIES[route[city_count - 1]][route[0]]

    return total_distance


def create_random_population() -> Population:
    """Создает случайную популяцию."""
    dummy = [city for city in range(1, CITIES + 1)]
    return get_random_unique_shuffles(dummy, N)


def calculate_breeding_chances(population: Population) -> list[float]:
    """Возвращает список вероятностей участия в размножении особей."""
    f_sum = 0
    chances: list[float] = []
    for s in population:
        f = calculate_route_total_distance(s)
        chances.append(f)
        f_sum += f
    
    for i, v in enumerate(chances):
        chances[i] = v / f_sum

    return chances


def get_pairs_for_breeding(population: Population) -> list[tuple[int, int]]:
    """
    Возвращает набор пар индексов особей для размножения.
    """
    return get_weighted_random_unique_index_pairs(calculate_breeding_chances(population), N // 2)


def print_parent_genes_with_divide(route: Route, l: int, r: int) -> None:
    """Выводит родителя."""
    for i in range(len(route)):
        if l == i or i == r:
            print('|', end='')
        else:
            print(' ', end='')
        print(route[i], end='')

    if r == len(route):
        print('|', end='')

    print()


def crossover(a: Route, b: Route) -> tuple[Route, Route]:
    """Оператор скрещивания."""
    l, r = get_random_pair_in_range(0, CITIES)
    print_parent_genes_with_divide(a, l, r)
    print_parent_genes_with_divide(b, l, r)

    child2_inner = a[l:r]
    child1_inner = b[l:r]

    child1_outer = [c for c in a[l + 1:] + a[:l + 1] if c not in child1_inner]
    child2_outer = [c for c in b[l + 1:] + b[:l + 1] if c not in child2_inner]

    child1 = child1_outer[:l] + child1_inner + child1_outer[l:]
    child2 = child2_outer[:l] + child2_inner + child2_outer[l:]

    return child1, child2


def create_children(parent_pairs: list[tuple[Route, Route]]) -> list[Route]:
    """
    Возвращает потомков, полученных с помощью оператора скрещивания для каждой пары родителей.
    """
    children = []

    for parent_pair in parent_pairs:
        child1, child2 = crossover(*parent_pair)
        children.append(child1)
        children.append(child2)

    return children


def mutate(route: Route) -> Route:
    """Оператор мутации."""
    new_route = deepcopy(route)

    a, b = get_random_pair_in_range(0, CITIES - 1)
    new_route[a], new_route[b] = new_route[b], new_route[a]

    print(f'Гены под номерами {a + 1} и {b + 1} меняются местами')

    return new_route


def reduce(population: Population) -> Population:
    """Оператор редукции."""
    reduced_population = deepcopy(population)
    
    reduced_population.sort(key=lambda r: calculate_route_total_distance(r))

    return reduced_population[:4]


def main() -> None:
    # Этап 1
    population = create_random_population()

    for i in range(ITERATIONS):
        print(f'Популяция #{i}')
        print_route_list(population)
        print()

        # Этап 2
        breeding_pair_indexes = get_pairs_for_breeding(population)
        breeding_pairs = [
            (population[first_index], population[second_index])
            for first_index, second_index in breeding_pair_indexes
        ]

        print('Пары родителей для размножения:')
        for first_index, second_index in breeding_pair_indexes:
            print(f'({first_index + 1}, {second_index + 1})')
        print()

        # Этап 3
        print('Размножение:')
        children = create_children(breeding_pairs)
        print()

        print('Потомки:')
        print_route_list(children, len(population))
        print()

        # Этап 4
        print('Мутации:')
        for i, v in enumerate(children):
            if random() < MUTATION_CHANCE:
                print(f'Мутация {i + 1} потомка:')
                children[i] = mutate(v)
        print()

        # Этап 5
        population.extend(children)
        print('Расширенная популяция:')
        print_route_list(population)
        print()

        # Этап 6
        population = reduce(population)
        print('Сокращенная популяция:')
        print_route_list(population)
        print()

        print('-------')

    # Этап 8
    min_f = 10 ** 100
    min_route: Route = []

    for route in population:
        f = calculate_route_total_distance(route)
        if f < min_f:
            min_f = f
            min_route = route
    
    print('Лучший маршрут:')
    print_route(min_route)


if __name__ == '__main__':
    main()
