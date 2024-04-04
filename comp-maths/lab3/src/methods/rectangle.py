"""
Метод прямоугольников.
"""

from functions import Function
from methods.method import Method
from input import read_choice_from_stdin

SUBMETHODS = [
    dict(
        name='Левые',
        starting_index=lambda a, h: a,
    ),
    dict(
        name='Средние',
        starting_index=lambda a, h: a + h / 2,
    ),
    dict(
        name='Правые',
        starting_index=lambda a, h: a + h,
    ),
]


class RectangleMethod(Method):
    NAME = 'Метод прямоугольников.'
    submethod_index: float

    def read_input(self):
        super().read_input()
        self.submethod_index = read_choice_from_stdin(
            'Выберите под-метод:',
            [sm['name'] for sm in SUBMETHODS],
        )

    def _perform(self, func: Function, interval_count: int) -> float:
        h = (self.b - self.a) / interval_count

        area = 0
        x = SUBMETHODS[self.submethod_index]['starting_index'](self.a, h)

        for _ in range(interval_count):
            y = func.safe_f(x, self.e)

            area += y * h

            x += h

        return area
