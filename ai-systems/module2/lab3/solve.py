import csv
from dataclasses import dataclass
from typing import Any, Callable
import matplotlib.pyplot as plt # type: ignore
import numpy as np
from math import sqrt
from random import random


def rounded(n: float) -> float:
    return round(n, 6)


def convert_bool(s: str) -> bool:
    return s == 'Yes'


@dataclass
class StudentInfo:
    hours_studied: int
    previous_scores: int
    extracurricular_activities: bool
    sleep_hours: int
    sample_question_papers_practiced: int
    performance_index: float


@dataclass
class NormalizedStudentInfo:
    hours_studied: float
    previous_scores: float
    extracurricular_activities: float
    sleep_hours: float
    sample_question_papers_practiced: float
    performance_index: float


StudentPropertyGetter = Callable[[StudentInfo | NormalizedStudentInfo], Any]


def draw_distribution_histogram(name: str, data: list[int | float], subplot: Any) -> None:
    values = sorted(data)
    min_value = min(values)
    max_value = max(values)

    subplot.set_title(name)
    subplot.hist(
        values,
        bins=int(max_value - min_value),
        linewidth=1,
        edgecolor='black',
    )


def print_stats(name: str, data: list[int | float]) -> None:
    values = sorted(data)
    length = len(values)
    average = sum(values) / length
    min_value = min(values)
    max_value = max(values)
    # sigma
    deviation = rounded(sqrt(sum((x - average)**2 for x in values) / length))

    print(f'### Статистика ({name})')
    print(f'- Среднее значение: {average}')
    print(f'- Минимальное значение: {min_value}')
    print(f'- Максимальное значение: {max_value}')
    print(f'- Стандартное отклонение: {deviation}')
    print(f'- Квантили:')
    for percentile in [25, 50, 75, 90]:
        index = int(length * (percentile / 100))
        quantile = values[index]
        print(f'  - {percentile}% процентиль: {quantile}')
    print()


def stats(name: str, data: list[int | float], subplot: Any) -> None:
    draw_distribution_histogram(name, data, subplot)
    print_stats(name, data)


def normalize(data: list[int | float]) -> list[float]:
    min_value = min(data)
    max_value = max(data)
    delta = max_value - min_value
    
    return [(v - min_value) / delta for v in data]


def normalize_students(students: list[StudentInfo]) -> list[NormalizedStudentInfo]:
    hours_studied = normalize([s.hours_studied for s in students])
    previous_scores = normalize([s.previous_scores for s in students])
    extracurricular_activities = normalize([s.extracurricular_activities for s in students])
    sleep_hours = normalize([s.sleep_hours for s in students])
    sample_question_papers_practiced = normalize([s.sample_question_papers_practiced for s in students])
    performance_index = normalize([s.performance_index for s in students])

    normalized_students = []

    for i in range(len(students)):
        normalized_students.append(
            NormalizedStudentInfo(
                hours_studied[i],
                previous_scores[i],
                extracurricular_activities[i],
                sleep_hours[i],
                sample_question_papers_practiced[i],
                performance_index[i],
            )
        )
    
    return normalized_students


def get_training_and_test_sets(dataset: list[Any], factor: float) -> tuple[list[Any], list[Any]]:
    """
    Обучающий набор данных - для, собственно, обучения, в реальном машинном обучении вместе с данными считается некий показатель, насколько близок ИИ к правде.

    Тестовый набор данных - для теста уже обученной модели.
    """
    training_set = []
    test_set = []

    for s in dataset:
        if random() <= factor:
            training_set.append(s)
        else:
            test_set.append(s)
    
    return training_set, test_set


def all_stats(students: list[StudentInfo]) -> None:
    fig, axs = plt.subplots(2, 3)
    fig.suptitle('Статистика')
    axs[1, 2].axis('off')

    stats('Hours Studied', [s.hours_studied for s in students], axs[0, 0])
    stats('Previous Scores', [s.previous_scores for s in students], axs[0, 1])
    stats('Sleep Hours', [s.sleep_hours for s in students], axs[0, 2])
    stats('Sample Question Papers Practiced', [s.sample_question_papers_practiced for s in students], axs[1, 0])
    stats('Performance Index', [s.performance_index for s in students], axs[1, 1])


