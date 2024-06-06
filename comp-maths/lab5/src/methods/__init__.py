from methods.lagrange import lagrange
from methods.newton_div import newton_div
from methods.newton_equidistant import newton_equidistant


METHODS = [
    dict(
        name='Многочлен Лагранжа',
        func=lagrange,
        draw=False,
    ),
    dict(
        name='Многочлен Ньютона с разделенными разностями',
        func=newton_div,
        draw=True,
    ),
    dict(
        name='Многочлен Ньютона с конечными разностями',
        func=newton_equidistant,
        draw=True,
    )
]