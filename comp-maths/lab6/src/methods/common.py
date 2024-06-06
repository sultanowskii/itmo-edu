from typing import Callable

from common import Function, Xs, Ys


MethodAction = Callable[[Function, float, float, float, float], tuple[Xs, Ys]]
