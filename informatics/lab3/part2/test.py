import sys
sys.path.append('..')

from part2.word_finder import find_all_special_words
from ftest import ftest


TEST_CASES = [
    (
        'Кривошеее существо гуляет по парку',
        ['гуляет'],
    ),
    (
        'Кривошеее нечто гуляет по парку',
        ['Кривошеее', 'гуляет'],
    ),
    (
        'Кривошеее! существо, гуляет. по: парку?',
        ['гуляет'],
    ),
    (
        'гуляет',
        [],
    ),
    (
        'aaa. eee, iii: ooo: uuu; ioi! eoe? aia (oua) uai \'aee\" uue',
        ['aaa', 'eee', 'iii', 'ooo', 'uuu', 'ioi', 'eoe', 'aia', 'oua', 'uai', 'aee'],
    ),
    (
        'КрИвОшЕеЕ сУщЕсТвО гУлЯеТ пО пАрКу',
        ['гУлЯеТ'],
    ),
]


def test() -> None:
    ftest(find_all_special_words, TEST_CASES, test_name='Lab 3. Special word counter')


if __name__ == '__main__':
    test()
