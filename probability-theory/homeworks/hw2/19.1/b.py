import matplotlib.pyplot as plt
from math import sqrt

from common import *

L = 9
OMEGA = '\u03C9'
POLYGON_FILENAME = 'poly.png'
HISTOGRAM_FILENAME = 'hist.png'
DIAGRAM_FILENAME = 'diag.png'

def get_freq(data, start, end):
    c = 0
    for n in data:
        if start <= n < end:
            c += 1
    return c

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
    ax.set_ylabel('Wi')
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


# === Б ===

data, rows, columns = read_data('a.csv')

n = len(data)

x_max = max(data)
x_min = min(data)

print('x_min =', x_min)
print('x_max =', x_max)

w = rounded(x_max - x_min)

print(f'{OMEGA} =', w)

h = rounded(w / L)

print(f'h = {OMEGA} / l =', h)
print()

freq_sum = 0

table1 = []

print('i start  end   mid  ni  Wi  Wi/h')
for i in range(L):
    x_start = rounded(x_min + h * i)
    x_end = rounded(x_start + h)
    x_mid = rounded((x_start + x_end) / 2)
    n_i = get_freq(data, x_start, x_end)

    if i == L - 1:
        n_i += data.count(x_max)
    freq_sum += n_i

    W_i = n_i / n

    dense = W_i / h

    print(f'{i + 1} {x_start} {x_end} {x_mid:.3f} {n_i:2} {W_i:.2f} {dense}')

    table1.append(
        dict(
            i=i,
            x_start=x_start,
            x_end=x_end,
            x_mid=x_mid,
            n_i=n_i,
            W_i=W_i,
            dense=dense,
        ),
    )

print()
assert freq_sum == n

q = 0
print('F*(x) = n_x / n')
print(f'F*({x_min}) = {q}')
for i in range(L):
    x = table1[i]['x_end']
    f = table1[i]['W_i']
    q = rounded(q + f)
    table1[i]['F*'] = q
    print(f'F*({x}) = {q}')
print()

# === В ===

draw_frequency_polygon(table1)

draw_hist(table1)

draw_empirical_distibution_function_diagram(table1)

print()

# === Г ===

print('i    range      mid     ni   ni*mid  mid^2  ni*mid^2')
for i, v in enumerate(table1):
    table1[i]['n_i*x_mid'] = rounded(v['n_i'] * v['x_mid'])
    table1[i]['x_mid^2'] = rounded(v['x_mid'] ** 2)
    table1[i]['n_i*x_mid^2'] = rounded(v['n_i'] * table1[i]['x_mid^2'])

    print(f'{i + 1} {v["x_start"]} - {v["x_end"]} {v["x_mid"]:.3f} {v["n_i"]:6.3f} {v["n_i*x_mid"]:4.3f} {v["x_mid^2"]:.6f} {v["n_i*x_mid^2"]:.6f}')

print()

x_selective_avg = rounded(sum(list(map(lambda v: v['n_i*x_mid'], table1))) / n)
print('x\u0304 =', x_selective_avg)

dispersion = rounded(sum(list(map(lambda v: v['n_i*x_mid^2'], table1))) / 100 - x_selective_avg ** 2)
print(f'D_в = {dispersion:.6f}')

deviation = rounded(sqrt(dispersion))
print(f'\u03C3_в = {deviation:.6f}')

print()

dispersion_fixed = round((n / (n - 1) * dispersion), 8)
print(f'D\u0303_в = {dispersion_fixed:.8f}')

deviation_fixed = round(sqrt(dispersion_fixed), 8)
print(f'\u03C3\u0303_в = {deviation_fixed:.8f}')
print()

# === Д ===

table2 = [
    {
        'x_i': v['x_start'],
        'x_i+1': v['x_end'],
        'x_i-x_mid': rounded(v['x_start'] - x_selective_avg),
        'x_i+1-x_mid': rounded(v['x_end'] - x_selective_avg),
    }
    for v in table1
]

for i, v in enumerate(table2):
    table2[i]['z_i'] = round(v['x_i-x_mid'] / deviation, 2)
    table2[i]['z_i+1'] = round(v['x_i+1-x_mid'] / deviation, 2)

table2[0]['z_i'] = ''
table2[0]['x_i-x_mid'] = ''
table2[-1]['z_i+1'] = ''
table2[-1]['x_i+1-x_mid'] = ''

print('i x_i   x_i+1 xi-xm    xi1-xm    zi    zi1')
for i, v in enumerate(table2):
    print(f'{i+1} {v["x_i"]} {v["x_i+1"]} {v["x_i-x_mid"]:8} {v["x_i+1-x_mid"]:8} {v["z_i"]:5} {v["z_i+1"]:5}')

print()
