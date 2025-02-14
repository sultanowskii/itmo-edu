import csv
import math
import random
import os
import glob
from typing import NamedTuple, Any, Sequence, Callable, DefaultDict, cast, Any, TypeVar
from collections import Counter, defaultdict

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd


KnnClass = str

class Entry(NamedTuple):
    pregnancies: int
    glucose: int
    blood_pressure: int
    skin_thickness: int
    insulin: int
    bmi: float
    pedigree: float
    age: int
    outcome: bool

    @staticmethod
    def bins() -> dict[str, int]:
        return {
            "outcome": 2,
        }

def main() -> None:
    dataset: list[Entry] = []
    with open("diabetes.csv", newline="") as f:
        reader = csv.reader(f)
        next(reader) # Skip header
        for row in reader:
            dataset.append(Entry(
                pregnancies=int(row[0]),
                glucose=int(row[1]),
                blood_pressure=int(row[2]),
                skin_thickness=int(row[3]),
                insulin=int(row[4]),
                bmi=float(row[5]),
                pedigree=float(row[6]),
                age=int(row[7]),
                outcome=bool(int(row[8])),
            ))

    training, testing = split_train_and_test(dataset, 0.5)

    print(f"Total number of entries: {len(dataset)} (training={len(training)}, testing={len(testing)})")
    print()

    plt.rc("figure", figsize=(16, 9))

    if not os.path.exists("images"):
        os.mkdir("images")
    # for image in glob.glob("images/*"):
    #     os.remove(image)

    plot_all_distributions(dataset)
    plt.tight_layout()
    plt.savefig("images/distributions.png")
    plt.close("all")

    fig = plt.figure()
    ax = fig.add_subplot(projection="3d")
    plot_scatter_3d(ax, "glucose", "blood_pressure", "outcome", training, testing)
    plt.savefig("images/3d_scatter.png")
    plt.close("all")

    ks = [3, 5, 10]

    random_fields = random.sample(without(list(Entry._fields), "outcome"), 2)
    for k in ks:
        fig = plt.figure()
        ax = fig.add_subplot()
        knn_plot_2d(
            ax,
            training,
            testing,
            random_fields[0],
            random_fields[1],
            k,
            lambda e: "diabetic" if e.outcome else "healthy",
            lambda c: {"healthy": "green", "diabetic": "red"}[c],
        )
        plt.savefig(f"images/random_knn_2d_{k}.png")
        plt.close("all")

    random_fields = random.sample(without(list(Entry._fields), "outcome"), 3)
    for k in ks:
        fig = plt.figure()
        ax = fig.add_subplot(projection="3d")
        knn_plot_3d(
            ax,
            training,
            testing,
            random_fields[0],
            random_fields[1],
            random_fields[2],
            k,
            lambda e: "diabetic" if e.outcome else "healthy",
            lambda c: {"healthy": "green", "diabetic": "red"}[c],
        )
        plt.savefig(f"images/random_knn_3d_{k}.png")
        plt.close("all")

    for k in ks:
        knn_with_info(
            training,
            testing,
            k,
            lambda e: "diabetic" if e.outcome else "healthy",
            ["bmi", "pedigree", "age", "blood_pressure"],
        )


def knn_plot_3d(
    ax: plt.Axes,
    training: list[Entry],
    testing: list[Entry],
    xf: str,
    yf: str,
    zf: str,
    k: int,
    classify: Callable[[Entry], KnnClass],
    class_to_color: Callable[[KnnClass], str]
):
    getx = lambda e: float(getattr(e, xf))
    gety = lambda e: float(getattr(e, yf))
    getz = lambda e: float(getattr(e, zf))

    class_to_entries = knn_with_info(training, testing, k, classify, [xf, yf, zf])

    for clas, entries in class_to_entries.items():
        ax.scatter(
            [getx(e) for e in entries],
            [gety(e) for e in entries],
            [getz(e) for e in entries],
            color=class_to_color(clas),
            marker="o"
        )

    ax.scatter(
        [getx(e) for e in training],
        [getz(e) for e in training],
        [getz(e) for e in training],
        c=[class_to_color(classify(e)) for e in training],
        marker="^",
        alpha=0.2,
    )

    ax.set_xlabel(snake_cast_to_title(xf))
    ax.set_ylabel(snake_cast_to_title(yf))
    cast(Any, ax).set_zlabel(snake_cast_to_title(zf))


def knn_plot_2d(
    ax: plt.Axes,
    training: list[Entry],
    testing: list[Entry],
    xf: str,
    yf: str,
    k: int,
    classify: Callable[[Entry], KnnClass],
    class_to_color: Callable[[KnnClass], str]
):
    getx = lambda e: float(getattr(e, xf))
    gety = lambda e: float(getattr(e, yf))

    class_to_entries = knn_with_info(training, testing, k, classify, [xf, yf])

    for clas, entries in class_to_entries.items():
        ax.scatter(
            [getx(e) for e in entries],
            [gety(e) for e in entries],
            color=class_to_color(clas),
            marker="o"
        )

    ax.scatter(
        [getx(e) for e in training],
        [gety(e) for e in training],
        c=[class_to_color(classify(e)) for e in training],
        marker="^",
        alpha=0.2,
    )

    ax.set_xlabel(snake_cast_to_title(xf))
    ax.set_ylabel(snake_cast_to_title(yf))


