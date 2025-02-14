from solve import *

from itertools import combinations

def calculate_f1_score(
    training_dataset: list[Person],
    test_dataset: list[Person],
    k: int,
    model: list[dict],
) -> int:
    get_property_values = lambda p: [f['getter'](p) for f in model]
    result_data = kNN(training_dataset, test_dataset, k, get_property_values)

    confusion_matrix = build_confusion_matrix(test_dataset, result_data)

    return confusion_matrix.f1_score()


def main():
    persons = read_from_file()

    persons = remove_all_anomalies(persons)

    training_dataset, test_dataset = get_training_and_test_sets(persons, 0.7)

    best_f1_score = 0
    best_model = None

    for m in (2, 3):
        for model in combinations(FIELDS, m):
            for k in (3, 5, 10):
                model_name = ', '.join([f['name'] for f in model]) + f' ({k=})'
                print(f'Trying {model_name}')
                f1_score = calculate_f1_score(training_dataset, test_dataset, k, model)
                if f1_score > best_f1_score:
                    best_f1_score = f1_score
                    best_model = model_name

    print(best_f1_score, best_model)


if __name__ == '__main__':
    main()
