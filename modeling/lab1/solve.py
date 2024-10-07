from math import sqrt
from typing import Any

import matplotlib.pyplot as plt

ProcessResult = dict[str, Any]


def calc_expectation(data: list[float]) -> float:
    """
    Рассчитывает мат.ожидание (MX).

    Характеризует положение случайной величины на числовой оси,
    т.е. показывает некоторое среднее вероятностное
    (не путать со средним арифметическим)
    значение, около которого группируются все возможные значения случайной величины.
    """
    return sum(data) / len(data)


def calc_dispersion(data: list[float]) -> float:
    """
    Рассчитывает дисперсию (DX).
    
    Дисперсия случайной величины, как и второй начальный момент,
    характеризует разброс значений случайной величины, но, в отличие от
    второго начального момента, относительно математического ожидания,
    и имеет размерность квадрата случайной величины.
    """
    expectation = calc_expectation(data)
    return calc_expectation([n**2 for n in data]) - expectation**2


def calc_standard_deviation(data: list[float]) -> float:
    """
    Рассчитывает среднеквадратичное отклонение (σ).

    Характеристика разброса, размерность которой совпадает с размерностью случайной величины.
    """
    dispersion = calc_dispersion(data)
    return sqrt(dispersion)


def calc_variation_coefficient(data: list[float]) -> float:
    """
    Рассчитывает коэффициент вариации (v).

    Безразмерная характеристика разброса случайных величин, определенных в области положительных значений.
    """
    expectation = calc_expectation(data)
    standard_deviation = calc_standard_deviation(data)
    return standard_deviation / expectation


def calc_confidence_interval(data: list[float]) -> tuple[float, float]:
    """
    Рассчитывает доверительный интервал.

    Это интервал, в пределах которого с некоторой заданной вероятностью,
    называемой доверительной вероятностью, находится истинное значение
    рассматриваемого параметра.
    """
    # TODO: Центральная предельная теорема?
    # Можно также попробовать bootstrapping
    # - https://en.wikipedia.org/wiki/Bootstrapping_(statistics)
    # - https://stackoverflow.com/questions/44392978/compute-a-confidence-interval-from-sample-data-assuming-unknown-distribution
    return 1, 1


def relative_deviation(before: float, after: float) -> float:
    """
    Возвращает относительное отклонение.
    """
    return abs(before - after) / before


def print_probability_as_percentage(probability: float) -> None:
    percentage = probability * 100
    print(f'{percentage:.2f}%', end='')


def calc_values(data: list[float], representative: ProcessResult = dict()) -> ProcessResult:
    print(f'Последовательность из {len(data)} случайных величин')

    expectation = calc_expectation(data)
    print(f'Мат.ожидание: {expectation}')
    expectation_relative_deviation = relative_deviation(expectation, representative.get('expectation', expectation))
    print(f'Относительное отклонение мат.ожидания:', end=' ')
    print_probability_as_percentage(expectation_relative_deviation)
    print()

    dispersion = calc_dispersion(data)
    print(f'Дисперсия: {dispersion}')
    dispersion_relative_deviation = relative_deviation(dispersion, representative.get('dispersion', dispersion))
    print(f'Относительное отклонение дисперсии:', end=' ')
    print_probability_as_percentage(dispersion_relative_deviation)
    print()

    standard_deviation = calc_standard_deviation(data)
    print(f'Среднеквадратичное отклонение: {standard_deviation}')
    standard_deviation_relative_deviation = relative_deviation(standard_deviation, representative.get('standard_deviation', standard_deviation))
    print(f'Относительное отклонение среднеквадратичного отклонения:', end=' ')
    print_probability_as_percentage(standard_deviation_relative_deviation)
    print()

    variation_coefficient = calc_variation_coefficient(data)
    print(f'Коэффициент вариации: {variation_coefficient}')
    variation_coefficient_relative_deviation = relative_deviation(variation_coefficient, representative.get('variation_coefficient', variation_coefficient))
    print(f'Относительное отклонение коэффициента вариации:', end=' ')
    print_probability_as_percentage(variation_coefficient_relative_deviation)
    print()

    confidence_interval = calc_confidence_interval(data)
    confidence_interval_left, confidence_interval_right = confidence_interval[0], confidence_interval[1]
    print(f'Доверительный интервал: {confidence_interval_left}; {confidence_interval_right}')
    representative_confidence_interval_left, representative_confidence_interval_right = representative.get('confidence_interval', confidence_interval)
    confidence_interval_left_relative_deviation = relative_deviation(confidence_interval_left, representative_confidence_interval_left)
    confidence_interval_right_relative_deviation = relative_deviation(confidence_interval_right, representative_confidence_interval_right)
    print(f'Относительное отклонение доверительного интервала:', end=' ')
    print_probability_as_percentage(confidence_interval_left_relative_deviation)
    print(';', end=' ')
    print_probability_as_percentage(confidence_interval_right_relative_deviation)
    print()

    print()

    return dict(
        expectation=expectation,
        dispersion=dispersion,
        standard_deviation=standard_deviation,
        variation_coefficient=variation_coefficient,
        confidence_interval=confidence_interval,
    )


