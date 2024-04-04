"""
Метод секущих.
"""

from functions import Function
from input import read_float_from_stdin
from methods.method import Input, Method


class SecantMethod(Method):
    name = 'Метод секущих'

    def read_input(self) -> Input:
        e = read_float_from_stdin(f'Пожалуйста, введите валидную точность (>0)', lambda e: e > 0)
        a = read_float_from_stdin(f'Пожалуйста, введите валидный a')
        b = read_float_from_stdin(f'Пожалуйста, введите валидный b (>a)', lambda b: b > a)
        return (e, a, b)

    def get_root_count_in_range(self, func: Function, inp: Input) -> int:
        _, a, b = inp
        return func.root_count_in_range(a, b)

    def perform(self, func: Function, inp: Input) -> tuple[float, float]:
        e, a, b = inp

        x_preprev = (a + b) / 2
        x_prev = x_preprev + abs(b - a) / 10
        f_preprev = func.f(x_preprev)
        f_prev = func.f(x_prev)

        while True:
            x = x_prev - (x_prev - x_preprev) / (f_prev - f_preprev) * f_prev
            f = func.f(x)

            if abs(f) <= e:
                return x, f

            x_preprev = x_prev
            x_prev = x
            f_preprev = f_prev
            f_prev = f
