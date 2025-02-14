# type: ignore

# 0 - диабета нет
# 1 - есть диабет

import csv
from dataclasses import dataclass
from random import random, sample
import matplotlib.pyplot as plt
from math import sqrt
from typing import Any, Callable


def rounded(n: float) -> float:
    return round(n, 6)


@dataclass
class Person:
    pregnancies: int
    glucose: int
    blood_pressure: int
    skin_thickness: int
    insulin: int
    bmi: float
    pedigree: float
    age: int
    outcome: bool

PGetter = Callable[[Person], Any]

FIELDS = [
    dict(
        name='Pregnancies',
        getter=lambda p: p.pregnancies,
    ),
    dict(
        name='Glucose',
        getter=lambda p: p.glucose,
    ),
    dict(
        name='Blood Pressure',
        getter=lambda p: p.blood_pressure,
    ),
    dict(
        name='Skin Thickness',
        getter=lambda p: p.skin_thickness,
    ),
    dict(
        name='Insulin',
        getter=lambda p: p.insulin,
    ),
    dict(
        name='BMI',
        getter=lambda p: p.bmi,
    ),
    dict(
        name='Pedigree',
        getter=lambda p: p.pedigree,
    ),
    dict(
        name='Age',
        getter=lambda p: p.age,
    ),
]


class ConfusionMatrix:
    matrix: list[list[int]]

    def __init__(self, m: list[list[int]]):
        self.matrix = m
    
    def __str__(self) -> str:
        m = self.matrix
        return (
            f'| Truth \ Predicted | Negative | Positive |\n'
            f'|-------------------|----------|----------|\n'
            f'| Negative  | {m[0][0]:>5} | {m[0][1]:>5} |\n'
            f'| Positive  | {m[1][0]:>5} | {m[1][1]:>5} |'
        )

    def TN(self) -> int:
        return self.matrix[0][0]
    
    def FP(self) -> int:
        return self.matrix[0][1]

    def FN(self) -> int:
        return self.matrix[1][0]

    def TP(self) -> int:
        return self.matrix[1][1]

    def recall(self) -> float:
        """
        Полнота
        
        Способность алгоритма определять класс 1 (positive)
        """
        return (
            self.TP() /
            (self.TP() + self.FN())
        )

    def precision(self) -> float:
        """
        Точность
        
        Насколько алгоритм способен правильно классифицировать класс
        1 (positive) из всех объектов, которые он распознал как 1 (positive)
        """
        return (
            self.TP() /
            (self.TP() + self.FP())
        )

    def accuracy(self) -> float:
        """
        Точность
        
        Насколько алгоритм способен правильно классифицировать класс
        """
        return (
            (self.TN() + self.TP()) /
            (self.TN() + self.FP()  + self.FN() + self.TP())
        )

    def f1_score(self) -> float:
        """
        F1-мера

        Метрика, мера оценки.
        """
        precision = self.precision()
        recall = self.recall()
        return (
            2 * (
                precision * recall /
                (precision + recall)
            )
        )


def build_confusion_matrix(
    true_data: list[Person],
    predicted_data: list[Person],
) -> ConfusionMatrix:
    # Matrix is 2x2 because we either have False or True
    #
    # TN FP
    # FN TP
    matrix = [[0 for _ in range(2)] for _ in range(2)]

    for true_person, predicted_person in zip(true_data, predicted_data):
        true_value = true_person.outcome
        predicted_value = predicted_person.outcome

        matrix[true_value][predicted_value] += 1

    return ConfusionMatrix(matrix)


def visualize_3d(
    x1_name: str,
    x1_get: PGetter,
    x2_name: str,
    x2_get: PGetter,
    x3_name: str,
    x3_get:  PGetter,
    data: list[Person],
    ax: Any,
) -> None:
    x1_data = [x1_get(s) for s in data]
    x2_data = [x2_get(s) for s in data]
    x3_data = [x3_get(s) for s in data]

    outcome_colors = ['red' if p.outcome else 'green' for p in data]

    ax.set_xlabel(x1_name)
    ax.set_ylabel(x2_name)
    ax.set_zlabel(x3_name)
    ax.scatter(x1_data, x2_data, x3_data, '.', alpha=0.7, color=outcome_colors)


def print_stats(name: str, data: list[int | float]) -> None:
    values = sorted(data)
    length = len(values)
    average = sum(values) / length
    min_value = min(values)
    max_value = max(values)
    # sigma
    deviation = rounded(sqrt(sum((x - average)**2 for x in values) / length))

    print(f'### Статистика ({name})\n')
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