def knn_with_info(
    training: list[Entry],
    testing: list[Entry],
    k: int,
    classify: Callable[[Entry], KnnClass],
    fields: list[str],
) -> dict[KnnClass, list[Entry]]:
    print(f"Performing kNN with k={k} on fields {', '.join(fields)}")

    class_to_entries, entry_to_class = knn(training, testing, k, euclidean_distance(fields), classify)

    confusion_matrix = new_confusion_matrix(
        testing,
        lambda e: entry_to_class[e],
        classify,
    )

    print("- Confusion Matrix:")
    print(pd.DataFrame(confusion_matrix).to_string(na_rep='-'))
    max_class_len = max(map(len, class_to_entries.keys()))
    print("- Classes stats:")
    for clas in class_to_entries.keys():
        precision = confusion_matrix[clas][clas] / sum(confusion_matrix[clas].values())
        recall = confusion_matrix[clas][clas] / sum(row[clas] for row in confusion_matrix.values())
        f1 = 2 * precision * recall / (precision + recall)
        print(" ", clas.ljust(max_class_len), ": ", end="")
        print(f"{precision=:.3f} {recall=:.3f} {f1=:.3f}")
    print()

    return class_to_entries


T = TypeVar("T")

def new_confusion_matrix(
    testing: list[T],
    predicted_classify: Callable[[T], KnnClass],
    reference_classify: Callable[[T], KnnClass],
) -> dict[KnnClass, dict[KnnClass, int]]:
    matrix: DefaultDict[KnnClass, DefaultDict[KnnClass, int]] = DefaultDict(lambda: DefaultDict(int))
    for entry in testing:
        predicted = predicted_classify(entry)
        reference = reference_classify(entry)
        matrix[reference][predicted] += 1    
    return dict(matrix)
    

def knn(
    training: list[T],
    testing: list[T],
    k: int,
    dist: Callable[[T, T], float],
    classify: Callable[[T], KnnClass],
) -> tuple[dict[KnnClass, list[T]], dict[T, KnnClass]]:
    class_to_entries: DefaultDict[KnnClass, list[T]] = defaultdict(list)
    entry_to_class: dict[T, KnnClass] = {}
    for test_e in testing:
        distances = [(dist(test_e, train_e), classify(train_e)) for train_e in training]
        # distances.extend((dist(test_e, ttest_e), clas) for ttest_e, clas in entry_to_class.items())
        class_count: Counter[KnnClass] = Counter()
        for _, clas in sorted(distances)[:k]:
            class_count[clas] += 1
        clas = class_count.most_common(1)[0][0]
        class_to_entries[clas].append(test_e)
        entry_to_class[test_e] = clas
    return class_to_entries, entry_to_class


def plot_scatter_3d(ax: plt.Axes, xf: str, yf: str, zf: str, train: list[Entry], testing: list[Entry]):
    getx = lambda e: float(getattr(e, xf))
    gety = lambda e: float(getattr(e, yf))
    getz = lambda e: float(getattr(e, zf))

    ax.scatter(list(map(getx, train)), list(map(gety, train)), list(map(getz, train)), marker="^", alpha=0.5, color="orange")
    ax.scatter(list(map(getx, testing)), list(map(gety, testing)), list(map(getz, testing)), marker="o", alpha=0.5, color="blue")

    ax.set_xlabel(snake_cast_to_title(xf))
    ax.set_ylabel(snake_cast_to_title(yf))
    cast(Any, ax).set_zlabel(snake_cast_to_title(zf))


def plot_all_distributions(dataset: list[Entry]):
    fields = Entry._fields
    rows = 3
    cols = math.ceil(len(fields) / rows)
    fig, axs = plt.subplots(rows, cols)
    fig.suptitle("Distributions")
    bins = Entry.bins()
    for i, field in enumerate(fields):
        ax = axs[i // cols, i % cols]
        values = [float(getattr(e, field)) for e in dataset]
        plot_distribution(ax, snake_cast_to_title(field), bins.get(field), values)


def plot_distribution(ax: plt.Axes, title: str, bins: int | None, xs: list[float]):
    avg = sum(xs) / len(xs)
    deviation = math.sqrt(sum((x - avg)**2 for x in xs) / len(xs))
    minimum = min(xs)
    maximum = max(xs)
    print(f"Distribution: {title}")
    print(f"- Average: {avg}")
    print(f"- Standard deviation: {deviation}")
    print(f"- Min: {minimum}")
    print(f"- Max: {maximum}")
    print(f"- Quantiles:")
    for percentile in [25, 50, 75]:
        idx = int(len(xs) * percentile / 100)
        print(f"  * {percentile}% percentile is {xs[idx]}")        
    print()

    ax.set_title(title)
    ax.hist(xs, bins=bins, edgecolor="black", linewidth=1)


def euclidean_distance(fields: list[str]) -> Callable[[Entry, Entry], float]:
    def dist(a: Entry, b: Entry) -> float:
        return math.sqrt(sum((getattr(a, f) - getattr(b, f))**2 for f in fields))

    return dist


def snake_cast_to_title(s: str) -> str:
    return " ".join(map(str.capitalize, s.replace("_", " ").split()))


def split_train_and_test(dataset: list[Entry], test_fraction: float) -> tuple[list[Entry], list[Entry]]:
    train: list[Entry] = []
    test: list[Entry] = []
    for entry in dataset:
        if random.random() < test_fraction:
            test.append(entry)
        else:
            train.append(entry)
    return train, test

def without(xs: list[str], x: str) -> list[str]:
    cpy = list(xs)
    cpy.remove(x)
    return cpy

main()
