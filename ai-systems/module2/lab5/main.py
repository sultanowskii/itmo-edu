from __future__ import annotations
from typing import Any
import inspect
import random
import math
import csv
from dataclasses import dataclass
from collections import defaultdict
import matplotlib.pyplot as plt

from datatypes import Student, OutputGrade

# Минимальная оценка, считающаяся 'хорошей'
THRESHOLD_GRADE = OutputGrade.CC
# Пороговое значение при сокращении дерева
TREE_CUT_THRESHOLD = 5


@dataclass
class TreeNode:
    # 1 - название атрибута
    # 2 - значение атрибута в результате деления по которому образовалось эта нода)
    attr: tuple[str, Any]
    # Список студентов, которые 'остались' в этом поддереве (т.е. либо здесь, либо ниже)
    lst: list[Student]
    # 'Потомки'
    children: list[Tree]


@dataclass
class TreeLeaf:
    lst: list[Student]
    last_attr_val: Any


Tree = TreeNode | TreeLeaf


def classify_student(student: Student) -> int:
    """
    Получил ли ученик хорошую оценку.
    """
    return int(student.out_grade >= THRESHOLD_GRADE)


def student_output(student: Student) -> float:
    """
    Получил ли ученик хорошую оценку.
    """
    return float(student.out_grade >= THRESHOLD_GRADE)


def node_output(students: list[Student]) -> float:
    """
    Подсчет части учеников, которые получили хорошую оценку.
    """
    passed = len([student for student in students if student.out_grade >= THRESHOLD_GRADE]) / len(students)
    return passed


def area_under_curve(points: list[tuple[float, float]]) -> float:
    """
    Расчет площади под кривой.

    AUC - Area Under Curve.
    """
    auc: float = 0
    for i in range(len(points) - 1):
        x, y = points[i]
        xn, yn = points[i + 1]
        auc += (xn - x) * (yn + y) / 2
    return auc


def confusion_matrix(test_dataset: list[Student], threshold_pass_rate: float, tree: Tree) -> tuple[int, int, int, int]:
    """
    Построение матрицы ошибок.
    """
    tp, fp, fn, tn = 0, 0, 0, 0
    for student in test_dataset:
        predicted = 1.0 if predict(student, tree) >= threshold_pass_rate else 0.0
        actual = student_output(student)
        if predicted == 1 and predicted == actual:
            tp += 1
        elif predicted == 1 and predicted != actual:
            fp += 1
        elif predicted == 0 and predicted == actual:
            tn += 1
        else:
            fn += 1
    return tp, fp, fn, tn


def predict(student: Student, tree: Tree) -> float:
    """
    Найти вероятность того, что ученик получит 'хорошую' оценку на основе дерева решений.

    Хорошая оценка - это оценка >= OutputGrade.CC
    """
    if isinstance(tree, TreeLeaf):
        return node_output(tree.lst)
    val = getattr(student, tree.attr[0])
    for child in tree.children:
        if isinstance(child, TreeLeaf):
            child_val = child.last_attr_val
        else:
            child_val = child.attr[1]
        if val == child_val:
            return predict(student, child)
    return node_output(tree.lst)


def print_tree(tree: Tree, last=True, header=''):
    elbow = '+---'
    pipe = '|   '
    tee = '\\---'
    blank = '   '
    start = header + (elbow if last else tee)
    if isinstance(tree, TreeLeaf):
        output = node_output(tree.lst)
        print(start, tree.last_attr_val, '-', output, f'({len(tree.lst)})')
    if isinstance(tree, TreeNode):
        if tree.attr[1] is not None:
            start += str(tree.attr[1])
        print(start, '-', tree.attr[0])
        for i, child in enumerate(tree.children):
            print_tree(
                child,
                header=header + (blank if last else pipe),
                last = i == len(tree.children) - 1,
            )

