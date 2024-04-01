from math import sqrt, log2, ceil

import matplotlib.pyplot as plt  # type: ignore

# [((x_min, x_max), y), ...]
PiecewiseFunc = list[tuple[tuple[float, float], float]]
Dots = list[tuple[float, float]]
VariationSeries = list[float]

DEFAULT_INPUT_FILENAME = 'input.txt'
DIAGRAM_FILENAME = 'diagram.png'
HISTOGRAM_FILENAME = 'hist.png'
POLYGON_FILENAME = 'poly.png'
PRECISION = 4
DELTA = 0.0001


def rounded(n):
    return round(n, PRECISION)


def read_input_data_from_file(filename: str) -> list[float]:
    with open(filename, 'r') as f:
        data = f.read().strip().split(' ')
        data = list(map(lambda token: float(token.strip()), data))

    return data


def get_variational_series(raw_data: list[float]) -> VariationSeries:
    """Получить вариационный ряд."""
    return sorted(raw_data)


def print_stat_distribution(data: VariationSeries) -> None:
    """Вывести статистическое распределение."""
    n = len(data)
    print('  xi  | ni')
    for x in data:
        print(f'{x}'.ljust(6) + f'| {data.count(x) / n}')


def get_min_value(data: VariationSeries) -> float:
    """Получить наименьшее значение выборки."""
    return min(data)


def get_max_value(data: VariationSeries) -> float:
    """Получить наибольшее значение выборки."""
    return max(data)


def get_sample_range(data: VariationSeries) -> float:
    """Получить размах выборки."""
    return rounded(get_max_value(data) - get_min_value(data))


def get_expected_value(data: VariationSeries) -> float:
    """Получить мат.ожидание"""
    uniques = set(data)
    n = len(data)

    result = 0
    for x in uniques:
        p = data.count(x) / n
        result += p * x
    
    return result

def get_average(data: VariationSeries) -> float:
    """Получить выборочное среднее."""
    return rounded(sum(data) / len(data))


def get_dispersion(data: VariationSeries) -> float:
    """Получить дисперсию."""
    average = get_average(data)

    result = 0
    for x in data:
        result += (x - average) ** 2
    
    return result


def get_standard_deviation(data: VariationSeries) -> float:
    """Получить среднеквадратичное отклонение."""
    return rounded(sqrt(get_dispersion(data)))


def get_empirical_distibution_function(data: VariationSeries) -> PiecewiseFunc:
    """Получить эмпирическую функцию распределения."""
    result = []
    result.append(((None, data[0]), 0))
    n = len(data)

    p = data.count(data[0]) / n

    for i in range(len(data) - 1):
        prob = data.count(data[i+1]) / n
        result.append(((data[i], data[i + 1]), p))
        p = rounded(p + prob)
    
    result.append(((data[-1], None), 1))

    return result


def print_empirical_distibution_function(func_data: PiecewiseFunc) -> None:
    """Напечатать эмпирическую функцию распределения."""
    for line in func_data:
        x_range, y = line
        x_min, x_max = x_range

        print(f'{y},'.ljust(6) + 'при', end=' ')

        if x_min:
            print(f'{x_min} <', end=' ')
        
        print('x', end=' ')

        if x_max:
            print(f'<= {x_max}', end=' ')

        print()


def draw_empirical_distibution_function_diagram(func_data: PiecewiseFunc) -> None:
    """Нарисовать график эмпирической функции распределения."""
    INF_LIMIT_DELTA = 0.2
    fig, ax = plt.subplots()

    for line in func_data:
        x_range, y = line
        x_min, x_max = x_range

        if not x_min and not x_max:
            return

        x_min = x_min if x_min is not None else x_max - INF_LIMIT_DELTA
        x_max = x_max if x_max is not None else x_min + INF_LIMIT_DELTA

        ax.hlines(y=y, xmin=x_min, xmax=x_max)

    ax.set_title('График эмпирической функции распределения')
    ax.set_xlabel('x')
    ax.set_ylabel('y')
    ax.grid()

    fig.savefig(DIAGRAM_FILENAME)

    print(f'График эмпирической функции распределения сохранен в {DIAGRAM_FILENAME}')


def get_interval_info(data: VariationSeries) -> float:
    """
    Получить:
        interval_count - количество интервалов
        h              - длину интервала
        x_s            - начало первого интервала
    """
    min_value = get_min_value(data)
    max_value = get_max_value(data)
    n = len(data)

    interval_count = ceil(1 + log2(n))

    # Формула Стерджеса
    h = rounded((max_value - min_value) / (interval_count - 1))

    x_s = rounded(min_value - h / 2)
    
    return (
        interval_count,
        h,
        x_s,
    )


def get_hist_data(data: VariationSeries) -> PiecewiseFunc:
    """Получить функцию для гистограммы частот."""
    result = []

    interval_count, h, x_s = get_interval_info(data)
    n = len(data)

    for i in range(interval_count):
        interval_start = rounded(x_s + i * h)
        interval_end = rounded(interval_start + h)

        value_in_interval_count = len(list(filter(lambda x: interval_start <= x < interval_end + DELTA, data)))

        # частота
        freq = value_in_interval_count / n
        # плотность частоты
        q = freq / h

        result.append([(interval_start, interval_end), rounded(q)])

    return result

