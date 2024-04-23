from dots import Dots
from input import read_choice_from_stdin

PRESETS = [
    dict(
        name='первая',
        dot_list=[
            (3, 3),
            (4, 4),
            (5, 5),
            (6, 6),
            (7, 7),
            (8, 8),
            (9, 9),
        ]
    ),
    dict(
        name='вторая',
        dot_list=[
            (0.0, 0),
            (0.4, 0.952),
            (0.8, 1.8494),
            (1.2, 2.4679),
            (1.6, 2.5366),
            (2.0, 2.1379),
            (2.4, 1.6112),
            (2.8, 1.1656),
            (3.2, 0.8417),
            (3.6, 0.6167),
            (4.0, 0.461),
        ],
    ),
    dict(
        name='третья',
        dot_list=[
            (0.5, 1),
            (1.5, 3),
            (3, 4),
            (3.3, 7),
            (3.9, 10),
            (4.4, 11.1),
        ],
    ),
]


def get_preset_function() -> Dots:
    preset_index = read_choice_from_stdin(
        'Выберите функцию',
        [
            preset['name']
            for preset in PRESETS
        ],
    )

    dot_list = PRESETS[preset_index]['dot_list']
    xs = [x for x, _y in dot_list]
    ys = [y for _x, y in dot_list]

    return Dots(xs, ys)
