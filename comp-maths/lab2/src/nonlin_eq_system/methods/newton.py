"""
Метод Ньютона.
"""

from systems import System
from input import read_float_from_stdin
from methods.method import Input, Method


class NewtonMethod(Method):
    name = 'Метод Ньютона'

    def read_input(self) -> Input:
        e = read_float_from_stdin(f'Пожалуйста, введите валидную точность (>0)', lambda e: e > 0)
        x0 = read_float_from_stdin(f'Пожалуйста, введите валидный x0')
        y0 = read_float_from_stdin(f'Пожалуйста, введите валидный y0')
        return (e, x0, y0)

    def get_root_count_in_range(self, system: System, inp: Input) -> int:
        return 1

    def perform(self, system: System, inp: Input) -> tuple[float, float]:
        e, x0, y0 = inp

        x_prev = 0
        y_prev = 0
        x = x0
        y = y0

        while True:
            x_prev = x
            y_prev = y
            a = [
                [
                    system.first.f_ders[0](x_prev, y_prev),
                    system.first.f_ders[1](x_prev, y_prev),
                ],
                [
                    system.second.f_ders[0](x_prev, y_prev),
                    system.second.f_ders[1](x_prev, y_prev),
                ],
            ]
            b = [
                system.first.f(x_prev, y_prev),
                system.second.f(x_prev, y_prev),
            ]

            j = a[0][0] * a[1][1] - a[0][1] * a[1][0]
            f = system.first.f(x_prev, y_prev)
            g = system.second.f(x_prev, y_prev)
            delta_x = - (f * a[1][1] - a[0][1] * g) / j
            delta_y = - (a[0][0] * g - f * a[1][0]) / j

            x += delta_x
            y += delta_y

            if abs(x - x_prev) < e and abs(y - y_prev) < e:
                return x, y
