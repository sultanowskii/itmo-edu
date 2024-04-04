from methods.method import Method

from methods.rectangle import RectangleMethod
from methods.trapezoid import TrapezoidMethod
from methods.simpson import SimpsonMethod


METHODS: list[Method] = [
    RectangleMethod(),
    TrapezoidMethod(),
    SimpsonMethod(),
]