def print_inteval_stat_distribution(data: VariationSeries) -> None:
    """Вывести интервальное статистическое распределение."""
    interval_count, h, x_s = get_interval_info(data)
    n = len(data)

    for i in range(interval_count):
        interval_start = rounded(x_s + i * h)
        interval_end = rounded(interval_start + h)

        value_in_interval_count = len(list(filter(lambda x: interval_start <= x < interval_end + DELTA, data)))

        # частота
        freq = value_in_interval_count / n
        print(f'[{interval_start}; {interval_end}): '.ljust(18) + f'{freq}')


def draw_hist(func_data: PiecewiseFunc) -> None:
    """Нарисовать гистограмму частот."""
    fig, ax = plt.subplots()

    for line in func_data:
        x_range, y = line
        x_min, x_max = x_range
        ax.bar((x_min + x_max) / 2, y, width=(x_max - x_min), align='center', alpha=0.7, label=f'[{x_min}, {x_max})')

    # Сдвигаем легенду так, чтобы она не перекрывала диаграмму
    legend = ax.legend(bbox_to_anchor=(1.04, 0.5), loc="center left", borderaxespad=0)

    ax.set_title('Гистограмма частот')
    ax.set_xlabel('x')
    ax.set_ylabel('y')
    ax.grid()

    # Умещаем легенду в изображение
    fig.savefig(HISTOGRAM_FILENAME, bbox_extra_artists=(legend,), bbox_inches='tight')

    print(f'Гистограмма частот сохранена в {HISTOGRAM_FILENAME}')


def get_frequency_polygon_data(data: VariationSeries)-> Dots:
    """Получить полигон частот."""
    result = []

    interval_count, h, x_s = get_interval_info(data)
    n = len(data)

    for i in range(interval_count):
        interval_start = rounded(x_s + i * h)
        interval_end = rounded(interval_start + h)
        mid = (interval_start + interval_end) / 2

        value_in_interval_count = len(list(filter(lambda x: interval_start <= x < interval_end + DELTA, data)))
        freq = value_in_interval_count / n

        result.append((mid, freq))

    return result


def draw_frequency_polygon(polyogn_data: Dots) -> None:
    """Нарисовать полигон частот."""
    fig, ax = plt.subplots()
    
    xs = list(map(lambda coords: coords[0], polyogn_data))
    ys = list(map(lambda coords: coords[1], polyogn_data))

    ax.plot(xs, ys, marker='o')

    ax.set_title('Полигон частот')
    ax.set_xlabel('x')
    ax.set_ylabel('y')
    ax.grid()

    fig.savefig(POLYGON_FILENAME)

    print(f'Полигон частот сохранен в {POLYGON_FILENAME}')


def get_fixed_dispersion(data):
    disp = get_dispersion(data)
    n = len(data)

    return rounded(n / (n - 1) * disp)


def main():
    input_filename = input(
        f'Введите имя файла с исходными данными (оставьте пустым для {DEFAULT_INPUT_FILENAME}): '
    ).strip()

    if not input_filename:
        input_filename = DEFAULT_INPUT_FILENAME

    try:
        input_data = read_input_data_from_file(input_filename)
    except OSError as e:
        print(f'Невозможно открыть файл "{input_filename}": {e}')
        return

    print()

    data = get_variational_series(input_data)
    print('Вариационный ряд:', data)

    print()

    print('Статистическое распределение:')
    print_stat_distribution(data)

    print()

    min_value = get_min_value(data)
    print('Минимальное значение:', min_value)

    max_value = get_max_value(data)
    print('Максимальное значение:', max_value)

    sample_range = get_sample_range(data)
    print('Размах выборки:', sample_range)

    print()

    expected_value = get_expected_value(data)
    print('Мат.ожидание:', expected_value)

    dispersion = get_dispersion(data)
    print('Дисперсия:', dispersion)

    fixed_dispersion = get_fixed_dispersion(data)
    print('Исправленная выборочная дисперсия:', fixed_dispersion)

    standard_deviation = get_standard_deviation(data)
    print('Среднеквадратичное отклонение:', standard_deviation)

    print()

    empirical_distibution_function_data = get_empirical_distibution_function(data)
    print('Эмпирическая функция распределения:')
    print_empirical_distibution_function(empirical_distibution_function_data)
    draw_empirical_distibution_function_diagram(empirical_distibution_function_data)

    print()

    interval_count, h, x_s = get_interval_info(data)
    print('Кол-во интервалов:', interval_count)
    print('Длина интервала:', h)
    print('x_нач:', x_s)

    print()

    print('Интервальное статистическое распределение:')
    print_inteval_stat_distribution(data)

    print()

    hist_data = get_hist_data(data)
    draw_hist(hist_data)

    print()

    frequency_polygon_data = get_frequency_polygon_data(data)
    draw_frequency_polygon(frequency_polygon_data)


if __name__ == '__main__':
    main()
