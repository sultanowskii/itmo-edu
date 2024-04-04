"""
Метод трапеций.
"""

from functions import Function
from methods.method import Method


class TrapezoidMethod(Method):
    NAME = 'Метод трапеций.'

    def _perform(self, func: Function, interval_count: int) -> float:
        h = (self.b - self.a) / interval_count

        x_0 = self._get_x(0, h)
        y_0 = func.safe_f(x_0, self.e)

        x_n = self._get_x(interval_count, h)
        y_n = func.safe_f(x_n, self.e)

        middle = sum([
            func.safe_f(self._get_x(i, h), self.e)
            for i in range(1, interval_count)
        ])

        area = h * ((y_0 + y_n) / 2 + middle)

        return area
