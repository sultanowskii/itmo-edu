FROM python:3.11

RUN mkdir /www
ADD . /www

RUN groupadd --gid 3000 dolphin
RUN useradd --uid 3000 --gid dolphin --shell /bin/bash --create-home --home-dir /home/dolphin dolphin
RUN chown -R dolphin:dolphin /www

USER dolphin

WORKDIR /www

RUN python -m pip install -r requirements.txt

CMD python -m flask --app app.server run -h 0.0.0.0 -p 8000 --no-debugger
