"""
Метод Симпсона.
"""

from functions import Function
from methods.method import Method


class SimpsonMethod(Method):
    NAME = 'Метод Симпсона.'

    def _perform(self, func: Function, interval_count: int) -> float:
        h = (self.b - self.a) / interval_count

        x_0 = self._get_x(0, h)
        y_0 = func.safe_f(x_0, self.e)

        x_n = self._get_x(interval_count, h)
        y_n = func.safe_f(x_n, self.e)

        odd = sum([
            func.safe_f(self._get_x(i, h), self.e)
            for i in range(1, interval_count, 2)
        ])

        even = sum([
            func.safe_f(self._get_x(i, h), self.e)
            for i in range(2, interval_count - 1, 2)
        ])

        area = (h / 3) * (y_0 + 4 * odd + 2 * even + y_n)

        return area