def stats(name: str, data: list[int | float], subplot: Any) -> None:
    draw_distribution_histogram(name, data, subplot)
    print_stats(name, data)


def all_stats(persons: list[Person]) -> None:
    fig, axs = plt.subplots(3, 3)
    fig.suptitle('Статистика')
    axs[2, 2].axis('off')

    stats('Pregnancies', [s.pregnancies for s in persons], axs[0, 0])
    stats('Glucose', [s.glucose for s in persons], axs[0, 1])
    stats('Blood pressure', [s.blood_pressure for s in persons], axs[0, 2])
    stats('Skin thickness', [s.skin_thickness for s in persons], axs[1, 0])
    stats('Insulin', [s.insulin for s in persons], axs[1, 1])
    stats('BMI', [s.bmi for s in persons], axs[1, 2])
    stats('Pedigree', [s.pedigree for s in persons], axs[2, 0])
    stats('Age', [s.age for s in persons], axs[2, 1])


def read_from_file() -> list[Person]:
    rows = []

    with open('diabetes.csv', 'r') as f:
        csvreader = csv.reader(f)

        next(csvreader)

        for row in csvreader:
            rows.append(row)

    persons = []
    for row in rows:
        persons.append(
            Person(
                int(row[0]),
                int(row[1]),
                int(row[2]),
                int(row[3]),
                int(row[4]),
                float(row[5]),
                float(row[6]),
                int(row[7]),
                bool(int(row[8])),
            ),
        )
    
    return persons


