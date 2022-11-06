import sys
sys.path.append('..')

from lab3.ftest import ftest
from converter_regex import xml_to_dict

XML1 = """
<a text="meme">q</a>
"""

XML2 = """
<data>
    <numbers>
        <number>128</number>
        <number>3000</number>
        <number>420</number>
        <number>777</number>
    </numbers>
</data>
"""

XML3 = """
<a src="wiki" lol="300">
    <c>QQQ</c>
    <b style="amazing">MEME</b>
    <d>
        <f>q</f>
    </d>
</a>
"""

XML4 = """
<?xml version="1.0" encoding="UTF-8"?>
<timetable>
    <days>
        <day id="3day" name="wednesday">
            <lessons>
                <lesson>
                    <time>11:40-13:10</time>
                    <weeks>
                        <week>2</week>
                        <week>4</week>
                        <week>6</week>
                        <week>8</week>
                        <week>10</week>
                        <week>12</week>
                        <week>14</week>
                        <week>16</week>
                    </weeks>
                    <room>
                        <address>Кронверкский пр., д.49, лит.А</address>
                        <name>2308 (бывш. 306) ауд.</name>
                    </room>
                    <detail>
                        <name>Информатика(Лаб)</name>
                        <teacher>Белозубов Александр Владимирович</teacher>
                        <format>Очно - дистанционный</format>
                    </detail>
                </lesson>
                <lesson>
                    <time>13:30-15:00</time>
                    <weeks>
                        <week>2</week>
                        <week>4</week>
                        <week>6</week>
                        <week>8</week>
                        <week>10</week>
                        <week>12</week>
                        <week>14</week>
                        <week>16</week>
                    </weeks>
                    <room>
                        <address>Кронверкский пр., д.49, лит.А</address>
                        <name>2308 (бывш. 306) ауд.</name>
                    </room>
                    <detail>
                        <name>Информатика(Лаб)</name>
                        <teacher>Белозубов Александр Владимирович</teacher>
                        <format>Очно - дистанционный</format>
                    </detail>
                </lesson>
                <lesson>
                    <time>13:30-15:00</time>
                    <weeks>
                        <week>2</week>
                        <week>4</week>
                        <week>6</week>
                        <week>8</week>
                        <week>10</week>
                        <week>12</week>
                        <week>14</week>
                        <week>16</week>
                    </weeks>
                    <room>
                        <address>Кронверкский пр., д.49, лит.А</address>
                        <name>2308 (бывш. 306) ауд.</name>
                    </room>
                    <detail>
                        <name>Информатика(Лаб)</name>
                        <teacher>Белозубов Александр Владимирович</teacher>
                        <format>Очно - дистанционный</format>
                    </detail>
                </lesson>
                <lesson>
                    <time>15:20-16:50</time>
                    <weeks>
                    </weeks>
                    <room>
                        <address>Кронверкский пр., д.49, лит.А</address>
                        <name>2304 (бывш. 302) ауд.</name>
                    </room>
                    <detail>
                        <name>Дискретная математика (базовый уровень)(лек)</name>
                        <teacher>Поляков Владимир Иванович</teacher>
                        <format>Очно - дистанционный</format>
                    </detail>
                </lesson>
            </lessons>
        </day>
    </days>
</timetable>
"""

XML5 = """
    <bookstore>  
      <book category="COOKING">  
        <title lang="en">Everyday Italian</title>  
        <author>Giada De Laurentiis</author>  
        <year>2005</year>  
        <price>30.00</price>  
      </book>  
      <book category="CHILDREN">  
        <title lang="en">Harry Potter</title>  
        <author>J K. Rowling</author>  
        <year>2005</year>  
        <price>29.99</price>  
      </book>  
      <book category="WEB">  
        <title lang="en">Learning XML</title>  
        <author>Erik T. Ray</author>  
        <year>2003</year>  
        <price>39.95</price>  
      </book>  
    </bookstore>  
"""

XML6 = """
<a><b></a></b>
"""