def calc_autocorrelation_coefficient(data: list[float], offset: int):
    """
    Рассчитывает коэффициент автокорреляции для сдвига offset.

    https://univer-nn.ru/ekonometrika/avtokorrelyaciya-koefficient-avtokorrelyacii/ 
    """
    left_part = data[:len(data) - offset]
    right_part = data[offset:]

    left_part_avg = sum(left_part) / len(left_part)
    right_part_avg = sum(right_part) / len(right_part)

    a = sum(
        [
            (x - left_part_avg) * (y - right_part_avg)
            for x, y in zip(left_part, right_part)
        ]
    )
    b = sqrt(
        sum([(x - left_part_avg) ** 2 for x in left_part]) * sum([(y - right_part_avg) ** 2 for y in right_part])
    )

    return a / b


def draw_value_chart(data: list[float]):
    sorted_data = sorted(data)
    length = len(data)

    plt.rc('figure', figsize=(16, 9))

    fig, axs = plt.subplots(2, 2)
    
    axs[0, 0].set_title("График значений")
    axs[0, 0].plot(range(1, length + 1), data)
    axs[0, 0].set_xlabel("Номер/индекс")
    axs[0, 0].set_ylabel("Значение")

    axs[0, 1].set_title("График значений (отсортированных)")
    axs[0, 1].plot(range(1, length + 1), sorted_data)
    axs[0, 1].set_ylabel("Значение")

    autocorrelation_coefficients = [
        calc_autocorrelation_coefficient(data, i)
        for i in range(1, length - 1)
    ]

    axs[1, 0].set_ylim([-1, 1])
    axs[1, 0].set_title("Коэффициенты автокорелляции")
    axs[1, 0].plot(range(1, len(autocorrelation_coefficients) + 1), autocorrelation_coefficients)

    axs[1, 1].set_title("Гистограмма распределения частот")
    axs[1, 1].hist(sorted_data, bins=length, edgecolor="black", linewidth=1, weights=[1 / length for _ in data])
    axs[1, 1].set_xlabel("Значение")
    axs[1, 1].set_ylabel("Частота значения")

    plt.tight_layout()

    fig.savefig(f'chart_{length}.png')
    plt.close(fig)


def process(data: list[float]):
    draw_value_chart(data)


def main():
    with open('data.txt', 'r') as f:
        data = [float(c) for c in f.read().strip().split('\n')]
    
    representative = calc_values(data)

    counts = [10, 20, 50, 100, 200]
    
    for count in counts:
        data_to_process = data[:count]
        calc_values(data_to_process, representative)

    counts.append(300)

    for count in counts:
        data_to_process = data[:count]
        process(data_to_process)


if __name__ == '__main__':
    main()
