"""
Метод градиентного спуска.
"""

from statements import f, gradient, mul_vec2d_by_number, sub_vec2d


def solve(_x1_0: float, _x2_0: float, eps: float) -> tuple[tuple[float, float], float]:
    x_prev = (_x1_0, _x2_0)
    f_prev = f(*x_prev)

    a = 0.5
    print(f'{eps=}')
    print(f'x0 = ({x_prev})')
    print(f'f(x0) = {f_prev}')
    print(f'{a=}')
    print('--start--')

    x = (0, 0)
    f_value = 0

    while True:
        # x = x_prev - a * grad(x)
        print(f'grad = {gradient(*x_prev)}')
        x = sub_vec2d(x_prev, mul_vec2d_by_number(gradient(*x_prev), a))
        print(f'next = {x}')
        f_value = f(*x)
        print(f'f(next) = {f_value}')

        if f_value >= f_prev:
            a = a * 0.9
            print(f'f(next) = {f_value} >= f_prev => уменьшаем a = {a}')

        print(f'|{f_value} - {f_prev}| = {abs(f_value - f_prev)}')
        if abs(f_value - f_prev) <= eps:
            print(f'|f_i - f_{{i-1}}| <= eps => точность достигнута')
            return (x, f_value)

        print(f'|f_i - f_{{i-1}}| > eps => точность не достигнута')

        print('-------')        
        x_prev = x
        f_prev = f_value