def shorten_tree(tree: Tree) -> Tree:
    """
    Сокращение дерева - 'обрезаем' поддеревья до листьев.
    """
    if isinstance(tree, TreeNode):
        # Нода превращается в лист, если у нее всего один потомок или кол-во учеников ниже порогового
        if len(tree.lst) <= TREE_CUT_THRESHOLD or len(tree.children) == 1:
            return TreeLeaf(
                lst=tree.lst,
                last_attr_val=tree.attr[1]
            )

        # Получаем сокращенных потомков (рекурсивно)
        children = [shorten_tree(c) for c in tree.children]

        # Возвращаем сокращенное поддерево
        return TreeNode(
            lst=tree.lst,
            attr=tree.attr,
            children=children
        )

    return tree


def tree_from(students: list[Student], attr_pool: list[str], last_branch_val: Any | None = None) -> Tree | None:
    """Построение дерева."""
    if len(attr_pool) == 0:
        return TreeLeaf(
            lst=students,
            last_attr_val=last_branch_val,
        )

    # Выбираем лучший атрибут - по кол-ву информации, который он нам даст
    attr_pool = attr_pool[:]
    best_attr = max(attr_pool, key=lambda attr: gain_ratio_for_attr(students, attr))
    # Убираем его
    attr_pool.remove(best_attr)

    # Маппим значение выбранного атрибута на список учеников, у кого выбранный атрибут равен данному
    groups_dict: defaultdict[Any, list[Student]] = defaultdict(list)
    for x in students:
        val = getattr(x, best_attr)
        groups_dict[val].append(x)

    # Рекурсивно проходим и составляем список потомков
    children: list[Tree] = []
    for attr_val, _students in groups_dict.items():
        child = tree_from(_students, attr_pool, last_branch_val=attr_val)
        if child is not None:
            children.append(child)

    if len(children) == 0:
        return TreeLeaf(
            lst=students,
            last_attr_val=last_branch_val,
        )

    return TreeNode(
        lst=students,
        attr=(best_attr, last_branch_val),
        children=children,
    )


def gain_ratio_for_attr(students: list[Student], attr: str) -> float:
    """
    Нормированный прирост информации (Gain Ratio), критерий.

    = (Info(T) - Info_x (T)) / split_info (X)
    """
    groups_dict: defaultdict[Any, list[Student]] = defaultdict(list)
    for student in students:
        val = getattr(student, attr)
        groups_dict[val].append(student)
    groups = list(groups_dict.values())
    split = split_info(groups)
    if split == 0:
        return 0
    return (info(students) - info_x(groups)) / split


def split_info(groups: list[list[Student]]) -> float:
    """
    Оценка потенциальной информации, получаемой при разбиении множества T на n подмножеств.

    Необходим для учета атрибутов с уникальными значениями.
    """
    total = sum(len(g) for g in groups)
    return -sum(len(g) / total * math.log2(len(g) / total) if len(g) != 0 else 0 for g in groups)


def info_x(groups: list[list[Student]]) -> float:
    """
    Оценка среднего количества информации, необходимого для определения класса примера из множества T после разбиения множества T по X (условная энтропия):
    """
    total = sum(len(g) for g in groups)
    return sum(len(g) / total * info(g) for g in groups)


def info(students: list[Student]) -> float:
    """
    Оценка среднего количества информации, необходимого для определения класса примера из множества T (энтропия).
    """
    if len(students) == 0:
        return 0
    _sum = 0.0
    classes = set(classify_student(student) for student in students)
    for _class in classes:
        ratio = len([student for student in students if classify_student(student) == _class]) / len(students)
        _sum += ratio * math.log2(ratio) if ratio != 0 else 0
    return -_sum


def split_train_and_test(dataset: list[Student]) -> tuple[list[Student], list[Student]]:
    """
    Разбитие данных на обучающие и тестовые.
    """
    train: list[Student] = []
    test: list[Student] = []
    random.seed(7)
    for entry in dataset:
        if random.random() < 0.7:
            train.append(entry)
        else:
            test.append(entry)
    return train, test