def read_students_from_file() -> list[StudentInfo]:
    rows = []

    with open('Student_Performance.csv', 'r') as f:
        csvreader = csv.reader(f)

        next(csvreader)

        for row in csvreader:
            rows.append(row)

    students = []
    for row in rows:
        students.append(
            StudentInfo(
                int(row[0]),
                int(row[1]),
                convert_bool(row[2]),
                int(row[3]),
                int(row[4]),
                float(row[5]),
            ),
        )
    
    return students


def model(
    x1_name: str,
    x1_get: StudentPropertyGetter,
    y_name: str,
    y_get:  StudentPropertyGetter,
    normalized_training_dataset: list[NormalizedStudentInfo],
    normalized_test_dataset: list[NormalizedStudentInfo],
    ax: Any,
) -> None:
    result = calc_linear_regression(normalized_training_dataset, y_get, [x1_get])
    a, b = result[0], result[1]

    reg_f = lambda x: a + b * x

    R2 = calc_determination_coefficient(
        [y_get(s) for s in normalized_test_dataset],
        [reg_f(x1_get(s)) for s in normalized_test_dataset],
    )

    print(f'### Регрессия ({y_name}, {x1_name})')
    print(f'- Уравнение (конкретное): $y = {a} + {b} x_1$')
    print(f'- Коэффициент детерминации ($R^2$): ${R2}$')
    print()

    x1_train_data = [x1_get(s) for s in normalized_training_dataset]
    y_train_data = [y_get(s) for s in normalized_training_dataset]

    ax.set_xlabel(x1_name)
    ax.set_ylabel(y_name)
    ax.plot(x1_train_data, y_train_data, '.', alpha=0.3)
    ax.plot(
        [x1_get(s) for s in normalized_test_dataset],
        [y_get(s) for s in normalized_test_dataset],
        '.',
        alpha=0.3,
    )
    ax.plot(
        [min(x1_train_data), max(x1_train_data)],
        [reg_f(min(x1_train_data)), reg_f(max(x1_train_data))],
    )


def model_with_2_params(
    x1_name: str,
    x1_get: StudentPropertyGetter,
    x2_name: str,
    x2_get: StudentPropertyGetter,
    y_name: str,
    y_get:  StudentPropertyGetter,
    normalized_training_dataset: list[NormalizedStudentInfo],
    normalized_test_dataset: list[NormalizedStudentInfo],
    ax: Any,
) -> None:
    result = calc_linear_regression(normalized_training_dataset, y_get, [x1_get, x2_get])
    a, b1, b2 = result[0], result[1], result[2]

    reg_f = lambda x1, x2: a + b1 * x1 + b2 * x2

    R2 = calc_determination_coefficient(
        [y_get(s) for s in normalized_test_dataset],
        [reg_f(x1_get(s), x2_get(s)) for s in normalized_test_dataset],
    )

    print(f'### Регрессия ({y_name}, {x1_name}, {x2_name})')
    print(f'- Уравнение (конкретное): $y = {a} + {b1} x_1 + {b2} x_2$')
    print(f'- Коэффициент детерминации ($R^2$): ${R2}$')
    print()

    x1_train_data = [x1_get(s) for s in normalized_training_dataset]
    x2_train_data = [x2_get(s) for s in normalized_training_dataset]
    y_train_data = [y_get(s) for s in normalized_training_dataset]

    ax.set_xlabel(x1_name)
    ax.set_ylabel(x2_name)
    ax.set_zlabel(y_name)
    ax.plot3D(x1_train_data, x2_train_data, y_train_data, '.', alpha=0.3)
    ax.plot3D(
        [x1_get(s) for s in normalized_test_dataset],
        [x2_get(s) for s in normalized_test_dataset],
        [y_get(s) for s in normalized_test_dataset],
        '.',
        alpha=0.3,
    )
    ax.plot3D(
        [min(x1_train_data), max(x1_train_data)],
        [min(x2_train_data), max(x2_train_data)],
        [reg_f(min(x1_train_data), min(x2_train_data)), reg_f(max(x1_train_data), max(x2_train_data))],
    )