def median(numbers: list[int]) -> int:
    q = sorted(numbers)
    q_len = len(q)
    if q_len % 2 == 1:
        return q[q_len // 2 + 1]
    return (q[q_len // 2] + q[q_len // 2 + 1]) / 2


def remove_anomalies(numbers: list[float]) -> list:
    m = median(list(filter(lambda x: x != 0, numbers)))

    return [
        n if n != 0 else m
        for n in numbers
    ]


def remove_all_anomalies(persons: list[Person]) -> list[Person]:
    pregnancies = [p.pregnancies for p in persons]
    glucoses = remove_anomalies([p.glucose for p in persons])
    blood_pressures = remove_anomalies([p.blood_pressure for p in persons])
    skin_thicknesses = remove_anomalies([p.skin_thickness for p in persons])
    insulins = remove_anomalies([p.insulin for p in persons])
    bmis = remove_anomalies([p.bmi for p in persons])
    pedigrees = [p.pedigree for p in persons]
    ages = [p.age for p in persons]
    outcomes = [p.outcome for p in persons]

    return [
        Person(
            pregnancies[i],
            glucoses[i],
            blood_pressures[i],
            skin_thicknesses[i],
            insulins[i],
            bmis[i],
            pedigrees[i],
            ages[i],
            outcomes[i],
        )
        for i in range(len(persons))
    ]


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


def distance(
    a: list[float],
    b: list[float],
) -> float:
    return sqrt(sum((ai - bi) ** 2 for ai, bi in zip(a, b)))


def sort_by_distance(
    target: Person,
    data: list[Person],
    get: Callable[[Person], list[float]],
) -> list[Person]:
    data_with_distances = [(p, distance(get(target), get(p))) for p in data]
    data_with_distances.sort(key=lambda t: t[1])
    return [t[0] for t in data_with_distances]


def kNN(
    training_dataset: list[Person],
    test_dataset: list[Person],
    k: int,
    get_property_values: Callable[[Person], list[float]],
) -> list[Person]:
    data = training_dataset.copy()
    result_data = []

    for i, p in enumerate(test_dataset):
        sorted_by_distances = sort_by_distance(p, data[:i]+data[i+1:], get_property_values)

        neighbors = sorted_by_distances[:k]
        neighbor_class_values = [p.outcome for p in neighbors]

        result_outcome = neighbor_class_values.count(True) > neighbor_class_values.count(False)

        result_p = Person(
            p.pregnancies,
            p.glucose,
            p.blood_pressure,
            p.skin_thickness,
            p.insulin,
            p.bmi,
            p.pedigree,
            p.age,
            result_outcome,
        )

        result_data.append(result_p)

    return result_data


def draw_2d_kNN(
    x1_name: str,
    x1_get: PGetter,
    x2_name: str,
    x2_get: PGetter,
    training_data: list[Person],
    result_data: list[Person],
    ax: Any,
):
    ax.set_xlabel(x1_name)
    ax.set_ylabel(x2_name)

    ax.scatter(
        [x1_get(s) for s in training_data],
        [x2_get(s) for s in training_data],
        marker='.',
        alpha=0.3,
        color=['red' if p.outcome else 'green' for p in training_data],
    )

    ax.scatter(
        [x1_get(s) for s in result_data],
        [x2_get(s) for s in result_data],
        marker='o',
        alpha=1,
        color=['red' if p.outcome else 'green' for p in result_data],
    )


def draw_3d_kNN(
    x1_name: str,
    x1_get: PGetter,
    x2_name: str,
    x2_get: PGetter,
    x3_name: str,
    x3_get:  PGetter,
    training_data: list[Person],
    result_data: list[Person],
    ax: Any,
) -> None:
    ax.set_xlabel(x1_name)
    ax.set_ylabel(x2_name)
    ax.set_zlabel(x3_name)

    ax.scatter(
        [x1_get(s) for s in training_data],
        [x2_get(s) for s in training_data],
        [x3_get(s) for s in training_data],
        marker='.',
        alpha=0.3,
        color=['red' if p.outcome else 'green' for p in training_data],
    )

    ax.scatter(
        [x1_get(s) for s in result_data],
        [x2_get(s) for s in result_data],
        [x3_get(s) for s in result_data],
        marker='o',
        alpha=1,
        color=['red' if p.outcome else 'green' for p in result_data],
    )


def draw_kNN(
    training_dataset: list[Person],
    result_dataset: list[Person],
    model: list[dict],
    filename: str,
):
    if len(model) == 2:
        ax = plt.axes()
        draw_2d_kNN(
            model[0]['name'],
            model[0]['getter'],
            model[1]['name'],
            model[1]['getter'],
            training_dataset,
            result_dataset,
            ax,
        )
    elif len(model) == 3:
        ax = plt.axes(projection='3d')
        draw_3d_kNN(
            model[0]['name'],
            model[0]['getter'],
            model[1]['name'],
            model[1]['getter'],
            model[2]['name'],
            model[2]['getter'],
            training_dataset,
            result_dataset,
            ax
        )

    plt.savefig(filename)
    plt.close()


def kNN_with_additional_info(
    training_dataset: list[Person],
    test_dataset: list[Person],
    k: int,
    model: list[dict],
):
    get_property_values = lambda p: [f['getter'](p) for f in model]
    result_data = kNN(training_dataset, test_dataset, k, get_property_values)

    confusion_matrix = build_confusion_matrix(test_dataset, result_data)

    full_name = ', '.join([f['name'] for f in model])
    filename = '_'.join([f['name'] for f in model])
    filename = f'{filename}_{k}.png'

    print(f'### Модель [{full_name}] ({k=})\n')

    print(f'![_]({filename.replace(" ", "%20")})\n')

    print('Матрица ошибок:\n')
    print(confusion_matrix)
    print()

    print(f'Точность (Accuracy): {rounded(confusion_matrix.accuracy())}')
    print(f'\nF1-мера (F1 score): {rounded(confusion_matrix.f1_score())}')

    draw_kNN(training_dataset, result_data, model, filename)


def gen_random_model() -> list[dict]:
    return sample(FIELDS, 2)


def main():
    persons = read_from_file()

    print(f'Выборка: {len(persons)} строк\n')

    persons = remove_all_anomalies(persons)

    plt.rc('figure', figsize=(16, 9))
    all_stats(persons)
    plt.tight_layout()
    plt.savefig('stats.png')
    plt.close()

    ax = plt.axes(projection='3d')
    visualize_3d(
        'Glucose',
        lambda p: p.glucose,
        'Blood Pressure',
        lambda p: p.blood_pressure,
        'BMI',
        lambda p: p.bmi,
        persons,
        ax,
    )
    plt.savefig('visualization.png')
    plt.close()

    training_dataset, test_dataset = get_training_and_test_sets(persons, 0.7)

    models = [
        # [
        #     dict(
        #         name='Glucose',
        #         getter=lambda p: p.glucose,
        #     ),
        #     dict(
        #         name='Blood Pressure',
        #         getter=lambda p: p.blood_pressure,
        #     ),
        #     dict(
        #         name='BMI',
        #         getter=lambda p: p.bmi,
        #     ),
        # ],
        # gen_random_model()
    ]

    for model in models:
        for k in [3, 5, 10]:
            kNN_with_additional_info(
                training_dataset,
                test_dataset,
                k,
                model,
            )
            print()

    additional_model = [
        dict(
            name='Glucose',
            getter=lambda p: p.glucose,
        ),
        dict(
            name='Age',
            getter=lambda p: p.age,
        ),
        dict(
            name='BMI',
            getter=lambda p: p.bmi,
        ),
    ]

    kNN_with_additional_info(training_dataset, test_dataset, 10, additional_model)


if __name__ == '__main__':
    main()
