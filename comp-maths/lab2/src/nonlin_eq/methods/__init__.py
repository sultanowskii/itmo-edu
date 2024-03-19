from methods.halving import HalvingMethod
from methods.iteration import IterationMethod
from methods.method import Method
from methods.secant import SecantMethod


METHODS: list[Method] = [
    IterationMethod(),
    HalvingMethod(),
    SecantMethod(),
]
