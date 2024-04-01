"""
Рисует график-свечу на данных.

Данные должны лежать в файле data.csv
"""

from csv import DictReader
from typing import Any

import matplotlib.pyplot as plt  # type: ignore
import pandas as pd  # type: ignore


def get_open_value(column: list[int]) -> int:
    """Получить открытие."""
    return column[0]


def get_high_value(column: list[int]) -> int:
    """Получить максимальное значение."""
    return max(column)


def get_low_value(column: list[int]) -> int:
    """Получить минимальное значение."""
    return min(column)


def get_close_value(column: list[int]) -> int:
    """Получить закрытие."""
    return column[-1]


SUBNAMES = [
    'Открытие',
    'Максимум',
    'Минимум',
    'Закрытие',
]
CANDLE_PARTS = [
    'open',
    'high',
    'low',
    'close',
]
CANDLE_PART_OPERATIONS = {
    'open': get_open_value,
    'high': get_high_value,
    'low': get_low_value,
    'close': get_close_value,
}
SUBNAME_COLUMN_NAMES = {
    'Открытие': '<OPEN>',
    'Максимум': '<HIGH>',
    'Минимум': '<LOW>',
    'Закрытие': '<CLOSE>',
}

COLORS = [
    'black',
    'red',
    'yellow',
    'green',
    'blue',
    'orange',
    'pink',
    'purple',
    'red',
    'grey',
    'lime',
    'brown',
    'gold',
    'cyan',
    'lightgray',
    'chocolate',
]


def int_list(ilist: list[str]) -> list[int]:
    """Трансформировать массив строк в массив чисел."""
    return [int(e) for e in ilist]


def read_data(filename: str) -> list[dict[str, str]]:
    """Прочитать данные из файла."""
    with open(filename) as csvfile:
        rows = [row for row in DictReader(csvfile)]
    return rows


def get_unique_days(rows: list[dict[str, str]]) -> list[str]:
    """Получить уникальные дни."""
    return list(
        set(
            get_column(rows, '<DATE>'),
        ),
    )


def get_column(rows: list[dict[str, str]], column_name: str, **filters: dict[str, str]) -> list[Any]:
    """Получить столбец. Поддерживает фильтрацию."""
    values = []

    for row in rows:
        filters_passed = True

        for filter_key, filter_value in filters.items():
            if row[filter_key] != filter_value:
                filters_passed = False
                break

        if not filters_passed:
            continue

        values.append(row[column_name])

    return values


def get_detailed_data(rows: list[dict[str, str]]) -> tuple[dict[str, list[str]], list[str]]:
    """Получить детальную информацию."""
    detailed_data = {
        'open': [],
        'high': [],
        'low': [],
        'close': [],
    }
    index = []

    days = get_unique_days(rows)

    for day in days:

        for subname in SUBNAMES:

            candle_name = f'{day} - {subname}'
            index.append(candle_name)
            column_name = SUBNAME_COLUMN_NAMES[subname]

            for candle_part in CANDLE_PARTS:
                column = int_list(
                    get_column(
                        rows,
                        column_name,
                        **{
                            '<DATE>': day,
                        },
                    ),
                )
                value = CANDLE_PART_OPERATIONS[candle_part](column)
                detailed_data[candle_part].append(value)

    return detailed_data, index


def draw_diagram(detailed_data: dict[str, list[str]], index: list[str]) -> None:
    """Нарисовать диграмму-свечку."""
    stock_prices = pd.DataFrame(
        detailed_data,
        index=index,
    )

    plt.figure()
    candle_width = 0.3
    stick_width = 0.03

    for i, name in enumerate(index):
        filt = stock_prices[stock_prices.index == name]
        color = COLORS[i % len(COLORS)]
        plt.bar(filt.index, filt.close - filt.open, candle_width, bottom=filt.open, color=color)
        plt.bar(filt.index, filt.high - filt.close, stick_width, bottom=filt.close, color=color)
        plt.bar(filt.index, filt.low - filt.open, stick_width, bottom=filt.open, color=color)

    plt.xticks(rotation=30, ha='right')

    plt.show()


def main() -> None:
    """Читает данные из файла и выводит диаграмму на основе данных."""
    rows = read_data('data.csv')

    detailed_data, index = get_detailed_data(rows)

    draw_diagram(detailed_data, index)


if __name__ == '__main__':
    main()