def calc_determination_coefficient(
    real_data: list[float],
    calculated_data: list[float],
) -> float:
    s_res = sum((y_real - y_calc) ** 2 for y_real, y_calc in zip(real_data, calculated_data))
    real_average = sum(real_data) / len(real_data)
    s_total = sum((y_real - real_average) ** 2 for y_real in real_data)

    return rounded(1 - s_res / s_total)


def calc_linear_regression(
    train_dataset: list[NormalizedStudentInfo],
    get_y: StudentPropertyGetter,
    get_xs: list[StudentPropertyGetter],
) -> list[float]:
    """
    https://ru.wikipedia.org/wiki/%D0%9C%D0%B5%D1%82%D0%BE%D0%B4_%D0%BD%D0%B0%D0%B8%D0%BC%D0%B5%D0%BD%D1%8C%D1%88%D0%B8%D1%85_%D0%BA%D0%B2%D0%B0%D0%B4%D1%80%D0%B0%D1%82%D0%BE%D0%B2#%D0%9C%D0%9D%D0%9A_%D0%B2_%D1%81%D0%BB%D1%83%D1%87%D0%B0%D0%B5_%D0%BB%D0%B8%D0%BD%D0%B5%D0%B9%D0%BD%D0%BE%D0%B9_%D1%80%D0%B5%D0%B3%D1%80%D0%B5%D1%81%D1%81%D0%B8%D0%B8
    """
    # чтобы была матрица N x (P+1), первый столбец - единицы
    get_xs_modified = [lambda _s: 1] + get_xs

    X = np.matrix([[get_x(s) for get_x in get_xs_modified] for s in train_dataset])
    y = np.matrix([[get_y(s)] for s in train_dataset])
    b = np.linalg.inv(X.transpose() * X) * X.transpose() * y

    coefficients = [rounded(q) for q in b.transpose().tolist()[0]]

    return coefficients


def main():
    students = read_students_from_file()

    plt.rc('figure', figsize=(16, 9))
    all_stats(students)
    plt.savefig('stats.png')
    plt.close()

    training_dataset, test_set = get_training_and_test_sets(students, 0.5)
    normalized_training_dataset = normalize_students(training_dataset)
    normalized_test_dataset = normalize_students(test_set)

    model_schemas = [
        ('Hours Studied', lambda s: s.hours_studied, 'Performance Index', lambda s: s.performance_index),
        ('Previous Scores', lambda s: s.previous_scores, 'Performance Index', lambda s: s.performance_index),
        ('Extracurricular Activities', lambda s: s.extracurricular_activities, 'Performance Index', lambda s: s.performance_index),
        ('Sleep Hours', lambda s: s.sleep_hours, 'Performance Index', lambda s: s.performance_index),
        ('Sample Question Papers Practiced', lambda s: s.sample_question_papers_practiced, 'Performance Index', lambda s: s.performance_index),
    ]

    fig, axs = plt.subplots(2, 3)
    fig.suptitle("Модели (линейные регрессии)")
    axs[1, 2].axis('off')
    
    for i, model_schema in enumerate(model_schemas):
        model(
            model_schema[0],
            model_schema[1],
            model_schema[2],
            model_schema[3],
            training_dataset,
            test_set,
            axs[i % 2, i // 2],
        )
    plt.savefig("regressions.png")
    plt.close()

    ax = plt.axes(projection='3d')
    model_with_2_params(
        'Hours Studied',
        lambda s: s.hours_studied,
        'Previous Scores',
        lambda s: s.previous_scores,
        'Performance Index',
        lambda s: s.performance_index,
        training_dataset,
        test_set,
        ax,
    )
    plt.savefig('regression3d.png')
    plt.close()


if __name__ == '__main__':
    main()
