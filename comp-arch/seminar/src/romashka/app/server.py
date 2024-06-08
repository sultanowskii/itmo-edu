from flask import Flask, request

from app.divisors import find_divisors

app = Flask(__name__)


@app.route('/')
def hello():
    return 'I\'m Romashka!'


@app.route('/divisors')
def divisors():
    n = request.args.get('n')

    if n is None:
        return 'please provide n'

    try:
        n = int(n)
    except:
        return 'please provide an integer'

    divisors = find_divisors(n)

    return ', '.join(list(map(lambda n: str(n), divisors)))
