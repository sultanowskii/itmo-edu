"""
Метод половинного сечения.
"""

from functions import Function
from methods.method import Input, Method
from input import read_float_from_stdin


class HalvingMethod(Method):
    name = 'Метод половинного сечения'
    
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

        x = (a + b) / 2
        f_x = func.f(x)
        f_a = func.f(a)

        while True:
            x = (a + b) / 2
            f_x = func.f(x)

            if f_a * f_x < 0:
                b = x
                f_b = func.f(b)
            else:
                a = x
                f_a = func.f(a)

            if abs(f_x) < e:
                return x, f_x
