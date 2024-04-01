"""
Метод наискорейшего спуска.
"""

from statements import sub_vec2d, div_vec2d_by_number, f, generate_f, gradient, mul_vec2d_by_number, vec2d_magnitude
from segment_halving import solve as minimize_1_argument_function


def solve(_x1_0: float, _x2_0: float, eps: float) -> tuple[tuple[float, float], float]:
    x = (_x1_0, _x2_0)
    x_prev = (0, 1e100)
    x_preprev = (1e100, 0)
    f_value = f(*x)
    print(f'x1 = {x}')
    print(f'{eps=}')
    print('--start--')

    while True:
        x_grad = gradient(*x)
        x_grad_magnitude = vec2d_magnitude(x_grad)
        if x_grad_magnitude < eps:
            print(f'||𝛻f|| (={x_grad_magnitude}) < eps (={eps}) => точность достигнута')
            return (x, f_value)
        print(f'||𝛻f|| (={x_grad_magnitude}) >= eps (={eps}) => точность не достигнута')

        s = div_vec2d_by_number(x_grad, x_grad_magnitude)
        print(f'S_k = {s}')

        q1 = lambda h: x[0] - h * s[0]
        q2 = lambda h: x[1] - h * s[1]

        print(f'dfx1 = {x_grad[0]}')
        print(f'dfx2 = {x_grad[1]}')

        print(f'x1 = x1_prev - h * s = {x[0]} - h * {s[0]}')
        print(f'x2 = x2_prev - h * s = {x[1]} - h * {s[1]}')

        f_on_h = generate_f(q1, q2)

        h_min, _ = minimize_1_argument_function(f_on_h, -50, 50, 0.000001)
        print(f'{h_min=}')

        x = sub_vec2d(x, mul_vec2d_by_number(s, h_min))
        print(f'x = (x1, x2) = {x}')
        f_value = f(*x)
        print(f'f(x) = {f_value}')
        print('---------')

        if x == x_prev or x == x_preprev:
            return (x, f_value)

        x_preprev = x_prev
        x_prev = x
