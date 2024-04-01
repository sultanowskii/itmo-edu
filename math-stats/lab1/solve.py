from math import ceil, sqrt

import matplotlib.pyplot as plt  # type: ignore

POLYGON_FILENAME = 'poly.png'
HISTOGRAM_FILENAME = 'hist.png'
DIAGRAM_FILENAME = 'diag.png'


def rounded(n):
    return round(n, 4)


def get_variational_series(raw_data):
    return sorted(raw_data)


def get_interval_info(data):
    k = ceil(sqrt(len(data)))

    x_max = max(data)
    x_min = min(data)
    interval_length = (x_max - x_min) / k
    start = x_min

    return k, interval_length, start


def draw_frequency_polygon(table) -> None:
    fig, ax = plt.subplots()

    ys = list(map(lambda part: part['n_i'], table))
    xs = list(map(lambda part: part['x_mid'], table))

    ax.plot(xs, ys, marker='o')

    ax.set_title('Полигон частот')
    ax.set_xlabel('ni')
    ax.set_ylabel('xi\'')
    ax.grid()

    fig.savefig(POLYGON_FILENAME)

    print(f'Полигон частот сохранен в {POLYGON_FILENAME}')


def draw_hist(table) -> None:
    fig, ax = plt.subplots()

    for part in table:
        x_start = part['x_start']
        x_end = part['x_end']
        dense = part['dense']
        ax.bar((x_start + x_end) / 2, dense, width=(x_end - x_start), align='center', alpha=0.7, label=f'{x_start} - {x_end}')

    # Сдвигаем легенду так, чтобы она не перекрывала диаграмму
    legend = ax.legend(bbox_to_anchor=(1.04, 0.5), loc='center left', borderaxespad=0)

    ax.set_title('Гистограмма относительных частот')
    ax.set_xlabel('xi')
    ax.set_ylabel('freq')
    ax.grid()

    # Умещаем легенду в изображение
    fig.savefig(HISTOGRAM_FILENAME, bbox_extra_artists=(legend,), bbox_inches='tight')

    print(f'Гистограмма частот сохранена в {HISTOGRAM_FILENAME}')


def draw_empirical_distibution_function_diagram(table) -> None:
    INF_LIMIT_DELTA = 0.2
    fig, ax = plt.subplots()

    xs = [table[0]['x_start']] + list(map(lambda part: part['x_end'], table))
    ys = [0] + list(map(lambda part: part['F*'], table))

    ax.scatter(xs, ys)
    ax.plot(xs, ys)

    ax.set_title('Эмпирическая функция распределения')
    ax.set_xlabel('xi\'')
    ax.set_ylabel('ni')
    ax.grid()

    fig.savefig(DIAGRAM_FILENAME)

    print(f'Эмпирической функции распределения сохранен в {DIAGRAM_FILENAME}')


def print_inteval_stat_distribution(data):
    interval_count, interval_length, start = get_interval_info(data)
    n = len(data)

    result = []

    for i in range(interval_count):
        number = i + 1

        interval_start = rounded(start + i * interval_length)
        interval_end = rounded(interval_start + interval_length)

        if number == interval_count:
            ending_brace = ']'
            value_in_interval_count = len(list(filter(lambda x: interval_start <= x <= interval_end, data)))
        else:
            ending_brace = ')'
            value_in_interval_count = len(list(filter(lambda x: interval_start <= x < interval_end, data)))

        # частота, p*_i
        freq = value_in_interval_count / n

        # высота столбика гистограммы
        h_i = rounded(freq / interval_length)

        mid = rounded((interval_start + interval_end) / 2)

        result.append(dict(
            i=i,
            x_start=interval_start,
            x_end=interval_end,
            x_mid=mid,
            n_i=value_in_interval_count,
            freq_i=freq,
            dense=h_i,
        ))

        print(f'{number}: [{interval_start}; {interval_end}{ending_brace}, {mid=}, {value_in_interval_count=}, {freq=}, {h_i=}')

    print()

    q = 0
    x_min = min(data)
    print('F*(x) = n_x / n')
    print(f'F*({x_min}) = {q}')
    for i in range(interval_count):
        x = result[i]['x_end']
        f = result[i]['freq_i']
        q = rounded(q + f)
        result[i]['F*'] = q
        print(f'F*({x}) = {q}')
    print()

    return result


def read_data():
    with open('input.txt', 'r') as f:
        return [float(t) for t in f.read().split(' ')]


def main():
    input_data = read_data()

    data = get_variational_series(input_data)
    print('Вариационный ряд:', data)
    print()

    n = len(data)
    print(f'{n=}')

    k = ceil(sqrt(n))

    x_max = max(data)
    x_min = min(data)

    # длина интервала
    interval_length = (x_max - x_min) / k

    print(f'{k=} {interval_length=} {x_min=} {x_max=}')
    print()

    table = print_inteval_stat_distribution(data)

    draw_frequency_polygon(table)

    draw_hist(table)

    draw_empirical_distibution_function_diagram(table)


if __name__ == '__main__':
    main()
