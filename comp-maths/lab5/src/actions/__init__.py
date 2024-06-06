from actions.custom import get_custom_function
from actions.presets import get_preset_function

ACTIONS = [
    dict(
        func=get_preset_function,
        name='Выбрать фунцкию',
    ),
    dict(
        func=get_custom_function,
        name='Ввести фунцкию',
    ),
]
