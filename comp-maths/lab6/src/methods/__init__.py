from typing import Callable, Optional
from common import Function
from methods.common import MethodAction
from methods.euler import get_euler_precision, euler
from methods.improved_euler import get_improved_euler_precision, improved_euler
from methods.milne import milne

PrecisionGetter = Callable[[Function, float, float, float, float, float], float]

METHODS: dict[str, str | bool | MethodAction | Optional[PrecisionGetter]] = [
    dict(
        name='Метод Эйлера',
        one_step=True,
        action=euler,
        get_precision=get_euler_precision,
    ),
    dict(
        name='Усовершенствованный метод Эйлера',
        one_step=True,
        action=improved_euler,
        get_precision=get_improved_euler_precision,
    ),
    dict(
        name='Метод Милна (пошаговый)',
        one_step=False,
        action=milne,
    )
]
