from typing import Any

from functions import Function

Input = tuple[float, float, float]


class Method:
    name: str

    def read_input(self) -> Input:
        raise NotImplementedError
    
    def get_root_count_in_range(self, func: Function, inp: Input) -> bool:
        raise NotImplementedError

    def perform(self, func: Function, inp: Input) -> tuple[float, float]:
        raise NotImplementedError
