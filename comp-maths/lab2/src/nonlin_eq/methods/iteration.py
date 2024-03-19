"""
Метод простой итерации.
"""

from functions import Function
from input import read_float_from_stdin
from methods.method import Input, Method


class IterationMethod(Method):
    name = 'Метод простой итерации'
    
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

        x = a

        lbd = (1 / max(abs(func.f_der(a)), abs(func.f_der(b))))

        if func.f_der(x) > 0:
            lbd = -lbd
        
        phi = lambda x: x + lbd * func.f(x)

        while True:
            x_prev = x
            x = phi(x)

            if abs(x - x_prev) <= e:
                return x, func.f(x)
