from functions import Function
from input import read_float_from_stdin

# Начальное значение числа разбиения интервала интегрирования
N = 4


class Method:
    NAME: str
    e: float
    a: float
    b: float

    def read_input(self):
        self.e = read_float_from_stdin(f'Пожалуйста, введите валидную точность (>0)', lambda e: e > 0)
        self.a = read_float_from_stdin(f'Пожалуйста, введите валидный a')
        self.b = read_float_from_stdin(f'Пожалуйста, введите валидный b (>a)', lambda b: b > self.a)

    def _get_x(self, index, h) -> float:
        return self.a + index * h

    def _perform(self, func: Function, interval_count: int) -> float:
        raise NotImplementedError

    def _is_function_convergent_in_a(self, func: Function):
        return func.is_convergent_in_x(self.a)

    def _is_function_convergent_in_b(self, func: Function):
        return func.is_convergent_in_x(self.b)

    def _is_function_convergent_in_mid(self, func: Function):
        delta = self.e
        x = self.a + delta
        mid = (self.a + self.b) / 2

        if not func.is_convergent_in_x(mid):
            return False

        while x < self.b:
            if not func.is_convergent_in_x(x):
                return False
            x += delta

        return True

    def perform(self, func: Function) -> float:
        if not self._is_function_convergent_in_a(func):
            print('Функция расходится в a.')
            if func.start is not None and self.a <= func.start:
                self.a = func.start + self.e
        if not self._is_function_convergent_in_b(func):
            print('Функция расходится в b.')
            if func.end is not None and self.b >= func.start:
                self.b = func.end - self.e
        if not self._is_function_convergent_in_mid(func):
            print('Функция расходится на отрезке интегрирования.')

        interval_count = N

        prev_area = self._perform(func, interval_count)
        interval_count *= 2
        area = self._perform(func, interval_count)
        cntr = 0

        while prev_area == 0 or abs((prev_area - area) / prev_area) > self.e:
            prev_area = area
            interval_count *= 2
            area = self._perform(func, interval_count)
            cntr += 1
            if cntr >= 10:
                break

        return area