TEST_CASES = [
    (
        XML1,
        {'a': {'value': 'q', 'attrs': {'text': 'meme'}}},
    ),
    (
        XML2,
        {'data': {'value': {'numbers': {'value': {'number': [{'value': '128', 'attrs': {}}, {'value': '3000', 'attrs': {}}, {'value': '420', 'attrs': {}}, {'value': '777', 'attrs': {}}]}, 'attrs': {}}}, 'attrs': {}}},
    ),
    (
        XML3,
        {'a': {'value': {'c': {'value': 'QQQ', 'attrs': {}}, 'b': {'value': 'MEME', 'attrs': {'style': 'amazing'}}, 'd': {'value': {'f': {'value': 'q', 'attrs': {}}}, 'attrs': {}}}, 'attrs': {'src': 'wiki', 'lol': '300'}}},
    ),
    (
        XML4,
        {'timetable': {'value': {'days': {'value': {'day': {'value': {'lessons': {'value': {'lesson': [{'value': {'time': {'value': '11:40-13:10', 'attrs': {}}, 'weeks': {'value': {'week': [{'value': '2', 'attrs': {}}, {'value': '4', 'attrs': {}}, {'value': '6', 'attrs': {}}, {'value': '8', 'attrs': {}}, {'value': '10', 'attrs': {}}, {'value': '12', 'attrs': {}}, {'value': '14', 'attrs': {}}, {'value': '16', 'attrs': {}}]}, 'attrs': {}}, 'room': {'value': {'address': {'value': 'Кронверкский пр., д.49, лит.А', 'attrs': {}}, 'name': {'value': '2308 (бывш. 306) ауд.', 'attrs': {}}}, 'attrs': {}}, 'detail': {'value': {'name': {'value': 'Информатика(Лаб)', 'attrs': {}}, 'teacher': {'value': 'Белозубов Александр Владимирович', 'attrs': {}}, 'format': {'value': 'Очно - дистанционный', 'attrs': {}}}, 'attrs': {}}}, 'attrs': {}}, {'value': {'time': {'value': '13:30-15:00', 'attrs': {}}, 'weeks': {'value': {'week': [{'value': '2', 'attrs': {}}, {'value': '4', 'attrs': {}}, {'value': '6', 'attrs': {}}, {'value': '8', 'attrs': {}}, {'value': '10', 'attrs': {}}, {'value': '12', 'attrs': {}}, {'value': '14', 'attrs': {}}, {'value': '16', 'attrs': {}}]}, 'attrs': {}}, 'room': {'value': {'address': {'value': 'Кронверкский пр., д.49, лит.А', 'attrs': {}}, 'name': {'value': '2308 (бывш. 306) ауд.', 'attrs': {}}}, 'attrs': {}}, 'detail': {'value': {'name': {'value': 'Информатика(Лаб)', 'attrs': {}}, 'teacher': {'value': 'Белозубов Александр Владимирович', 'attrs': {}}, 'format': {'value': 'Очно - дистанционный', 'attrs': {}}}, 'attrs': {}}}, 'attrs': {}}, {'value': {'time': {'value': '13:30-15:00', 'attrs': {}}, 'weeks': {'value': {'week': [{'value': '2', 'attrs': {}}, {'value': '4', 'attrs': {}}, {'value': '6', 'attrs': {}}, {'value': '8', 'attrs': {}}, {'value': '10', 'attrs': {}}, {'value': '12', 'attrs': {}}, {'value': '14', 'attrs': {}}, {'value': '16', 'attrs': {}}]}, 'attrs': {}}, 'room': {'value': {'address': {'value': 'Кронверкский пр., д.49, лит.А', 'attrs': {}}, 'name': {'value': '2308 (бывш. 306) ауд.', 'attrs': {}}}, 'attrs': {}}, 'detail': {'value': {'name': {'value': 'Информатика(Лаб)', 'attrs': {}}, 'teacher': {'value': 'Белозубов Александр Владимирович', 'attrs': {}}, 'format': {'value': 'Очно - дистанционный', 'attrs': {}}}, 'attrs': {}}}, 'attrs': {}}, {'value': {'time': {'value': '15:20-16:50', 'attrs': {}}, 'weeks': {'value': {}, 'attrs': {}}, 'room': {'value': {'address': {'value': 'Кронверкский пр., д.49, лит.А', 'attrs': {}}, 'name': {'value': '2304 (бывш. 302) ауд.', 'attrs': {}}}, 'attrs': {}}, 'detail': {'value': {'name': {'value': 'Дискретная математика (базовый уровень)(лек)', 'attrs': {}}, 'teacher': {'value': 'Поляков Владимир Иванович', 'attrs': {}}, 'format': {'value': 'Очно - дистанционный', 'attrs': {}}}, 'attrs': {}}}, 'attrs': {}}]}, 'attrs': {}}}, 'attrs': {'id': '3day', 'name': 'wednesday'}}}, 'attrs': {}}}, 'attrs': {}}},
    ),
    (
        XML5,
        {'bookstore': {'value': {'book': [{'value': {'title': {'value': 'Everyday Italian', 'attrs': {'lang': 'en'}}, 'author': {'value': 'Giada De Laurentiis', 'attrs': {}}, 'year': {'value': '2005', 'attrs': {}}, 'price': {'value': '30.00', 'attrs': {}}}, 'attrs': {'category': 'COOKING'}}, {'value': {'title': {'value': 'Harry Potter', 'attrs': {'lang': 'en'}}, 'author': {'value': 'J K. Rowling', 'attrs': {}}, 'year': {'value': '2005', 'attrs': {}}, 'price': {'value': '29.99', 'attrs': {}}}, 'attrs': {'category': 'CHILDREN'}}, {'value': {'title': {'value': 'Learning XML', 'attrs': {'lang': 'en'}}, 'author': {'value': 'Erik T. Ray', 'attrs': {}}, 'year': {'value': '2003', 'attrs': {}}, 'price': {'value': '39.95', 'attrs': {}}}, 'attrs': {'category': 'WEB'}}]}, 'attrs': {}}},
    ),
    (
        XML6,  # error
        None,
    ),
]


def test() -> None:
    ftest(xml_to_dict, TEST_CASES, test_name='Lab 4. Regex XML Parser.')


if __name__ == '__main__':
    test()