def main() -> None:
    # Чтение из файла
    dataset: list[Student] = []
    with open('data.csv', 'r') as f:
        reader = csv.reader(f)
        next(reader)
        for row in reader:
            dataset.append(Student.from_row(row))

    # Выбор sqrt(n) атрибутов (по заданию)
    all_attrs = [a[0] for a in inspect.getmembers(Student, lambda m: not inspect.isroutine(m)) if not a[0].startswith('_')]
    all_attrs.remove('out_grade')
    all_attrs.remove('student_id')
    all_attrs.remove('course_id')
    random.seed(1)
    chosen_attrs = random.sample(all_attrs, int(math.sqrt(len(all_attrs))))
    print(f'{chosen_attrs=}')
    train_dataset, test_dataset = split_train_and_test(dataset)
    tree = tree_from(train_dataset, chosen_attrs)
    assert tree is not None
    tree = shorten_tree(tree)

    # Для построения графиков по заданию: ROC и PR
    roc_points: list[tuple[float, float]] = []
    pr_points: list[tuple[float, float]] = []
    iters = 100

    # В цикле threshold_pass_rate - это 'пороговое значение'
    # Иначе говоря, это пороговая вероятность, с которой ученик получает хорошую оценку
    # Также мы находим точки для графиков:
    # - ROC: receiver operating characteristic, рабочая характеристика приёмника - график соотношения TPR к FPR.
    # - PR: precision-recall
    best_threshold = 0.0
    best_f1 = 0.0
    for threshold_pass_rate in (x / iters for x in range(iters)):
        tp, fp, fn, tn = confusion_matrix(test_dataset, threshold_pass_rate, tree)
        print()
        print(f'{threshold_pass_rate=}')
        print('  Confusion matrix:')
        print(f'    {tp=} {fp=}\n    {fn=} {tn=}')
        if tp + fp == 0 or tp + fn == 0 or fp + tn == 0:
            print('  division by zero: skipping')
            continue

        # Точность - Насколько алгоритм способен правильно классифицировать класс
        # 1 (positive) из всех объектов, которые он распознал как 1 (positive)
        precision = tp / (tp + fp)

        # Полнота - Способность алгоритма определять класс 1 (positive)
        # Равносильно true_positive_rate
        recall = tp / (tp + fn)

        false_positive_rate = fp / (fp + tn)

        # Точность - Насколько алгоритм способен правильно классифицировать класс
        accuracy = (tp + tn) / (tp + fp + fn + tn)

        # F1-мера - Метрика, мера оценки.
        f1 = 2 * precision * recall / (precision + recall)
        if best_f1 < f1:
            best_f1 = f1
            best_threshold = threshold_pass_rate
        print(f'  {precision=}')
        print(f'  {recall=} (true positive rate)')
        print(f'  {false_positive_rate=}')
        print(f'  {accuracy=}')
        print(f'  {f1=}')
        roc_points.append((false_positive_rate, recall))
        pr_points.append((recall, precision))

    roc_points.sort()
    roc_points.insert(0, (0, 0))
    roc_points.append((1, 1))

    pr_points.sort()
    pr_points.insert(0, (0, 1))
    pr_points.append((1, 0))

    # Построение графика ROC
    plt.plot([0, 1], [0, 1])
    plt.xlabel('False Positive Rate')
    plt.ylabel('True Positive Rate')
    plt.plot([x for x, _ in roc_points], [y for _, y in roc_points])
    plt.savefig('roc.png')

    plt.cla()

    # Построение графика PR
    plt.xlabel('Recall')
    plt.ylabel('Precision')
    plt.plot([x for x, _ in pr_points], [y for _, y in pr_points])
    plt.savefig('pr.png')

    print()
    print_tree(tree)
    # AUC - Area Under Curve
    print(f'roc auc={area_under_curve(roc_points)}')
    print(f'pr auc={area_under_curve(pr_points)}')
    # Лучшее пороговое значение
    print(f'best threshold={best_threshold} (f1 = {best_f1})')


if __name__ == '__main__':
    main()
