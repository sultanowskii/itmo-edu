from math import sqrt

from common import Function, rounded


Row = list[float]


class Dots:
    def __init__(self, xs: Row, ys: Row) -> None:
        self.xs = xs
        self.ys = ys
        
    def get_n(self) -> int:
        return len(self.xs)

    def get_xs(self) -> Row:
        return self.xs

    def get_ys(self) -> Row:
        return self.ys

    def get_paired(self) -> list[tuple[float, float]]:
        return [dot for dot in zip(self.xs, self.ys)]

    def get_phis(self, phi: Function) -> list[float]:
        return [rounded(phi(x)) for x in self.get_xs()]

    def get_epsilons(self, phi: Function) -> list[float]:
        return [rounded(phi_i - y) for phi_i, y in zip(self.get_phis(phi), self.get_ys())]
    
    def get_deviation(self, phi: Function) -> float:
        epsilons = self.get_epsilons(phi)
        return rounded(sqrt(sum(list(map(lambda epsilon: rounded(epsilon ** 2), epsilons))) / self.get_n()))

    def get_dot_at(self, i: int) -> tuple[float, float]:
        return (self.xs[i], self.ys[i])

    def _sxnym(self, n: int = 0, m: int = 0) -> float:
        return sum(list(map(lambda dot: dot[0] ** n * dot[1] ** m, self.get_paired())))

    def _sxn(self, n: int) -> float:
        return self._sxnym(n, 0)

    def _sym(self, m: int) -> float:
        return self._sxnym(0, m)

    def sx(self) -> float:
        return self._sxn(1)

    def sxx(self) -> float:
        return self._sxn(2)

    def sx3(self) -> float:
        return self._sxn(3)

    def sx4(self) -> float:
        return self._sxn(4)

    def sx5(self) -> float:
        return self._sxn(5)

    def sx6(self) -> float:
        return self._sxn(6)
    
    def sy(self) -> float:
        return self._sym(1)

    def sxy(self) -> float:
        return self._sxnym(1, 1)
    
    def sx2y(self) -> float:
        return self._sxnym(2, 1)
    
    def sx3y(self) -> float:
        return self._sxnym(3, 1)

    def all_xs_exp_safe(self) -> bool:
        return all(map(lambda x: x > 0 and x != 1, self.xs))

    def all_ys_exp_safe(self) -> bool:
        return all(map(lambda y: y > 0 and y != 1, self.ys))
