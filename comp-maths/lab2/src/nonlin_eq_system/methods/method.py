from typing import Any

from systems import System

Input = tuple[float, float, float]


class Method:
    name: str

    def read_input(self) -> Input:
        raise NotImplementedError
    
    def get_root_count_in_range(self, system: System, inp: Input) -> bool:
        raise NotImplementedError

    def perform(self, system: System, inp: Input) -> tuple[float, float]:
        raise NotImplementedError
